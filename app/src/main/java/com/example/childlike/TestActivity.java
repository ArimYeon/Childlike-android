package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        title = findViewById(R.id.test_title);
        getIntentData();

        ImageView back = findViewById(R.id.test_back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIntentData(){
        Intent intent = getIntent();
        String testTitle = intent.getExtras().getString("testTitle");
        title.setText(testTitle+" 테스트");
    }
}
