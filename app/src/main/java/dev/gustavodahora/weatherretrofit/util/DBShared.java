package dev.gustavodahora.weatherretrofit.util;

import android.content.SharedPreferences;

public class DBShared {

    SharedPreferences.Editor editor;

    public String getCity(SharedPreferences pref) {
        return pref.getString("city", "NO_CITY");
    }

    public void setCity(SharedPreferences pref, String city) {
        getEditor(pref);
        editor.putString("city", city).apply();
    }

    public void getEditor(SharedPreferences pref) {
        editor = pref.edit();
    }

}
