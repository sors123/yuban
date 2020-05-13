package com.hanyuluo.yuban.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.hanyuluo.yuban.FollowActivity;
import com.hanyuluo.yuban.FollowedActivity;
import com.hanyuluo.yuban.LoginActivity;
import com.hanyuluo.yuban.Loginuser;
import com.hanyuluo.yuban.R;
import com.hanyuluo.yuban.ui.userpage.UserpageActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.INVISIBLE;
import static com.hanyuluo.yuban.LoginActivity.loginuserid;
import static com.hanyuluo.yuban.LoginActivity.loginusername;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private Button button;
    public static int guanzhu;
    public static int fensi;
    public Button userpage_click;
    public int userid_to;
    public Button userpage_edit;
    public Button login;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        userid_to=loginuserid;



        //粉丝数
        String url="http://39.97.251.92:8000/home/api/user/followed/"+loginuserid;
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
                List<Loginuser>loginusers=gson.fromJson(result,new TypeToken<List<Loginuser>>(){}.getType());
                fensi=loginusers.size();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button button3 =(Button)getActivity().findViewById(R.id.button_follow);
                        button3.setText("粉丝数 "+fensi);

                        button3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent =new Intent(getActivity(), FollowActivity.class);
                                intent.putExtra("userid", Integer.toString(userid_to));
                                startActivity(intent);
                            }
                        });

                    }
                });
            }
        });

        //关注数
        String url2="http://39.97.251.92:8000/home/api/user/follow/"+loginuserid;
        //第一步获取okHttpClient对象
        OkHttpClient client2 = new OkHttpClient.Builder() .build();
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
                List<Loginuser>loginusers2=gson.fromJson(result2,new TypeToken<List<Loginuser>>(){}.getType());
                guanzhu=loginusers2.size();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button button2 =(Button)getActivity().findViewById(R.id.button_followed);
                        button2.setText("关注数 "+guanzhu);
                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent =new Intent(getActivity(), FollowedActivity.class);
                                intent.putExtra("userid",Integer.toString(userid_to));
                                startActivity(intent);
                            }
                        });


                    }
                });
            }
        });


        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //用户username
        TextView textView=(TextView)getActivity().findViewById(R.id.textView_username);
        textView.setText(loginusername);

        login=(Button)getActivity().findViewById(R.id.button2_to_web_login) ;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),webloginActivity.class);

                startActivity(intent);
            }
        });
        userpage_edit=(Button)getActivity().findViewById(R.id.button_edituserinfo) ;
        userpage_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),webeditActivity.class);

                startActivity(intent);
            }
        });
        userpage_click=(Button)getActivity().findViewById(R.id.button_userpage);
        userpage_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), UserpageActivity.class);
                intent.putExtra("userid", Integer.toString(userid_to));

                startActivity(intent);

            }
        });



        Button button =(Button)getActivity().findViewById(R.id.button_login);
        if (userid_to !=0){
            button.setVisibility(INVISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}