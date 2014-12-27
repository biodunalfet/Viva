package com.devone.hamzafetuga.viva;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;


public class InfoPage extends Activity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private UiLifecycleHelper uiHelper;
    Bundle sIS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        getActionBar().hide();

        sIS= savedInstanceState;
        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);

        sharedPreferences= getSharedPreferences(guide.MyPREFERENCES, Context.MODE_PRIVATE); //gets sharedPreferences
        int drug_index= sharedPreferences.getInt("DRUG_INDEX", 0);   //gets Drug selected by User
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putBoolean("SUBSCRIBED", true);
        editor.commit();


        Toast.makeText(this, drug_index+"", Toast.LENGTH_SHORT).show();
        User user= new User(new Drug(drug_index));  //Creates new User object
        user.setElementOfHasTakenDrug(0, true);     //specifies that the user has used the first dose

        Drug currentDrug= new Drug(drug_index);     //Creates new Drug object
        int Timing[]= currentDrug.Timing;           //gets the Timing array of the drug

        UserHelper.saveHasTakenDrugArray(this, user.hasTakenDrug, UserHelper.ARRAY_NAME); //saves the UserTaken Array to sharedPrefs


        alarmMgr = (AlarmManager) this.getApplicationContext().getSystemService(Context.ALARM_SERVICE); //
        Intent intent = new Intent(this, AlarmReceiver.class);                                          //Creates intent for Alarm class
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);                                               //clears previous activities

        Toast.makeText(this, Timing[0]+"", Toast.LENGTH_SHORT).show();

        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + Timing[0]*1000,
                alarmIntent);
    }

    @Override
    protected void onRestart() {
        boolean subscription= sharedPreferences.getBoolean("SUBSCRIBED", false);

        if (subscription == true)
        {
            Intent intent= new Intent(this, SideEffect.class);
            startActivity(intent);
        }

        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_page, menu);
        return true;
    }

    public void doFacebookShit()
    {

        if (FacebookDialog.canPresentShareDialog(getApplicationContext(),   //checks if user has the Facebook app installed
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
            // Publish the post using the Share Dialog

                //noFacebookApp();
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                    .setLink("https://developers.facebook.com/android")
                    .setName("Viva")
                    .setApplicationName("Viva")
                    .setCaption("Share Viva with Friends")
                    .setDescription("Viva is an health app that helps with "+
        "drug adherence and provides easy and friendly reminders for your drug usage. Download now from the play store")
                    .setPicture("http://www.whitelies.org.uk/sites/default/files/milkmyths/sites/default/files/Viva!-logo-white-bkg.gif")
                    .build();
            uiHelper.trackPendingDialogCall(shareDialog.present());

        } else {
            // Fallback. For example, publish the post using the Feed Dialog
                noFacebookApp();
        }


    }

    public void noFacebookApp()
    {
        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            Log.d("Tag", "Success!");
            Toast.makeText(this,"not null",Toast.LENGTH_SHORT).show();
            publishFeedDialog();
        } else {

            Toast.makeText(this,"null",Toast.LENGTH_SHORT).show();
            Session NewSession = Session.getActiveSession();
            if (!NewSession.isOpened() && !NewSession.isClosed()) {

                //          List<String> permissions = new ArrayList<String>();
                //            permissions.add("email");
                NewSession.openForRead(new Session.OpenRequest(this)
                        //                .setPermissions(permissions)
                        .setCallback(mFacebookCallback));
            } else {
                Session.openActiveSession(InfoPage.this, true, mFacebookCallback);
            }
        }
    }

    private Session.StatusCallback mFacebookCallback = new Session.StatusCallback() {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) {

            if (state.isOpened()) {
                String facebookToken = session.getAccessToken();
                Log.i("MainActivityFaceBook", facebookToken);
                Request.newMeRequest(session, new Request.GraphUserCallback() {

                    @Override
                    public void onCompleted(GraphUser user,
                                            com.facebook.Response response) {
                        publishFeedDialog();
                    }
                }).executeAsync();
                //Prefs.setStringProperty(getActivity(), R.string.key_prefs_facebook_token, facebookToken);
            }
        }
    };
    private void publishFeedDialog() {

        Bundle params = new Bundle();
        params.putString("name", "Viva");
        params.putString("caption", "Share Viva with Friends");
        params.putString("description", "Viva is an health app that helps with drug adherence and " +
                "provides easy and friendly reminders for your drug usage. Download now from the play store");
        params.putString("link", "https://developers.facebook.com/android");
        params.putString("picture", "http://www.whitelies.org.uk/sites/default/files/milkmyths/" +
                "sites/default/files/Viva!-logo-white-bkg.gif");

        WebDialog feedDialog = (
                new WebDialog.FeedDialogBuilder(this,
                        Session.getActiveSession(),
                        params))
                .setOnCompleteListener(new WebDialog.OnCompleteListener() {

                    @Override
                    public void onComplete(Bundle values,
                                           FacebookException error) {
                        if (error == null) {
                            // When the story is posted, echo the success
                            // and the post Id.
                            final String postId = values.getString("post_id");
                            if (postId != null) {
                                Toast.makeText(InfoPage.this,
                                        "Posted story, id: "+postId,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // User clicked the Cancel button
                                Toast.makeText(InfoPage.this.getApplicationContext(),
                                        "Publish cancelled",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else if (error instanceof FacebookOperationCanceledException) {
                            // User clicked the "x" button
                            Toast.makeText(InfoPage.this.getApplicationContext(),
                                    "Publish cancelled",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Generic, ex: network error
                            Toast.makeText(InfoPage.this.getApplicationContext(),
                                    "Error posting story",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Facebook", error.toString());
                        }
                    }

                })
                .build();
        feedDialog.show();
    }





    @Override
    public void onBackPressed() {

        boolean sub= sharedPreferences.getBoolean("SUBSCRIBED", false);
        if (sub== true)
        {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        else
        {
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
        }

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
    else if (id ==  R.id.shareToFacebook){
            doFacebookShit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
}
