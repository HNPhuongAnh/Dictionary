package com.example.doancuoikymonandroid2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ForgotPass extends AppCompatActivity {
    private boolean otpSent = false;
    private String id;
    EditText txtPhoneNumber;
    Button btnSendCode;
    TextView textLogin;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        txtPhoneNumber = (EditText)findViewById(R.id.txtPhoneNumber);
        btnSendCode = (Button)findViewById(R.id.btnSendCode);
        textLogin = (TextView)findViewById(R.id.textLogin);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityLogin(view);
            }
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = txtPhoneNumber.getText().toString();
                Query query = reference.orderByChild("phone").equalTo(phoneNumber);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            sendCode();
                        } else {
                            showToast("The phone number does not exist in any account!");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        showToast("Something went wrong");
                    }
                });
            }
        });


    }
    public void openActivityLogin(View view) {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
    private boolean isValidPhone(String input) {
        String regex = "^[0-9\\-\\+]{9,15}$";
        return input.matches(regex);
    }
    private void sendCode(){
        String phoneNumber;
        phoneNumber = txtPhoneNumber.getText().toString();

        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phone", phoneNumber);
        editor.apply();

        if(TextUtils.isEmpty(phoneNumber)){
            showToast("Phone number empty!");
        }else if(!isValidPhone(phoneNumber)) {
            showToast("Phone number not correct!");
        } else {
            if(otpSent){
                new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        Intent a = new Intent(ForgotPass.this, EnterCodePhone.class);
                        startActivity(a);
                    }
                }.start();
            }else {
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber("+84"+phoneNumber)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(ForgotPass.this)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        showToast("OTP send successfully!");

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        showToast(e.getMessage());
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(s, forceResendingToken);
                                        id = s;
                                        otpSent = true;


                                        Intent a = new Intent(ForgotPass.this, EnterCodePhone.class);
                                        a.putExtra("id", id);
                                        startActivity(a);
                                    }
                                })
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
        }
    }
}
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}