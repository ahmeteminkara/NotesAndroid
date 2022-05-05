package com.aek.notes.viewmodel;


import static com.aek.notes.core.constants.AppConstants.DEFAULT_NOTE_COLOR;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aek.notes.core.roomdb.AppDatabase;
import com.aek.notes.model.ModelNote;

import java.util.Date;

public class ViewModelNoteForm extends ViewModel {

    private ViewModelNoteForm() {
        modelNote = new ModelNote().setColorHex(DEFAULT_NOTE_COLOR).setTitle("").setContent("");
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
    public final MutableLiveData<String> liveDataColor = new MutableLiveData<>();

    public ModelNote modelNote;

    public void setTitle(String title) {
        if (modelNote != null)
            modelNote.setTitle(title);
    }

    public void setContent(String content) {
        if (modelNote != null)
            modelNote.setContent(content);
    }

    public void setBgColor(String color) {
        liveDataColor.setValue(color);
        if (modelNote != null)
            modelNote.setColorHex(color);
    }

    public void setBgColorDefault() {
        setBgColor(DEFAULT_NOTE_COLOR);
    }

    public boolean addNote(Context context) {
        modelNote.createdTime = new Date().getTime();
        try {
            AppDatabase.getInstance(context).daoNote().insertReplace(modelNote);
            return true;
        } catch (Exception exception) {
            Log.e("addNote", exception.toString());
            return false;
        }

    }

}
