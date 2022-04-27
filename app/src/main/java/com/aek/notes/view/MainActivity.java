package com.aek.notes.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aek.notes.R;
import com.aek.notes.core.room.AppDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}