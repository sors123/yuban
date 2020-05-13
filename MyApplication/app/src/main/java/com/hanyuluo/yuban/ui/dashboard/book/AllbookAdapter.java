package com.hanyuluo.yuban.ui.dashboard.book;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.books.BookDetail;
import com.hanyuluo.yuban.ui.books.BookDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllbookAdapter extends RecyclerView.Adapter<AllbookAdapter.VH>{
    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView title;
        public ImageView imageView;
        public TextView text2;
        public RatingBar rating;
        public TextView time;
        public TextView text3;
        public CardView cardview;
        public VH(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.textView1_allbook_item_title);
            imageView=(ImageView)v.findViewById(R.id.imageView_allbook_item);
            text2=(TextView)v.findViewById(R.id.textView_allbook_item_author);

            text3=(TextView)v.findViewById(R.id.textView8_allbook_item_text);
            cardview=(CardView)v.findViewById(R.id.cardview_allbook);

        }
    }

    private List<BookDetail> mDatas;
    public AllbookAdapter(List<BookDetail> data) {
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, int position) {

        if (mDatas.get(position).getIntroduction().length()>50)
        {
            holder.text3.setText(mDatas.get(position).getIntroduction().substring(0,49));
        }
        else
        {
            holder.text3.setText(mDatas.get(position).getIntroduction());
        }

        holder.title.setText(mDatas.get(position).getBookName());
        holder.text2.setText(mDatas.get(position).getAuthorName()+"/"+
                mDatas.get(position).getPressName());

        Picasso.get().load("http://39.97.251.92:8000"+
               mDatas.get(position).getImgurl()).into(holder.imageView);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                int i = mDatas.get(position).getId();
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allbook, parent, false);
        return new VH(v);
    }
}