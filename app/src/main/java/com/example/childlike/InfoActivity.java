package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.childlike.LoginActivity.CODE;
import static com.example.childlike.MypageActivity.SELECTED_USER;

public class InfoActivity extends AppCompatActivity {

    TextView cmpBtn, name, age;
    Spinner sex;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        cmpBtn = findViewById(R.id.info_cmp_btn);
        name = findViewById(R.id.name_text);
        age = findViewById(R.id.age_text);
        sex = findViewById(R.id.sex_spin);

        setListeners();
        getIntentCode();
    }

    private void setListeners(){
        cmpBtn.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                SELECTED_USER = name.getText().toString();
                SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
                SharedPreferences.Editor editor = a.edit();
                editor.putString("selectedUser", SELECTED_USER);
                editor.commit();
                if(code == 100) {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(code == 101){
                    intent = new Intent(getApplicationContext(), MypageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    private void getIntentCode(){
        Intent intent = getIntent();
        code = intent.getIntExtra(CODE, 0);
    }
}
