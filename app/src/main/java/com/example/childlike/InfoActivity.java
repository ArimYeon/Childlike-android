package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.InputStream;
import java.util.ArrayList;

import static com.example.childlike.LoginActivity.CODE;
import static com.example.childlike.MypageActivity.SELECTED_USER;

public class InfoActivity extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 1;

    TextView cmpBtn, name, age;
    ImageView backBtn, profileImg;
    Spinner sex;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        cmpBtn = findViewById(R.id.info_cmp_btn);
        name = findViewById(R.id.name_text);
        age = findViewById(R.id.age_text);
        sex = findViewById(R.id.sex_spin);
        backBtn = findViewById(R.id.info_back_btn);
        profileImg = findViewById(R.id.info_img);

        setListeners();
        getIntentCode();
    }

    private void setListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cmpBtn.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                SELECTED_USER = name.getText().toString();
                SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
                SharedPreferences.Editor editor = a.edit();
                editor.putString("selectedUser", SELECTED_USER);
                editor.commit();
                if(code == 100) {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(code == 101){
                    intent = new Intent(getApplicationContext(), MypageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tedPermission();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });
    }

    private void tedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                Log.d("permission","권한 요청 성공");
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                Log.d("permission", "권한 요청 실패");
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PICK_FROM_ALBUM)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    profileImg.setImageBitmap(img);
                }catch(Exception e) {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void getIntentCode(){
        Intent intent = getIntent();
        code = intent.getIntExtra(CODE, 0);
        if(code==100) backBtn.setVisibility(View.INVISIBLE);
        else if(code==101) backBtn.setVisibility(View.VISIBLE);
    }
}
