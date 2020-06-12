package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.childlike.retrofit.RetrofitManager;
import com.example.childlike.retrofit.retrofitdata.RequestChildrenPost;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.childlike.LoginActivity.CODE;
import static com.example.childlike.LoginActivity.kuid;
import static com.example.childlike.MypageActivity.SELECTED_USER;

public class InfoActivity extends AppCompatActivity {

    private Call<RequestBody> call;

    private static final int PICK_FROM_ALBUM = 1;

    String imageFileName = null;

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
                RequestChildrenPost children = makeRequestChildrenPost();
                retrofitPutChildrenData(children);
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
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                //Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });
    }

    //RequestChildrenPost객체 생성
    private RequestChildrenPost makeRequestChildrenPost(){
        String cage = age.getText().toString();
        String csex = sex.getSelectedItem().toString();
        String cname = name.getText().toString();
        RequestChildrenPost children;
        if(imageFileName!=null){
            children = new RequestChildrenPost(cage, csex, cname, imageFileName ,kuid);
        }
        else{
            children = new RequestChildrenPost(cage, csex, cname, null ,kuid);
        }
        return children;
    }

    //retrofit으로 children 정보 서버에 저장
    private void retrofitPutChildrenData(RequestChildrenPost children){
        Log.d("children", children.getAge()+" "+children.getGender()+" "+children.getName()+" "+children.getImage()+" "+children.getUid());
        Call<RequestChildrenPost> call = RetrofitManager.createApi().postChildren(children);
        call.enqueue(new Callback<RequestChildrenPost>(){
            @Override
            public void onResponse(Call<RequestChildrenPost> call, Response<RequestChildrenPost> response) {
                if (response.isSuccessful()) {
                    Log.d("retrofit_children","서버에 값을 전달했습니다");
                } else {
                    Log.d("retrofit_children","서버에 값 전달을 실패했습니다 : "+response.message());
                }
            }

            @Override
            public void onFailure(Call<RequestChildrenPost> call, Throwable t) {
                Log.d("retrofit_children","서버와 통신중 에러가 발생했습니다 : "+t.toString());
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
                    String imagePath = getRealPathFromURI(data.getData());
                    sendProfileImage(imagePath);

                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    profileImg.setImageBitmap(img);
                }catch(Exception e) {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
               Log.e("image", "사진 선택 취소");
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        return cursor.getString(column_index);
    }

    private void sendProfileImage(String imagePath){
        File file = new File(imagePath);
        imageFileName = file.getName();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("uprofile", file.getName(), requestBody);
        call = RetrofitManager.createApi().uploadProfile(multiPartBody);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.isSuccessful()) {
                    Log.d("profile_img_retrofit","서버에 이미지 전달 성공");
                } else {
                    Log.d("profile_img_retrofit","서버에 이미지 전달 실패 : "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.d("profile_img_retrofit", "fail "+t.toString());
            }
        });
    }

    private void getIntentCode(){
        Intent intent = getIntent();
        code = intent.getIntExtra(CODE, 0);
        if(code==100) backBtn.setVisibility(View.INVISIBLE);
        else if(code==101) backBtn.setVisibility(View.VISIBLE);
    }
}
