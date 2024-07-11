package com.example.courseconnectapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private TextView tvCourseName;
    private TextView tvOriginalPrice;
    private TextView tvFinalPrice;
    private EditText etEmail;
    private EditText etPromoCode;
    private Button btnDone;
    private Button btnApplyPromo;

    private Map<String, Integer> promoCodes;
    private String courseName;
    private double coursePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvCourseName = findViewById(R.id.tvCourseName);
        tvOriginalPrice = findViewById(R.id.tvOriginalPrice);
        tvFinalPrice = findViewById(R.id.tvFinalPrice);
        etEmail = findViewById(R.id.etEmail);
        etPromoCode = findViewById(R.id.etPromoCode);
        btnDone = findViewById(R.id.btnDone);
        btnApplyPromo = findViewById(R.id.btnApplyPromo);

        // Define promo codes and their discount rates
        promoCodes = new HashMap<>();
        promoCodes.put("DISCOUNT10", 10);
        promoCodes.put("SAVE20", 20);
        promoCodes.put("OFFER30", 30);

        Intent intent = getIntent();
        courseName = intent.getStringExtra("courseName");
        String finalPrice = intent.getStringExtra("finalPrice");

        tvCourseName.setText("Course Name: " + courseName);
        coursePrice = Double.parseDouble(finalPrice);
        tvOriginalPrice.setText("Original Price: LKR " + finalPrice);
        tvFinalPrice.setText("Discounted Price: LKR " + finalPrice);

        btnApplyPromo.setOnClickListener(v -> applyPromoCode());

        btnDone.setOnClickListener(v -> {
            Intent homeIntent = new Intent(RegistrationActivity.this, MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        });
    }

    private void applyPromoCode() {
        String promoCode = etPromoCode.getText().toString().trim();
        if (promoCodes.containsKey(promoCode)) {
            int discountPercentage = promoCodes.get(promoCode);
            double discountedPrice = coursePrice - (coursePrice * discountPercentage / 100);
            tvFinalPrice.setText("Discounted Price: LKR " + String.format("%.2f", discountedPrice));
            Toast.makeText(this, "Promo code applied! " + discountPercentage + "% discount", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid promo code", Toast.LENGTH_SHORT).show();
        }

    }
}