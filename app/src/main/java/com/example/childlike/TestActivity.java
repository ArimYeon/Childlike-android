package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.childlike.draw.DrawView;
import com.example.childlike.retrofit.RetrofitManager;
import com.example.childlike.retrofit.retrofitdata.RequestResultPost;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

import static com.example.childlike.LoginActivity.kuid;
import static com.example.childlike.MainActivity.TEST_TITLE;
import static com.example.childlike.MypageActivity.SELECTED_USER;
import static com.example.childlike.draw.DrawCature.PictureSaveToBitmapFile;

public class TestActivity extends AppCompatActivity {

    private Call<RequestBody> call;

    TextView title;
    ImageView backBtn;
    DrawView drawView;
    Button cancelBtn, resetBtn, completeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        title = findViewById(R.id.test_title);
        backBtn = findViewById(R.id.test_back_btn);
        drawView = findViewById(R.id.painter);
        cancelBtn = findViewById(R.id.cancel_btn);
        resetBtn = findViewById(R.id.reset_btn);
        completeBtn = findViewById(R.id.complete_btn);
        getIntentData();
        setListeners();

    }

    private void getIntentData(){
        Intent intent = getIntent();
        String testTitle = intent.getExtras().getString(TEST_TITLE);
        title.setText(testTitle+" 테스트");
    }

    private void setListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = nowDateForImgName();
                String filename = kuid+"_"+date;
                Log.d("img_date","이미지 파일명: "+filename);
                storeImage(filename);
                uploadImage(filename);
                //itype 임의 지정 해놨음
                RequestResultPost item = new RequestResultPost(SELECTED_USER, filename+".png", nowDate(), kuid, "2");
                retrofitResultPost(item);
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("code", 202);
                intent.putExtra("name", SELECTED_USER);
                intent.putExtra("uid", kuid);
                startActivity(intent);
                finish();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.reset();
            }
        });
    }

    private void storeImage(String filename){
        //내부저장소 캐시 경로를 받아옵니다.
        File storage = getCacheDir();
        PictureSaveToBitmapFile(drawView, storage, filename);
    }

    private void uploadImage(String filename){
        //내부저장소 캐시 경로를 받아옵니다.
        File storage = getCacheDir();
        String image_path = storage+"/"+filename+".png";
        File imageFile = new File(image_path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), imageFile);
        MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("uimage", imageFile.getName(), requestBody);
        call = RetrofitManager.createApi().uploadFile(multiPartBody);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.isSuccessful()) {
                    Log.d("img_retrofit","서버에 이미지 전달 성공");
                } else {
                    Log.d("img_retrofit","서버에 이미지 전달 실패 : "+response.message());
                }
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.d("img_retrofit", "fail "+t.toString());
            }
        });
    }

    private void retrofitResultPost(RequestResultPost resultItem){
        Call<RequestResultPost> call = RetrofitManager.createApi().postResult(resultItem);
        call.enqueue(new Callback<RequestResultPost>() {
            @Override
            public void onResponse(Call<RequestResultPost> call, Response<RequestResultPost> response) {
                if (response.isSuccessful()) {
                    Log.d("retrofit_result_post","서버에 값을 전달했습니다");
                } else {
                    Log.d("retrofit_result_post","서버에 값 전달을 실패했습니다 : "+response.message());
                }
            }

            @Override
            public void onFailure(Call<RequestResultPost> call, Throwable t) {
                Log.d("retrofit_result_post","서버와 통신중 에러가 발생했습니다 : "+t.toString());
            }
        });
    }

    //오늘 날짜,시간 받아오기
    private String nowDateForImgName(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyMMddHHmmss");
        String formatDate = sdfNow.format(date);
        return formatDate;
    }
    private String nowDate(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yy.MM.dd");
        String formatDate = sdfNow.format(date);
        return formatDate;
    }
}
