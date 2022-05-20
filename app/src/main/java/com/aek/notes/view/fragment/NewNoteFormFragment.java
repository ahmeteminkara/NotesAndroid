package com.aek.notes.view.fragment;


import static com.aek.notes.core.constants.AppConstants.DEFAULT_NOTE_COLOR;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aek.notes.R;
import com.aek.notes.core.constants.ColorPaletteConstants;
import com.aek.notes.core.util.AppUtils;
import com.aek.notes.databinding.FragmentNewNoteFormBinding;
import com.aek.notes.view.colorpalette.ColorPalette;
import com.aek.notes.viewmodel.ViewModelNote;
import com.aek.notes.viewmodel.ViewModelNoteForm;

public class NewNoteFormFragment extends Fragment {

    private FragmentNewNoteFormBinding binding;
    private final Runnable onCallback;

    public NewNoteFormFragment(Runnable onCallback) {
        this.onCallback = onCallback;
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

        initView();

        ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.observe(getViewLifecycleOwner(), isOpen -> {
            if (!isOpen)
                binding.colorPalette.hidePalette();

        });
        return binding.getRoot();
    }

    private void initView() {
        /*
        binding.fabColor.setOnClickListener(view -> ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.setValue(true));
        */
        binding.fabSave.setOnClickListener(view -> save());

        if (ViewModelNote.getInstance().updateFormData != null) {

            String color = ViewModelNote.getInstance().updateFormData.colorHex;
            ViewModelNoteForm.getInstance().setBgColor(color);
            binding.colorPalette.setForegroundColor(color);
            binding.colorPalette.setDefaultColor(color);
            binding.fabSave.setColorFilter(Color.parseColor(color));

            binding.textInputTitle.setText(ViewModelNote.getInstance().updateFormData.title);
            binding.textInputContent.setText(ViewModelNote.getInstance().updateFormData.content);
            ViewModelNoteForm.getInstance().modelNote = ViewModelNote.getInstance().updateFormData;
        } else {

            binding.colorPalette.setDefaultColor(DEFAULT_NOTE_COLOR);
        }
        binding.colorPalette.setBackgroundColor("#000000");

        binding.colorPalette.setColorList(ColorPaletteConstants.COLOR_LIST);
        binding.colorPalette.setListener(new ColorPalette.ColorPaletteListener() {
            @Override
            public void onChangeVisiblePalette(boolean visible) {
                ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.setValue(visible);
                if (visible) {
                    if (ViewModelNoteForm.getInstance().modelNote != null)
                        binding.colorPalette.setForegroundColor(ViewModelNoteForm.getInstance().modelNote.colorHex);
                }
            }

            @Override
            public void onColorSelected(String color) {
                ViewModelNoteForm.getInstance().setBgColor(color);
                binding.colorPalette.setForegroundColor(color);
                binding.fabSave.setColorFilter(Color.parseColor(color));
            }
        });

        initTextEdit();
    }

    private void initTextEdit() {

        binding.textInputTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ViewModelNoteForm.getInstance().setTitle(editable.toString());
            }
        });
        binding.textInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ViewModelNoteForm.getInstance().setContent(editable.toString());
            }
        });

    }

    private void save() {

        if (ViewModelNoteForm.getInstance().modelNote.title.isEmpty()) {
            AppUtils.showSnackBar(binding.formLayout, "Title is empty");
            return;
        }

        if (ViewModelNoteForm.getInstance().modelNote.content.isEmpty()) {
            AppUtils.showSnackBar(binding.formLayout, "Description is empty");
            return;
        }

        if (ViewModelNote.getInstance().updateFormData == null) {
            // güncelle
            if (ViewModelNoteForm.getInstance().updateNote(getContext())) {
                onCallback.run();
            } else {
                AppUtils.showSnackBar(binding.formLayout, "No note updated");
            }

        } else {
            // ekle
            if (ViewModelNoteForm.getInstance().addNote(getContext())) {
                onCallback.run();
            } else {
                AppUtils.showSnackBar(binding.formLayout, "No note added");
            }
        }
    }


}