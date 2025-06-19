package com.example.gameease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class TicTacToeRulesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_tictactoe);

        Button startButton = findViewById(R.id.btnStartGame);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This will be the actual game activity (to be created later)
                Intent intent = new Intent(TicTacToeRulesActivity.this, TicTacToeGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
