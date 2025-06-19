package com.example.gameease;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryMatchGameActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private LinearLayout rootLayout;
    private TextView tvTurn, tvRedScore, tvBlueScore;
    private Button btnRestart, btnHome;

    private int[] icons = {
            R.drawable.icon_apple,
            R.drawable.icon_star,
            R.drawable.icon_cat,
            R.drawable.icon_dog,
            R.drawable.icon_car,
            R.drawable.icon_heart,
            R.drawable.icon_flower,
            R.drawable.icon_banana
    };

    private ArrayList<Integer> cardImages = new ArrayList<>();
    private ImageView[][] cardViews = new ImageView[4][4];
    private boolean playerRedTurn = true;

    private ImageView firstCard, secondCard;
    private int firstImage, secondImage;
    private boolean isBusy = false;

    private int redScore = 0, blueScore = 0;

    private Animation flipIn, flipOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_memorymatch);

        gridLayout = findViewById(R.id.gridLayout);
        rootLayout = findViewById(R.id.rootLayout);
        tvTurn = findViewById(R.id.tvTurn);
        tvRedScore = findViewById(R.id.tvRedScore);
        tvBlueScore = findViewById(R.id.tvBlueScore);
        btnRestart = findViewById(R.id.btnRestart);
        btnHome = findViewById(R.id.btnHome);

        flipIn = AnimationUtils.loadAnimation(this, R.anim.flip_in);
        flipOut = AnimationUtils.loadAnimation(this, R.anim.flip_out);

        setupBoard();
        updateTurnDisplay();
        updateScores();

        btnRestart.setOnClickListener(v -> recreate());

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void setupBoard() {
        cardImages.clear();
        for (int icon : icons) {
            cardImages.add(icon);
            cardImages.add(icon);
        }
        Collections.shuffle(cardImages);

        gridLayout.removeAllViews();
        gridLayout.setRowCount(4);
        gridLayout.setColumnCount(4);

        // Calculate screen width in pixels
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        float density = getResources().getDisplayMetrics().density;

        // Convert dp to px properly
        int marginDp = 8;
        int layoutPaddingDp = 16;

        int totalMarginPx = (int) (marginDp * 2 * 4 * density); // 8dp left+right × 4 cards
        int totalPaddingPx = (int) (layoutPaddingDp * 2 * density); // 16dp left+right padding

        // Available space after margins and padding
        int availableWidth = screenWidth - totalMarginPx - totalPaddingPx;

        // Final safe card size
        int cellSize = availableWidth / 4;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ImageView card = new ImageView(this);
                card.setImageResource(R.drawable.card_back);
                card.setScaleType(ImageView.ScaleType.CENTER_CROP);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = cellSize;
                params.height = cellSize;

                // Set proper margin in dp
                int marginPx = (int) (marginDp * density);
                params.setMargins(marginPx, marginPx, marginPx, marginPx);

                card.setLayoutParams(params);
                gridLayout.addView(card);
                cardViews[i][j] = card;

                final int pos = i * 4 + j;
                card.setOnClickListener(view -> handleCardClick(card, cardImages.get(pos)));
            }
        }
    }



    private void handleCardClick(ImageView card, int image) {
        if (isBusy || card == firstCard || card.getTag() != null) return;

        card.startAnimation(flipOut);
        card.postDelayed(() -> {
            card.setImageResource(image);
            card.startAnimation(flipIn);
        }, 150);

        if (firstCard == null) {
            firstCard = card;
            firstImage = image;
        } else {
            secondCard = card;
            secondImage = image;
            isBusy = true;

            new Handler().postDelayed(() -> {
                if (firstImage == secondImage) {
                    firstCard.setTag("matched");
                    secondCard.setTag("matched");
                    if (playerRedTurn) redScore++;
                    else blueScore++;
                    updateScores();
                } else {
                    flipBack(firstCard);
                    flipBack(secondCard);
                    playerRedTurn = !playerRedTurn;
                }

                firstCard = null;
                secondCard = null;
                isBusy = false;
                updateTurnDisplay();
                checkGameEnd();
            }, 700);
        }
    }

    private void flipBack(ImageView card) {
        card.startAnimation(flipOut);
        card.postDelayed(() -> {
            card.setImageResource(R.drawable.card_back);
            card.startAnimation(flipIn);
        }, 150);
    }

    private void updateTurnDisplay() {
        if (playerRedTurn) {
            tvTurn.setText("Player Red's Turn");
            rootLayout.setBackgroundColor(getResources().getColor(R.color.light_red));
        } else {
            tvTurn.setText("Player Blue's Turn");
            rootLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        }
    }

    private void updateScores() {
        tvRedScore.setText("Red: " + redScore);
        tvBlueScore.setText("Blue: " + blueScore);
    }

    private void checkGameEnd() {
        int totalMatched = 0;
        for (ImageView[] row : cardViews)
            for (ImageView card : row)
                if ("matched".equals(card.getTag())) totalMatched++;

        if (totalMatched == 16) {
            String result;
            if (redScore > blueScore) result = "Player Red Wins!";
            else if (blueScore > redScore) result = "Player Blue Wins!";
            else result = "It's a Draw!";
            tvTurn.setText(result);
        }
    }
}






