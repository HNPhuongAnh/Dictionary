package com.example.doancuoikymonandroid2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    private SearchView searchView;
    private DictionaryAPI dictionaryAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchView = findViewById(R.id.SearchView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dictionaryAPI = retrofit.create(DictionaryAPI.class);


        setupSearchView();


        MaterialButton btnSearchResults = findViewById(R.id.btnSearched_result);
        btnSearchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchResultsActivity();
            }
        });
        MaterialButton btnYour_Vocabulary = findViewById(R.id.btnYour_Vocabulary);
        btnYour_Vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, FavoriteActivity.class);
                String user = getIntent().getStringExtra("user");
                startActivity(a);
            }
        });
        MaterialButton btnDictionary = findViewById(R.id.btnDictionary);
        btnDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDictionaryActivity();
            }
        });

        Button btnTestApp = (Button) findViewById(R.id.btnTestApp);
        btnTestApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuizMenu.class));
                finish();
            }
        });

        MaterialButton btnAccount = (MaterialButton) findViewById(R.id.btnAccount);
//        btnAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent a = new Intent(MainActivity.this, UserAccount.class);
//                String user = getIntent().getStringExtra("user");
//                startActivity(a);
//            }
//        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    Intent intent = new Intent(MainActivity.this, Openning.class);
                    startActivity(intent);
                    finish();
                } else {
                    btnAccount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent a = new Intent(MainActivity.this, UserAccount.class);
                            startActivity(a);
                        }
                    });
                }
            }
        };
        mAuth.addAuthStateListener(authStateListener);
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void openSearchResultsActivity() {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);
    }

    private void openDictionaryActivity() {
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);
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
                            Toast.makeText(MainActivity.this, "Please enter only letters, not numbers..", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "Please enter only letters, not numbers..", Toast.LENGTH_SHORT).show();
                    return false;
                }

                SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("SEARCHVIEW_WORD", query);
                editor.apply();

                getWordDetails(query);


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
    private void getWordDetails(String word) {
        Call<List<DictionaryEntry>> call = dictionaryAPI.getWordDetails(word);
        call.enqueue(new Callback<List<DictionaryEntry>>() {
            @Override
            public void onResponse(Call<List<DictionaryEntry>> call, Response<List<DictionaryEntry>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    DictionaryEntry entry = response.body().get(0);

                    if (entry != null) {
                        List<DictionaryEntry.Phonetic> phonetics = entry.getPhonetics();

                        if (phonetics != null && !phonetics.isEmpty()) {
                            String audioPath = phonetics.get(0).getAudio();

                            if (audioPath != null) {
                                // Tạo Intent và chuyển hướng sang ResultActivity
                                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
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
                    // Xử lý khi không tìm thấy từ
                    // Ví dụ: hiển thị Toast thông báo
                    Toast.makeText(MainActivity.this, "Không tìm thấy từ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DictionaryEntry>> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                // Xử lý khi có lỗi kết nối
                // Ví dụ: hiển thị Toast thông báo
                Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
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

        // Sử dụng Html.fromHtml để hiển thị văn bản được định dạng trong TextView
        return details.toString();
    }
}


