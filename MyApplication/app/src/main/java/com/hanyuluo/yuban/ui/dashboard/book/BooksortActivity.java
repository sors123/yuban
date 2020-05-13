package com.hanyuluo.yuban.ui.dashboard.book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.hanyuluo.yuban.R;

public class BooksortActivity extends AppCompatActivity {
    public WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booksort);

        String url="http://39.97.251.92:8000/api/tag/";
        web=(WebView)findViewById(R.id.webview_sortbook);
        web.loadUrl(url);
    }
}
