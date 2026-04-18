package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.*;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    EditText etPhone, etCode;

    FirebaseAuth auth;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        etPhone = findViewById(R.id.etPhone);
        etCode = findViewById(R.id.etCode);

        auth = FirebaseAuth.getInstance();
    }

    // 🔹 SEND OTP
    public void sendOTP(View view) {

        String phone = etPhone.getText().toString().trim();

        if (phone.isEmpty()) {
            Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(callbacks)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // 🔹 CALLBACKS
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String id,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {

                    verificationId = id;

                    Toast.makeText(OtpActivity.this,
                            "OTP Sent. ID: " + id,
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    // Auto verification (optional)
                    signInUser(credential);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(OtpActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            };

    // 🔹 VERIFY OTP
    public void verifyCode(View view) {

        String code = etCode.getText().toString().trim();

        if (code.isEmpty()) {
            Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (verificationId == null) {
            Toast.makeText(this, "Send OTP first", Toast.LENGTH_SHORT).show();
            return;
        }

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(verificationId, code);

        signInUser(credential);
    }

    // 🔹 SIGN IN
    private void signInUser(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(this, DashboardActivity.class));
                        finish();

                    } else {

                        String error = task.getException().getMessage();

                        Toast.makeText(this,
                                "Error: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void goToLogin(View view){startActivity(new Intent(this, LoginActivity.class));}
}
