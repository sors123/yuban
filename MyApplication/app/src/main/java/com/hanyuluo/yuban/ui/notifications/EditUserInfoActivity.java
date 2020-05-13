package com.hanyuluo.yuban.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.read.Read;
import com.hanyuluo.yuban.ui.read.ReadActivity;
import com.hanyuluo.yuban.ui.read.ReadAdapter;
import com.hanyuluo.yuban.ui.userpage.Userpage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EditUserInfoActivity extends AppCompatActivity {

    public Button tijiao;
    public Button touxiangbutton;
   public ImageView touxiang;
   public EditText qianming;
   public EditText sex;
   public EditText location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        tijiao=(Button)findViewById(R.id.button_edituserinfo);
        touxiangbutton=(Button)findViewById(R.id.button2_edit_img);
        touxiang=(ImageView)findViewById(R.id.imageView3_edit_touxiang);
        qianming=(EditText)findViewById(R.id.editText_edit_qianming);
        sex=(EditText)findViewById(R.id.editText4_edit_sex);
        location=(EditText)findViewById(R.id.editText5_edit_location);

        String url2="http://39.97.251.92:8000/home/api/edituserinfo/";
        //第一步获取okHttpClient对象
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(EditUserInfoActivity.this));
        OkHttpClient client2 = new OkHttpClient.Builder() .cookieJar(cookieJar).build();
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
                System.out.println(result2);

                Gson gson=new Gson();
                final Userpage userpage=gson.fromJson(result2,Userpage.class);
                EditUserInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    sex.setText(userpage.getSex());
                    location.setText(userpage.getLocation());
                        Picasso.get().load("http://39.97.251.92:8000"+
                                userpage.getImage()).into(touxiang);
                        qianming.setText(userpage.getQianming());

                    }
                });



            }
        });
    }
}
