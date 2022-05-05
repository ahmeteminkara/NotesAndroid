package com.aek.notes.view;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;

import com.aek.notes.R;
import com.aek.notes.core.constants.AppConstants;
import com.aek.notes.core.util.AppUtils;
import com.aek.notes.databinding.ActivityMainBinding;
import com.aek.notes.model.ModelNote;
import com.aek.notes.view.fragment.NoteListFragment;
import com.aek.notes.viewmodel.ViewModelNote;
import com.aek.notes.viewmodel.ViewModelNoteForm;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        // binding
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));

        // transform
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);

        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        binding.fabAddNote.setOnClickListener(view -> {
            ViewModelNoteForm.getInstance().setBgColorDefault();
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                    MainActivity.this, binding.fabAddNote,
                    AppConstants.FAB_BUTTON_TO_ADD_NOTE_TRANSITION_NAME).toBundle();
            startActivity(intent, bundle);
        });

        showFormFragment();


    }

    @Override
    protected void onResume() {
        ViewModelNote.getInstance().loadLocalData(this);
        super.onResume();
    }

    private void showFormFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true)
                .replace(binding.frameLayoutMain.getId(), NoteListFragment.class, null)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        initSearchMenu(menu);

        ViewModelNote.getInstance().mutableLiveDataSelectedNoteList.observe(this, modelNotes -> {
            menu.findItem(R.id.action_search).setVisible(modelNotes.isEmpty());
            menu.findItem(R.id.action_deselect).setVisible(!modelNotes.isEmpty());
            menu.findItem(R.id.action_all_select).setVisible(!ViewModelNote.getInstance().selectedCountEqualAllListSize());
            menu.findItem(R.id.action_selected_delete).setVisible(!modelNotes.isEmpty());
        });

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all_select:
                ViewModelNote.getInstance().selectAll();
                return true;
            case R.id.action_selected_delete:
                if (!ViewModelNote.getInstance().deleteSelected(this)) {
                    AppUtils.showSnackBar(binding.frameLayoutMain, "Selected notes are not deleted");
                }
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSearchMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);


        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_deselect).setVisible(false);
        menu.findItem(R.id.action_all_select).setVisible(false);
        menu.findItem(R.id.action_selected_delete).setVisible(false);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


}