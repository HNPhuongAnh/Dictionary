package com.example.doancuoikymonandroid2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText txtUserName, txtEmail, txtPass, txtConfirmPass, txtPhone;
    Button btnCreate;
    TextView textLogin;
    RadioButton rdYes;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtUserName = (EditText)findViewById(R.id.txtUserName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPass = (EditText)findViewById(R.id.txtPass);
        txtPhone = (EditText)findViewById(R.id.txtPhone);
        txtConfirmPass = (EditText)findViewById(R.id.txtConfirmPass);
        btnCreate = (Button)findViewById(R.id.btnCreate);
        textLogin = (TextView)findViewById(R.id.textLogin);
        rdYes = (RadioButton)findViewById(R.id.rdYes);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityLogin(view);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email, phone, password, confirmPass;
                name = String.valueOf(txtUserName.getText());
                email = String.valueOf(txtEmail.getText());
                phone = String.valueOf(txtPhone.getText());
                password = String.valueOf(txtPass.getText());
                confirmPass = String.valueOf(txtConfirmPass.getText());

                reference = database.getReference("users");

                if (TextUtils.isEmpty(name)) {
                    showToast("Please enter complete information!");
                } else if (TextUtils.isEmpty(email)) {
                    showToast("Please enter complete information!");
                } else if (TextUtils.isEmpty(password)) {
                    showToast("Please enter complete information!");
                } else if (TextUtils.isEmpty(confirmPass)) {
                    showToast("Please enter complete information!");
                } else if (password.length() <= 3 ) {
                    showToast("Password is too short!");
                } else if (!confirmPass.equals(password)) {
                    showToast("Password confirmation does not match!");
                } else if (!isValidEmail(email)) {
                } else if (!isValidString(password)) {
                    showToast("Password cannot contain special characters!");
                } else if (!isValidPhone(phone)) {
                    showToast("Phone number not correct!");
                }else if (!isRadioButtonSelected()) {
                } else {
                    checkPhoneExistAndCreateAccount(name, email, phone, password);
                    new CountDownTimer(4000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {
                            Intent intent = new Intent(SignUp.this, LogIn.class);
                            startActivity(intent);
                        }
                    }.start();


                }
            }
        });
    }
    public void openActivityLogin(View view) {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    private boolean isRadioButtonSelected() {
        if (rdYes.isChecked()) {
            return true;
        } else {
            showToast("Please check the term!");
            return false;
        }
    }
    private boolean isValidString(String input) {
        String regex = "^[a-zA-Z0-9]+$";
        return input.matches(regex);
    }
    private boolean isValidPhone(String input) {
        String regex = "[0-9*#+() -]*";
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
    private void checkPhoneExistAndCreateAccount(String name, String email, String phone, String password) {
        Query query = reference.orderByChild("phone").equalTo(phone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    showToast("Phone already exist!");
                } else {
                    createAccount(name, email, phone, password);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                showToast("Error checking phone existence");
            }
        });
    }

    private void createAccount(String name, String email, String phone, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = reference.push().getKey();
                            User user = new User(userId, name, email, phone, password);
                            reference.child(userId).setValue(user);
                            showToast("Create account successfully.");
                        } else {
                            showToast("Create account failed.");
                        }
                    }
                });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}