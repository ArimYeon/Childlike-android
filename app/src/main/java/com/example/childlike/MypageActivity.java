package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.childlike.dataclass.MypageItem;

import java.util.ArrayList;

import static com.example.childlike.LoginActivity.CODE;

public class MypageActivity extends AppCompatActivity {

    private final static int INFO_REQ_CODE = 101;
    private final static int RESULT_FROM_MYPAGE_CODE = 201;
    public static String SELECTED_USER;

    ArrayList<MypageItem> list = new ArrayList<>();
    ImageView backBtn;
    Button addUserBtn, memberInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        backBtn = findViewById(R.id.mypage_back_btn);
        addUserBtn = findViewById(R.id.add_user_btn);
        memberInfoBtn = findViewById(R.id.member_info_btn);
        setListeners();
        initList();

        SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
        SharedPreferences.Editor edit = a.edit();
        edit.putString("selectedUser", SELECTED_USER);

        RecyclerView recyclerView = findViewById(R.id.mypage_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 MypageAdapter 객체 지정.
        MypageAdapter adapter = new MypageAdapter(list);
        adapter.setOnButtonClickListener(new MypageAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(View v, int position) {
                MypageItem item = list.get(position);
                String name = item.getName();
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("code",RESULT_FROM_MYPAGE_CODE);
                startActivity(intent);
            }
        }) ;
        adapter.setOnRadioButtonClickListener(new MypageAdapter.OnRadioButtonClickListener() {
            @Override
            public void onRadioButtonClick(int position) {
                MypageItem item = list.get(position);
                String name = item.getName();
                SELECTED_USER = name;
                SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
                SharedPreferences.Editor editor = a.edit();
                editor.putString("selectedUser", SELECTED_USER);
                editor.commit();
                //Log.d("MypageActivity","선택된 유저 : "+SELECTED_USER);
            }
        });
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
                startActivity(intent);
            }
        });
        memberInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MemberInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initList(){
        list.add(new MypageItem(R.drawable.img_110x110, "연아림", "24","여자"));
        list.add(new MypageItem(R.drawable.img_110x110, "나희원", "24","여자"));
        list.add(new MypageItem(R.drawable.img_110x110, "이슬기", "24","여자"));
        list.add(new MypageItem(R.drawable.img_110x110, "정주연", "24","여자"));
        list.add(new MypageItem(R.drawable.img_110x110, "이호진", "23","여자"));
        list.add(new MypageItem(R.drawable.img_110x110, "김연정", "23","여자"));
    }
}
