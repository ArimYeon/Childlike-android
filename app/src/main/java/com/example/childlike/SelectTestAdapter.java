package com.example.childlike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectTestAdapter extends RecyclerView.Adapter<SelectTestAdapter.ViewHolder>{
    private ArrayList<SelectTestItem> mData = null;
    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            itemText = itemView.findViewById(R.id.test_item_title);
            // 뷰 리스너 달기
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SelectTestAdapter(ArrayList<SelectTestItem> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SelectTestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.select_test_item, parent, false);
        SelectTestAdapter.ViewHolder vh = new SelectTestAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SelectTestAdapter.ViewHolder holder, int position) {
        String text = mData.get(position).getTitle();
        holder.itemText.setText(text);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
