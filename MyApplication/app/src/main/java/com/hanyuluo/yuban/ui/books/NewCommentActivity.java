package com.hanyuluo.yuban.ui.books;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.home.Status;
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

public class NewCommentActivity extends AppCompatActivity {

    EditText title;
    EditText text;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        Intent getIntent = getIntent();
        final String booknum = getIntent.getStringExtra("booknum");

        title=(EditText)findViewById(R.id.editText_newcomment_title);
        text=(EditText)findViewById(R.id.editText4_newcomment_text);

        send=(Button)findViewById(R.id.button_new_comment_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(NewCommentActivity.this));
                OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();
                FormBody formBody = new FormBody.Builder()
                        .add("title", title.getText().toString())
                        .add("text",text.getText().toString())

                        .build();
                final Request request = new Request.Builder()
                        .url("http://39.97.251.92:8000/home/api/user/newcomment/"+booknum)
                        .post(formBody)
                        .build();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(NewCommentActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String res = response.body().string();
                        Gson gson=new Gson();
                        final Status s=gson.fromJson(res,Status.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(s.getData().equals("success")){
                                    Toast.makeText(NewCommentActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                                else{
                                AlertDialog.Builder dialog=new AlertDialog.Builder(NewCommentActivity.this);
                                dialog.setTitle("Error");
                                dialog.setMessage(res);
                                dialog.show();}
                            }
                        });
                    }
                });
            }
        });
    }
}
