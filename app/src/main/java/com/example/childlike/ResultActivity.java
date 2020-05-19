package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<ResultItem> list = new ArrayList<>();

    int code;
    String name;
    ImageView backBtn;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initList();
        getIntentCode();
        backBtn = findViewById(R.id.result_back_btn);
        title = findViewById(R.id.result_title);
        setListeners();
        title.setText(name+"의 결과");

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.result_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        ResultAdapter adapter = new ResultAdapter(list);
        adapter.setOnItemClickListener(new ResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //리사이클러뷰 객체 누르면 결과 상세페이지로 구현
            }
        }) ;
        recyclerView.setAdapter(adapter);
    }

    private void setListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //테스트페이지에서 왔으면 mainActivity로
                //마이페이지에서 왔으면 마이페이지로
                if(code==201){
                    finish();
                } else if(code==202){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }
        });
    }

    private void getIntentCode(){
        Intent intent = getIntent();
        code = intent.getIntExtra("code", 0);
        name = intent.getStringExtra("name");
    }

    private void initList(){
        list.add(new ResultItem("20.05.19", "마음이 평온하고 행복합니다. 스트레스도 없습니다."));
        list.add(new ResultItem("20.05.18", "마음이 평온하고 행복합니다. 스트레스도 없습니다."));
        list.add(new ResultItem("20.04.19", "마음이 평온하고 행복합니다. 스트레스도 없습니다."));
        list.add(new ResultItem("20.01.19", "마음이 평온하고 행복합니다. 스트레스도 없습니다."));
        list.add(new ResultItem("19.05.19", "마음이 평온하고 행복합니다. 스트레스도 없습니다."));
    }
}
