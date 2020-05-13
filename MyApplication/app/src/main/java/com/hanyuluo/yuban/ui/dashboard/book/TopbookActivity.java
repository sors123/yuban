package com.hanyuluo.yuban.ui.dashboard.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.books.BookDetail;
import com.hanyuluo.yuban.ui.dashboard.NewbooksAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TopbookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;

    private RecyclerView recyclerView2;
    private RecyclerView.Adapter mAdapter2;
    private LinearLayoutManager layoutManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topbook);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_hotbooks);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        getData2();

        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview_bestbooks);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView2.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);
        getData3();
    }

    private  void getData3() {

        //关注数
        String url2="http://39.97.251.92:8000/home/api/bestbooks/";
        //第一步获取okHttpClient对象
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
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


                Gson gson=new Gson();
                final List<BookDetail> reads=gson.fromJson(result2,new TypeToken<List<BookDetail>>(){}.getType());
                TopbookActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter2 = new NewbooksAdapter(reads);

                        recyclerView2.setAdapter(mAdapter2);
                    }
                });



            }
        });


    }

    private  void getData2() {

        //关注数
        String url2="http://39.97.251.92:8000/home/api/hotbooks/";
        //第一步获取okHttpClient对象
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
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


                Gson gson=new Gson();
                final List<BookDetail> reads=gson.fromJson(result2,new TypeToken<List<BookDetail>>(){}.getType());
                TopbookActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new NewbooksAdapter(reads);

                        recyclerView.setAdapter(mAdapter);
                    }
                });



            }
        });


    }
}
