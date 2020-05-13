package com.hanyuluo.yuban.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanyuluo.yuban.R;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.hanyuluo.yuban.LoginActivity.loginuserid;
import static com.hanyuluo.yuban.LoginActivity.loginusername;


public class HomeFragment extends Fragment {

    private  HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static ArrayList<TimeLine> data = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_home);
        islogined();
        init_recyclerview();


        return root;
    }

    private void islogined(){
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getActivity()));
        OkHttpClient client = new OkHttpClient.Builder() .cookieJar(cookieJar).build();

        final Request request = new Request.Builder()
                .url("http://39.97.251.92:8000/home/test/")
                .get()
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Gson gson=new Gson();
                final Status status=gson.fromJson(res,Status.class);
                if(status.getData().equals("logined")){
                    loginuserid=status.getUserid();
                    loginusername=status.getUsername();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "欢迎回来，"+status.getUsername(), Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
//                        dialog.setTitle("sss");
//                        dialog.setMessage(res);
//                        dialog.show();
                        }
                    });
                }
                else{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "您还没登陆", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
//                        dialog.setTitle("sss");
//                        dialog.setMessage(res);
//                        dialog.show();
                    }
                });}
            }
        });
    }

    private  void getData() {

        //关注数
        String url2="http://39.97.251.92:8000/home/api/timeline/";
        //第一步获取okHttpClient对象
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getActivity()));
        OkHttpClient client2 = new OkHttpClient.Builder() .cookieJar(cookieJar).build();
        //第二步构建Request对象
        final Request request2 = new Request.Builder()
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
                if(result2.equals("\"[]\"")){
                    System.out.println(result2);
                    System.out.println("[]");
                }
                else{
                    Gson gson=new Gson();
                    List<TimeLine> timeLines=gson.fromJson(result2,new TypeToken<List<TimeLine>>(){}.getType());

                    data = new ArrayList<>();
                    Iterator<TimeLine> iter=timeLines.iterator();
                    while (iter.hasNext()) {

                        TimeLine s = (TimeLine) iter.next();
                        if(data.contains(s)){}
                        else{data.add(s);}
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);

                            // specify an adapter (see also next example)
                            mAdapter = new HomeAdapter(data);

                            recyclerView.setAdapter(mAdapter);

                        }
                    });


                }



            }
        });


    }

    public void init_recyclerview(){



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        getData();
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RefreshLayout refreshLayout = (RefreshLayout)getActivity().findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {


                init_recyclerview();
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败


            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });



    }



}