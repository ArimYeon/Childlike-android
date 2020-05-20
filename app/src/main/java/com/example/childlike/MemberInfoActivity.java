package com.example.childlike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import static com.example.childlike.LoginActivity.kAge;
import static com.example.childlike.LoginActivity.kEmail;
import static com.example.childlike.LoginActivity.kGender;
import static com.example.childlike.LoginActivity.kNick;

public class MemberInfoActivity extends AppCompatActivity {

    ImageView mInfoBtn, profileImg;
    Button logoutBtn;
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
        setListners();
        setProfile();
    }

    private void setProfile(){
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

    private void redirectLoginActivity(){
        final Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
