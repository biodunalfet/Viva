package com.devone.hamzafetuga.viva;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.widget.Toast;

/**
 * Created by Hamza Fetuga on 11/23/2014.
 */
public class AlarmReceiver extends BroadcastReceiver {
SharedPreferences spref;
SharedPreferences.Editor editor;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    int index;
    @Override
    public void onReceive(Context context, Intent intent) {

        spref= context.getSharedPreferences(guide.MyPREFERENCES,Context.MODE_PRIVATE);//get Shared Preferences
        editor= spref.edit();
        index= spref.getInt("INDEX",1);
        // Toast.makeText(context, "Alarm "+index, Toast.LENGTH_SHORT).show();


        Intent intent1 = new Intent(context, NotificationActivity.class);
        intent1.putExtra("INDEX", index)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



        context.startActivity(intent1);

        setNextAlarm(context);
    }

    public void setNextAlarm(Context context) {
        int drug_index= spref.getInt("DRUG_INDEX",0);
        Drug currentDrug= new Drug(drug_index);
        int Timing[]= currentDrug.Timing;
        boolean[] hasTakenDrug= UserHelper.loadHasTakenDrugArray(context, UserHelper.ARRAY_NAME);



        if (index < currentDrug.Dosage.length)
        {
            int nextTime= Timing[index-1];

            index++;
            editor.putInt("INDEX", index);
            editor.commit();

            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            Toast.makeText(context, "Timing "+ nextTime, Toast.LENGTH_SHORT).show();

            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() +
                            nextTime*1000*1000, alarmIntent);
        }
        else
        {
            editor.remove("INDEX");
            editor.remove("SUBSCRIBED");
            editor.commit();
        }

    }
}
