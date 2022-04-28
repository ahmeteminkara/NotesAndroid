package com.aek.notes.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.aek.notes.R;
import com.aek.notes.constants.AppConstants;
import com.aek.notes.databinding.ActivityAddNoteBinding;
import com.aek.notes.view.fragment.NewNoteFormFragment;
import com.aek.notes.viewmodel.ViewModelNoteForm;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.platform.MaterialArcMotion;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class AddNoteActivity extends AppCompatActivity {

    ActivityAddNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setWindowParams();

        // binding & transform
        View view = setBingingAndTransformParams();

        // layout
        setContentView(view);
        super.onCreate(savedInstanceState);

        actionBarCustomising();
        showFormFragment();

        ViewModelNoteForm.getInstance().liveDataModelForm.observe(this, modelNoteForm -> {
            binding.activityAddNote.setBackgroundColor(Color.parseColor(modelNoteForm.colorHex));
        });


    }

    private void setWindowParams() {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.defaultTransparentColor));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }


    private void actionBarCustomising() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.defaultTransparentColor)));
        }
    }

    private View setBingingAndTransformParams() {
        final View view = getLayoutInflater().inflate(R.layout.activity_add_note, null, false);
        binding = ActivityAddNoteBinding.bind(view);
        binding.activityAddNote.setTransitionName(AppConstants.FAB_BUTTON_TO_ADD_NOTE_TRANSITION_NAME);

        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementEnterTransition(buildTransform(true));
        getWindow().setSharedElementReturnTransition(buildTransform(false));
        binding.activityAddNote.setBackgroundColor(Color.parseColor(AppConstants.DEFAULT_NOTE_COLOR));

        return binding.getRoot();

    }

    private void showFormFragment() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(binding.frameLayoutForm.getId(), NewNoteFormFragment.class, null)
                .commit();

    }

    private Transition buildTransform(boolean entering) {
        MaterialContainerTransform transform = new MaterialContainerTransform(this, entering);
        transform.addTarget(binding.activityAddNote)
                .setDuration(400)
                .setInterpolator(new LinearInterpolator())
                .setPathMotion(new MaterialArcMotion());
        transform.setAllContainerColors(MaterialColors.getColor(
                findViewById(android.R.id.content),
                com.google.android.material.R.attr.colorSurface));

        return transform;
    }

    @Override
    public void onBackPressed() {
        if (ViewModelNoteForm.getInstance().getColorPaletteStatus()){
            ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.setValue(false);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}