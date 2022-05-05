package com.aek.notes.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aek.notes.R;
import com.aek.notes.adapter.MainListAdapter;
import com.aek.notes.core.util.VibrateUtils;
import com.aek.notes.databinding.FragmentNoteListBinding;
import com.aek.notes.model.ModelNote;
import com.aek.notes.viewmodel.ViewModelNote;

public class NoteListFragment extends Fragment implements MainListAdapter.OnTouchListener {

    private FragmentNoteListBinding binding;
    public MainListAdapter adapter;

    public NoteListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false);


        ViewModelNote.getInstance().mutableLiveDataNoteList.observe(getViewLifecycleOwner(), modelNotes -> {
            if (adapter != null){
                adapter.setDataList(modelNotes);
                adapter.notifyDataSetChanged();
            }
        });

        ViewModelNote.getInstance().loadLocalData(getContext());
        initView();

        return binding.getRoot();
    }

    private void initView() {

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        binding.recyclerViewNotes.setLayoutManager(layoutManager);

        adapter = new MainListAdapter(
                getContext(),
                ViewModelNote.getInstance().mutableLiveDataNoteList.getValue(), this);
        binding.recyclerViewNotes.setAdapter(adapter);

    }

    @Override
    public void onTouch(ModelNote note) {

    }

    @Override
    public void onLongTouch(ModelNote note) {
        if (ViewModelNote.getInstance().toggleSelected(note)) {
            VibrateUtils.doVibrate(getContext(), 100);
        }
    }
}