package com.aek.notes.core.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class VibrateUtils {
    private static Vibrator vibrator;

    public static void doVibrate(Context context, int duration) {
        try {
            if (vibrator == null) vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
            else vibrator.vibrate(duration);
        } catch (Exception e) {
            Log.e("DeviceTool", "vibrate: " + e.toString());
        }
    }

    public static void doVibrate(Context context, long[] pattern) {
        try {
            if (vibrator == null) vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(pattern, -1);
        } catch (Exception e) {
            Log.e("DeviceTool", "vibrate: " + e.toString());
        }
    }

}
