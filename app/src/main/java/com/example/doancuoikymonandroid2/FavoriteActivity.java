package com.example.doancuoikymonandroid2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.doancuoikymonandroid2.CustomAdapterFav;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    ListView lvFav;
    DatabaseReference reference;
    ArrayList<String> favoriteWordsList;
    ArrayAdapter<String> adapter;
    DatabaseReference favoritesRef;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        lvFav = findViewById(R.id.lvFav);
        favoriteWordsList = new ArrayList<>();
        AppCompatImageButton backButton = findViewById(R.id.btnBackFav);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("favorites");
            adapter = new CustomAdapterFav(this, favoriteWordsList, favoritesRef);
            lvFav.setAdapter(adapter);

            favoritesRef.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    favoriteWordsList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String word = snapshot.child("word").getValue(String.class);
                        if (word != null) {
                            favoriteWordsList.add(word);
                        } else {
                            Toast.makeText(FavoriteActivity.this, "Word is null", Toast.LENGTH_SHORT).show();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }




        SearchView searchView = findViewById(R.id.FavSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    adapter = new CustomAdapterFav(FavoriteActivity.this, favoriteWordsList, favoritesRef);
                    lvFav.setAdapter(adapter);
                } else {
                    ArrayList<String> filteredList = new ArrayList<>();

                    for (String word : favoriteWordsList) {
                        if (word.toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(word);
                        }
                    }
                    adapter = new CustomAdapterFav(FavoriteActivity.this, filteredList, favoritesRef);
                    lvFav.setAdapter(adapter);
                }
                return true;
            }
        });



    }



}