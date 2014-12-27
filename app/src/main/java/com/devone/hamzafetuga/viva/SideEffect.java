package com.devone.hamzafetuga.viva;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Method;


public class SideEffect extends Activity {
    RelativeLayout rl1;
    RelativeLayout rl2;
    RelativeLayout rl3;
    RelativeLayout rl4;
    RelativeLayout rl5;
    Fragment frag;
    int Visi[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_effect);
        FragmentManager fm= getFragmentManager();
        fm.beginTransaction().replace(R.id.bottom, new side_effect_list_frag()).commit();
    }

    @Override
    protected void onRestart() {
        CallBroadcastReceiver.cancelView();
        DeleteCallLogByNumber("08065869206");
        super.onRestart();
    }

    public void DeleteCallLogByNumber(String number) {
        String queryString="NUMBER="+number;
        this.getContentResolver().delete(CallLog.Calls.CONTENT_URI,queryString,null);
    }


    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        super.onBackPressed();
    }

    public void disconnectCall(){
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        TelephonyManager tm = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        try
        {

            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephonyService = m.invoke(tm); // Get the internal ITelephony object
            c = Class.forName(telephonyService.getClass().getName()); // Get its class
            m = c.getDeclaredMethod("endCall"); // Get the "endCall()" method
            m.setAccessible(true); // Make it accessible
            m.invoke(telephonyService); // invoke endCall()
        }
        catch (Exception e)
        {

        }
    }

    public void CallDoc(View view)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        //intent.setClassName("com.hamzafetuga.calling","com.hamzafetuga.calling.Call");
        SharedPreferences sharedPreferences= getSharedPreferences(guide.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("Source","Viva");
        editor.commit();
        intent.setData(Uri.parse("tel:08065869206"));
        startActivity(intent);
    }

    public void allGone(View view)
    {
        rl1= (RelativeLayout) findViewById(R.id.rl_1);
        rl2= (RelativeLayout) findViewById(R.id.rl_2);
        rl3= (RelativeLayout) findViewById(R.id.rl_3);
        rl4= (RelativeLayout) findViewById(R.id.rl_4);
        rl5= (RelativeLayout) findViewById(R.id.rl_5);

        RelativeLayout rls[]= new RelativeLayout[]{rl1,rl2,rl3,rl4,rl5};
        int ttx[]= new int[]{R.id.textView1,
                R.id.textView2,
                R.id.textView3,
                R.id.textView4,
                R.id.textView5};

        for (int x=0; x<5; x++)
        {
            if (view.getId()!=ttx[x])
            {
                rls[x].setVisibility(View.GONE);
            }
        }

    }

    public void toggleVisibility(RelativeLayout relativeLayout)
    {
        if (relativeLayout.getVisibility()== View.VISIBLE)
        {
            relativeLayout.setVisibility(View.GONE);
        }
        else
        {
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    public void switchFrag(View v)
    {
        rl1= (RelativeLayout) findViewById(R.id.rl_1);
        rl2= (RelativeLayout) findViewById(R.id.rl_2);
        rl3= (RelativeLayout) findViewById(R.id.rl_3);
        rl4= (RelativeLayout) findViewById(R.id.rl_4);
        rl5= (RelativeLayout) findViewById(R.id.rl_5);

        allGone(v);

        switch (v.getId())
        {
            case R.id.textView1:
                toggleVisibility(rl1);
                break;
            case R.id.textView2:
                toggleVisibility(rl2);
                break;
            case R.id.textView3:
                toggleVisibility(rl3);
                break;
            case R.id.textView4:
                toggleVisibility(rl4);
                break;
            case R.id.textView5:
                toggleVisibility(rl5);
                break;
        }


    }

    public void changeFrag(View v)
    {
        int index=0;
        switch (v.getId())
        {
            case R.id.button_1_2:
                index=1;
                break;
            case R.id.button_2_2:
                index=2;
                break;
            case R.id.button_3_2:
                index=3;
                break;
            case R.id.button_4_2:
                index=4;
                break;
            case R.id.button_5_2:
                index=5;
                break;
        }


        FragmentManager fm= getFragmentManager();
        Fragment x= new SideEffectDetails();
        Bundle abc= new Bundle();


        abc.putInt("index", index);
        x.setArguments(abc);
        findViewById(R.id.top).setVisibility(View.GONE);
        fm.beginTransaction().replace(R.id.bottom, x).commit();
    }

    public void back_to_base(View v)
    {
        FragmentManager fm= getFragmentManager();
        fm.beginTransaction().replace(R.id.bottom, new side_effect_list_frag()).commit();
    }




}
