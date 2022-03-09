package dev.gustavodahora.weatherretrofit.util;

import android.content.SharedPreferences;

public class DBShared {

    SharedPreferences.Editor editor;

    public void getEditor(SharedPreferences pref) {
        editor = pref.edit();
    }

    public String getCity(SharedPreferences pref) {
        return pref.getString("city", "NO_CITY");
    }

    public void setCity(SharedPreferences pref, String city) {
        getEditor(pref);
        editor.putString("city", city).apply();
    }

    public String getCountry(SharedPreferences pref) {
        return pref.getString("country", "NO_COUNTRY");
    }

    public void setCountry(SharedPreferences pref, String city) {
        getEditor(pref);
        editor.putString("country", city).apply();
    }

    public String getState(SharedPreferences pref) {
        return pref.getString("state", "NO_STATE");
    }

    public void setState(SharedPreferences pref, String city) {
        getEditor(pref);
        editor.putString("state", city).apply();
    }

    public double getLatitude(SharedPreferences pref) {
        String stringLatitude = pref.getString("latitude", "0");
        return Double.parseDouble(stringLatitude);
    }

    public void setLatitude(SharedPreferences pref, double city) {
        getEditor(pref);
        editor.putString("latitude", Double.toString(city)).apply();
    }

    public double getLongitude(SharedPreferences pref) {
        String stringLongitude = pref.getString("longitude", "0");
        return Double.parseDouble(stringLongitude);
    }

    public void setLongitude(SharedPreferences pref, double city) {
        getEditor(pref);
        editor.putString("longitude", Double.toString(city)).apply();
    }
}
