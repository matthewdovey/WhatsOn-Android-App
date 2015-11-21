package com.matthew.whatsonandroidapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventActivity extends AppCompatActivity {

    static String eventLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }

    @Override
    protected void onResume() {
        super.onResume();

        eventLink = MainActivity.focusLink;

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
