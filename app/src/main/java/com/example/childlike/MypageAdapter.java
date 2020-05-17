package com.example.childlike;

import android.content.Context;
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

import java.util.ArrayList;

import static com.example.childlike.MypageActivity.SELECTED_USER;

public class MypageAdapter extends RecyclerView.Adapter<MypageAdapter.ViewHolder> {

    private RadioGroup lastCheckedRG = null;
    private ArrayList<MypageItem> mData = null;
    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
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
                    SELECTED_USER = nameText.getText().toString();
                    //Log.d("MypageActivity","선택된 유저 : "+SELECTED_USER);
                }
            });

            showResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //결과보기 버튼 구현
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MypageAdapter(ArrayList<MypageItem> list) {
        mData = list;
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
        int img = mData.get(position).getImg();
        String name = mData.get(position).getName();
        String age = mData.get(position).getAge();
        String sex = mData.get(position).getSex();
        holder.imgView.setImageResource(img);
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
}
