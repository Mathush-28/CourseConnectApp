package com.example.courseconnectapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText LoginMail, LoginPassword;
    private Button LoginButton;
    private TextView SignupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        LoginMail = findViewById(R.id.Login_email);
        LoginPassword = findViewById(R.id.Login_password);
        LoginButton = findViewById(R.id.Login_btn);
//        SignupRedirectText = findViewById(R.id.SignupRedirectingtext);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginMail.getText().toString();
                String pass = LoginPassword.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        if (email.equals("admin@gmail.com") && pass.equals("admin123")) {
                            Toast.makeText(AdminLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminLogin.this, AdminHomePage.class));
                            finish();
                        } else {
                            Toast.makeText(AdminLogin.this, "Invalid admin credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        LoginPassword.setError("Password can't be empty");
                    }
                } else if (email.isEmpty()) {
                    LoginMail.setError("Email can't be empty");
                } else {
                    LoginMail.setError("Please enter a valid email");
                }
            }
        });

//        SignupRedirectText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AdminLogin.this, UserSignup.class));
//            }
//        });
    }
}
