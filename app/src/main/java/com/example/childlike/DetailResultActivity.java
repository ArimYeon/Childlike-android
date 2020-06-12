package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailResultActivity extends AppCompatActivity {

    Bitmap bitmap;
    String date, result, img;
    ImageView backBtn, userImg;
    TextView dateText, resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_result);

        backBtn = findViewById(R.id.detail_result_back_btn);
        userImg = findViewById(R.id.user_image);
        dateText = findViewById(R.id.date_text);
        resultText = findViewById(R.id.result_text);
        resultText.setMovementMethod(ScrollingMovementMethod.getInstance());
        getIntents();
        setListeners();
        setTexts();
    }

    private void setListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setTexts(){
        dateText.setText(date);
        resultText.setText(result);
    }

    private void getIntents(){
        Intent intent = getIntent();
        img = intent.getStringExtra("img");
        downloadUserDrawImg(img);
        userImg.setImageBitmap(bitmap);
        date = intent.getStringExtra("date");
        result = intent.getStringExtra("result");
    }

    private void downloadUserDrawImg(final String image){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL("http://52.79.242.93:8000/media/userimage/"+image);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        };
        thread.start();
        try{
            thread.join();

        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
