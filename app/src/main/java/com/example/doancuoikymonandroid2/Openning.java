package com.example.doancuoikymonandroid2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Openning extends AppCompatActivity {
    ImageView imageView;
    Button btnSignIn, btnCreateAccount;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openning);
        FirebaseApp.initializeApp(this);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        imageView = (ImageView) findViewById(R.id.imageView);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        imageView.startAnimation(animation);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(Openning.this, LogIn.class);
                startActivity(signIn);
            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(Openning.this, SignUp.class);
                startActivity(signUp);
            }
        });

    }
}