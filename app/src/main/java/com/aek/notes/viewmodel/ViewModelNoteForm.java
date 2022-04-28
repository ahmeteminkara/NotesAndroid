package com.aek.notes.viewmodel;

import static com.aek.notes.constants.AppConstants.DEFAULT_NOTE_COLOR;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aek.notes.model.ModelNoteForm;

public class ViewModelNoteForm extends ViewModel {

    private ViewModelNoteForm() {
        liveDataModelForm.setValue(new ModelNoteForm().setColorHex(DEFAULT_NOTE_COLOR));
    }

    private static ViewModelNoteForm instance;

    public static ViewModelNoteForm getInstance() {
        if (instance == null) instance = new ViewModelNoteForm();
        return instance;
    }

    public boolean getColorPaletteStatus() {
        if (liveDataColorPaletteStatus.getValue() != null)
            return liveDataColorPaletteStatus.getValue();
        return false;
    }

    public final MutableLiveData<Boolean> liveDataColorPaletteStatus = new MutableLiveData<>();

    public final MutableLiveData<ModelNoteForm> liveDataModelForm = new MutableLiveData<>();

    public void setTitle(String title) {
        if (liveDataModelForm.getValue() != null)
            liveDataModelForm.setValue(liveDataModelForm.getValue().setTitle(title));
    }

    public void setContent(String content) {
        if (liveDataModelForm.getValue() != null)
            liveDataModelForm.setValue(liveDataModelForm.getValue().setTitle(content));
    }

    public void setBgColor(String color) {
        if (liveDataModelForm.getValue() != null)
            liveDataModelForm.setValue(liveDataModelForm.getValue().setColorHex(color));
    }


}
