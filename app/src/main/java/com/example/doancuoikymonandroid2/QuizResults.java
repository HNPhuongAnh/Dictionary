package com.example.doancuoikymonandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizResults extends AppCompatActivity {

    Button btnStartNewGame;
    TextView tvCorrectAnswers, tvIncorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        btnStartNewGame = (Button) findViewById(R.id.btnStartNewGame);
        tvCorrectAnswers = (TextView) findViewById(R.id.tvCorrectAnswers);
        tvIncorrectAnswers = (TextView) findViewById(R.id.tvIncorrectAnswers);

        int getCorrectAnswers = getIntent().getIntExtra("correct", 0);
        int getIncorrectAnswers = getIntent().getIntExtra("incorrect", 0);

        tvCorrectAnswers.setText(String.valueOf(getCorrectAnswers));
        tvIncorrectAnswers.setText(String.valueOf(getIncorrectAnswers));

        btnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizResults.this, QuizMenu.class));
                finish();
            }
        });

    }
}