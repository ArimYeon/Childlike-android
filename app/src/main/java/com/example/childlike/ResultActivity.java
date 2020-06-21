package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.childlike.dataclass.ResultItem;
import com.example.childlike.dataclass.ResultTypeItem;
import com.example.childlike.retrofit.RetrofitManager;
import com.example.childlike.retrofit.retrofitdata.RequestCommentsGet;
import com.example.childlike.retrofit.retrofitdata.RequestResultGet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<ResultItem> list = new ArrayList<>();
    private ArrayList<ResultTypeItem> typeList = new ArrayList<>();
    private ArrayList<RequestResultGet> apiData = new ArrayList<>();

    //int code;
    String name, uid;
    ImageView backBtn;
    TextView title;
    ResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getIntentCode();
        retrofitResultGet(uid, name);
        backBtn = findViewById(R.id.result_back_btn);
        title = findViewById(R.id.result_title);
        setListeners();
        title.setText(name+"의 결과");

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.result_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new ResultAdapter(list);
        adapter.setOnItemClickListener(new ResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //리사이클러뷰 객체 누르면 결과 상세페이지로 구현
                ResultItem item = list.get(position);
                String img = item.getImg();
                String date = item.getDate();
                String result = item.getResult();
                Intent intent = new Intent(getApplicationContext(), DetailResultActivity.class);
                intent.putExtra("code",201);
                intent.putExtra("img", img);
                intent.putExtra("date", date);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        }) ;
        recyclerView.setAdapter(adapter);
    }

    private void setListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                /*
                //테스트페이지에서 왔으면 mainActivity로
                //마이페이지에서 왔으면 마이페이지로
                if(code==201){
                    finish();
                } else if(code==202){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                 */
            }
        });
    }

    private void retrofitResultGet(String uid, String name){
        Call<ArrayList<RequestResultGet>> call = RetrofitManager.createApi().getResult(uid, name);
        call.enqueue(new Callback<ArrayList<RequestResultGet>>() {
            @Override
            public void onResponse(Call<ArrayList<RequestResultGet>> call, Response<ArrayList<RequestResultGet>> response) {
                if(response.isSuccessful()){
                    apiData = response.body();
                    for(RequestResultGet item : apiData){
                        String date = item.getDate();
                        String img = item.getUimage();
                        String type = item.getItype();
                        typeList.add(new ResultTypeItem(img, date, type));
                    }
                    retrofitCommentsGet();
                }
                else{
                    Log.d("Result_Get", "result 가져오기 실패: "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RequestResultGet>> call, Throwable t) {
                Log.d("Result_Get", t.toString());
            }
        });
    }

    private void retrofitCommentsGet(){
        for(ResultTypeItem typeItem : typeList){
            final String img = typeItem.getImg();
            final String date = typeItem.getDate();
            Call<RequestCommentsGet> call = RetrofitManager.createApi().getComment(typeItem.getType());
            call.enqueue(new Callback<RequestCommentsGet>() {
                @Override
                public void onResponse(Call<RequestCommentsGet> call, Response<RequestCommentsGet> response) {
                    if(response.isSuccessful()){
                        RequestCommentsGet comments = response.body();
                        String comment = comments.getCtext();
                        list.add(new ResultItem(img, date, comment));
                        adapter.setData(list);
                    }
                    else{
                        Log.d("Comment_Get", "comment 가져오기 실패");
                    }
                }

                @Override
                public void onFailure(Call<RequestCommentsGet> call, Throwable t) {
                    Log.d("Comment_Get", t.toString());
                }
            });
        }
    }

    private void getIntentCode(){
        Intent intent = getIntent();
        //code = intent.getIntExtra("code", 0);
        name = intent.getStringExtra("name");
        uid = intent.getStringExtra("uid");
    }
}
