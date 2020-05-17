package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.example.childlike.LoginActivity.CODE;

public class MypageActivity extends AppCompatActivity {

    private final static int INFO_REQ_CODE = 101;
    public static String SELECTED_USER;

    ArrayList<MypageItem> list = new ArrayList<>();
    ImageView backBtn;
    Button addUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        backBtn = findViewById(R.id.mypage_back_btn);
        addUserBtn = findViewById(R.id.add_user_btn);
        setListeners();
        initList();

        RecyclerView recyclerView = findViewById(R.id.mypage_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MypageAdapter adapter = new MypageAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void setListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra(CODE, 101);
                startActivityForResult(intent, INFO_REQ_CODE);
            }
        });
    }

    private void initList(){
        list.add(new MypageItem(R.drawable.logo3, "연아림", "24","여자"));
        list.add(new MypageItem(R.drawable.logo3, "나희원", "24","여자"));
        list.add(new MypageItem(R.drawable.logo3, "이슬기", "24","여자"));
        list.add(new MypageItem(R.drawable.logo3, "정주연", "24","여자"));
        list.add(new MypageItem(R.drawable.logo3, "이호진", "23","여자"));
        list.add(new MypageItem(R.drawable.logo3, "김연정", "23","여자"));
    }
}