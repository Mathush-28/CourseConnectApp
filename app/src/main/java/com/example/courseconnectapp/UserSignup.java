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

public class UserSignup extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText SignUpEmail, SignUpPassword;
    private Button SignUpButton;

    private TextView LoginRedirectText;


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
                    auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UserSignup.this, "Login successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserSignup.this, UserLogin.class));
                            }
                            else{
                                Toast.makeText(UserSignup.this, "Login Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
