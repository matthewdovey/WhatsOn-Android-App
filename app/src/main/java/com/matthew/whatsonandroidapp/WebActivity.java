package com.matthew.whatsonandroidapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

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