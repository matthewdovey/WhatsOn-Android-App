package com.matthew.whatsonandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        ImageView eventImage = (ImageView) findViewById(R.id.focusImageId);
        TextView eventTitle = (TextView) findViewById(R.id.focusTitleId);
        TextView eventInfo = (TextView) findViewById(R.id.focusInfoId);
        TextView eventDesc = (TextView) findViewById(R.id.focusDescId);
        Button webEvent = (Button) findViewById(R.id.webButton);

        Picasso.with(eventImage.getContext()).load(MainActivity.focusImage).into(eventImage);
        eventTitle.setText(MainActivity.focusTitle);
        eventInfo.setText(MainActivity.focusInfo);
        eventDesc.setText(MainActivity.focusDesc);

        webEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventActivity.this, WebActivity.class));
            }
        });

    }
}
