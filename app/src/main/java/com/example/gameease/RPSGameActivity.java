package com.example.gameease;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RPSGameActivity extends AppCompatActivity {

    private Button btnRockRed, btnPaperRed, btnScissorsRed;
    private Button btnRockBlue, btnPaperBlue, btnScissorsBlue;
    private Button btnRestart, btnHome;

    private TextView tvTurn, tvResult, tvRedScore, tvBlueScore;
    private LinearLayout rootLayout;

    private boolean isRedTurn = true;
    private String redChoice = "", blueChoice = "";
    private int redWins = 0, blueWins = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rps);

        // View Initialization
        rootLayout = findViewById(R.id.rootLayout);
        tvTurn = findViewById(R.id.tvTurn);
        tvResult = findViewById(R.id.tvResult);
        tvRedScore = findViewById(R.id.tvRedScore);
        tvBlueScore = findViewById(R.id.tvBlueScore);

        btnRockRed = findViewById(R.id.btnRockRed);
        btnPaperRed = findViewById(R.id.btnPaperRed);
        btnScissorsRed = findViewById(R.id.btnScissorsRed);
        btnRockBlue = findViewById(R.id.btnRockBlue);
        btnPaperBlue = findViewById(R.id.btnPaperBlue);
        btnScissorsBlue = findViewById(R.id.btnScissorsBlue);

        btnRestart = findViewById(R.id.btnRestart);
        btnHome = findViewById(R.id.btnHome);

        // Button Actions
        btnRestart.setOnClickListener(v -> resetGame());
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        setListeners();
        updateTurnDisplay();
        updateScoreDisplay();
    }

    private void setListeners() {
        View.OnClickListener redListener = v -> {
            if (!isRedTurn) return;
            redChoice = getChoiceFromButton(v.getId());
            isRedTurn = false;
            updateTurnDisplay();
        };

        View.OnClickListener blueListener = v -> {
            if (isRedTurn || redChoice.isEmpty()) return;
            blueChoice = getChoiceFromButton(v.getId());
            checkWinner();
        };

        btnRockRed.setOnClickListener(redListener);
        btnPaperRed.setOnClickListener(redListener);
        btnScissorsRed.setOnClickListener(redListener);

        btnRockBlue.setOnClickListener(blueListener);
        btnPaperBlue.setOnClickListener(blueListener);
        btnScissorsBlue.setOnClickListener(blueListener);
    }

    private String getChoiceFromButton(int id) {
        if (id == R.id.btnRockRed || id == R.id.btnRockBlue) return "Rock";
        if (id == R.id.btnPaperRed || id == R.id.btnPaperBlue) return "Paper";
        return "Scissors";
    }

    private void checkWinner() {
        String result;
        if (redChoice.equals(blueChoice)) {
            result = "It's a Draw!";
        } else if (
                (redChoice.equals("Rock") && blueChoice.equals("Scissors")) ||
                        (redChoice.equals("Paper") && blueChoice.equals("Rock")) ||
                        (redChoice.equals("Scissors") && blueChoice.equals("Paper"))
        ) {
            redWins++;
            result = "Player Red wins this round!";
        } else {
            blueWins++;
            result = "Player Blue wins this round!";
        }

        // Show result and update score
        tvResult.setText(result);
        updateScoreDisplay();

        redChoice = "";
        blueChoice = "";
        isRedTurn = true;

        if (redWins == 2 || blueWins == 2) {
            declareFinalWinner();
        } else {
            updateTurnDisplay();
        }
    }

    private void updateScoreDisplay() {
        tvRedScore.setText("Red: " + redWins);
        tvBlueScore.setText("Blue: " + blueWins);
    }

    private void declareFinalWinner() {
        String finalResult = (redWins == 2) ? "Player Red wins the game!" : "Player Blue wins the game!";
        tvTurn.setText(finalResult);
        disableButtons();
    }

    private void disableButtons() {
        btnRockRed.setEnabled(false);
        btnPaperRed.setEnabled(false);
        btnScissorsRed.setEnabled(false);
        btnRockBlue.setEnabled(false);
        btnPaperBlue.setEnabled(false);
        btnScissorsBlue.setEnabled(false);
    }

    private void resetGame() {
        redChoice = "";
        blueChoice = "";
        redWins = 0;
        blueWins = 0;
        isRedTurn = true;

        tvResult.setText("");
        updateScoreDisplay();
        updateTurnDisplay();

        btnRockRed.setEnabled(true);
        btnPaperRed.setEnabled(true);
        btnScissorsRed.setEnabled(true);
        btnRockBlue.setEnabled(true);
        btnPaperBlue.setEnabled(true);
        btnScissorsBlue.setEnabled(true);
    }

    private void updateTurnDisplay() {
        if (isRedTurn) {
            tvTurn.setText("Player Red's Turn");
            rootLayout.setBackgroundColor(Color.parseColor("#FFCCCC")); // light red
        } else {
            tvTurn.setText("Player Blue's Turn");
            rootLayout.setBackgroundColor(Color.parseColor("#CCE5FF")); // light blue
        }
    }
}

