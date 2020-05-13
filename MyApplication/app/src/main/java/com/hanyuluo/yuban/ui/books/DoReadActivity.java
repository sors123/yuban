package com.hanyuluo.yuban.ui.books;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class DoReadActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_read);


        Intent getIntent = getIntent();
        final String bookid = getIntent.getStringExtra("bookid");

        final EditText editetxt=(EditText)findViewById(R.id.add_content_doread);
        final Button button22=(Button)findViewById(R.id.button_doread);
        final RatingBar ratingBar=(RatingBar)findViewById(R.id.ratingBar_doread);
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int rating=(int)ratingBar.getRating();
                String comment=editetxt.getText().toString();
                if (rating==0){
                    rating=1;
                }

                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(DoReadActivity.this));
                OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();
                FormBody formBody = new FormBody.Builder()
                        .add("rating", Integer.toString(rating))
                        .add("comment",comment)

                        .build();
                final Request request = new Request.Builder()
                        .url("http://39.97.251.92:8000/home/api/user/doread/"+bookid)
                        .post(formBody)
                        .build();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(DoReadActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String res = response.body().string();
                        Gson gson=new Gson();
                        final Status s=gson.fromJson(res,Status.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                if(s.getData().equals("error")){
                                    AlertDialog.Builder dialog=new AlertDialog.Builder(DoReadActivity.this);
                                    dialog.setTitle("失败");
                                    dialog.setMessage("你已经读过此书");
                                    dialog.show();}
                                else if(s.getData().equals("success")){
                                    Toast.makeText(DoReadActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                            }
                        });
                    }
                });

            }
        });

    }



}
