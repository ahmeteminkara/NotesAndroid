package com.aek.notes.core.util;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class AppUtils {


    private static Snackbar snackbar;

    public static void showSnackBar(View parentView, String message) {
        if (snackbar != null) snackbar.dismiss();
        snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
