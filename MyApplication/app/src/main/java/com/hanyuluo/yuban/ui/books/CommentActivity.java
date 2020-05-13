package com.hanyuluo.yuban.ui.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hanyuluo.yuban.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {

    TextView commenttitle;
    TextView commenttext;
    TextView username;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        commenttext=(TextView)findViewById(R.id.textView_commenttext);
        commenttitle=(TextView)findViewById(R.id.textView_comment_title);
        username=(TextView)findViewById(R.id.textView16_username_comment);
        time=(TextView)findViewById(R.id.textView15_time_comment);

        Intent getIntent = getIntent();
        String commentid = getIntent.getStringExtra("commentnum");
        getdata(commentid);
    }


public void getdata(String commentnum){


    //粉丝数
    String url="http://39.97.251.92:8000/home/api/comment/"+commentnum+"/";
    //第一步获取okHttpClient对象
    OkHttpClient client = new OkHttpClient.Builder() .build();
    //第二步构建Request对象
    Request request = new Request.Builder()
            .url(url)
            .get()
            .build();
    //第三步构建Call对象
    Call call = client.newCall(request);
    //第四步:异步get请求
    call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.i("onFailure", e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            //得到的子线程
            String result = response.body().string();
            Gson gson=new Gson();
            final Comment comment=gson.fromJson(result,Comment.class);

            CommentActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    commenttext.setText(comment.getText());
                    commenttitle.setText(comment.getTitle());
                    username.setText(comment.getUsername());
                    time.setText(comment.getTime());

                }
            });


        }
    });
}
}

