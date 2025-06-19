package com.example.gameease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class NumberNimGameActivity extends AppCompatActivity {

    private LinearLayout rootLayout;
    private TextView tvTurn, tvNumber;
    private Button btn1, btn2, btn3, btnRestart, btnHome;

    private int currentNumber;
    private boolean redTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_numbernim);

        rootLayout = findViewById(R.id.rootLayout);
        tvTurn = findViewById(R.id.tvTurn);
        tvNumber = findViewById(R.id.tvNumber);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btnRestart = findViewById(R.id.btnRestart);
        btnHome = findViewById(R.id.btnHome);

        btn1.setOnClickListener(v -> subtract(1));
        btn2.setOnClickListener(v -> subtract(2));
        btn3.setOnClickListener(v -> subtract(3));
        btnRestart.setOnClickListener(v -> startGame());
        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        startGame();
    }

    private void startGame() {
        currentNumber = (int)(Math.random() * 31) + 10; // 10 to 40
        redTurn = true;
        enableButtons(true);
        updateDisplay();
    }

    private void subtract(int value) {
        if (currentNumber - value < 0) return;

        currentNumber -= value;
        tvNumber.setText(String.valueOf(currentNumber)); // <- Add this line

        if (currentNumber == 0) {
            tvTurn.setText(redTurn ? "Player Blue Wins!" : "Player Red Wins!");
            enableButtons(false);
        } else {
            redTurn = !redTurn;
            updateDisplay();
        }
    }


    private void updateDisplay() {
        tvNumber.setText(String.valueOf(currentNumber));
        if (redTurn) {
            tvTurn.setText("Player Red's Turn");
            rootLayout.setBackgroundColor(getResources().getColor(R.color.light_red));
        } else {
            tvTurn.setText("Player Blue's Turn");
            rootLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        }
    }

    private void enableButtons(boolean enabled) {
        btn1.setEnabled(enabled);
        btn2.setEnabled(enabled);
        btn3.setEnabled(enabled);
    }
}

