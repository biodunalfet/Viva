package com.devone.hamzafetuga.viva;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hamza Fetuga on 11/23/2014.
 */
public class UserHelper {

    final static String ARRAY_NAME = "hasTakenDrug";

    public static void saveHasTakenDrugArray(Context mContext, boolean[] array, String arrayName) {
        SharedPreferences prefs = mContext.getSharedPreferences(guide.MyPREFERENCES, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(arrayName +"_size", array.length);

        for(int i=0;i<array.length;i++)
            editor.putBoolean(arrayName + "_" + i, array[i]);
        editor.commit();
    }


    public static boolean[] loadHasTakenDrugArray(Context mContext, String arrayName) {
        SharedPreferences prefs = mContext.getSharedPreferences(guide.MyPREFERENCES, 0);

        int size = prefs.getInt(arrayName + "_size", 1);
        boolean array[] = new boolean[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getBoolean(arrayName + "_" + i, false);
        return array;
    }
}
