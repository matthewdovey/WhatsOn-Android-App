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
import java.util.List;

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
        //Makes button objects so that we can add action listeners

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

        //Actionlistener added
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

    //Action Listener
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

    //I think this populates the ap with saved data

    /*@Override 
    protected void onSaveInstanceState(Bundle bundle) { 
            bundle.putStringArrayList("links", eventLinks);
             bundle.putStringArrayList("images", eventImages); 
            bundle.putStringArrayList("titles", eventTitles); 
            bundle.putStringArrayList("info", eventInfos); 
            bundle.putStringArrayList("desc", eventDescs); 
            super.onSaveInstanceState(bundle); 
    }*/

    //Refresh button

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        optionsMenu = menu;

        return super.onCreateOptionsMenu(menu);
    }

    //On click of the refresh button do this
    //example of how to complete actions on click
    //Currently using this version of completing an action
    //Does this actually do what I want it to do?

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_refresh:
            if (finished) {
                // Do animation start
                //Just delete if the animation currently being used works perfectly
                //Otherwise this is another way the animation can be done
                /*LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ImageView iv = (ImageView)inflater.inflate(R.layout.iv_refresh, null);
                Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh);
                rotation.setRepeatCount(Animation.INFINITE);
                iv.startAnimation(rotation);
                item.setActionView(iv);*/

                setRefreshActionButtonState(true);
                new JsoupListView().execute();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    //Refresh animation http://www.michenux.net/android-refresh-item-action-bar-circular-progressbar-578.html

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

    //Reset animation back to original picture
    //http://androidblog.reindustries.com/animating-update-or-loading-refresh-actionbar-button/

    public void resetUpdating()
    {
        System.out.println("Reset refresh button");
        // Get our refresh item from the menu
        MenuItem m = optionsMenu.findItem(R.id.action_refresh);
        if(m.getActionView()!=null)
        {
            // Remove the animation.
            m.getActionView().clearAnimation();
            m.setActionView(null);
        }
    }


    private class JsoupListView extends AsyncTask<ArrayList<String>,Void,ArrayList<String>>{

        private int x;
        private int itemNumber = x;

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... params) {

            WebScraper whatson = new WebScraper();

            //I don't think I need this
            //whatson.connect();

            System.out.println("starting");

            //Might not need this if statement
            //Might be slowing the app down
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

                System.out.println("Stage one complete");
            }
            resetUpdating();
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
                    //System.out.println(j);
                    //System.out.println(imgId);
                    int resID = getResources().getIdentifier(imgId, "id", "com.matthew.whatsonandroidapp");
                    ImageView changeImage = (ImageView) findViewById(resID);

                    //System.out.println(changeImage);

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

        //Saving some data when the app closes

        /*@Override 
        protected void onSaveInstanceState(Bundle bundle) { 
            bundle.putStringArrayList("links", eventLinks); 
            bundle.putStringArrayList("images", eventImages); 
            bundle.putStringArrayList("titles", eventTitles); 
            bundle.putStringArrayList("info", eventInfos); 
            bundle.putStringArrayList("desc", eventDescs);  
            bundle.putString("", focusLink); 
            bundle.putString("", focusImage); 
            bundle.putString("", focusTitle); 
            bundle.putString("", focusInfo); 
            bundle.putString("", focusDesc);  
            super.onSaveInstanceState(bundle); 
        }*/
    }
}
