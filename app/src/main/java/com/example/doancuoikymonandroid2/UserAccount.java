package com.example.doancuoikymonandroid2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserAccount extends AppCompatActivity {
    FirebaseAuth auth;
    TextView textUser, textEmail, textPhoneNumber, textPassword;
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseUser user;
    FirebaseAuth mAuth;

    Button btnLogOut, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        textUser = (TextView) findViewById(R.id.textUser);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textPhoneNumber = (TextView) findViewById(R.id.textPhoneNumber);
        textPassword = (TextView) findViewById(R.id.textPassword);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnBack = (Button) findViewById(R.id.btnBack);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            Intent a = new Intent(UserAccount.this, LogIn.class);
            startActivity(a);
            finish();
        } else {
            textEmail.setText(user.getEmail());
            showProfile(user.getEmail());
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(UserAccount.this, Openning.class);
                startActivity(a);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(UserAccount.this, MainActivity.class);
                finish();
            }
        });
    }
    public void showProfile(String email) {
        Query query = reference.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userPhone = userSnapshot.child("phone").getValue(String.class);
                        String userName = userSnapshot.child("userName").getValue(String.class);
                        String userPass = userSnapshot.child("password").getValue(String.class);

                        textPassword.setText(userPass);
                        textPhoneNumber.setText(userPhone);
                        textUser.setText(userName);
                    }
                } else {
                    Intent a = new Intent(getApplicationContext(), Openning.class);
                    startActivity(a);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showToast("Something went wrong");
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}