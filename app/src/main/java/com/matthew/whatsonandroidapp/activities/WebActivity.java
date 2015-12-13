package com.matthew.whatsonandroidapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.matthew.whatsonandroidapp.R;
import com.matthew.whatsonandroidapp.activities.EventActivity;

public class WebActivity extends AppCompatActivity {

    public WebView eventWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        eventWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = eventWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String sendTo = EventActivity.eventLink;

        //Test website please delete!
        eventWebView.loadUrl(sendTo);

    }
}