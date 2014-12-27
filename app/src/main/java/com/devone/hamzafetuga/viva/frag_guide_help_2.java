package com.devone.hamzafetuga.viva;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class frag_guide_help_2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_slide2, container, false);
        TextView half= (TextView) rootView.findViewById(R.id.textView_halfway);
        TextView before= (TextView) rootView.findViewById(R.id.textView_before);
        TextView after= (TextView) rootView.findViewById(R.id.textView_after);
        String texth = "<font color='#000000'>Use dose </font> <font color='#13832B'>HALFWAY</font>.";
        String textb = "<font color='#000000'>Not </font> <font color='#FF0000'>BEFORE</font>.";
        String texta = "<font color='#000000'>Not </font> <font color='#FF0000'>AFTER</font>.";
        half.setText(Html.fromHtml((texth)));
        before.setText(Html.fromHtml((textb)));
        after.setText(Html.fromHtml((texta)));



        return rootView;
    }
}