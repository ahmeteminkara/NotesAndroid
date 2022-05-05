package com.aek.notes.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aek.notes.core.roomdb.AppDatabase;
import com.aek.notes.model.ModelNote;

import java.util.ArrayList;
import java.util.List;

public class ViewModelNote extends ViewModel {

    private ViewModelNote() {
        temp = new ArrayList<>();
        mutableLiveDataNoteList = new MutableLiveData<>();
        mutableLiveDataSelectedNoteList = new MutableLiveData<>();
    }

    private static ViewModelNote instance;

    public static ViewModelNote getInstance() {
        if (instance == null) instance = new ViewModelNote();
        return instance;
    }


    private final List<ModelNote> temp;
    public final MutableLiveData<List<ModelNote>> mutableLiveDataNoteList;

    public final MutableLiveData<List<ModelNote>> mutableLiveDataSelectedNoteList;

    public boolean selectedCountEqualAllListSize() {
        if (mutableLiveDataNoteList.getValue() != null && mutableLiveDataSelectedNoteList.getValue() != null)
            return mutableLiveDataNoteList.getValue().size() == mutableLiveDataSelectedNoteList.getValue().size();
        else
            return false;
    }

    public boolean toggleSelected(ModelNote note) {
        temp.clear();
        if (mutableLiveDataSelectedNoteList.getValue() != null)
            temp.addAll(mutableLiveDataSelectedNoteList.getValue());
        if (temp.contains(note))
            temp.remove(note);
        else
            temp.add(note);

        mutableLiveDataSelectedNoteList.setValue(temp);
        return !temp.contains(note);
    }

    public void loadLocalData(Context context) {
        mutableLiveDataNoteList.setValue(AppDatabase.getInstance(context).daoNote().getAll());
    }

    public void selectAll() {
        if (mutableLiveDataNoteList.getValue() == null) return;
        temp.clear();
        temp.addAll(mutableLiveDataNoteList.getValue());
        mutableLiveDataSelectedNoteList.setValue(temp);
        temp.clear();
    }

    public boolean deleteSelected(Context context) {

        try {
            for (ModelNote modelNote : mutableLiveDataSelectedNoteList.getValue()) {
                AppDatabase.getInstance(context).daoNote().delete(modelNote);
            }
            mutableLiveDataSelectedNoteList.setValue(new ArrayList<>());
            loadLocalData(context);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
