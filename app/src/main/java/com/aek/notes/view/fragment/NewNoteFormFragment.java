package com.aek.notes.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.transition.TransitionManager;

import com.aek.notes.R;
import com.aek.notes.databinding.FragmentNewNoteFormBinding;
import com.aek.notes.viewmodel.ViewModelNoteForm;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.MaterialContainerTransform;

public class NewNoteFormFragment extends Fragment {

    private FragmentNewNoteFormBinding binding;

    public NewNoteFormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_note_form, container, false);

        binding.fabColor.setOnClickListener(view -> ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.setValue(true));
        binding.fabSave.setOnClickListener(view -> save());

        ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.observe(getViewLifecycleOwner(), isOpen -> {
            if (isOpen)
                colorPaletteOpen();
            else
                colorPaletteClose();

        });

        return binding.getRoot();
    }


    private void colorPaletteOpen() {
        MaterialContainerTransform transition = buildContainerTransformation();

        transition.setStartView(binding.fabColor);
        transition.setEndView(binding.card);
        transition.addTarget(binding.card);

        TransitionManager.beginDelayedTransition(binding.formLayout, transition);
        binding.card.setVisibility(View.VISIBLE);
        binding.fabColor.setVisibility(View.INVISIBLE);
        //binding.fabScrim.visibility = View.VISIBLE

    }


    private void colorPaletteClose() {
        MaterialContainerTransform transition = buildContainerTransformation();

        transition.setStartView(binding.card);
        transition.setEndView(binding.fabColor);
        transition.addTarget(binding.fabColor);

        TransitionManager.beginDelayedTransition(binding.formLayout, transition);
        binding.card.setVisibility(View.INVISIBLE);
        binding.fabColor.setVisibility(View.VISIBLE);
        //binding.fabScrim.visibility = View.VISIBLE

    }


    private MaterialContainerTransform buildContainerTransformation() {
        MaterialContainerTransform transition = new MaterialContainerTransform();
        transition.setFadeMode(MaterialContainerTransform.FADE_MODE_IN);
        transition.setContainerColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorSecondary));
        transition.setScrimColor(Color.TRANSPARENT);
        transition.setDuration(300);
        transition.setInterpolator(new FastOutSlowInInterpolator());
        return transition;
    }

    private void save() {
    }


}