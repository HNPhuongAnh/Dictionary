package com.example.doancuoikymonandroid2;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {

    TextView tvTimer, selectedLevelName, tvQuestions, tvQuestion;
    ImageView btnBack;
    AppCompatButton btnOption1, btnOption2, btnOption3, btnOption4;
    Button btnNext;
    String getSelectedLevelName;
    Timer quizTimer;
    int totalTimeInMins = 1;
    int seconds = 0;
    List<QuestionsList> questionsLists;
    int currentQuestionPosition = 0;
    String selectedOptionByUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        selectedLevelName = (TextView) findViewById(R.id.tvLevelName);
        tvQuestions = (TextView) findViewById(R.id.tvQuestions);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);

        btnOption1 = (AppCompatButton) findViewById(R.id.btnOption1);
        btnOption2 = (AppCompatButton) findViewById(R.id.btnOption2);
        btnOption3 = (AppCompatButton) findViewById(R.id.btnOption3);
        btnOption4 = (AppCompatButton) findViewById(R.id.btnOption4);
        btnNext = (Button) findViewById(R.id.btnNext);

        getSelectedLevelName = getIntent().getStringExtra("selectedLevel");
        selectedLevelName.setText(getSelectedLevelName);

        questionsLists = QuestionsBank.getQuestions(getSelectedLevelName);

        startTimer(tvTimer);

        tvQuestions.setText((currentQuestionPosition+1)+"/"+questionsLists.size());
        tvQuestion.setText(questionsLists.get(0).getTvQuestion());
        btnOption1.setText(questionsLists.get(0).getBtnOption1());
        btnOption2.setText(questionsLists.get(0).getBtnOption2());
        btnOption3.setText(questionsLists.get(0).getBtnOption3());
        btnOption4.setText(questionsLists.get(0).getBtnOption4());

        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = btnOption1.getText().toString();
                    btnOption1.setBackgroundResource(R.drawable.round_back_red10);
                    btnOption1.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = btnOption2.getText().toString();
                    btnOption2.setBackgroundResource(R.drawable.round_back_red10);
                    btnOption2.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = btnOption3.getText().toString();
                    btnOption3.setBackgroundResource(R.drawable.round_back_red10);
                    btnOption3.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        btnOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = btnOption4.getText().toString();
                    btnOption4.setBackgroundResource(R.drawable.round_back_red10);
                    btnOption4.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    Toast.makeText(QuizActivity.this, "Please select an option!", Toast.LENGTH_SHORT).show();
                }
                else {
                    changeNextQuestion();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizTimer.purge();
                quizTimer.cancel();

                startActivity(new Intent(QuizActivity.this, QuizMenu.class));
                finish();
            }
        });
    }

    private void changeNextQuestion(){

        if(currentQuestionPosition < questionsLists.size() - 1){
            selectedOptionByUser = "";
            currentQuestionPosition++;
            btnOption1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            btnOption1.setTextColor(Color.parseColor("#1F6BB8"));

            btnOption2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            btnOption2.setTextColor(Color.parseColor("#1F6BB8"));

            btnOption3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            btnOption3.setTextColor(Color.parseColor("#1F6BB8"));

            btnOption4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            btnOption4.setTextColor(Color.parseColor("#1F6BB8"));

            tvQuestions.setText((currentQuestionPosition+1) + "/" + questionsLists.size());
            tvQuestion.setText(questionsLists.get(currentQuestionPosition).getTvQuestion());
            btnOption1.setText(questionsLists.get(currentQuestionPosition).getBtnOption1());
            btnOption2.setText(questionsLists.get(currentQuestionPosition).getBtnOption2());
            btnOption3.setText(questionsLists.get(currentQuestionPosition).getBtnOption3());
            btnOption4.setText(questionsLists.get(currentQuestionPosition).getBtnOption4());
            if((currentQuestionPosition+1) == questionsLists.size()){
                btnNext.setText("Finish game");
            }
        }


        else{
            Intent intent = new Intent(QuizActivity.this, QuizResults.class);
            intent.putExtra("correct", getCorrectAnswers());
            intent.putExtra("incorrect", getIncorrectAnswers());
            startActivity(intent);

            quizTimer.cancel();
            quizTimer.purge();
            finish();
        }
    }


    private void startTimer(TextView timerTextView){
        quizTimer = new Timer();
        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(seconds == 0){
                    totalTimeInMins--;
                    seconds = 59;
                }
                else if(seconds == 0 && totalTimeInMins == 0){
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(QuizActivity.this, "Time over!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(QuizActivity.this, QuizResults.class);
                    intent.putExtra("correct", getCorrectAnswers());
                    intent.putExtra("incorrect", getIncorrectAnswers());
                    startActivity(intent);

                    finish();
                }
                else{
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if(finalMinutes.length() == 1){
                            finalMinutes = "0" + finalMinutes;
                        }
                        if(finalSeconds.length() == 1){
                            finalSeconds = "0" + finalSeconds;
                        }

                        timerTextView.setText(finalMinutes + ":" + finalSeconds);

                        if (totalTimeInMins == 0 && seconds == 0) {
                            quizTimer.purge();
                            quizTimer.cancel();

                            Toast.makeText(QuizActivity.this, "Time Over!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(QuizActivity.this, QuizResults.class);
                            intent.putExtra("correct", getCorrectAnswers());
                            intent.putExtra("incorrect", getIncorrectAnswers());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }, 1000, 1000);
    }


    private int getCorrectAnswers(){
        int correctAnswers = 0;

        for(int i = 0; i < questionsLists.size(); i++){
            String getUserSelectedAnswer = questionsLists.get(i).getUserSelectedAnswer();
            String getAnswer = questionsLists.get(i).getAnswer();

            if(getUserSelectedAnswer != null && getAnswer != null){
                if(getUserSelectedAnswer.equals(getAnswer)){
                    correctAnswers++;
                }
            }
        }
        return correctAnswers;
    }

    private int getIncorrectAnswers(){
        int incorrectAnswers = 0;

        for(int i = 0; i < questionsLists.size(); i++){
            String getUserSelectedAnswer = questionsLists.get(i).getUserSelectedAnswer();
            String getAnswer = questionsLists.get(i).getAnswer();

            if(getUserSelectedAnswer != null && getAnswer != null){
                if(!getUserSelectedAnswer.equals(getAnswer)){
                    incorrectAnswers++;
                }
            }
        }
        return incorrectAnswers;
    }

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(QuizActivity.this, QuizMenu.class));
        finish();
    }

    private void revealAnswer(){
        String getAnswer = questionsLists.get(currentQuestionPosition).getAnswer();

        if(btnOption1.getText().toString().equals(getAnswer)){
            btnOption1.setBackgroundResource(R.drawable.round_back_green10);
            btnOption1.setTextColor(Color.WHITE);
        }
        else if(btnOption2.getText().toString().equals(getAnswer)){
            btnOption2.setBackgroundResource(R.drawable.round_back_green10);
            btnOption2.setTextColor(Color.WHITE);
        }
        else if(btnOption3.getText().toString().equals(getAnswer)){
            btnOption3.setBackgroundResource(R.drawable.round_back_green10);
            btnOption3.setTextColor(Color.WHITE);
        }
        else if(btnOption4.getText().toString().equals(getAnswer)){
            btnOption4.setBackgroundResource(R.drawable.round_back_green10);
            btnOption4.setTextColor(Color.WHITE);
        }
    }
}