package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.childlike.dataclass.MypageItem;
import com.example.childlike.retrofit.RetrofitManager;
import com.example.childlike.retrofit.retrofitdata.RequestChildrenGet;
import com.example.childlike.retrofit.retrofitdata.RequestChildrenPost;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.childlike.LoginActivity.CODE;
import static com.example.childlike.LoginActivity.kuid;

public class MypageActivity extends AppCompatActivity {

    private final static int INFO_REQ_CODE = 101;
    private final static int RESULT_FROM_MYPAGE_CODE = 201;
    public static String SELECTED_USER;

    ArrayList<RequestChildrenGet> list = new ArrayList<>();
    //ArrayList<RequestChildrenGet> retrofitList = new ArrayList<>;
    ImageView backBtn;
    Button addUserBtn, memberInfoBtn;

    MypageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        backBtn = findViewById(R.id.mypage_back_btn);
        addUserBtn = findViewById(R.id.add_user_btn);
        memberInfoBtn = findViewById(R.id.member_info_btn);
        setListeners();
        retrofitChildrenGet();

        SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
        SharedPreferences.Editor edit = a.edit();
        edit.putString("selectedUser", SELECTED_USER);

        RecyclerView recyclerView = findViewById(R.id.mypage_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 MypageAdapter 객체 지정.
        adapter = new MypageAdapter(list);
        adapter.setOnButtonClickListener(new MypageAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(View v, int position) {
                RequestChildrenGet item = list.get(position);
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
                RequestChildrenGet item = list.get(position);
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

    private void retrofitChildrenGet(){
        Call<ArrayList<RequestChildrenGet>> call = RetrofitManager.createApi().getChildren(kuid);
        call.enqueue(new Callback<ArrayList<RequestChildrenGet>> (){
            @Override
            public void onResponse(Call<ArrayList<RequestChildrenGet>> call, Response<ArrayList<RequestChildrenGet>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    //Log.d("mypageList", list.get(0).getName()+list.get(0).getId());
                    //setMypageDataList(retrofitList);
                    adapter.setData(list);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RequestChildrenGet>> call, Throwable t) {
                Log.d("children_get","데이터 get 실패");
            }
        });
    }

    /*
    private void setMypageDataList(ArrayList<RequestChildrenGet> retrofitList){
        for(int i=0; i<retrofitList.size(); i++){
            list.add(new MypageItem(
                    retrofitList.get(i).getImage(),
                    retrofitList.get(i).getName(),
                    retrofitList.get(i).getAge(),
                    retrofitList.get(i).getGender()
            ));
        }
        //adapter.setData();
    }
    */
}
