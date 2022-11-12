package com.alcsource.genculturetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

//Developed by Paul Batut and Amaury Le Conte

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //we create a view for each element of the layout

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button answerA,answerB,answerC,answerD;
    Button submitButton;

    int score = 0;
    int totalQuestion = QuestionsAndAnswers.questions.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we reference all the views we created

        totalQuestionsTextView = findViewById(R.id.total_questions);
        questionTextView = findViewById(R.id.question);
        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);
        submitButton = findViewById(R.id.submit_button);

        //we set onClickListener to all the buttons

        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : " + totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        answerA.setBackgroundColor(Color.WHITE);
        answerB.setBackgroundColor(Color.WHITE);
        answerC.setBackgroundColor(Color.WHITE);
        answerD.setBackgroundColor(Color.WHITE);
        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_button) {
            if(selectedAnswer.equals(QuestionsAndAnswers.correctAnswers[currentQuestionIndex])) {
                score++; //if the answer is correct, the score goes up by 1
            }
            currentQuestionIndex++;
            loadNewQuestion(); //if clicking on submit button, the next question is loaded
        } else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GRAY); //when the user selects an answer, the button turns gray
        }
    }

    void loadNewQuestion() {

        if(currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionsAndAnswers.questions[currentQuestionIndex]); //we set the current question to the corresponding current index
        answerA.setText(QuestionsAndAnswers.choices[currentQuestionIndex][0]); //we set the current answers to the corresponding current index
        answerB.setText(QuestionsAndAnswers.choices[currentQuestionIndex][1]);
        answerC.setText(QuestionsAndAnswers.choices[currentQuestionIndex][2]);
        answerD.setText(QuestionsAndAnswers.choices[currentQuestionIndex][3]);
    }

    void finishQuiz() {
        String passStatus = "";
        if(score > totalQuestion*0.8) {
            passStatus = "You passed the culture test"; //if we get above 80% good answers, we pass the test
        } else {
            passStatus = "You failed the culture test";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart quizz",(dialogInterface, i) -> restartQuiz())
                .show();
    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion(); //sets all variables to 0 to reload first question and set score to 0
    }
}