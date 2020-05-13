package com.hanyuluo.yuban.ui.books.BookReads;



import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.read.Read;
import com.hanyuluo.yuban.ui.userpage.UserpageActivity;

import java.util.List;

public class BookReadsAdapter extends RecyclerView.Adapter<BookReadsAdapter.VH>{
    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public TextView textView_username;
        public TextView textView_time;
        public TextView textView_text;
        public  RatingBar ratingbar;
        public VH(View v) {
            super(v);
            textView_text=(TextView)v.findViewById(R.id.textView13);
            textView_time=(TextView)v.findViewById(R.id.textView5);
            textView_username=(TextView)v.findViewById(R.id.textView2_username_book_reads);
            ratingbar=(RatingBar)v.findViewById(R.id.ratingBar_item_reads);

        }
    }

    private List<Read> mDatas;
    public BookReadsAdapter(List<Read> data) {
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, final int position) {

        holder.textView_username.setText(mDatas.get(position).getUsername());
        holder.textView_time.setText(mDatas.get(position).getTime());
        holder.textView_text.setText(mDatas.get(position).getText());
        holder.ratingbar.setRating(mDatas.get(position).getRating());
        holder.textView_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), UserpageActivity.class);
                System.out.println(position);
                System.out.println("userid"+mDatas.get(holder.getAdapterPosition()).getUserid());
                intent.putExtra("userid", Integer.toString(mDatas.get(holder.getAdapterPosition()).getUserid()));
                System.out.println(Integer.toString(mDatas.get(holder.getAdapterPosition()).getUserid()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_reads, parent, false);
        return new VH(v);
    }
}