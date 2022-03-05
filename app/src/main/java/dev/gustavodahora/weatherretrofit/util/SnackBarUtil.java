package dev.gustavodahora.weatherretrofit.util;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import dev.gustavodahora.weatherretrofit.R;

public class SnackBarUtil {

    public static void showShort(View screen, String message, Context context) {
        Snackbar.make(screen, message, 3000)
                .setAction("Action", null)
                .setBackgroundTint(ContextCompat.getColor(context, R.color.black))
                .setTextColor(ContextCompat.getColor(context, R.color.white))
                .show();
    }

    public static void showLong(View screen, String message, Context context) {
        Snackbar.make(screen, message, 10000)
                .setAction("Action", null)
                .setBackgroundTint(ContextCompat.getColor(context, R.color.black))
                .setTextColor(ContextCompat.getColor(context, R.color.white))
                .show();
    }
}
