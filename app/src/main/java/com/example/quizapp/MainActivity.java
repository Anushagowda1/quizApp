package com.example.quizapp;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView questionTextView;
    TextView totalQuestionTextView;
    Button ansA, ansB, ansC, ansD;
    Button btn_submit;

    int score = 0;
    int totalQuestion = com.example.quizapp.QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalQuestionTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        btn_submit = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        totalQuestionTextView.setText("Total questions: " + totalQuestion);

        loadNewQuestion();
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }
        questionTextView.setText(com.example.quizapp.QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(com.example.quizapp.QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(com.example.quizapp.QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(com.example.quizapp.QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(com.example.quizapp.QuestionAnswer.choices[currentQuestionIndex][3]);
        // Reset button background colors and selected answer
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);
        selectedAnswer = "";
    }

    private void finishQuiz() {
        String passStatus = (score >= totalQuestion * 0.6) ? "Passed" : "Failed";
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialog, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;

        if (clickedButton.getId() == R.id.submit_btn) {
            if (!selectedAnswer.isEmpty()) {
                // Check the selected answer
                if (selectedAnswer.equals(com.example.quizapp.QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                    score++;
                }

                // Move to the next question
                currentQuestionIndex++;
                loadNewQuestion();
            } else {
                // No answer selected
                new AlertDialog.Builder(this)
                        .setMessage("Please select an answer before submitting!")
                        .setPositiveButton("OK", null)
                        .show();
            }
        } else {
            // Set the selected answer and change the button color
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.YELLOW);
        }
    }
}
