package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailResultActivity extends AppCompatActivity {

    int img;
    String date, result;

    ImageView backBtn;
    TextView dateText, resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_result);

        backBtn = findViewById(R.id.detail_result_back_btn);
        dateText = findViewById(R.id.date_text);
        resultText = findViewById(R.id.result_text);
        resultText.setMovementMethod(ScrollingMovementMethod.getInstance());
        setListeners();
        getIntents();
        setTexts();
    }

    private void setListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setTexts(){
        dateText.setText(date);
        resultText.setText(result);
    }

    private void getIntents(){
        Intent intent = getIntent();
        img = intent.getIntExtra("img",0);
        date = intent.getStringExtra("date");
        result = intent.getStringExtra("result");
    }
}
