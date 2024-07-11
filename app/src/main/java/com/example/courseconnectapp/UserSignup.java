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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.SecureRandom;

public class UserSignup extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText SignUpEmail, SignUpPassword;
    private Button SignUpButton;

    private TextView LoginRedirectText;


    public static String generateOTP(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be a positive integer.");
        }

        String numbers = "0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder otp = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            otp.append(numbers.charAt(secureRandom.nextInt(numbers.length())));
        }

        return otp.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        auth = FirebaseAuth.getInstance();

        SignUpEmail = findViewById(R.id.SignUp_email);
        SignUpPassword = findViewById(R.id.SignUp_password);
        SignUpButton = findViewById(R.id.SignUp_btn);
        LoginRedirectText = findViewById(R.id.LoginRedirectingtext);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = SignUpEmail.getText().toString().trim();
                String pass = SignUpPassword.getText().toString().trim();

                if(user.isEmpty()){
                    SignUpEmail.setError("Email can't be empty");
                }
                if(pass.isEmpty()){
                    SignUpPassword.setError("Password can't be empty");
                }
                else {
                    String otp = generateOTP(4);
                    Intent intent= new Intent(UserSignup.this, OtpActivity.class);
                    intent.putExtra("otp", otp);
                    intent.putExtra("email", user);
                    intent.putExtra("password", pass);
                    EmailSender emailSender = new EmailSender(user, "OTP - verification", otp);
                    emailSender.execute();
                    startActivity(intent);

                }


            }
        });

        LoginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSignup.this, UserLogin.class));
            }
        });

    }
}
