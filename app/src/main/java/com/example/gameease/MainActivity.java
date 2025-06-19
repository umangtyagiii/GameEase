package com.example.gameease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnTicTacToe, btnRPS, btnMemoryMatch, btnDotsBoxes, btnNumberNim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTicTacToe = findViewById(R.id.btnTicTacToe);
        btnRPS = findViewById(R.id.btnRPS);
        btnMemoryMatch = findViewById(R.id.btnMemoryMatch);
        btnDotsBoxes = findViewById(R.id.btnDotsBoxes);
        btnNumberNim = findViewById(R.id.btnNumberNim);

        btnTicTacToe.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TicTacToeRulesActivity.class)));

        btnRPS.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, RPSRulesActivity.class)));

        btnMemoryMatch.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MemoryMatchRulesActivity.class)));

        btnDotsBoxes.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DotsBoxesRulesActivity.class)));

        btnNumberNim.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, NumberNimRulesActivity.class)));
    }
}
