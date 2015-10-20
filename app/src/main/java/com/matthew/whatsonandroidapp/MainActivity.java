package com.matthew.whatsonandroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new JsoupListView().execute();

        Button eventPage = (Button) findViewById(R.id.button);
        eventPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
            }
        });
    }

    private class JsoupListView extends AsyncTask<ArrayList<String>,Void,ArrayList<String>>{

        private int x;
        private int itemNumber = x;
        private String url = "http://www.essexstudent.com";

        private ArrayList<String> eventLinks = new ArrayList<String>();
        private ArrayList<String> eventTitles = new ArrayList<String>();
        private ArrayList<String> eventInfos = new ArrayList<String>();
        private ArrayList<String> eventImages = new ArrayList<String>();
        private ArrayList<String> eventDescs = new ArrayList<String>();

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... params) {

            WebScraper whatson = new WebScraper();

            whatson.connect();

            System.out.println("starting");

            if (whatson.connect()==1) {
                whatson.getLinks();

                System.out.println("stage one");

                for(x=0;x<whatson.returnLinks().size();x++) {
                    whatson.getTitles(whatson.getEvent(x));
                    whatson.getInfo(whatson.getEvent(x));
                    whatson.getDesc(whatson.getEvent(x));
                }
                whatson.getImages(itemNumber);

                eventLinks = whatson.returnLinks();
                eventImages = whatson.returnImages();
                eventTitles = whatson.returnTitles();
                eventInfos = whatson.returnInfos();
                eventDescs = whatson.returnDescs();

                System.out.println("stage two");

                for (int u=0;u<eventLinks.size();u++) {
                    System.out.println(u);
                    System.out.println("Event Link: "+ eventLinks.get(u));
                }
                for (int u=0;u<eventImages.size();u++) {
                    System.out.println(u);
                    System.out.println("Event Image: "+ eventImages.get(u));
                }
                for (int u=0;u<eventTitles.size();u++) {
                    System.out.println(u);
                    System.out.println("Event Title: "+ eventTitles.get(u));
                }
                for (int u=0;u<eventInfos.size();u++) {
                    System.out.println(u);
                    System.out.println("Event Info: "+ eventInfos.get(u));
                }
                for (int u=0;u<eventDescs.size();u++) {
                    System.out.println(u);
                    System.out.println("Event Desc: "+ eventDescs.get(u));
                }

                System.out.println("stage three");
            }
            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            String link;
            int j = 1;

            String imgId = "imageView"+j;
            for(int i=0;i<15;i++) {
                if (j == 1) {
                    ImageView changeImage = (ImageView) findViewById(R.id.imageView);
                    link = eventImages.get(i);
                    Picasso.with(changeImage.getContext()).load(link).into(changeImage);
                } else {
                    int resID = getResources().getIdentifier(imgId, "id", "com.matthew.whatsonandroidapp");
                    ImageView changeImage = (ImageView) findViewById(resID);

                    link = eventImages.get(i);
                    System.out.println("-------------------"+j+"----------------------");
                    System.out.println("-------------------"+i+"-----------------------");
                    System.out.println(url + link);
                    //Picasso.with(changeImage.getContext()).load(link).into(changeImage);
                    Picasso.with(changeImage.getContext()).load("http://www.essexstudent.com/asset/Event/6006/back_to_the_future.jpg?thumbnail_width=400&thumbnail_height=225&resize_type=CropToFit").into(changeImage);
                }
                j++;
            }
        }
    }
}
