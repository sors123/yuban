package com.hanyuluo.yuban.ui.books;



import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.userpage.UserpageActivity;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.VH>{
    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{

        public TextView text_username;
        public TextView text_title;
        public TextView text_text;
        public CardView cardview;
        public VH(View v) {
            super(v);

            text_username=(TextView)v.findViewById(R.id.textView15_bookdetail_comments_username);
            text_title=(TextView)v.findViewById(R.id.textView15_bookdetail_comments_title);
            text_text=(TextView)v.findViewById(R.id.textView15_bookdetail_comments_text);
            cardview=(CardView)v.findViewById(R.id.cardview_bookdetail_comments);

        }
    }

    private List<Comment> mDatas;
    public CommentAdapter(List<Comment> data) {
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.text_username.setText(mDatas.get(position).getUsername());
        holder.text_title.setText(mDatas.get(position).getTitle());
        if(mDatas.get(position).getText().length()>100){
            holder.text_text.setText(mDatas.get(position).getText().substring(0,99));}
        else{
            holder.text_text.setText(mDatas.get(position).getText());
        }

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), CommentActivity.class);

                intent.putExtra("commentnum", Integer.toString(mDatas.get(position).getId()));
                v.getContext().startActivity(intent);
            }
        });
        holder.text_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), UserpageActivity.class);

                intent.putExtra("userid", Integer.toString(mDatas.get(position).getUserid()));
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookdetail_comments, parent, false);
        return new VH(v);
    }
}