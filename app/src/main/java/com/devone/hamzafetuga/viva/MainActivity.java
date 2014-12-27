package com.devone.hamzafetuga.viva;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends Activity {
    String terms;
    String selectedDrug;
    Button spinner;
    ArrayAdapter<CharSequence> adapter;
    int drug_index;
    SharedPreferences sprefs;
    SharedPreferences.Editor editor;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Select Drug");
        Button btn= (Button) findViewById(R.id.spinner_);
        Typeface tf= Typeface.createFromAsset(getAssets(),"KGBlankSpaceSketch.ttf");
        btn.setTypeface(tf);
        getActionBar().hide();
        sprefs= getSharedPreferences(guide.MyPREFERENCES, Context.MODE_PRIVATE);
        editor= sprefs.edit();



        boolean subscription= sprefs.getBoolean("SUBSCRIBED", false);

        if (subscription == true)
        {
            Intent intent= new Intent(this, SideEffect.class);
            startActivity(intent);
        }


        spinner= (Button)findViewById(R.id.spinner_);
        spinner.setTypeface(tf);
        adapter= ArrayAdapter.createFromResource(this,R.array.Drugs,R.layout.spinner_centre);
        adapter.setDropDownViewResource(R.layout.spinner_centre);

    }



    public void showDrugList(View v)
    {
        final String[] drugs= getResources().getStringArray(R.array.Drugs);
        AlertDialog.Builder dl= new AlertDialog.Builder(this);
        dl.setTitle("Select your drug").setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,i+"", Toast.LENGTH_SHORT).show();
                editor.putInt("DRUG_INDEX", i);
                editor.commit();
                ShowTermsAndConditions(drugs[i]);
            }
        }).create().show();
    }

    public void ShowTermsAndConditions(final String selectedDrug)
    {
        //selectedDrug= spinner.getSelectedItem().toString();
        terms= "By choosing "+ selectedDrug + ", Viva assumes you have just taken the first dose of 1 tablets" +
                "(or not more than 10 minutes ago";
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage(terms).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                verifyTime();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

    private void verifyTime() {

        Calendar c= Calendar.getInstance();
        Date now= c.getTime();
        Date start;
        Date end;
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("HH:mm");

        try {
            start= simpleDateFormat.parse("00:00");
            end= simpleDateFormat.parse("23:59");

            String h= simpleDateFormat.format(c.getTime());
            Toast.makeText(this,h,Toast.LENGTH_SHORT).show();
            Date ct= simpleDateFormat.parse(h);
            boolean diff= ct.after(start);
            boolean diff2= ct.before(end);

            if (diff && diff2)
            {
                editor.putBoolean("SUBSCRIBED",true );
                editor.commit();
                startTransaction(selectedDrug);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    public void startTransaction(String selectedDrug)
    {
        Intent intent= new Intent(this,InfoPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
