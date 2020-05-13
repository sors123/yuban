package com.hanyuluo.yuban;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hanyuluo.yuban.ui.home.Status;
import com.hanyuluo.yuban.ui.userpage.RegisterActivity;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {


    public static int loginuserid;
    public static String loginusername;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //注册按钮
        Button register=(Button)findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        // 退出按钮
        Button button3=(Button)findViewById(R.id.button10);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://39.97.251.92:8000/home/logout";
                //第一步获取okHttpClient对象
                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(LoginActivity.this));
                OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();
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
                        Status loginuser=gson.fromJson(result,Status.class);
                        Toast.makeText(LoginActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                        loginusername="";
                        loginuserid=0;
                        Log.i("result", result);
                    }
                });
            }
        });

        //测试按钮
        Button button2=(Button)findViewById(R.id.button9);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://39.97.251.92:8000/home/test/";

                //第一步获取okHttpClient对象
                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(LoginActivity.this));
                OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();
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
                        Log.i("result", result);
                    }
                });


            }});


        //登录按钮按下后
        Button button =(Button)findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText login_text = (EditText) findViewById(R.id.editText2);
                EditText psw_text = (EditText) findViewById(R.id.editText3);
                String account = login_text.getText().toString();//账号
                String password = psw_text.getText().toString();//密码

                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(LoginActivity.this));
                OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();
                FormBody formBody = new FormBody.Builder()
                        .add("username", account)
                        .add("password", password)

                        .build();
                final Request request = new Request.Builder()
                        .url("http://39.97.251.92:8000/home/login")
                        .post(formBody)
                        .build();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(LoginActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson=new Gson();
                                Status loginuser=gson.fromJson(res,Status.class);
                                loginuserid=loginuser.getUserid();
                                loginusername=loginuser.getUsername();

                                if(loginuser.getData().equals("login success")){
                                    loginuserid=loginuser.getUserid();
                                    loginusername=loginuser.getUsername();
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                AlertDialog.Builder dialog=new AlertDialog.Builder(LoginActivity.this);
                                dialog.setTitle("失败");
                                dialog.setMessage(loginuser.getData());
                                dialog.show();}
                            }
                        });
                    }
                });






            }
        });
    }





}
