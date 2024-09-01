package com.example.doancuoikymonandroid2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailAccount extends AppCompatActivity {

    TextView textUsername, textEmail, textPhone;
    Button btnDelete, btnBack;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_account);

        textUsername = (TextView) findViewById(R.id.textUsername);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textPhone = (TextView) findViewById(R.id.textPhone);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        String userId = getIntent().getStringExtra("userId");

        Query query = reference.orderByChild("userId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userName = userSnapshot.child("userName").getValue(String.class);
                        String phone = userSnapshot.child("phone").getValue(String.class);
                        String email = userSnapshot.child("email").getValue(String.class);

                        textUsername.setText(userName);
                        textEmail.setText(email);
                        textPhone.setText(phone);
                    }
                } else {
//                    showToast("Something went wrong!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showToast("Something went wrong");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(DetailAccount.this, ListAccount.class);
                startActivity(a);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailAccount.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want delete this account?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAccount();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

    }
    private void deleteAccount(){
        String userId = getIntent().getStringExtra("userId");
        DatabaseReference userRef = reference.child(userId);
        userRef.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showToast("Account deleted successfully!");
                            Intent intent = new Intent(DetailAccount.this, ListAccount.class);
                            startActivity(intent);
                        } else {
                            showToast("Failed to delete account");
                        }
                    }
                });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}