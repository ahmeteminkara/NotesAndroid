package com.aek.notes.view.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.aek.notes.R;
import com.aek.notes.adapter.ColorAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialContainerTransform;

import java.util.ArrayList;
import java.util.List;

public abstract class SuperColorPalette extends FrameLayout {

    protected String bgColor;
    protected String fgColor;

    public SuperColorPalette(@NonNull Context context) {
        super(context);
        initComponent(context);
    }

    public SuperColorPalette(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponent(context);
    }

    public SuperColorPalette(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent(context);
    }

    public SuperColorPalette(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initComponent(context);
    }

    protected Context context;
    private boolean isOpenPalette = false;

    protected ColorPalette.ColorPaletteListener colorPaletteListener;
    protected List<String> colors = new ArrayList<>();

    public abstract void setColorList(List<String> color);

    public abstract void hidePalette();

    public abstract void setListener(ColorPalette.ColorPaletteListener listener);

    public abstract void setBackgroundColor(String color);

    public abstract void setForegroundColor(String color);

    public abstract void setIcon(int icon);

    protected FloatingActionButton fabColorButton;
    protected MaterialButton btnPaletteClose;
    protected MaterialCardView cardPalette;
    private FrameLayout customLayout;
    private RecyclerView recyclerViewCardOption;
    protected ColorAdapter colorAdapter;
    private String selectedColor;

    protected abstract String setDefaultColor();

    public abstract String setDefaultColor(String color);

    private void initComponent(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_color_palette, this);

        initView();
        initRecycler();
    }

    private void initView() {
        customLayout = findViewById(R.id.customLayout);
        fabColorButton = findViewById(R.id.fabColorButton);
        btnPaletteClose = findViewById(R.id.btnPaletteClose);
        cardPalette = findViewById(R.id.cardPalette);

        fabColorButton.setOnClickListener(view -> openPalette());
        btnPaletteClose.setOnClickListener(view -> closePalette());
        btnPaletteClose.setBackgroundColor(Color.BLACK);
    }

    private void initRecycler() {
        recyclerViewCardOption = findViewById(R.id.recyclerViewCardOption);
        recyclerViewCardOption.setLayoutManager(new GridLayoutManager(context, 2));


        colorAdapter = new ColorAdapter(context, colors, s -> {
            selectedColor = s;
            closePalette();
            if (colorPaletteListener != null) colorPaletteListener.onColorSelected(s);
        });
        recyclerViewCardOption.setAdapter(colorAdapter);

    }

    private void openPalette() {

        MaterialContainerTransform transition = buildContainerTransformation();

        transition.setStartView(fabColorButton);
        transition.setEndView(cardPalette);
        transition.addTarget(cardPalette);

        TransitionManager.beginDelayedTransition(customLayout, transition);
        cardPalette.setVisibility(View.VISIBLE);
        fabColorButton.setVisibility(View.GONE);
        if (colorPaletteListener != null && !isOpenPalette) colorPaletteListener.onChangeVisiblePalette(true);
        isOpenPalette = true;


        if (colors.size() > 0) {
            ViewGroup.LayoutParams params = recyclerViewCardOption.getLayoutParams();
            int row = (int) Math.ceil(colors.size() / 2) * 70;

            params.height = colors.size() * 70;
            recyclerViewCardOption.setLayoutParams(params);
        }


    }

    protected void closePalette() {
        MaterialContainerTransform transition = buildContainerTransformation();

        transition.setStartView(cardPalette);
        transition.setEndView(fabColorButton);
        transition.addTarget(fabColorButton);

        TransitionManager.beginDelayedTransition(customLayout, transition);
        cardPalette.setVisibility(View.GONE);
        fabColorButton.setVisibility(View.VISIBLE);

        if (colorPaletteListener != null && isOpenPalette) colorPaletteListener.onChangeVisiblePalette(false);
        isOpenPalette = false;
    }


    private MaterialContainerTransform buildContainerTransformation() {
        MaterialContainerTransform transition = new MaterialContainerTransform();
        transition.setFadeMode(MaterialContainerTransform.FADE_MODE_IN);
        transition.setContainerColor(MaterialColors.getColor(getRootView(), com.google.android.material.R.attr.colorSecondary));
        transition.setScrimColor(Color.TRANSPARENT);
        transition.setDuration(300);
        transition.setInterpolator(new FastOutSlowInInterpolator());
        return transition;
    }

}
