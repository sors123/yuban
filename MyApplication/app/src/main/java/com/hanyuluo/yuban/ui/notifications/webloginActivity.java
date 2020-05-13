package com.hanyuluo.yuban.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.hanyuluo.yuban.R;

public class webloginActivity extends AppCompatActivity {
    public WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weblogin);
        String url="http://39.97.251.92:8000/login";
        web=(WebView)findViewById(R.id.web_login);
        web.loadUrl(url);
    }
}
