package com.hanyuluo.yuban.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.books.BookDetailActivity;
import com.hanyuluo.yuban.ui.books.CommentActivity;
import com.hanyuluo.yuban.ui.userpage.UserpageActivity;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private  ArrayList<TimeLine> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View homeview;
        public TextView textView;
        public TextView username;
        public TextView type;
        public TextView bookname;
        public TextView textView2;
        public TextView time;
        public ImageView imageView;
        public TextView textView_author;
        public TextView textView_press;
        public ImageView touxiang;
        CardView cardView;
        public TextView likenum;
        public Button like;
        public MyViewHolder(View itemView) {
            super(itemView);
            homeview=itemView;
            textView = (TextView) itemView.findViewById(R.id.home_item_textView);
            username = (TextView) itemView.findViewById(R.id.home_item_username);
            type=(TextView) itemView.findViewById(R.id.home_item_type);
            bookname=(TextView) itemView.findViewById(R.id.home_item_bookname);
            textView2=(TextView) itemView.findViewById(R.id.home_item_textView2);
            time=(TextView) itemView.findViewById(R.id.home_item_time);
            imageView=(ImageView)itemView.findViewById(R.id.imageView12);
            cardView=(CardView)itemView.findViewById(R.id.cardview_home_item);
            textView_author=(TextView)itemView.findViewById(R.id.textView_author_item);
            textView_press=(TextView)itemView.findViewById(R.id.textView_press_item);
            likenum=(TextView)itemView.findViewById(R.id.textView_home_likenum);
            like=(Button)itemView.findViewById(R.id.button_home_like);
            touxiang=(ImageView)itemView.findViewById(R.id.imageView3_touxiang_timeline);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeAdapter( ArrayList<TimeLine> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recyclerview_item, parent, false);
        // 实例化viewholder
        MyViewHolder vh=new MyViewHolder(v);



        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if(mDataset.get(position).getType().equals("comment")){
            holder.type.setText("评论");
            holder.textView.setText(mDataset.get(position).getCommenttitle());
            holder.textView2.setText(mDataset.get(position).getComment());

            holder.textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=holder.getAdapterPosition();
                    int i = mDataset.get(position).getCommentid();
                    Intent intent =new Intent(v.getContext(), CommentActivity.class);
                    intent.putExtra("commentnum", Integer.toString(i));
                    v.getContext().startActivity(intent);

                }
            });

        }
        else if(mDataset.get(position).getType().equals("read")){
            holder.type.setText("读过");
            if (mDataset.get(position).getRating()==1){
                holder.textView.setText("给出评价 不推荐");
            }
            else if (mDataset.get(position).getRating()==2){
                holder.textView.setText("给出评价 一般");
            }
            else if (mDataset.get(position).getRating()==3){
                holder.textView.setText("给出评价 推荐");
            }
            holder.textView2.setText(mDataset.get(position).getText());

        }


        Picasso.get().load("http://39.97.251.92:8000"+
                mDataset.get(position).getImage()).into(holder.touxiang);
        holder.username.setText(mDataset.get(position).getUsername());
        holder.textView_press.setText(mDataset.get(position).getPress());
        holder.textView_author.setText(mDataset.get(position).getAuthor());
        holder.time.setText(mDataset.get(position).getTime());
        holder.bookname.setText(mDataset.get(position).getBookname());
        holder.likenum.setText(Integer.toString(mDataset.get(position).getLikenum()));
        Picasso.get().load("http://39.97.251.92:8000"+
                mDataset.get(position).getBookimgurl()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                int i = mDataset.get(position).getUserid();
                Intent intent =new Intent(v.getContext(), UserpageActivity.class);
                intent.putExtra("userid", Integer.toString(i));

                v.getContext().startActivity(intent);
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dolike(position,holder,v,mDataset.get(position).getType(),Integer.toString(mDataset.get(position).getId()));
            }
        });

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                int i = mDataset.get(position).getUserid();
                Intent intent =new Intent(v.getContext(), UserpageActivity.class);
                intent.putExtra("userid", Integer.toString(i));

                v.getContext().startActivity(intent);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                int i = mDataset.get(position).getBookid();
                Intent intent =new Intent(v.getContext(), BookDetailActivity.class);
                intent.putExtra("booknum", Integer.toString(i));
                v.getContext().startActivity(intent);

            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void dolike(final int position,final MyViewHolder holder,final View v,String type,String id){
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(v.getContext()));
        OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();

        final Request request = new Request.Builder()
                .url("http://39.97.251.92:8000/home/api/like"+type+"/"+id+"/")
                .get()
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(v.getContext(), "Post Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Gson gson=new Gson();

                final Status s=gson.fromJson(res,Status.class);
                System.out.println(res);
                System.out.println(s);
                if(s.getData().equals("success")){
                    Activity activity = (Activity)holder.likenum.getContext();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.likenum.setText(Integer.toString(1+mDataset.get(position).getLikenum()));
                        }
                    });
                }

            }
        });
    }


    public static Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

}