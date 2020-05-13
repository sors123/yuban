package com.hanyuluo.yuban.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanyuluo.yuban.LoginActivity;
import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.books.BookDetail;
import com.hanyuluo.yuban.ui.books.BookDetailActivity;
import com.hanyuluo.yuban.ui.books.CommentActivity;
import com.hanyuluo.yuban.ui.books.DoReadActivity;
import com.hanyuluo.yuban.ui.dashboard.book.AllbookActivity;
import com.hanyuluo.yuban.ui.dashboard.book.BooksortActivity;
import com.hanyuluo.yuban.ui.dashboard.book.SearchbookActivity;
import com.hanyuluo.yuban.ui.dashboard.book.TopbookActivity;
import com.hanyuluo.yuban.ui.read.ReadActivity;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.hanyuluo.yuban.LoginActivity.loginuserid;
import static com.hanyuluo.yuban.LoginActivity.loginusername;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;


    private SearchView mSearchView;
    private Toolbar mToolbar;
    Activity mActivity;
    AppCompatActivity mAppCompatActivity;

    private LinearLayout toall;
    private LinearLayout tosort;
    private LinearLayout totop;

    private DashboardViewModel dashboardViewModel;
    public static String bookNum2;
    EditText editText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        editText=(EditText)root.findViewById(R.id.editText111);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(loginuserid+loginusername);
            }
        });

        mActivity = getActivity();

        final SearchView sv=(SearchView)root.findViewById(R.id.searchiview_book) ;
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent =new Intent(getActivity(), SearchbookActivity.class);
                intent.putExtra("text", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });









        recyclerView = (RecyclerView) root. findViewById(R.id.recyclerview_dashboard);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);


        toall=(LinearLayout)root.findViewById(R.id.to_allbooks);
        tosort=(LinearLayout)root.findViewById(R.id.to_booksort);
        totop=(LinearLayout)root.findViewById(R.id.to_topbooks);

        toall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), AllbookActivity.class);
                startActivity(intent);
            }
        });

        tosort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), BooksortActivity.class);
                startActivity(intent);
            }
        });

        totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), TopbookActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData2();
        Button button4=(Button)getActivity().findViewById(R.id.button_to_doread);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), DoReadActivity.class);
                bookNum2=editText.getText().toString();

                intent.putExtra("bookid", bookNum2);
                startActivity(intent);
            }
        });

        Button button3=(Button)getActivity().findViewById(R.id.button_to_userread);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), ReadActivity.class);
                bookNum2=editText.getText().toString();

                intent.putExtra("userid", bookNum2);
                startActivity(intent);
            }
        });

        Button button2=(Button)getActivity().findViewById(R.id.button_to_comment);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), CommentActivity.class);
                bookNum2=editText.getText().toString();

                intent.putExtra("commentnum", bookNum2);
                startActivity(intent);
            }
        });

        Button button =(Button)getActivity().findViewById(R.id.button_to_bookdetail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), BookDetailActivity.class);
                bookNum2=editText.getText().toString();

                intent.putExtra("booknum", bookNum2);
                startActivity(intent);
            }
        });
    }

    private  void getData2() {

        //关注数
        String url2="http://39.97.251.92:8000/home/api/newbooks/";
        //第一步获取okHttpClient对象
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getActivity()));
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
                getActivity().runOnUiThread(new Runnable() {
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