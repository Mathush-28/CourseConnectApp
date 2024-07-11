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

public class UserLogin extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText LoginMail, LoginPassword;
    private Button LoginButton;

    private TextView SignupRedirectText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        LoginMail = findViewById(R.id.UserLogin_email);
        LoginPassword = findViewById(R.id.UserLogin_password);
        LoginButton = findViewById(R.id.UserLogin_btn);
        SignupRedirectText = findViewById(R.id.UserSignupRedirectingtext);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginMail.getText().toString();
                String pass = LoginPassword.getText().toString();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(!pass.isEmpty() ){
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(UserLogin.this, "SignIn successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UserLogin.this, User.class));
                                        finish();
                                    }

                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(UserLogin.this, "SignIn failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        LoginPassword.setError("Password cant be empty");

                    }
                }
                else if (email.isEmpty()){
                    LoginMail.setError("Email cant be empty");
                } else {
                    LoginMail.setError("Please enter valid email");
                }
            }
        });
        SignupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, UserSignup.class));
            }
        });
    }


}

