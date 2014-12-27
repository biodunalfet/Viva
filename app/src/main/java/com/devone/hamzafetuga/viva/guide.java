package com.devone.hamzafetuga.viva;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AppEventsLogger;

import java.util.ArrayList;
import java.util.List;


public class guide extends FragmentActivity{
    MyPageAdapter pageAdapter;
    FragmentManager fman;
    ActionBar aBar;
    SharedPreferences spref;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spref= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int h= spref.getInt("GUIDE_COUNT",0);

        if (h>0)
        {
            skipGuide();
        }
        setContentView(R.layout.activity_guide);


        h++;
        SharedPreferences.Editor editor = spref.edit();
        editor.putInt("GUIDE_COUNT",h);
        editor.commit();

        aBar= getActionBar();
        getActionBar().hide();
        aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        pageAdapter= new MyPageAdapter(getSupportFragmentManager());
        final ViewPager pager;
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pageAdapter);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabSelected(ActionBar.Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub
                pager.setCurrentItem(arg0.getPosition());
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }


            public void onTabUnselected(ActionBar.Tab arg0, FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }


        };

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 4; i++) {
            aBar.addTab(
                    aBar.newTab()
                            .setText("Tab " + (i + 1))
                            .setTabListener(tabListener));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }

    public void skipGuide(View v)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void skipGuide()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
