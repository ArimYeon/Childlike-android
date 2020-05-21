package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.childlike.draw.DrawView;

import static com.example.childlike.MainActivity.TEST_TITLE;
import static com.example.childlike.MypageActivity.SELECTED_USER;

public class TestActivity extends AppCompatActivity {

    TextView title;
    ImageView backBtn;
    DrawView drawView;
    Button cancelBtn, resetBtn, completeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        title = findViewById(R.id.test_title);
        backBtn = findViewById(R.id.test_back_btn);
        drawView = findViewById(R.id.painter);
        cancelBtn = findViewById(R.id.cancel_btn);
        resetBtn = findViewById(R.id.reset_btn);
        completeBtn = findViewById(R.id.complete_btn);
        getIntentData();
        setListeners();

    }

    private void getIntentData(){
        Intent intent = getIntent();
        String testTitle = intent.getExtras().getString(TEST_TITLE);
        title.setText(testTitle+" 테스트");
    }

    private void setListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("code", 202);
                intent.putExtra("name", SELECTED_USER);
                startActivity(intent);
                finish();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.reset();
            }
        });
    }
}
