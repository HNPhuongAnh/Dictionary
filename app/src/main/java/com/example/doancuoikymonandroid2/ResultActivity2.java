package com.example.doancuoikymonandroid2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoikymonandroid2.DictionaryAPI;
import com.example.doancuoikymonandroid2.DictionaryEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultActivity2 extends AppCompatActivity {
    private TextView resultTextView;
    private DictionaryAPI dictionaryAPI;
    private FirebaseAuth mAuth;
    private ImageButton btnLike, backButton;
    private String selectedWord;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ResultActivity2", "onCreate() is called");
        Log.e("Tag", "Error message");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        backButton = findViewById(R.id.backButton2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        resultTextView = findViewById(R.id.resultTextView);
        btnLike = findViewById(R.id.btnLike2);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Nhận dữ liệu từ Intent
        String selectedWord = getIntent().getStringExtra("WORD");

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo đối tượng API
        dictionaryAPI = retrofit.create(DictionaryAPI.class);

        // Gửi yêu cầu API để lấy chi tiết về từ selectedWord
        getWordDetails(selectedWord);
        if (currentUser != null) {
            String email = currentUser.getEmail();
            Toast.makeText(ResultActivity2.this, "Current user: " + email, Toast.LENGTH_SHORT).show();
        } else {
        }
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("favorites");

        if (currentUser != null) {
            String email = currentUser.getEmail();

            favoritesRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                boolean isLiked = false;

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String word = snapshot.child("word").getValue(String.class);
                            if (word != null && word.equals(selectedWord)) {
                                btnLike.setImageResource(R.drawable.ic_baseline_star_36);
                                btnLike.setTag(R.drawable.ic_baseline_star_36);
                                isLiked = true;
                                break;
                            }
                        }
                    }

                    if (!isLiked) {
                        btnLike.setImageResource(R.drawable.ic_baseline_star_outline_36);
                        btnLike.setTag(R.drawable.ic_baseline_star_outline_36);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        btnLike.setOnClickListener(new View.OnClickListener() {
            boolean isLiked = false;

            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    isLiked = !isLiked;
                    if (isLiked) {
                        if (btnLike.getTag().equals(R.drawable.ic_baseline_star_outline_36)) {
                            String favoriteWord = selectedWord;
                            addWordToNewFavorites(favoriteWord);
                            btnLike.setImageResource(R.drawable.ic_baseline_star_36);
                            btnLike.setTag(R.drawable.ic_baseline_star_36);
                        } else {
                            removeWordFromFavorites();
                        }
                    } else {
                        btnLike.setImageResource(R.drawable.ic_baseline_star_outline_36);
                        btnLike.setTag(R.drawable.ic_baseline_star_outline_36);
                    }
                } else {
                    Toast.makeText(ResultActivity2.this, "Please log in to add/remove word from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getWordDetails(String word) {
        Call<List<DictionaryEntry>> call = dictionaryAPI.getWordDetails(word);
        call.enqueue(new Callback<List<DictionaryEntry>>() {
            @Override
            public void onResponse(Call<List<DictionaryEntry>> call, Response<List<DictionaryEntry>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        DictionaryEntry entry = response.body().get(0); // Lấy entry đầu tiên trong danh sách
                        // Hiển thị thông tin trong resultTextView
                        displayWordDetails(entry);
                    } else {
                        // Không có dữ liệu trả về
                        // Giữ nguyên văn bản hiện tại trong resultTextView
                    }
                } else {
                    // Xử lý khi có lỗi kết nối
                    // Giữ nguyên văn bản hiện tại trong resultTextView
                }
            }

            @Override
            public void onFailure(Call<List<DictionaryEntry>> call, Throwable t) {
                // Xử lý khi có lỗi kết nối
                // Giữ nguyên văn bản hiện tại trong resultTextView
            }
        });
    }

    private void displayWordDetails(DictionaryEntry entry) {
        String displayText = "Word: " + entry.getWord() + "\n";

        // Hiển thị các ý nghĩa và định nghĩa
        List<DictionaryEntry.Meaning> meanings = entry.getMeanings();
        if (meanings != null && !meanings.isEmpty()) {
            for (DictionaryEntry.Meaning meaning : meanings) {
                displayText += "\nPart of Speech: " + meaning.getPartOfSpeech() + "\n";

                List<DictionaryEntry.Meaning.Definition> definitions = meaning.getDefinitions();
                if (definitions != null && !definitions.isEmpty()) {
                    for (DictionaryEntry.Meaning.Definition definition : definitions) {
                        displayText += " - Definition: " + definition.getDefinition() + "\n";
                        if (definition.getExample() != null && !definition.getExample().isEmpty()) {
                            displayText += "   Example: " + definition.getExample() + "\n";
                        }
                    }
                }
            }
        }

        resultTextView.setText(displayText);
    }

    private void addWordToNewFavorites(String word) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String email = currentUser.getEmail();

            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("favorites");
            String favoriteId = favoritesRef.push().getKey();

            if (favoriteId != null) {
                DatabaseReference favoriteItemRef = favoritesRef.child(favoriteId);

                favoriteItemRef.child("email").setValue(email);
                favoriteItemRef.child("word").setValue(word)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ResultActivity2.this, "Word added to new favorites", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ResultActivity2.this, "Failed to add word to new favorites", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            Toast.makeText(ResultActivity2.this, "Please log in to add word to favorites", Toast.LENGTH_SHORT).show();
        }
    }


    private void removeWordFromFavorites() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String email = currentUser.getEmail();

            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("favorites");

            favoritesRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String word = snapshot.child("word").getValue(String.class);
                            String key = snapshot.getKey();
                            if (word != null && word.equals(selectedWord)) {
                                favoritesRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ResultActivity2.this, "Word removed from favorites", Toast.LENGTH_SHORT).show();
                                        btnLike.setImageResource(R.drawable.ic_baseline_star_outline_36);
                                        btnLike.setTag(R.drawable.ic_baseline_star_outline_36);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ResultActivity2.this, "Failed to remove word from favorites", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            Toast.makeText(ResultActivity2.this, "Please log in to remove word from favorites", Toast.LENGTH_SHORT).show();
        }
    }
}


