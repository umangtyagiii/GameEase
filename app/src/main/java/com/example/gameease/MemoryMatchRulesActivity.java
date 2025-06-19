package com.example.gameease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MemoryMatchRulesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_memorymatch);

        Button startButton = findViewById(R.id.btnStartGame);
        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MemoryMatchGameActivity.class);
            startActivity(intent);
        });
    }
}
