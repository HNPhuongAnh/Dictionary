package com.example.doancuoikymonandroid2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultsActivity extends AppCompatActivity {
    private DictionaryAPI dictionaryAPI;
    private AutoCompleteTextView autoCompleteTextView;
    private SearchView searchView;
    private Button removeButton;
    private ListView searchHistoryListView;
    private ArrayAdapter<String> searchHistoryAdapter;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        searchView = findViewById(R.id.searchView);
        removeButton = findViewById(R.id.removeButton);
        searchHistoryListView = findViewById(R.id.searchHistoryListView);
        backButton = findViewById(R.id.backButton);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dictionaryAPI = retrofit.create(DictionaryAPI.class);

        setupAutoCompleteTextView();
        setupSearchView();
        setupSearchHistoryListView();

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSearchHistory();
                updateListView();
            }
        });
        // Ánh xạ nút backButton và thiết lập sự kiện onClickListener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupAutoCompleteTextView() {
        String[] keywordList = {"Apple", "Animal", "Adventure", "Amazing", "Active", "Architecture", "Accurate", "Atmosphere", "Artistic", "Access",
                "Beautiful", "Bicycle", "Balance", "Book", "Blue", "Brave", "Business", "Breakfast", "Bridge", "Brilliant",
                "C", "Cat","Calm","Curious","Clever","Cheerful","Charming","Courageous","Candid","Capable",
                "D","Delightful","Dynamic", "Dazzling", "Daring", "Dapper", "Dashing","Divine", "Dreamy", "Dependable", "Diligent",
                "E","Energetic", "Enthusiastic", "Empathetic", "Expressive", "Efficient", "Eager", "Easygoing", "Elegant", "Endearing", "Educated",
                "F","Friendly", "Fearless", "Fantastic", "Fascinating", "Fierce", "Frank", "Funny", "Futuristic", "Flexible", "Faithful",
                "G","Graceful", "Genuine", "Gregarious", "Glowing", "Gutsy", "Generous", "Gracious", "Grounded", "Gentle", "Genuine",
                "H", "Hello", "Hey","Happy", "Healthy", "Harmonious", "Hopeful", "Helpful", "Hardworking", "Honest", "Humorous", "Humble",
                "I","Innovative", "Inspiring", "Intelligent", "Inquisitive", "Inclusive", "Independent", "Inventive",
                "J","Joyful", "Jovial", "Judicious", "Jubilant", "Just", "Jocular",
                "K","Kind", "Knowledgeable", "Keen", "Kaleidoscopic",
                "L","Lively", "Loving", "Loyal", "Luminous", "Limitless",
                "M", "Mindful", "Motivated", "Magnificent", "Majestic", "Mirthful", "Modest",
                "N","Nurturing", "Nonjudgmental", "Nifty", "Noble",
                "O", "Optimistic", "Open-minded", "Outgoing", "Original",
                "P", "Positive", "Playful", "Peaceful", "Passionate", "Polite",
                "Q","Quirky", "Quick-witted", "Quiet",
                "R", "Radiant", "Resilient", "Resourceful", "Reverent",
                "S", " Sorry", "Sincere", "Supportive", "Spontaneous", "Steadfast", "Serene",
                "T","Thoughtful", "Tolerant", "Trustworthy", "Thankful",
                "U","Understanding", "Upbeat", "Unique",
                "V","Vibrant", "Vivacious", "Versatile",
                "W", "Witty", "Wise", "Warmhearted", "Welcoming",
                "X","Xenial",
                "Y","Youthful", "Yummy",
                "Z", "Zesty"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, keywordList);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedKeyword = (String) parent.getItemAtPosition(position);
            passSelectedKeyword(selectedKeyword);
            getWordDetails(selectedKeyword);
        });

        autoCompleteTextView.setThreshold(0);

        // Lấy EditText từ AutoCompleteTextView
        EditText autoCompleteEditText = autoCompleteTextView;

        // Thêm InputFilter để chỉ cho nhập chữ
        autoCompleteEditText.setFilters(new InputFilter[] {
                (source, start, end, dest, dstart, dend) -> {
                    for (int i = start; i < end; i++) {
                        if (!Character.isLetter(source.charAt(i))) {
                            // Hiển thị thông báo nếu người dùng nhập số
                            Toast.makeText(SearchResultsActivity.this, "Please enter only letters, not numbers..", Toast.LENGTH_SHORT).show();
                            return "";
                        }
                    }
                    return null;
                }
        });
    }

    private void passSelectedKeyword(String selectedKeyword) {
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SEARCHVIEW_WORD", selectedKeyword);
        editor.apply();

        updateSearchHistory(selectedKeyword);
    }

    private void setupSearchView() {
        // Lấy EditText từ SearchView
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        // Thêm InputFilter để chỉ cho nhập chữ
        searchEditText.setFilters(new InputFilter[] {
                (source, start, end, dest, dstart, dend) -> {
                    for (int i = start; i < end; i++) {
                        if (!Character.isLetter(source.charAt(i))) {
                            // Hiển thị thông báo nếu người dùng nhập số
                            Toast.makeText(SearchResultsActivity.this, "Please enter only letters, not numbers..", Toast.LENGTH_SHORT).show();
                            return "";
                        }
                    }
                    return null;
                }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Kiểm tra và thông báo khi người dùng nhập số
                if (!containsOnlyLetters(query)) {
                    Toast.makeText(SearchResultsActivity.this, "Please enter only letters, not numbers..", Toast.LENGTH_SHORT).show();
                    return false;
                }

                SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("SEARCHVIEW_WORD", query);
                editor.apply();

                getWordDetails(query);
                updateSearchHistory(query);

                // Xóa nội dung trong SearchView
                searchView.setQuery("", false);
                searchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // Kiểm tra xem chuỗi chỉ chứa chữ cái hay không
    private boolean containsOnlyLetters(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private void setupSearchHistoryListView() {
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String savedKeywords = preferences.getString("SAVED_KEYWORDS", "");

        String[] keywordArray = savedKeywords.split(",");
        List<String> keywordList = new ArrayList<>(Arrays.asList(keywordArray));

        searchHistoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, keywordList);
        searchHistoryListView.setAdapter(searchHistoryAdapter);

        searchHistoryListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedKeyword = (String) parent.getItemAtPosition(position);
            getWordDetails(selectedKeyword);
        });
    }

    private void updateSearchHistory(String newKeyword) {
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String savedKeywords = preferences.getString("SAVED_KEYWORDS", "");

        // Kiểm tra xem từ khóa đã có trong danh sách chưa
        if (!isKeywordInList(newKeyword, preferences)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("SEARCHVIEW_WORD", newKeyword);
            editor.putString("SAVED_KEYWORDS", savedKeywords + "," + newKeyword);
            editor.apply();

            // Cập nhật danh sách từ khóa
            searchHistoryAdapter.add(newKeyword);
            searchHistoryAdapter.notifyDataSetChanged();
        }
    }

    private boolean isKeywordInList(String keyword, SharedPreferences preferences) {
        String savedKeywords = preferences.getString("SAVED_KEYWORDS", "");
        String[] keywordArray = savedKeywords.split(",");
        return Arrays.asList(keywordArray).contains(keyword);
    }

    private void getWordDetails(String word) {
        Call<List<DictionaryEntry>> call = dictionaryAPI.getWordDetails(word);
        call.enqueue(new Callback<List<DictionaryEntry>>() {
            @Override
            public void onResponse(Call<List<DictionaryEntry>> call, Response<List<DictionaryEntry>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    DictionaryEntry entry = response.body().get(0);

                    if (entry != null) {
                        // Lưu thông tin từ API vào SharedPreferences
                        saveApiResultToSharedPreferences(entry);

                        List<DictionaryEntry.Phonetic> phonetics = entry.getPhonetics();

                        if (phonetics != null && !phonetics.isEmpty()) {
                            String audioPath = phonetics.get(0).getAudio();

                            if (audioPath != null) {
                                Intent intent = new Intent(SearchResultsActivity.this, ResultActivity.class);
                                intent.putExtra("WORD_DETAILS", formatWordDetails(entry));
                                intent.putExtra("AUDIO_PATH", audioPath);
                                startActivity(intent);
                            } else {
                                Log.e("API Error", "Audio path is null");
                            }
                        } else {
                            Log.e("API Error", "Phonetics list is null or empty");
                        }
                    } else {
                        Log.e("API Error", "Response body is null or empty");
                    }
                } else {
                    Log.e("API Error", "Error: " + response.message());
                    Toast.makeText(SearchResultsActivity.this, "Không tìm thấy từ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DictionaryEntry>> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(SearchResultsActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveApiResultToSharedPreferences(DictionaryEntry entry) {
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Lấy danh sách các từ khóa đã tìm kiếm trước đó
        String savedKeywords = preferences.getString("SAVED_KEYWORDS", "");

        // Thêm từ khóa mới vào danh sách (nếu chưa có)
        if (!isKeywordInList(entry.getWord(), preferences)) {
            savedKeywords += entry.getWord() + ",";
            editor.putString("SAVED_KEYWORDS", savedKeywords);
        }

        // Lưu thông tin từ API
        editor.putString("WORD_DETAILS", formatWordDetails(entry));
        editor.apply();
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

        // Sử dụng Html.fromHtml để hiển thị văn bản được định dạng trong TextView
        return details.toString();
    }

    private void clearSearchHistory() {
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("SAVED_KEYWORDS");
        editor.apply();

        // Xóa danh sách từ khóa hiển thị trên ListView
        searchHistoryAdapter.clear();
        searchHistoryAdapter.notifyDataSetChanged();
    }

    private void updateListView() {
        // Không cần thêm gì ở đây vì đã được xử lý trong phương thức clearSearchHistory
    }
}

