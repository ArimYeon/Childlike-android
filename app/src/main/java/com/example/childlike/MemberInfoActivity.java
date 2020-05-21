package com.example.childlike;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import static com.example.childlike.LoginActivity.kAge;
import static com.example.childlike.LoginActivity.kEmail;
import static com.example.childlike.LoginActivity.kGender;
import static com.example.childlike.LoginActivity.kNick;
import static com.example.childlike.LoginActivity.kProfileImg;

public class MemberInfoActivity extends AppCompatActivity {

    ImageView mInfoBtn, profileImg;
    Button logoutBtn, withdrawalBtn;
    TextView nick, email, age, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        mInfoBtn = findViewById(R.id.member_info_back_btn);
        profileImg = findViewById(R.id.profile_img);
        logoutBtn = findViewById(R.id.logout_btn);
        nick = findViewById(R.id.member_info_name_text);
        email = findViewById(R.id.email_text);
        age = findViewById(R.id.age_text);
        gender = findViewById(R.id.gender_text);
        withdrawalBtn = findViewById(R.id.withdrawal_btn);
        setListners();
        setProfile();
    }

    private void setProfile(){
        imageDownload();
        nick.setText(kNick+" 님");
        email.setText(kEmail);
        age.setText(kAge);
        gender.setText(kGender);
    }

    private void setListners(){
        mInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        withdrawalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MemberInfoActivity.this);
                builder.setTitle("회원 탈퇴").setMessage("회원 탈퇴를 하게 되면 기존의 데이터가 사라집니다.");
                builder.setPositiveButton("탈퇴", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        withdrawal();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });
    }

    private void imageDownload(){
        if(kProfileImg != null){
            Glide.with(this).load(kProfileImg).into(profileImg);
        }
    }

    private void logout(){
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Log.i("KAKAO_API", "로그아웃 완료");
                redirectLoginActivity();
            }
        });
    }

    private void withdrawal(){
        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("KAKAO_API", "연결 끊기 실패: " + errorResult);

            }
            @Override
            public void onSuccess(Long result) {
                Log.i("KAKAO_API", "연결 끊기 성공. id: " + result);
                SharedPreferences loginPref = getSharedPreferences("loginPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginPref.edit();
                editor.putInt("isLogin", 0);
                editor.commit();
                redirectLoginActivity();
            }
        });
    }

    private void redirectLoginActivity(){
        final Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
