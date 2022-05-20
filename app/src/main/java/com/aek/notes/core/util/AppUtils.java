package com.aek.notes.core.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class AppUtils {


    private static Snackbar snackbar;

    public static void showSnackBar(View parentView, String message) {
        if (snackbar != null) snackbar.dismiss();
        snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showAlertDialog(Context context, String message, Runnable positive, Runnable negative) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Okay", (dialogInterface, i) -> positive.run());
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            if (negative != null) negative.run();
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

}
