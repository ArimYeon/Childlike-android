package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.childlike.LoginActivity.CODE;
import static com.example.childlike.MypageActivity.SELECTED_USER;

public class InfoActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView cmpBtn, name, age;
    Spinner sex;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        backBtn = findViewById(R.id.info_back_btn);
        cmpBtn = findViewById(R.id.info_cmp_btn);
        name = findViewById(R.id.name_text);
        age = findViewById(R.id.age_text);
        sex = findViewById(R.id.sex_spin);

        setListeners();
        getIntentCode();
    }

    private void setListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cmpBtn.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                if(code == 100) {
                    SELECTED_USER = name.getText().toString();
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(code == 101){
                    intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void getIntentCode(){
        Intent intent = getIntent();
        code = intent.getIntExtra(CODE, 0);
    }
}
