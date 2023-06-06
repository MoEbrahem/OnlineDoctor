package com.example.onlinedoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinedoctor.breast_cancer.BreastCancer;

public class MenuScreen extends AppCompatActivity {
    CardView heart, skinCancer, pneumonia, malaria,breast,liver;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        userName = findViewById(R.id.textView_name);
        heart = findViewById(R.id.cv_heart);
        skinCancer = findViewById(R.id.cv_skinCancer);
        pneumonia = findViewById(R.id.cv_pneumonia);
        malaria = findViewById(R.id.cv_malaria);
        breast = findViewById(R.id.cv_breastCancer);
        liver = findViewById(R.id.cv_liver);
        userName.setText("Welcome: "+getIntent().getStringExtra("name"));
        heart.setOnClickListener(v -> {
            startActivity(new Intent(MenuScreen.this , Heart.class));
        });
        skinCancer.setOnClickListener(v -> {
            startActivity(new Intent(MenuScreen.this , SkinDetectionScreen.class));
        });
        pneumonia.setOnClickListener(v -> {
            startActivity(new Intent(MenuScreen.this , Pneumonia.class));
        });
        malaria.setOnClickListener(v -> {
            startActivity(new Intent(MenuScreen.this , Malaria.class));
        });
        breast.setOnClickListener(v -> {
            startActivity(new Intent(MenuScreen.this , BreastCancer.class));
        });

        liver.setOnClickListener(v ->
        {
            startActivity(new Intent(MenuScreen.this ,Liver.class));
        });

    }
}