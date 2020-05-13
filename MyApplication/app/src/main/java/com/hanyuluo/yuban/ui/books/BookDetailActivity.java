package com.hanyuluo.yuban.ui.books;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hanyuluo.yuban.LoginActivity;
import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.books.BookReads.BookReadsActivity;
import com.hanyuluo.yuban.ui.books.BookReads.BookReadsAdapter;
import com.hanyuluo.yuban.ui.dashboard.NewbooksAdapter;
import com.hanyuluo.yuban.ui.dashboard.book.BooksortActivity;
import com.hanyuluo.yuban.ui.home.Status;
import com.hanyuluo.yuban.ui.read.Read;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookDetailActivity extends AppCompatActivity {

    private String[] data={"abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc","abc"};
    TextView authorName;
    TextView pageNum;
    TextView bookName;
    TextView pricingNum;
    TextView introduction;
    TextView pressName;
    ImageView imageView2;
    Button button;
    Button dowantbutton;
    ProgressBar barhaoping;
    ProgressBar baryiban;
    ProgressBar barchaping;
    TextView tuijianlv;
    TextView total;
    Button newcomment;
    Button button_to_reads;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView recyclerView2;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.LayoutManager layoutManager2;


    private RecyclerView recyclerView3;
    private RecyclerView.Adapter mAdapter3;
    private LinearLayoutManager layoutManager3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);


        Intent getIntent = getIntent();
        final String booknum = getIntent.getStringExtra("booknum");



        authorName = (TextView) findViewById(R.id.textView2_bookauthor_detail);
        pageNum = (TextView) findViewById(R.id.textView3__booknum_detail);
        bookName = (TextView) findViewById(R.id.textView_bookname_detail);
        pricingNum = (TextView) findViewById(R.id.textView7_bookprice_detail);
        introduction = (TextView) findViewById(R.id.textView_booktext_detail);
        pressName= (TextView) findViewById(R.id.textView6_bookpress_detail);
        imageView2=(ImageView) findViewById(R.id.imageView_bookdetail);
        dowantbutton=(Button)findViewById(R.id.button20_dowantread) ;
        button=(Button)findViewById(R.id.button19_doread);
        newcomment=(Button)findViewById(R.id.button_newcomment);
        newcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BookDetailActivity.this, NewCommentActivity.class);
                intent.putExtra("booknum", booknum);

                startActivity(intent);
            }
        });

        barchaping=(ProgressBar)findViewById(R.id.progressBar2) ;
        barhaoping=(ProgressBar)findViewById(R.id.progressBar3) ;
        baryiban=(ProgressBar)findViewById(R.id.progressBar4);
        tuijianlv=(TextView)findViewById(R.id.textView9);
        total=(TextView)findViewById(R.id.textView10);


        button_to_reads=(Button)findViewById(R.id.button_to_reads);
        button_to_reads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BookDetailActivity.this, BookReadsActivity.class);
                intent.putExtra("booknum", booknum);

                startActivity(intent);
            }
        });

        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview_bookdetail_comments);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView2.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        getData3(booknum);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_bookdetail);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getData2(booknum);

        getdata(booknum);
        getrating( booknum);



        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerview_youwilllike);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView3.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager3 = new LinearLayoutManager(this);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);
        getData4();


        dowantbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowantread(booknum);
            }
        });


    }
    public void  dowantread(String booknum){

        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BookDetailActivity.this));
        OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();

        final Request request = new Request.Builder()
                .url("http://39.97.251.92:8000/home/api/user/dowantread/"+booknum)
                .get()
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(BookDetailActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Gson gson=new Gson();
                final Status s=gson.fromJson(res,Status.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(BookDetailActivity.this, s.getData(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
    public void getrating(String booknum){

        String url="http://39.97.251.92:8000/home/api/books/rating/"+booknum+"/";
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
                final Rating rating=gson.fromJson(result,Rating.class);

                BookDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tuijianlv.setText(rating.getTuijian());
                        total.setText("共"+rating.getTotal()+"人给出了评价");
                        barhaoping.setMax(rating.getTotal());
                        barchaping.setMax(rating.getTotal());
                        baryiban.setMax(rating.getTotal());
                        barhaoping.setProgress(rating.getPositive());
                        baryiban.setProgress(rating.getModerate());
                        barchaping.setProgress(rating.getNegative());

                    }
                });


            }
        });
    }




    public void getdata(String booknum){


        //粉丝数
        String url="http://39.97.251.92:8000/home/api/books/"+booknum+"/";
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
                final BookDetail bookDetail=gson.fromJson(result,BookDetail.class);

                BookDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        introduction.setText(bookDetail.getIntroduction());
                        authorName.setText(bookDetail.getAuthorName());
                        bookName.setText(bookDetail.getBookName());
                        pageNum.setText( Integer.toString(bookDetail.getPageNum())+"页");
                        pricingNum.setText( Integer.toString(bookDetail.getPricingNum())+"元");
                        pressName.setText(bookDetail.getPressName());
                        Picasso.get().load("http://39.97.251.92:8000"+
                                bookDetail.getImgurl()).into(imageView2);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(BookDetailActivity.this, DoReadActivity.class);

                                intent.putExtra("bookid",Integer.toString(bookDetail.getId()));
                                startActivity(intent);
                            }
                        });

                    }
                });


            }
        });
    }


    private  void getData2(String booknum) {

        //关注数
        String url2="http://39.97.251.92:8000/home/api/books/read/"+booknum+"/";
        //第一步获取okHttpClient对象
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BookDetailActivity.this));
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
                final List<Read> reads=gson.fromJson(result2,new TypeToken<List<Read>>(){}.getType());
                BookDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (reads.size()>=3){mAdapter = new BookReadsAdapter(reads.subList(0,3));}
                        else{mAdapter = new BookReadsAdapter(reads);}

                        recyclerView.setAdapter(mAdapter);
                    }
                });



            }
        });


    }


    private  void getData3(String booknum) {

        //关注数
        String url2="http://39.97.251.92:8000/home/api/books/comment/"+booknum+"/";
        //第一步获取okHttpClient对象
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BookDetailActivity.this));
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
                final List<Comment> reads=gson.fromJson(result2,new TypeToken<List<Comment>>(){}.getType());
                BookDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter2 = new CommentAdapter(reads);

                        recyclerView2.setAdapter(mAdapter2);
                    }
                });



            }
        });


    }


    private  void getData4() {

        //关注数
        String url2="http://39.97.251.92:8000/home/api/newbooks/";
        //第一步获取okHttpClient对象
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BookDetailActivity.this));
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
                BookDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter3 = new NewbooksAdapter(reads);

                        recyclerView3.setAdapter(mAdapter3);
                    }
                });



            }
        });


    }

}
