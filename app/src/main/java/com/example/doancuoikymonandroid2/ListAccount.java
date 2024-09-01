package com.example.doancuoikymonandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAccount extends AppCompatActivity {

    Button btnHome;
    ListView listViewAccount;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account);

        listViewAccount = (ListView) findViewById(R.id.listViewAccount);
        btnHome = (Button) findViewById(R.id.btnHome);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        List<String> dataList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listViewAccount.setAdapter(adapter);

        Query query = reference.orderByChild("userId");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.child("userId").getValue(String.class);
                        dataList.add(userId);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    showToast("User not found");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                showToast("Database error");
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(ListAccount.this, MainActivity.class);
                startActivity(a);
            }
        });

        listViewAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUserId = dataList.get(position);
                Intent intent = new Intent(ListAccount.this, DetailAccount.class);
                intent.putExtra("userId", selectedUserId);
                startActivity(intent);
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}