package com.example.childlike;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.childlike.dataclass.MypageItem;
import com.example.childlike.retrofit.retrofitdata.RequestChildrenGet;
import com.example.childlike.retrofit.retrofitdata.RequestChildrenPost;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.childlike.MypageActivity.SELECTED_USER;

public class MypageAdapter extends RecyclerView.Adapter<MypageAdapter.ViewHolder> {

    private RadioGroup lastCheckedRG = null;
    private ArrayList<RequestChildrenGet> mData = null;
    // 리스너 객체 참조를 저장하는 변수
    private OnButtonClickListener mListener = null ;
    private OnRadioButtonClickListener rListener = null;
    String img;
    Bitmap bitmap;

    public interface OnButtonClickListener {
        void onButtonClick(View v, int position) ;
    }
    // OnButtonClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.mListener = listener ;
    }

    public interface OnRadioButtonClickListener{
        void onRadioButtonClick(int position);
    }
    // OnRadioButtonClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnRadioButtonClickListener(OnRadioButtonClickListener listener){
        this.rListener = listener;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView nameText;
        TextView ageText;
        TextView sexText;
        RadioGroup selectGroup;
        RadioButton selectUser;
        Button showResult;

        ViewHolder(final View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            imgView = itemView.findViewById(R.id.mypage_img);
            nameText = itemView.findViewById(R.id.name_text);
            ageText = itemView.findViewById(R.id.age_text);
            sexText = itemView.findViewById(R.id.sex_text);
            selectGroup = itemView.findViewById(R.id.select_user_group);
            selectUser = itemView.findViewById(R.id.select_user_btn);
            showResult = itemView.findViewById(R.id.show_result_btn);

            selectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(lastCheckedRG!=null && lastCheckedRG!=radioGroup){
                        lastCheckedRG.clearCheck();
                    }
                    lastCheckedRG = radioGroup;

                    if(selectUser.isChecked()){
                        selectUser.setText("선택됨");
                        selectUser.setTextColor(Color.parseColor("#ffffff"));
                        selectUser.setBackgroundResource(R.drawable.radio_btn_on);
                    }
                    else{
                        selectUser.setText("선택하기");
                        selectUser.setTextColor(Color.parseColor("#D5D5D5"));
                        selectUser.setBackgroundResource(R.drawable.radio_btn_off);
                    }


                    int pos = getAdapterPosition();
                    if(rListener != null){
                        rListener.onRadioButtonClick(pos);
                    }
                }
            });

            showResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //결과보기 버튼 구현
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onButtonClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MypageAdapter(ArrayList<RequestChildrenGet> list) {
        mData = list;
        notifyDataSetChanged();
        //Log.d("mData", mData.get(0).getName()+mData.get(0).getImage());
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public MypageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.mypage_item, parent, false);
        MypageAdapter.ViewHolder vh = new MypageAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MypageAdapter.ViewHolder holder, int position) {
        img = mData.get(position).getImage();
        if(img==null){
            holder.imgView.setImageResource(R.drawable.default_profile);
        }
        else{
            downloadProfile();
            holder.imgView.setImageBitmap(bitmap);
        }
        String name = mData.get(position).getName();
        String age = mData.get(position).getAge();
        String sex = mData.get(position).getGender();
        holder.nameText.setText(name);
        holder.ageText.setText(age);
        holder.sexText.setText(sex);
        if(name.equals(SELECTED_USER)){
            holder.selectUser.setChecked(true);
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void downloadProfile(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL("http://52.79.242.93:8000/media/userprofile/"+img);
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

    public void setData(ArrayList<RequestChildrenGet> list){
        mData = list;
        notifyDataSetChanged();
    }
}
