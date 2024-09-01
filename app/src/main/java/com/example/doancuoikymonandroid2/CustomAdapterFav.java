package com.example.doancuoikymonandroid2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomAdapterFav extends ArrayAdapter<String> {
    private ArrayList<String> favoriteWordsList;
    private LayoutInflater inflater;
    private DatabaseReference favoritesRef;

    public CustomAdapterFav(Context context, ArrayList<String> favoriteWordsList, DatabaseReference favoritesRef) {
        super(context, R.layout.layout_listview, favoriteWordsList);
        this.favoriteWordsList = favoriteWordsList;
        this.inflater = LayoutInflater.from(context);
        this.favoritesRef = favoritesRef;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_listview, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.tvWordFav);
        ImageButton starButton = convertView.findViewById(R.id.btnLike2);
        String word = favoriteWordsList.get(position);
        textView.setText(word);

        starButton.setOnClickListener(v -> {
            String selectedWord = favoriteWordsList.get(position);
            removeFromFavorites(selectedWord);
        });

        textView.setOnClickListener(v -> {
            String selectedWord = favoriteWordsList.get(position);
            launchResultActivity(selectedWord);
        });

        return convertView;
    }

    private void showToast(String word) {
        Toast.makeText(getContext(), "Selected word: " + word, Toast.LENGTH_SHORT).show();
    }

    private void launchResultActivity(String word) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent(getContext(), FavSearchResultsActivity.class);
            intent.putExtra("SELECTED_WORD", word);
            context.startActivity(intent);
        }
    }


    private void removeFromFavorites(String word) {
        favoritesRef.orderByChild("word").equalTo(word).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

