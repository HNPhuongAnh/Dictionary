package com.example.doancuoikymonandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class QuizMenu extends AppCompatActivity {

    LinearLayout ielts, toeic, cambridge, cefr;
    Button btnStartQuiz, btnBack;
    String selectedLevelName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);

        ielts = (LinearLayout) findViewById(R.id.ietls);
        toeic = (LinearLayout) findViewById(R.id.toeic);
        cambridge = (LinearLayout) findViewById(R.id.cambridge);
        cefr = (LinearLayout) findViewById(R.id.cefr);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnStartQuiz = (Button) findViewById(R.id.btnStartQuiz);

        ielts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevelName = "ielts";
                ielts.setBackgroundResource(R.drawable.round_back_white_stroke10);
                toeic.setBackgroundResource(R.drawable.round_back_white10);
                cambridge.setBackgroundResource(R.drawable.round_back_white10);
                cefr.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        toeic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevelName = "toeic";
                toeic.setBackgroundResource(R.drawable.round_back_white_stroke10);
                ielts.setBackgroundResource(R.drawable.round_back_white10);
                cambridge.setBackgroundResource(R.drawable.round_back_white10);
                cefr.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        cambridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevelName = "cambridge";
                cambridge.setBackgroundResource(R.drawable.round_back_white_stroke10);
                ielts.setBackgroundResource(R.drawable.round_back_white10);
                toeic.setBackgroundResource(R.drawable.round_back_white10);
                cefr.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        cefr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLevelName = "cefr";
                cefr.setBackgroundResource(R.drawable.round_back_white_stroke10);
                ielts.setBackgroundResource(R.drawable.round_back_white10);
                cambridge.setBackgroundResource(R.drawable.round_back_white10);
                toeic.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedLevelName.isEmpty()){
                    Toast.makeText(QuizMenu.this, "Please select a level!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(QuizMenu.this, QuizActivity.class);
                    intent.putExtra("selectedLevel", selectedLevelName);
                    startActivity(intent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
