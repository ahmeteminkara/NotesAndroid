package com.aek.notes.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aek.notes.core.roomdb.AppDatabase;
import com.aek.notes.model.ModelNote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModelNote extends ViewModel {

    private static ViewModelNote instance;
    public ModelNote updateFormData = null;

    public static ViewModelNote getInstance() {
        if (instance == null) instance = new ViewModelNote();
        return instance;
    }

    public final Map<Integer, ModelNote> mapData = new HashMap<>();
    public final Map<Integer, ModelNote> mapSelectedData = new HashMap<>();

    public final MutableLiveData<ModelNote> liveUpdateHandler = new MutableLiveData<>();
    public final MutableLiveData<Integer> liveDataNotes = new MutableLiveData<>();
    public final MutableLiveData<Integer> liveDataNotesSelected = new MutableLiveData<>();
    public final MutableLiveData<String> liveDataSearchWord = new MutableLiveData<>();


    public boolean isSelect(ModelNote note) {
        return mapSelectedData.containsKey(note.id);
    }


    public void toggleSelected(ModelNote note, boolean isAdd) {
        if (isAdd) {
            mapSelectedData.put(note.id, note);
        } else {
            mapSelectedData.remove(note.id);
        }
        liveUpdateHandler.setValue(note);
        liveDataNotesSelected.setValue(mapSelectedData.size());
    }

    public void loadLocalData(Context context) {
        mapData.clear();
        List<ModelNote> list = AppDatabase.getInstance(context).daoNote().getAll();
        for (ModelNote note : list) {
            mapData.put(note.id, note);
        }
        liveDataNotes.setValue(mapData.size());
    }


    public boolean deleteSelected(Context context) {

        try {
            for (ModelNote note : mapSelectedData.values()) {
                AppDatabase.getInstance(context).daoNote().deleteWithId(new int[]{note.id});
            }
            mapSelectedData.clear();
            liveDataNotesSelected.setValue(mapSelectedData.size());
            loadLocalData(context);
            return true;
        } catch (Exception e) {
            Log.e("deleteSelected", "error: " + e.getMessage());
            return false;
        }
    }
}
