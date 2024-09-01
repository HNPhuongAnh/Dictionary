package com.example.doancuoikymonandroid2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity {
    private TextView resultTextView;
    private ImageButton audioButton;
    private ImageButton backButton;
    private DatabaseReference favoritesReference;
    private FirebaseAuth mAuth;
    private String searchedWord;
    private ImageButton btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setMovementMethod(new ScrollingMovementMethod());
        audioButton = findViewById(R.id.audioButton);
        backButton = findViewById(R.id.backButton);

        btnLike = findViewById(R.id.btnLike);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Intent intent = getIntent();
        if (intent != null) {
            String formattedDetails = getIntent().getStringExtra("WORD_DETAILS");

            resultTextView.setText(Html.fromHtml(formattedDetails));
            resultTextView.setTextSize(24);
            // Lấy đường dẫn audio từ Intent
            String audioPath = intent.getStringExtra("AUDIO_PATH");
            // Ánh xạ nút backButton và thiết lập sự kiện onClickListener
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            // Kiểm tra nếu có đường dẫn audio, hiển thị nút audioButton và thiết lập sự kiện
            if (audioPath != null && !audioPath.isEmpty()) {
                audioButton.setVisibility(View.VISIBLE);

                audioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Thực hiện logic phát audio ở đây, có thể sử dụng MediaPlayer hoặc thư viện khác
                        // Ví dụ: sử dụng MediaPlayer
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(audioPath);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                // Nếu không có đường dẫn audio, ẩn nút audioButton
                audioButton.setVisibility(View.GONE);
            }

        }

        Intent intent2 = getIntent();
        if (intent2 != null) {
            SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
            searchedWord = preferences.getString("SEARCHVIEW_WORD", "searchedWord");
            Toast.makeText(ResultActivity.this, "word: " + searchedWord, Toast.LENGTH_SHORT).show();
        }


        if (currentUser != null) {
            String email = currentUser.getEmail();
            Toast.makeText(ResultActivity.this, "Current user: " + email, Toast.LENGTH_SHORT).show();
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
                            if (word != null && word.equals(searchedWord)) {
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
                            String favoriteWord = searchedWord;
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
                    Toast.makeText(ResultActivity.this, "Please log in to add/remove word from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });



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
                                Toast.makeText(ResultActivity.this, "Word added to new favorites", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ResultActivity.this, "Failed to add word to new favorites", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            Toast.makeText(ResultActivity.this, "Please log in to add word to favorites", Toast.LENGTH_SHORT).show();
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
                            if (word != null && word.equals(searchedWord)) {
                                favoritesRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ResultActivity.this, "Word removed from favorites", Toast.LENGTH_SHORT).show();
                                        btnLike.setImageResource(R.drawable.ic_baseline_star_outline_36);
                                        btnLike.setTag(R.drawable.ic_baseline_star_outline_36);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ResultActivity.this, "Failed to remove word from favorites", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ResultActivity.this, "Please log in to remove word from favorites", Toast.LENGTH_SHORT).show();
        }
    }
}






