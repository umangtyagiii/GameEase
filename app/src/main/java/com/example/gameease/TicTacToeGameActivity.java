package com.example.gameease;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TicTacToeGameActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private TextView tvTurn;
    private LinearLayout rootLayout;
    private Button restartButton, homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tictactoe);

        rootLayout = findViewById(R.id.rootLayout);
        tvTurn = findViewById(R.id.tvTurn);
        restartButton = findViewById(R.id.btnRestart);
        homeButton = findViewById(R.id.btnHome);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        setupGameBoard(gridLayout);

        restartButton.setOnClickListener(v -> resetGame());

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        updateTurnDisplay();
    }

    private void setupGameBoard(GridLayout gridLayout) {
        gridLayout.setRowCount(3);
        gridLayout.setColumnCount(3);

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        float density = getResources().getDisplayMetrics().density;

        int marginDp = 8;
        int paddingDp = 16;

        int totalMarginPx = (int) (marginDp * 2 * 3 * density); // 3 cols × left+right margin
        int totalPaddingPx = (int) (paddingDp * 2 * density);   // layout padding (left+right)

        int availableWidth = screenWidth - totalMarginPx - totalPaddingPx;
        int cellSize = availableWidth / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(this);
                button.setText("");
                button.setTextSize(28);
                button.setTextColor(Color.BLACK);
                button.setBackgroundColor(Color.WHITE);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = cellSize;
                params.height = cellSize;

                int marginPx = (int) (marginDp * density);
                params.setMargins(marginPx, marginPx, marginPx, marginPx);

                button.setLayoutParams(params);
                gridLayout.addView(button);
                buttons[i][j] = button;

                final int finalI = i;
                final int finalJ = j;
                button.setOnClickListener(v -> handleMove(finalI, finalJ));
            }
        }
    }


    private void handleMove(int row, int col) {
        if (!buttons[row][col].getText().toString().equals("")) return;

        String symbol = playerXTurn ? "X" : "O";
        buttons[row][col].setText(symbol);
        buttons[row][col].setTextColor(playerXTurn ? Color.RED : Color.BLUE);

        if (checkWin()) {
            tvTurn.setText("Player " + symbol + " Wins!");
            disableBoard();
        } else if (isBoardFull()) {
            tvTurn.setText("It's a Draw!");
        } else {
            playerXTurn = !playerXTurn;
            updateTurnDisplay();
        }
    }

    private void updateTurnDisplay() {
        if (playerXTurn) {
            tvTurn.setText("Player X's Turn");
            rootLayout.setBackgroundColor(Color.parseColor("#FFCCCC")); // light red
        } else {
            tvTurn.setText("Player O's Turn");
            rootLayout.setBackgroundColor(Color.parseColor("#CCE5FF")); // light blue
        }
    }

    private boolean checkWin() {
        String playerSymbol = playerXTurn ? "X" : "O";

        // Check rows & columns
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(playerSymbol) &&
                    buttons[i][1].getText().equals(playerSymbol) &&
                    buttons[i][2].getText().equals(playerSymbol))
                return true;

            if (buttons[0][i].getText().equals(playerSymbol) &&
                    buttons[1][i].getText().equals(playerSymbol) &&
                    buttons[2][i].getText().equals(playerSymbol))
                return true;
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(playerSymbol) &&
                buttons[1][1].getText().equals(playerSymbol) &&
                buttons[2][2].getText().equals(playerSymbol))
            return true;

        if (buttons[0][2].getText().equals(playerSymbol) &&
                buttons[1][1].getText().equals(playerSymbol) &&
                buttons[2][0].getText().equals(playerSymbol))
            return true;

        return false;
    }

    private boolean isBoardFull() {
        for (Button[] row : buttons)
            for (Button cell : row)
                if (cell.getText().toString().isEmpty())
                    return false;
        return true;
    }

    private void disableBoard() {
        for (Button[] row : buttons)
            for (Button cell : row)
                cell.setEnabled(false);
    }

    private void resetGame() {
        for (Button[] row : buttons)
            for (Button cell : row) {
                cell.setText("");
                cell.setEnabled(true);
            }

        playerXTurn = true;
        updateTurnDisplay();
    }
}

