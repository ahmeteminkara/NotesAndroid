package com.aek.notes.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ViewModelNote extends ViewModel {

    private ViewModelNote() {
    }

    private static ViewModelNote instance;

    public static ViewModelNote getInstance() {
        if (instance == null) instance = new ViewModelNote();
        return instance;
    }


    final MutableLiveData<List<Integer>> mutableLiveDataNotes = new MutableLiveData<>();

    final MutableLiveData<List<Integer>> mutableLiveDataSelectedNoteIDs = new MutableLiveData<>();



}
