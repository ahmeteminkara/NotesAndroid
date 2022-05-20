package com.aek.notes.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

    @Override
    public void onResume() {
        super.onResume();
        ViewModelNote.getInstance().loadLocalData(getContext());
    }

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false);


        initView();
        ViewModelNote.getInstance().liveDataNotes.observe(getViewLifecycleOwner(), dataSize -> {
            if (adapter != null) {
                adapter.setDataList(ViewModelNote.getInstance().mapData);
                adapter.notifyDataSetChanged();
            }
        });

        ViewModelNote.getInstance().liveUpdateHandler.observe(getViewLifecycleOwner(), note -> {
            if (adapter != null) {
                adapter.notifyItemChanged(adapter.updateDataItem(note));
            }
        });


        return binding.getRoot();
    }

    private void initView() {

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        binding.recyclerViewNotes.setLayoutManager(layoutManager);

        adapter = new MainListAdapter(ViewModelNote.getInstance().mapData, this);
        binding.recyclerViewNotes.setAdapter(adapter);

    }

    @Override
    public void onTouch(ModelNote note) {
        // selected map is not empty
        if (!ViewModelNote.getInstance().mapSelectedData.isEmpty()) {

            ViewModelNote.getInstance().toggleSelected(note,
                    // toggle
                    !ViewModelNote.getInstance().isSelect(note));
            return;
        }



    }

    @Override
    public void onLongTouch(ModelNote note) {
        if (!ViewModelNote.getInstance().isSelect(note)) {
            ViewModelNote.getInstance().toggleSelected(note, true);
            VibrateUtils.doVibrate(getContext(), 100);
        }
    }
}