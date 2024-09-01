package com.example.doancuoikymonandroid2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnterCodePhone extends AppCompatActivity {
    private boolean otpSent = false;
    private String id;
    EditText txtOtp;
    Button btnVerify;
    TextView textFailCode, textResend;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code_phone);

        btnVerify = (Button) findViewById(R.id.btnVerify);
        textFailCode = (TextView) findViewById(R.id.textFailCode);
        textResend = (TextView) findViewById(R.id.textResend);
        txtOtp = (EditText) findViewById(R.id.txtOtp);

        textResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openResendCode();
            }
        });

        new CountDownTimer(20000, 10000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                readOTPFromSMS();
            }
        }.start();

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        String otp = txtOtp.getText().toString().trim();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = txtOtp.getText().toString().trim();
                if (otp.isEmpty()) {
                    showToast("Unable to verify OTP");
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otp);
                    mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent a = new Intent(EnterCodePhone.this, ResetPass.class);
                                a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(a);
//                                finish();
                            } else {
                                textFailCode.setTextColor(Color.parseColor("#FF0000"));
                                textFailCode.setText("Verify went wrong");
                            }

                        }
                    });
                }
            }
        });
    }
    private void openResendCode() {
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String retrievedValue = preferences.getString("phone", "defaultValue");

        if (otpSent) {
            resendOTP("+84" + retrievedValue);
        } else {
            showToast("Please wait for the initial OTP or try again later.");
        }
    }

    private void resendOTP(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(EnterCodePhone.this)
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
                        showToast("OTP resent successfully!");
                    }
                })
                .setForceResendingToken(null)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to check the OTP?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                readOTPFromSMS();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    private void readOTPFromSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 101);
        } else {
            Uri smsUri = Uri.parse("content://sms");
            String sortOrder = Telephony.Sms.DATE + " DESC";
            try (Cursor cursor = getContentResolver().query(smsUri, null, null, null, sortOrder)) {
                if (cursor != null && cursor.moveToFirst()) {
                    String body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    readOTPFromMessage(body);
                }
            } catch (Exception e) {
                Log.e("OTPDemoActivity", "Error reading SMS", e);
            }
        }
    }

    private void readOTPFromMessage(String message) {
        String otpRegex = "(\\d{6})";
        Pattern pattern = Pattern.compile(otpRegex);
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String otp = matcher.group(1);
            txtOtp.setText(otp);
        } else {
            showToast("OTP not found in SMS");
        }
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}