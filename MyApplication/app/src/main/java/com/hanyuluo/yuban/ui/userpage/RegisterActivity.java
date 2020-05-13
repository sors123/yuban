package com.hanyuluo.yuban.ui.userpage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
        EditText username;
        EditText password;
        Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=(EditText)findViewById(R.id.editText6_register_username);
        password=(EditText)findViewById(R.id.editText5_register_password);
        button=(Button)findViewById(R.id.button_register_do);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(RegisterActivity.this));
                OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();
                FormBody formBody = new FormBody.Builder()
                        .add("username", username.getText().toString())
                        .add("password", password.getText().toString())

                        .build();
                final Request request = new Request.Builder()
                        .url("http://39.97.251.92:8000/home/register")
                        .post(formBody)
                        .build();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(RegisterActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson=new Gson();
                                Status s=gson.fromJson(res,Status.class);


                                if(s.getId()==2){
                                AlertDialog.Builder dialog=new AlertDialog.Builder(RegisterActivity.this);
                                dialog.setTitle("注册失败");
                                dialog.setMessage(s.getData());
                                dialog.show();}
                                else if(s.getId()==1){
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
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
