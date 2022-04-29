package com.aek.notes.view.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ColorPalette extends SuperColorPalette {


    public interface ColorPaletteListener {
        void onChangeVisiblePalette(boolean visible);

        void onColorSelected(String color);
    }

    @Override
    protected String setDefaultColor() {
        return "#00ff00";
    }

    @Override
    public String setDefaultColor(String color) {
        setForegroundColor(color);
        return color;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void setColorList(List<String> colorList) {
        super.colors = colorList;
        if (colorAdapter != null)
            colorAdapter.addColorList(colorList);
    }

    @Override
    public void setListener(ColorPaletteListener listener) {
        super.colorPaletteListener = listener;
    }

    @Override
    public void hidePalette() {
        super.closePalette();
    }

    @Override
    public void setBackgroundColor(String color) {
        fabColorButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
    }

    @Override
    public void setForegroundColor(String color) {
        fabColorButton.setColorFilter(Color.parseColor(color));
        btnPaletteClose.setIconTint(ColorStateList.valueOf(Color.parseColor(color)));
        btnPaletteClose.setTextColor(Color.parseColor(color));
    }

    @Override
    public void setIcon(int icon) {

    }

    public ColorPalette(@NonNull Context context) {
        super(context);
    }

    public ColorPalette(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorPalette(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ColorPalette(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
