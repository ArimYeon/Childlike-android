package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.childlike.dataclass.SelectTestItem;

import java.util.ArrayList;

import static com.example.childlike.MypageActivity.SELECTED_USER;

public class MainActivity extends AppCompatActivity {

    public final static String TEST_TITLE = "testTitle";

    ArrayList<SelectTestItem> list = new ArrayList<>();
    ImageView mypageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mypageBtn = findViewById(R.id.mypage_btn);

        getSelectedUser();
        setListeners();
        initList();

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.select_test_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SelectTestAdapter adapter = new SelectTestAdapter(list);
        adapter.setOnItemClickListener(new SelectTestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                SelectTestItem item = list.get(position);
                String testTitle = item.getTitle();
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                intent.putExtra(TEST_TITLE, testTitle);
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

    private void getSelectedUser(){
        SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
        SELECTED_USER = a.getString("selectedUser","");
    }

    private void initList(){
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        list.add(new SelectTestItem("나무"));
        list.add(new SelectTestItem("집"));
        list.add(new SelectTestItem("사람"));
    }
}
