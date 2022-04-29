package com.aek.notes.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.aek.notes.R;
import com.aek.notes.constants.AppConstants;
import com.aek.notes.constants.ColorPaletteConstants;
import com.aek.notes.databinding.ActivityMainBinding;
import com.aek.notes.view.customview.ColorPalette;
import com.aek.notes.viewmodel.ViewModelNoteForm;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        // binding
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));

        // transform
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);

        binding.cp.setColorList(ColorPaletteConstants.COLOR_LIST);
        binding.cp.setBackgroundColor("#000000");
        binding.cp.setForegroundColor(ColorPaletteConstants.RED);
        binding.cp.setListener(new ColorPalette.ColorPaletteListener() {
            @Override
            public void onChangeVisiblePalette(boolean visible) {
                ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.setValue(visible);
            }

            @Override
            public void onColorSelected(String color) {
                ViewModelNoteForm.getInstance().setBgColor(color);
                binding.cp.setForegroundColor(ColorPaletteConstants.GREEN);
            }
        });

        ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.observe(this, isOpen -> {
            if (!isOpen)
                binding.cp.hidePalette();

        });

        binding.fabAddNote.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                    MainActivity.this, binding.fabAddNote,
                    AppConstants.FAB_BUTTON_TO_ADD_NOTE_TRANSITION_NAME).toBundle();
            startActivity(intent, bundle);
        });

        // layout
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        boolean status = ViewModelNoteForm.getInstance().getColorPaletteStatus();
        if (status) {
            ViewModelNoteForm.getInstance().liveDataColorPaletteStatus.setValue(false);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}