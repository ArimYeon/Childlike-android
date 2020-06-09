package com.example.childlike;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.childlike.retrofit.RetrofitManager;
import com.example.childlike.retrofit.retrofitdata.RequestAppuserPost;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.AgeRange;
import com.kakao.usermgmt.response.model.Gender;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Call<RequestAppuserPost> call;

    Session session;
    public static String kuid;
    public static String kProfileImg;
    public static String kNick;
    public static String kAge = "비공개";
    public static String kEmail = "비공개";
    public static String kGender = "비공개";

    public final static String CODE = "code";
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getHashKey();

        loginBtn = findViewById(R.id.custom_login_btn);
        init();
    }

    private void setListeners(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.open(AuthType.KAKAO_ACCOUNT, LoginActivity.this);
            }
        });
    }

    private void init(){
        initSession();
        setListeners();
    }

    private void initSession(){
        // 세션 콜백 등록
        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);
        //토큰 만료시 갱신 시켜줌
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    // 세션 콜백 구현
    private ISessionCallback sessionCallback = new ISessionCallback() {
        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공");
            requestMe();
        }

        // 로그인에 실패한 상태
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            //Log.e("KAKAO_SESSION", "로그인 실패", exception);
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }

        // 사용자 정보 요청
        public void requestMe() {
            // 사용자정보 요청 결과에 대한 Callback
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                // 세션 오픈 실패. 세션이 삭제된 경우
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                }

                // 사용자 정보 요청 실패
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                }

                // 사용자정보 요청에 성공한 경우,
                @Override
                public void onSuccess(MeV2Response result) {
                    String uid = Long.toString(result.getId());
                    Log.i("KAKAO_API", "사용자 아이디: " + uid);
                    kuid = uid;

                    UserAccount kakaoAccount = result.getKakaoAccount();
                    if (kakaoAccount != null) {
                        // 이메일
                        String email = kakaoAccount.getEmail();
                        if (email != null) {
                            Log.i("KAKAO_API", "email: " + email);
                            kEmail = email;
                        } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                            // 동의 요청 후 이메일 획득 가능
                            // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 함
                        } else {
                            // 이메일 획득 불가
                        }

                        // 프로필
                        Profile profile = kakaoAccount.getProfile();
                        if (profile != null) {
                            Log.d("KAKAO_API", "nickname: " + profile.getNickname());
                            Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl());
                            Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl());
                            kNick = profile.getNickname();
                            kProfileImg = profile.getThumbnailImageUrl();
                        } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                            // 동의 요청 후 프로필 정보 획득 가능
                        } else {
                            // 프로필 획득 불가
                        }

                        //나이
                        AgeRange ageRange = kakaoAccount.getAgeRange();
                        if(ageRange != null){
                            Log.d("KAKAO API", "age range: "+ageRange.getValue());
                            kAge = ageRange.getValue();
                        }

                        //성별
                        Gender gender = kakaoAccount.getGender();
                        if(gender != null){
                            Log.d("KAKAO API", "gender: "+gender.getValue());
                            kGender = gender.getValue();
                        }
                    }

                    retrofitPutAppuserData(kuid, kAge, kGender, kNick, kEmail);

                    SharedPreferences loginPref = getSharedPreferences("loginPref", MODE_PRIVATE);
                    int isLogin = loginPref.getInt("isLogin",0);
                    if(isLogin==0){
                        redirectInfoActivity();
                    }
                    else if(isLogin==1){
                        redirectMainActivity();
                    }
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void redirectInfoActivity(){
        SharedPreferences loginPref = getSharedPreferences("loginPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPref.edit();
        editor.putInt("isLogin", 1);
        editor.commit();

        final Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("code", 100);
        startActivity(intent);
        finish();
    }

    private void redirectMainActivity(){
        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void retrofitPutAppuserData(String uid, String age, String gender, String name, String email){
        RequestAppuserPost appuser = new RequestAppuserPost(uid, age, gender, name, email);
        call = RetrofitManager.createApi().postAppuser(appuser);
        call.enqueue(new Callback<RequestAppuserPost>() {
            @Override
            public void onResponse(Call<RequestAppuserPost> call, Response<RequestAppuserPost> response) {
                if (response.isSuccessful()) {
                    Log.d("retrofit","서버에 값을 전달했습니다 : ");
                } else {
                    Log.d("retrofit","서버에 값 전달을 실패했습니다 : "+response.message());
                }
            }

            @Override
            public void onFailure(Call<RequestAppuserPost> call, Throwable t) {
                Log.d("retrofit","서버와 통신중 에러가 발생했습니다 : "+t.toString());
            }
        });
    }

    //해시키 얻는 코드
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}
