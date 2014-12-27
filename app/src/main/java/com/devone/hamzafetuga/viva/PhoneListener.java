package com.devone.hamzafetuga.viva;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Hamza Fetuga on 12/27/2014.
 */
public class PhoneListener extends PhoneStateListener {
    public static String condition="";

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            //Hangup
            case TelephonyManager.CALL_STATE_IDLE:
                condition="idle";
                //CallBroadcastReceiver.cancelView();
                break;
            //Outgoing
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("state_call", "on going");
                condition= "on-going";
                break;
            //Incoming
            case TelephonyManager.CALL_STATE_RINGING:
                condition= "ringing";

                break;
        }
    }
}
