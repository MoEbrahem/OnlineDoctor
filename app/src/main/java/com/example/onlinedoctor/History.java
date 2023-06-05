package com.example.onlinedoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.onlinedoctor.breast_cancer.myDbAdapter;

import java.util.Arrays;

public class History extends AppCompatActivity {
    TextView tes;
    myDbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

            helper = new myDbAdapter(this);
            String[] data = helper.getData();
            tes = findViewById(R.id.databse);
            tes.setText(Arrays.toString(data));

    }
}