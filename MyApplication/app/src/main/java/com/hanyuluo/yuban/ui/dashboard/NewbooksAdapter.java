package com.hanyuluo.yuban.ui.dashboard;




import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.books.BookDetail;
import com.hanyuluo.yuban.ui.books.BookDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewbooksAdapter extends RecyclerView.Adapter<NewbooksAdapter.VH>{
    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView text_text;
        public CardView cardview;
        public VH(View v) {
            super(v);

            imageView=(ImageView)v.findViewById(R.id.imageView3_itembookhori) ;
            text_text=(TextView)v.findViewById(R.id.textView15_itembookhori);
            cardview=(CardView)v.findViewById(R.id.cardview_book_hori);

        }
    }

    private List<BookDetail> mDatas;
    public NewbooksAdapter(List<BookDetail> data) {
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, final int position) {

        holder.text_text.setText(mDatas.get(position).getBookName());
        Picasso.get().load("http://39.97.251.92:8000"+
                mDatas.get(position).getImgurl()).into(holder.imageView);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), BookDetailActivity.class);

                intent.putExtra("booknum", Integer.toString(mDatas.get(position).getId()));
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_horizontal, parent, false);
        return new VH(v);
    }
}