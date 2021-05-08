package com.example.newsapplication.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class mysharedprefernce {

    //variables
    private static Context mAppContext = null;
    private final static String mySharedPreferenceName = "News";
    private final static String mySharedPreference_userOBJ = "userOBJ";






    private mysharedprefernce() {
    }

    //for send context
    public static void init(Context appContext) {
        mAppContext = appContext;
    }
    //---------------------

    private static SharedPreferences getSharedPreferences() {


        return mAppContext.getSharedPreferences(mySharedPreferenceName, Context.MODE_PRIVATE);
    }
    //setData
    public static void setUserOBJ(String userOBJSTR) {


        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(mySharedPreference_userOBJ, userOBJSTR).apply();
    }
    //----------------------------

    //getData
    public static String getUserOBJ() {
        return getSharedPreferences().getString(mySharedPreference_userOBJ, "");
    }
    //-----------------------------


}
