package com.devone.hamzafetuga.viva;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Hamza Fetuga on 11/23/2014.
 */
public class NotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(R.layout.notifs);
        SharedPreferences sprefs= getSharedPreferences(guide.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sprefs.edit();


        int generalIndex= sprefs.getInt("DRUG_INDEX",0);
        Drug currentDrug= MalariaDrugs.malariaDrugs[generalIndex];
        String currentDrugName= currentDrug.Name;
        int userDosageIndex= getIntent().getIntExtra("INDEX", 0);
        int currentDrugDose= currentDrug.Dosage[userDosageIndex-1];
//        LayoutInflater layoutInflater= this.getLayoutInflater();
//        View x=layoutInflater.inflate(R.layout.reminder_alertdialog,null);
//        TextView re= (TextView)x.findViewById(R.id.textView_re);
//        re.setText("Please, take "+ currentDrugDose +" of "+currentDrugName+" now!");

        Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reminder_alertdialog);
        TextView re= (TextView) dialog.findViewById(R.id.textView_re);
        re.setText("Please, take "+ currentDrugDose +" tablets of "+currentDrugName+" now!");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        dialog.show();


//        AlertDialog alertDialog = new AlertDialog.Builder(this)
//
//                .setView(x)
//                .setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialogInterface) {
//                        finish();
//                    }
//                })
//                .create();
//
//        alertDialog.show();
    }
}
