package com.hanyuluo.yuban.ui.read;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.books.BookDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.VH>{
    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView title;
        public ImageView imageView;
        public TextView text2;
        public RatingBar rating;
        public TextView time;
        public TextView text3;
        public VH(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.textView_read_item);
            imageView=(ImageView)v.findViewById(R.id.imageView_read_item);
            text2=(TextView)v.findViewById(R.id.textView_read_item_author);
            rating=(RatingBar)v.findViewById(R.id.ratingBar_readitem);
            time=(TextView)v.findViewById(R.id.textView8_readite_time);
            text3=(TextView)v.findViewById(R.id.textView_readitem_text);

        }
    }

    private List<Read> mDatas;
    public ReadAdapter(List<Read> data) {
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.title.setText(mDatas.get(position).getBookname());
        holder.text2.setText(mDatas.get(position).getUsername());
        holder.rating.setRating(mDatas.get(position).getRating());
        holder.time.setText(mDatas.get(position).getTime());
        holder.text3.setText(mDatas.get(position).getText());
        Picasso.get().load("http://39.97.251.92:8000"+
                mDatas.get(position).getBookimgurl()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
                int position=holder.getAdapterPosition();
                int i = mDatas.get(position).getBookid();
                Intent intent =new Intent(v.getContext(), BookDetailActivity.class);
                intent.putExtra("booknum", Integer.toString(i));
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_read, parent, false);
        return new VH(v);
    }
}