package com.devone.hamzafetuga.viva;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hamza Fetuga on 12/24/2014.
 */

public class side_effect_list_frag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.side_effect_list, container, false);
        return rootView;
    }
}
