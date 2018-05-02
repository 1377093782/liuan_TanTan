package com.example.liuan.test;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liuan on 2018-04-28.
 */

public class SpUtils {
    private static SharedPreferences sp;
    public  static void getSharedPreference(Context context){
        if(sp==null){
            sp=context.getSharedPreferences("config",context.MODE_PRIVATE);
        }
    }
    public static void putString(Context context,String key,String value){
        getSharedPreference(context);
        sp.edit().putString(key,value).commit();
    }
    public static String getString(Context context,String key,String defValue){
        getSharedPreference(context);
       return sp.getString(key,defValue);
    }
    public static boolean getBoolean(Context context,String key,boolean defValue){
        getSharedPreference(context);
        return sp.getBoolean(key,false);
    }

    public static void putBoolean(Context context,String key,boolean value){
        getSharedPreference(context);
        sp.edit().putBoolean(key,value).commit();
    }
}
