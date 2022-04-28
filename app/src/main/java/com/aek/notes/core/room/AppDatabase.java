package com.aek.notes.core.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aek.notes.model.ModelNote;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ModelNote.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoNote daoNote();

    private static AppDatabase instance;

    protected AppDatabase() {
    }

    public static AppDatabase getInstance(Context context) {
        if (instance == null)
                instance = Room.databaseBuilder(context, AppDatabase.class, "room_db")
                        .allowMainThreadQueries()
                        .build();

        return instance;
    }


}
