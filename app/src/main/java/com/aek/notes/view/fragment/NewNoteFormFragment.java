package com.aek.notes.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aek.notes.R;
import com.aek.notes.constants.ColorPaletteConstants;
import com.aek.notes.databinding.FragmentNewNoteFormBinding;
import com.aek.notes.view.customview.ColorPalette;
import com.aek.notes.viewmodel.ViewModelNoteForm;

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

        /*
        binding.fabColor.setOnClickListener(view -> ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.setValue(true));
        */
        binding.fabSave.setOnClickListener(view -> save());

        binding.colorPalette.setColorList(ColorPaletteConstants.COLOR_LIST);
        binding.colorPalette.setListener(new ColorPalette.ColorPaletteListener() {
            @Override
            public void onChangeVisiblePalette(boolean visible) {
                ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.setValue(visible);
            }

            @Override
            public void onColorSelected(String color) {
                ViewModelNoteForm.getInstance().setBgColor(color);
                binding.colorPalette.setForegroundColor(color);
            }
        });

        ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.observe(getViewLifecycleOwner(), isOpen -> {
            if (!isOpen)
                binding.colorPalette.hidePalette();

        });
        return binding.getRoot();
    }


    private void save() {
    }

}