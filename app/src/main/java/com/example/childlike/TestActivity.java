package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.childlike.MainActivity.TEST_TITLE;

public class TestActivity extends AppCompatActivity {

    TextView title;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        title = findViewById(R.id.test_title);
        backBtn = findViewById(R.id.test_back_btn);
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
    }
}
