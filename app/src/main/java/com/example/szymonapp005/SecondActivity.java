package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textLabel = findViewById(R.id.textLabel);
        Bundle bundle = getIntent().getExtras();
        String text = bundle.get("name").toString();
        textLabel.setText(text);
    }
}