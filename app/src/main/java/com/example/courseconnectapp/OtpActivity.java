package com.example.courseconnectapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class OtpActivity extends AppCompatActivity {
    private EditText otpEditText;
    private Button verifyButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        otpEditText = findViewById(R.id.otp_enter);
       // String otp_entered=otpEditText.getText().toString().trim();
        verifyButton = findViewById(R.id.otp_verify_btn);

        Intent intent = getIntent();
        String otp = intent.getStringExtra("otp");
        String email= intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        System.out.println("abi new1: "+email+" : "+password+" otp "+otp);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp_entered=otpEditText.getText().toString().trim();
                System.out.println("abi new1: "+email+" : "+password+" otp "+otp+" enter : "+otp_entered);
                if(otp_entered.equals(otp)){
                    System.out.println("abi new 7 "+email+" : "+password+" otp "+otp+" enter : "+otp_entered);

                    assert email != null;
                    assert password != null;
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Log user details and OTP status for debugging purposes
                                    System.out.println("Email: " + email + " | Password: " + password + " | OTP Sent: " + otp + " | OTP Entered: " + otp_entered);

                                    if (task.isSuccessful()) {
                                        // Log success message
                                        System.out.println("Sign up successful for email: " + email);

                                        // Navigate to the UserLogin activity
                                        startActivity(new Intent(OtpActivity.this, UserLogin.class));
                                    } else {
                                        // Log failure message
                                        System.out.println("Sign up failed for email: " + email);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Log the exception details
                                    System.out.println("Sign up failed with exception: " + e.getMessage());

                                    // Show toast message for sign up failure
                                    Toast.makeText(OtpActivity.this, "Sign up Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }else{
                    Toast.makeText(OtpActivity.this, "Incorrect otp", Toast.LENGTH_SHORT).show();

                }
                    /*
                    auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UserSignup.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserSignup.this, UserLogin.class));
                            }
                            else{
                                Toast.makeText(UserSignup.this, "Sign up Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    */
                }



        });

    }

}