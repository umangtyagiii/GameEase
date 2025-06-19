package com.example.gameease;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DotsBoxesGameActivity extends AppCompatActivity {

    private static final int BOXES = 5;
    private static final int DOTS = BOXES + 1;

    private View[][] horizontalLines = new View[DOTS][BOXES];
    private View[][] verticalLines = new View[BOXES][DOTS];
    private ImageView[][] boxOwners = new ImageView[BOXES][BOXES];

    private boolean redTurn = true;
    private int redScore = 0, blueScore = 0;

    private TextView tvTurn, tvScore;
    private LinearLayout rootLayout;
    private Button btnRestart, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_dotsboxes);

        tvTurn = findViewById(R.id.tvTurn);
        tvScore = findViewById(R.id.tvScore);
        rootLayout = findViewById(R.id.rootLayout);
        btnRestart = findViewById(R.id.btnRestart);
        btnHome = findViewById(R.id.btnHome);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        setupBoard(gridLayout);

        btnRestart.setOnClickListener(v -> recreate());
        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        updateTurnDisplay();
    }

    private void setupBoard(GridLayout gridLayout) {
        gridLayout.setRowCount(DOTS * 2 - 1);
        gridLayout.setColumnCount(DOTS * 2 - 1);

        for (int row = 0; row < DOTS * 2 - 1; row++) {
            for (int col = 0; col < DOTS * 2 - 1; col++) {
                if (row % 2 == 0 && col % 2 == 0) {
                    View dot = new View(this);
                    dot.setBackgroundColor(Color.BLACK);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = 20;
                    params.height = 20;
                    dot.setLayoutParams(params);
                    gridLayout.addView(dot);

                } else if (row % 2 == 0) {
                    View hLine = new View(this);
                    hLine.setBackgroundColor(Color.LTGRAY);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = 100;
                    params.height = 20;
                    hLine.setLayoutParams(params);
                    gridLayout.addView(hLine);

                    final int r = row / 2, c = col / 2;
                    horizontalLines[r][c] = hLine;
                    hLine.setOnClickListener(v -> handleLineClick(hLine, true, r, c));

                } else if (col % 2 == 0) {
                    View vLine = new View(this);
                    vLine.setBackgroundColor(Color.LTGRAY);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = 20;
                    params.height = 100;
                    vLine.setLayoutParams(params);
                    gridLayout.addView(vLine);

                    final int r = row / 2, c = col / 2;
                    verticalLines[r][c] = vLine;
                    vLine.setOnClickListener(v -> handleLineClick(vLine, false, r, c));

                } else {
                    ImageView box = new ImageView(this);
                    box.setBackgroundColor(Color.WHITE);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = 100;
                    params.height = 100;
                    box.setLayoutParams(params);
                    gridLayout.addView(box);

                    boxOwners[row / 2][col / 2] = box;
                }
            }
        }
    }

    private void handleLineClick(View line, boolean isHorizontal, int row, int col) {
        if (((ColorDrawable) line.getBackground()).getColor() != Color.LTGRAY) return;

        line.setBackgroundColor(redTurn ? Color.RED : Color.BLUE);
        boolean boxCompleted = false;

        if (isHorizontal) {
            if (row > 0 && isBoxCompleted(row - 1, col)) {
                claimBox(row - 1, col);
                boxCompleted = true;
            }
            if (row < BOXES && isBoxCompleted(row, col)) {
                claimBox(row, col);
                boxCompleted = true;
            }
        } else {
            if (col > 0 && isBoxCompleted(row, col - 1)) {
                claimBox(row, col - 1);
                boxCompleted = true;
            }
            if (col < BOXES && isBoxCompleted(row, col)) {
                claimBox(row, col);
                boxCompleted = true;
            }
        }

        if (!boxCompleted) {
            redTurn = !redTurn;
        }
        updateTurnDisplay();
        checkGameEnd();
    }

    private boolean isBoxCompleted(int row, int col) {
        return ((ColorDrawable) horizontalLines[row][col].getBackground()).getColor() != Color.LTGRAY &&
                ((ColorDrawable) horizontalLines[row + 1][col].getBackground()).getColor() != Color.LTGRAY &&
                ((ColorDrawable) verticalLines[row][col].getBackground()).getColor() != Color.LTGRAY &&
                ((ColorDrawable) verticalLines[row][col + 1].getBackground()).getColor() != Color.LTGRAY;
    }

    private void claimBox(int row, int col) {
        boxOwners[row][col].setBackgroundColor(redTurn ? Color.RED : Color.BLUE);
        if (redTurn) redScore++; else blueScore++;
    }

    private void updateTurnDisplay() {
        if (redTurn) {
            tvTurn.setText("Player Red's Turn");
            rootLayout.setBackgroundColor(getResources().getColor(R.color.light_red));
        } else {
            tvTurn.setText("Player Blue's Turn");
            rootLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        }

        String redPart = "Red: " + redScore + "  ";
        String bluePart = "Blue: " + blueScore;
        String fullText = redPart + bluePart;

        SpannableString spannable = new SpannableString(fullText);

        // Apply player_red color
        spannable.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(this, R.color.player_red)),
                0,
                redPart.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // Apply player_blue color
        spannable.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(this, R.color.player_blue)),
                redPart.length(),
                fullText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvScore.setText(spannable);
    }




    private void checkGameEnd() {
        if (redScore + blueScore == BOXES * BOXES) {
            if (redScore > blueScore) tvTurn.setText("Player Red Wins!");
            else if (blueScore > redScore) tvTurn.setText("Player Blue Wins!");
            else tvTurn.setText("It's a Draw!");
        }
    }
}

