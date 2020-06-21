package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.childlike.retrofit.RetrofitManager;
import com.example.childlike.retrofit.retrofitdata.RequestCommentsGet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {

    String date, img, comment, itype;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView loadimg = findViewById(R.id.loading_gif_img);
        Glide.with(this).load(R.drawable.loading).into(loadimg);

        getIntents();
        retrofitCommentsGet();
        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), DetailResultActivity.class);
                intent.putExtra("code", code);
                intent.putExtra("img", img);
                intent.putExtra("date", date);
                intent.putExtra("result", comment);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        }, 2000);
    }

    private void retrofitCommentsGet(){
        Call<RequestCommentsGet> call = RetrofitManager.createApi().getComment(itype);
        call.enqueue(new Callback<RequestCommentsGet>() {
            @Override
            public void onResponse(Call<RequestCommentsGet> call, Response<RequestCommentsGet> response) {
                if(response.isSuccessful()){
                    RequestCommentsGet comments = response.body();
                    comment = comments.getCtext();
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

    private void getIntents(){
        Intent intent = getIntent();
        img = intent.getStringExtra("img");
        date = intent.getStringExtra("date");
        itype = intent.getStringExtra("itype");
        code = intent.getIntExtra("code",0);
    }
}
