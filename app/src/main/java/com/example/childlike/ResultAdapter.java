package com.example.childlike;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.childlike.dataclass.ResultItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{
    private ArrayList<ResultItem> mData = null;
    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    Bitmap bitmap;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, resultText;
        ImageView resultImg;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            dateText = itemView.findViewById(R.id.date_text);
            resultText = itemView.findViewById(R.id.result_text);
            resultImg = itemView.findViewById(R.id.result_img);
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
    ResultAdapter(ArrayList<ResultItem> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.result_item, parent, false);
        ResultAdapter.ViewHolder vh = new ResultAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ResultAdapter.ViewHolder holder, int position) {
        String img = mData.get(position).getImg();
        downloadUserDrawImg(img);
        String date = mData.get(position).getDate();
        String result = mData.get(position).getResult();
        holder.resultImg.setImageBitmap(bitmap);
        holder.dateText.setText(date);
        holder.resultText.setText(result);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<ResultItem> list){
        mData = list;
        notifyDataSetChanged();
    }

    private void downloadUserDrawImg(final String img){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL("http://52.79.242.93:8000/media/userimage/"+img);
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
