package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SelectTestItem> list = new ArrayList<>();
    ImageView mypageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mypageBtn = findViewById(R.id.mypage_btn);

        setListeners();

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        list.add(new SelectTestItem("나무"));
        list.add(new SelectTestItem("집"));
        list.add(new SelectTestItem("사람"));
        list.add(new SelectTestItem("어항"));
        list.add(new SelectTestItem("나무"));
        list.add(new SelectTestItem("나무"));
        list.add(new SelectTestItem("사람"));
        list.add(new SelectTestItem("어항"));
        list.add(new SelectTestItem("사람"));
        list.add(new SelectTestItem("어항"));

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.select_test_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SelectTestAdapter adapter = new SelectTestAdapter(list);
        adapter.setOnItemClickListener(new SelectTestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                SelectTestItem item = list.get(position);
                String testTitle = item.getTitle();
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                intent.putExtra("testTitle", testTitle);
                startActivity(intent);
            }
        }) ;
        recyclerView.setAdapter(adapter);
    }

    private void setListeners(){
        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent);
            }
        });
    }
}
