package com.devone.hamzafetuga.viva;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Created by Hamza Fetuga on 12/27/2014.
 */
public class CallBroadcastReceiver extends BroadcastReceiver{

    private WindowManager windowManager;
    private static LinearLayout linearLayout;
    private  WindowManager.LayoutParams layoutParams;
    public static Context contcan;

    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "In", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences= context.getSharedPreferences(guide.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        String source= sharedPreferences.getString("Source","");
        editor.putString("Source","");
        editor.commit();
        final Context ctx= context;
        final Intent intnt= intent;
        contcan= context;

        if (source == "Viva")
        {

            ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).listen(new PhoneListener(),
                    PhoneStateListener.LISTEN_CALL_STATE);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    createView(ctx,intnt);
                }
            }, 900);
        }

    }


    public static void cancelView()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                try
                {
                    WindowManager windowManager1= (WindowManager) contcan.getSystemService(Context.WINDOW_SERVICE);

                    if (linearLayout!=null)
                    {
                        windowManager1.removeView(linearLayout);
                        linearLayout=null;
                    }
                }
                catch (Exception e)
                {

                }

            }
        }, 300);
    }

    public void createView(final Context context, Intent intent)
    {
        windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        layoutParams= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);

        layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.x= 265;
//        layoutParams.y= 400;
        layoutParams.format= PixelFormat.TRANSLUCENT;

        linearLayout= new LinearLayout((context));
        linearLayout.setBackgroundColor(Color.BLACK);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //linearLayout.addView(inflate);

        View view= View.inflate(context, R.layout.activity_call, null);
        view.findViewById(R.id.ec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                TelephonyManager tm = (TelephonyManager) context
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
        });
        linearLayout.addView(view);
        windowManager.addView(linearLayout, layoutParams);

    }
}
