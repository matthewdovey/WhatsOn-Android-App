package com.matthew.whatsonandroidapp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static String focusLink;
    static String focusImage;
    static String focusTitle;
    static String focusInfo;
    static String focusDesc;

    private ArrayList<String> eventLinks = new ArrayList<String>();
    private ArrayList<String> eventTitles = new ArrayList<String>();
    private ArrayList<String> eventInfos = new ArrayList<String>();
    private ArrayList<String> eventImages = new ArrayList<String>();
    private ArrayList<String> eventDescs = new ArrayList<String>();

    private Menu optionsMenu;
    private boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView splashScreen = (ImageView) findViewById(R.id.splash);
        splashScreen.setScaleType(ImageView.ScaleType.FIT_XY);

        new JsoupListView().execute();

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
    }

    protected void onStart(Bundle savedInstanceState){
        //Populate array lists with saved data if they are not empty
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
            }
            focusLink = eventLinks.get(idNumber-1);
            focusImage = eventImages.get(idNumber-1);
            focusTitle = eventTitles.get(idNumber-1);
            focusInfo = eventInfos.get(idNumber-1);
            focusDesc = eventDescs.get(idNumber-1);
            startActivity(new Intent(MainActivity.this, EventActivity.class));
        }
    };

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        optionsMenu = menu;

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_refresh:
            if (finished) {
                setRefreshActionButtonState(true);
                new JsoupListView().execute();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setRefreshActionButtonState(final boolean refreshing) {
        if (optionsMenu != null) {
            final MenuItem refreshItem = optionsMenu
                    .findItem(R.id.action_refresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }

    public void resetUpdating() {
        MenuItem m = optionsMenu.findItem(R.id.action_refresh);
        if(m.getActionView()!=null)
        {
            m.getActionView().clearAnimation();
            m.setActionView(null);
        }
    }

    /*
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        eventLinks = savedInstanceState.getStringArrayList("Links");
        eventTitles = savedInstanceState.getStringArrayList("Images");
        eventInfos = savedInstanceState.getStringArrayList("Titles");
        eventImages = savedInstanceState.getStringArrayList("Info");
        eventDescs = savedInstanceState.getStringArrayList("Desc");
    }
    */

    private class JsoupListView extends AsyncTask<ArrayList<String>,Void,ArrayList<String>> {
        private int x;
        private int itemNumber = x;
        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... params) {
            WebScraper whatson = new WebScraper();
            whatson.connect();
            if (whatson.returnConnected()) {
                whatson.getLinks();
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);

            ImageView splashScreen = (ImageView) findViewById(R.id.splash);
            RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main);

            splashScreen.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);

            finished = true;

            String link;
            String buttName;
            String infoName;
            String descName;

            int j=1;

            for(int i=0;i<15;i++) {
                String imgId = "imageView"+j;
                if (j == 1) {
                    ImageView changeImage = (ImageView) findViewById(R.id.imageView);
                    link = eventImages.get(i);
                    Picasso.with(changeImage.getContext()).load(link).into(changeImage);
                } else {
                    int resID = getResources().getIdentifier(imgId, "id", "com.matthew.whatsonandroidapp");
                    ImageView changeImage = (ImageView) findViewById(resID);
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
            resetUpdating();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedpreferences = SharedPreferences.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        Set<String> linkSet = new HashSet<String>();
        Set<String> titleSet = new HashSet<String>();
        Set<String> infoSet = new HashSet<String>();
        Set<String> imageSet = new HashSet<String>();
        Set<String> descSet = new HashSet<String>();

        linkSet.addAll(eventLinks);
        titleSet.addAll(eventTitles);
        infoSet.addAll(eventInfos);
        imageSet.addAll(eventImages);
        descSet.addAll(eventDescs);

        editor.putStringSet("links", linkSet);
        editor.putStringSet("images", titleSet);
        editor.putStringSet("titles", infoSet);
        editor.putStringSet("info", imageSet);
        editor.putStringSet("desc", descSet);

        editor.apply();

        /*
        //Retrieve the values
        Set<String> set = myScores.getStringSet("key", null);

        //Set the values
        Set<String> set = new HashSet<String>();
        set.addAll(listOfExistingScores);
        scoreEditor.putStringSet("key", set);
        scoreEditor.commit();
        */
    }
}
