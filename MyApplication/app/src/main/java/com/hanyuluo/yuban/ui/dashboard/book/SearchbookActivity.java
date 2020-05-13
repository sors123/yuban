package com.hanyuluo.yuban.ui.dashboard.book;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.books.BookDetail;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchbookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allbook);
        Intent getIntent = getIntent();
        final String booknum = getIntent.getStringExtra("text");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_allbook);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData(booknum);
    }

    private  void getData(String text) {

        //关注数
        String url2="http://39.97.251.92:8000/home/api/books/search/";
        //第一步获取okHttpClient对象
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(SearchbookActivity.this));
        OkHttpClient client2 = new OkHttpClient.Builder() .cookieJar(cookieJar).build();
        //第二步构建Request对象
        FormBody formBody = new FormBody.Builder()
                .add("content", text)

                .build();
        Request request2 = new Request.Builder()
                .url(url2)
                .post(formBody)
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
                final List<BookDetail> reads=gson.fromJson(result2,new TypeToken<List<BookDetail>>(){}.getType());
                SearchbookActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter = new AllbookAdapter(reads);
                        recyclerView.setAdapter(mAdapter);
                    }
                });



            }
        });


    }
}
