package com.example.doancuoikymonandroid2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {
    EditText txtEmailLogin, txtPassLogin;
    Button btnLogin;
    TextView textForgot, textSignup;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtEmailLogin = (EditText)findViewById(R.id.txtEmailLogin);
        txtPassLogin = (EditText)findViewById(R.id.txtPassLogin);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        textForgot = (TextView)findViewById(R.id.textForgot);
        textSignup = (TextView)findViewById(R.id.textSignup);

        mAuth = FirebaseAuth.getInstance();

        textForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityForgotPass(view);
            }
        });
        textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivitySignup(view);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(txtEmailLogin.getText());
                password = String.valueOf(txtPassLogin.getText());

                if (TextUtils.isEmpty(email)) {
                    showToast("Please enter complete information!");
                }else if (TextUtils.isEmpty(password)) {
                    showToast("Please enter complete information!");
                }else if (!isValidEmail(email)) {
                    return;
                }else if (!isValidString(password)) {
                    showToast("Password cannot contain special characters!");
                }else{
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        showToast("Login successfully.");
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        if (currentUser.getEmail().equals("admin@gmail.com")) {
                                            showToast("Login as admin successfully.");
                                            Intent a = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(a);
                                            finish();
                                        } else {
                                            showToast("Login successfully.");
                                            Intent a = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(a);
                                            finish();
                                        }
                                    } else {
                                        showToast("Login unsuccessfully.");
                                    }
                                }
                            });
                }
            }
        });

    }
    private boolean isValidString(String input) {
        String regex = "^[a-zA-Z0-9]+$";
        return input.matches(regex);
    }
    private boolean isValidEmail(String input) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (pattern.matcher(input).matches()) {
            return true;
        } else {
            showToast("Email not valid");
            return false;
        }
    }
    public void openActivityForgotPass(View view) {
        Intent intent = new Intent(this, ForgotPass.class);
        startActivity(intent);
    }
    public void openActivitySignup(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}