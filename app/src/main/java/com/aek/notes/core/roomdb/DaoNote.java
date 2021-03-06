package com.aek.notes.core.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aek.notes.model.ModelNote;

import java.util.List;

@Dao
public interface DaoNote {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<ModelNote> getAll();

    @Query("SELECT * FROM notes WHERE title LIKE :word OR content LIKE :word ORDER BY id DESC")
    List<ModelNote> findByWord(String word);

    @Query("SELECT * FROM notes WHERE id = :noteIds")
    ModelNote loadAllById(int[] noteIds);

    @Query("DELETE FROM notes WHERE id IN (:noteIds)")
    int deleteWithId(int[] noteIds);

    @Insert
    void insert(ModelNote notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReplace(ModelNote notes);

    @Update
    int update(ModelNote note);

    @Delete
    int delete(ModelNote note);

}
