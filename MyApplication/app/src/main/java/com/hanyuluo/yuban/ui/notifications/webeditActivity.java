package com.hanyuluo.yuban.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.hanyuluo.yuban.R;

public class webeditActivity extends AppCompatActivity {

    public  WebView web;
    private static final int REQUEST_STORAGE = 1;
    private static final int REQUEST_LOCATION = 2;
    public ValueCallback<Uri> mUploadMessage;
    public static final int FILECHOOSER_RESULTCODE = 5173;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webedit);



        String url="http://39.97.251.92:8000/api/edituserinfo/";
        web=(WebView)findViewById(R.id.webview_editinfo);
        web.loadUrl(url);






    }


}
