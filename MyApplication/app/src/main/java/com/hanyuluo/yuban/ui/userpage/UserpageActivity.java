package com.hanyuluo.yuban.ui.userpage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hanyuluo.yuban.FollowActivity;
import com.hanyuluo.yuban.FollowedActivity;
import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.home.Status;
import com.hanyuluo.yuban.ui.read.ReadActivity;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.hanyuluo.yuban.ui.read.WantReadActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserpageActivity extends AppCompatActivity {
    TextView username;
    TextView read;
    Button guanzhu;
    Button fensi;
    Button follow;
    LinearLayout read_click;
    LinearLayout wantread_click;
    TextView qianming;
    ImageView touxiang;
    ImageView readimg;
    ImageView wantread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        username=(TextView)findViewById(R.id.textView_username);
        read=(TextView)findViewById(R.id.textView8_read_userpage);
        guanzhu=(Button)findViewById(R.id.button2_guanzhu_userpage);
        fensi=(Button)findViewById(R.id.button_fensi_userpage);
        read_click=(LinearLayout)findViewById(R.id.read_userpage);
        follow=(Button)findViewById(R.id.button_userpage_follow) ;
        qianming=(TextView)findViewById(R.id.textView6_qianming);
        touxiang=(ImageView)findViewById(R.id.imageView3_touxiang_userpage);
        wantread_click=(LinearLayout)findViewById(R.id.wantread_userpage) ;

        readimg=(ImageView)findViewById(R.id.imageView2_read) ;
        wantread=(ImageView)findViewById(R.id.imageView_wantread) ;

        Intent getIntent = getIntent();
        final String userid = getIntent.getStringExtra("userid");
        wantread_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserpageActivity.this, WantReadActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        read_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserpageActivity.this, ReadActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });

        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserpageActivity.this, FollowedActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });
        fensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserpageActivity.this, FollowActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(UserpageActivity.this));
                OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();

                final Request request = new Request.Builder()
                        .url("http://39.97.251.92:8000/home/api/followuser/"+userid)
                        .get()
                        .build();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(UserpageActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String res = response.body().string();
                        Gson gson=new Gson();
                        final Status s=gson.fromJson(res,Status.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {



                                Toast.makeText(UserpageActivity.this, s.getData(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

            }
        });


        getdata(userid);
    }

    private void getdata(final String userid){

        //关注数
        String url2="http://39.97.251.92:8000/home/api/user/"+userid;
        //第一步获取okHttpClient对象
        OkHttpClient client2 = new OkHttpClient.Builder() .build();
        //第二步构建Request对象
        Request request2 = new Request.Builder()
                .url(url2)
                .get()
                .build();
        //第三步构建Call对象
        Call call2 = client2.newCall(request2);
        //第四步:异步get请求
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call2, IOException e) {
                Log.i("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(Call call2, Response response) throws IOException {
                //得到的子线程
                String result2 = response.body().string();
                Gson gson=new Gson();
                System.out.println("ceshi"+userid);
                final Userpage userpage=gson.fromJson(result2,Userpage.class);

                UserpageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        username.setText(userpage.getUsername());
                        read.setText("读过 "+userpage.getReadnum());
                        guanzhu.setText("关注 "+userpage.getGuanzhu());
                        fensi.setText("粉丝 "+userpage.getFensi());
                        qianming.setText(userpage.getQianming());
                        Picasso.get().load("http://39.97.251.92:8000"+
                            userpage.getImage()).into(touxiang);
                        Picasso.get().load("http://39.97.251.92:8000"+
                                userpage.getWantimg()).into(wantread);
                        Picasso.get().load("http://39.97.251.92:8000"+
                                userpage.getReadimg()).into( readimg);



                    }
                });
            }
        });

    }
}
