package com.example.doancuoikymonandroid2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavSearchResultsActivity extends AppCompatActivity {
    private DictionaryAPI dictionaryAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        dictionaryAPI = retrofit.create(DictionaryAPI.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("SELECTED_WORD")) {
            String selectedWord = intent.getStringExtra("SELECTED_WORD");
            Toast.makeText(this, "Received word: " + selectedWord, Toast.LENGTH_SHORT).show();
            getWordDetails(selectedWord);


            Intent intent2 = new Intent(FavSearchResultsActivity.this, FavResultActivity.class);
            intent2.putExtra("SELECTED_WORD", selectedWord);
            startActivity(intent2);
            finish();
        } else {
            Toast.makeText(this, "No searched word found", Toast.LENGTH_SHORT).show();
        }
    }


    private void getWordDetails(String word) {
        Call<List<DictionaryEntry>> call = dictionaryAPI.getWordDetails(word);
        call.enqueue(new Callback<List<DictionaryEntry>>() {
            @Override
            public void onResponse(Call<List<DictionaryEntry>> call, Response<List<DictionaryEntry>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Log.d("API Response", "Received data: " + response.body().toString());
                    DictionaryEntry entry = response.body().get(0);

                    if (entry != null) {
                        Log.d("API Response", "Entry details: " + entry.toString());
                        List<DictionaryEntry.Phonetic> phonetics = entry.getPhonetics();

                        if (phonetics != null && !phonetics.isEmpty()) {
                            String audioPath = phonetics.get(0).getAudio();
                            Log.d("API Response", "Entry: " + entry.toString());
                            if (audioPath != null) {
                                SharedPreferences preferences = getSharedPreferences("myPrefsFav", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("WORD_DETAILS_FAV", formatWordDetails(entry));
                                editor.putString("AUDIO_PATH_FAV", audioPath);
                                editor.apply();
                            } else {
                                Log.e("API Error", "Audio path is null");
                            }
                        } else {
                            Log.e("API Error", "Phonetics list is null or empty");
                        }
                    } else {
                        Log.e("API Error", "Response body is null or empty");
                        Log.e("API Error", "Empty response body");
                    }
                } else {
                    Log.e("API Error", "Response not successful: " + response.message());
                    Toast.makeText(FavSearchResultsActivity.this, "Không tìm thấy từ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DictionaryEntry>> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(FavSearchResultsActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formatWordDetails(DictionaryEntry entry) {
        StringBuilder details = new StringBuilder();
        details.append("<b>Word:</b> ").append(entry.getWord()).append("<br>");
        details.append("<b>Phonetic:</b> ").append(entry.getPhonetic()).append("<br>");
        details.append("<b>Origin:</b> ").append(entry.getOrigin()).append("<br>");

        details.append("<br><b>Phonetics:</b><br>");
        for (DictionaryEntry.Phonetic phonetic : entry.getPhonetics()) {
            details.append("- <b>Text:</b> ").append(phonetic.getText()).append("<br>");
        }

        details.append("<br><b>Meanings:</b><br>");
        for (DictionaryEntry.Meaning meaning : entry.getMeanings()) {
            details.append("- <b>Part of Speech:</b> ").append(meaning.getPartOfSpeech()).append("<br>");
            details.append("  <b>Definitions:</b><br>");
            for (DictionaryEntry.Meaning.Definition definition : meaning.getDefinitions()) {
                details.append("  - <b>Definition:</b> ").append(definition.getDefinition()).append("<br>");
                details.append("    <b>Example:</b> ").append(definition.getExample()).append("<br>");
            }
        }

        return details.toString();
    }
}

