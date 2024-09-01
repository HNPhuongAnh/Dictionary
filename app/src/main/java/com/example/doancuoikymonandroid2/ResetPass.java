
package com.example.doancuoikymonandroid2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ResetPass extends AppCompatActivity {
    EditText txtNewPass, txtConfirmNewPass;
    Button btnResetPass;
    TextView textLogin;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        txtNewPass = (EditText) findViewById(R.id.txtNewPass);
        txtConfirmNewPass = (EditText) findViewById(R.id.txtConfirmNewPass);
        btnResetPass = (Button) findViewById(R.id.btnResetPass);
        textLogin = (TextView) findViewById(R.id.textLogin);

        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String retrievedValue = preferences.getString("phone", "defaultValue");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityLogin(view);
            }
        });

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPass = txtNewPass.getText().toString();
                String confirmPass = txtConfirmNewPass.getText().toString();

                if (newPass.isEmpty() || confirmPass.isEmpty()) {
                    showToast("Please enter complete information!");
                } else if (!newPass.equals(confirmPass)) {
                    showToast("Password confirmation does not match!");
                } else {
                    SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    String retrievedValue = preferences.getString("phone", "defaultValue");
                    Query query = reference.orderByChild("phone").equalTo(retrievedValue);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    DatabaseReference passwordRef = userSnapshot.getRef().child("password");
                                    passwordRef.setValue(newPass)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        showToast("Update password successfully!");

                                                        mAuth.signInWithEmailAndPassword(userSnapshot.child("email").getValue(String.class), userSnapshot.child("password").getValue(String.class))
                                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                        if (task.isSuccessful()) {
                                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                                            if (user != null) {
                                                                                user.updatePassword(newPass)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    Intent a = new Intent(ResetPass.this, LogIn.class);
                                                                                                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                                                                    startActivity(a);
                                                                                                } else {
                                                                                                    showToast("Failed to update password");
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        } else {
                                                                            showToast("Authentication failed. Check your current password.");
                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        showToast("Failed to update password");
                                                    }
                                                }
                                            });
                                }
                            } else {
                                showToast("User not found");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            showToast("Database error");
                        }
                    });
                }
            }
        });
    }
    public void openActivityLogin(View view) {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}