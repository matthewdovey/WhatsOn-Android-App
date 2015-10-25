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
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static String focusImage;
    static String focusTitle;
    static String focusInfo;
    static String focusDesc;

    //private ArrayList<String> eventLinks = new ArrayList<String>();
    private ArrayList<String> eventTitles = new ArrayList<String>();
    private ArrayList<String> eventInfos = new ArrayList<String>();
    private ArrayList<String> eventImages = new ArrayList<String>();
    private ArrayList<String> eventDescs = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new JsoupListView().execute();

        /*Button eventPage = (Button) findViewById(R.id.button1);
        eventPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
            }
        });*/

        Button event1 = (Button) findViewById(R.id.button1);
        Button event2 = (Button) findViewById(R.id.button2);
        Button event3 = (Button) findViewById(R.id.button3);
        Button event4 = (Button) findViewById(R.id.button4);
        Button event5 = (Button) findViewById(R.id.button5);
        Button event6 = (Button) findViewById(R.id.button6);
        Button event7 = (Button) findViewById(R.id.button7);
        Button event8 = (Button) findViewById(R.id.button8);
        Button event9 = (Button) findViewById(R.id.button9);
        Button event10 = (Button) findViewById(R.id.button10);
        Button event11 = (Button) findViewById(R.id.button11);
        Button event12 = (Button) findViewById(R.id.button12);
        Button event13 = (Button) findViewById(R.id.button13);
        Button event14 = (Button) findViewById(R.id.button14);
        Button event15 = (Button) findViewById(R.id.button15);

        event1.setOnClickListener(buttonCheck);
        event2.setOnClickListener(buttonCheck);
        event3.setOnClickListener(buttonCheck);
        event4.setOnClickListener(buttonCheck);
        event5.setOnClickListener(buttonCheck);
        event6.setOnClickListener(buttonCheck);
        event7.setOnClickListener(buttonCheck);
        event8.setOnClickListener(buttonCheck);
        event9.setOnClickListener(buttonCheck);
        event10.setOnClickListener(buttonCheck);
        event11.setOnClickListener(buttonCheck);
        event12.setOnClickListener(buttonCheck);
        event13.setOnClickListener(buttonCheck);
        event14.setOnClickListener(buttonCheck);
        event15.setOnClickListener(buttonCheck);




        event14.setOnClickListener(buttonCheck);
    }

    View.OnClickListener buttonCheck = new View.OnClickListener() {
        public void onClick(View v) {

            int longId = v.getId();
            boolean buttFinder = false;
            int idNumber = 1;

            while (!buttFinder) {
                String id = "button"+idNumber;
                int resID = getResources().getIdentifier(id, "id", "com.matthew.whatsonandroidapp");
                if (longId == resID) {
                    buttFinder = true;
                    idNumber--;
                }
                idNumber++;
                System.out.println(idNumber);
            }
            System.out.println(idNumber);

            focusImage = eventImages.get(idNumber);
            focusTitle = eventTitles.get(idNumber);
            focusInfo = eventInfos.get(idNumber);
            focusDesc = eventDescs.get(idNumber);

            startActivity(new Intent(MainActivity.this, EventActivity.class));
        }
    };

    private class JsoupListView extends AsyncTask<ArrayList<String>,Void,ArrayList<String>>{

        private int x;
        private int itemNumber = x;

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

                //eventLinks = whatson.returnLinks();
                eventImages = whatson.returnImages();
                eventTitles = whatson.returnTitles();
                eventInfos = whatson.returnInfos();
                eventDescs = whatson.returnDescs();

                System.out.println("Stage one complete");
            }
            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            String link;
            String buttName;
            String infoName;
            String descName;

            System.out.println("stage two");

            //J might be changed by each for loop fucking up the other for loops.
            //Might need to put j=1 in each for loop or see if you can protect it.
            int j=1;

            for(int i=0;i<15;i++) {
                String imgId = "imageView"+j;
                if (j == 1) {
                    ImageView changeImage = (ImageView) findViewById(R.id.imageView);
                    link = eventImages.get(i);
                    Picasso.with(changeImage.getContext()).load(link).into(changeImage);
                } else {
                    System.out.println(j);
                    System.out.println(imgId);
                    int resID = getResources().getIdentifier(imgId, "id", "com.matthew.whatsonandroidapp");
                    ImageView changeImage = (ImageView) findViewById(resID);

                    System.out.println(changeImage);

                    link = eventImages.get(i);
                    try {
                        Picasso.with(changeImage.getContext()).load(link).into(changeImage);
                    } catch (Exception e) {
                        System.out.println("ERROR: Unable to use picture");
                    }
                }
                j++;
            }

            j=1;

            for(int i=0;i<15;i++) {
                String butId = "button"+j;
                int resID = getResources().getIdentifier(butId, "id", "com.matthew.whatsonandroidapp");
                Button changeButton = (Button) findViewById(resID);
                buttName = eventTitles.get(i);

                changeButton.setText(buttName);
                j++;
            }

            j=1;

            for(int i=0;i<15;i++) {
                String infoId = "info"+j;
                int resID = getResources().getIdentifier(infoId, "id", "com.matthew.whatsonandroidapp");
                TextView changeInfo = (TextView) findViewById(resID);
                infoName = eventInfos.get(i);

                changeInfo.setText(infoName);
                j++;
            }

            j=1;

            for(int i=0;i<15;i++) {
                String descId = "desc"+j;
                int resID = getResources().getIdentifier(descId, "id", "com.matthew.whatsonandroidapp");
                TextView changeDesc = (TextView) findViewById(resID);
                descName = eventDescs.get(i);

                changeDesc.setText(descName);
                j++;
            }
        }
    }
}
