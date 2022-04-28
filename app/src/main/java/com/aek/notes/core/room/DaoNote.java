package com.aek.notes.core.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aek.notes.model.ModelNote;

import java.util.List;

@Dao
public interface DaoNote {

    @Query("SELECT * FROM notes")
    List<ModelNote> getAll();

    @Query("SELECT * FROM notes WHERE title LIKE :word OR content LIKE :word")
    List<ModelNote> findByWord(String word);

    @Query("SELECT * FROM notes WHERE id = :noteIds")
    ModelNote loadAllById(int[] noteIds);

    @Query("DELETE FROM notes WHERE id IN (:noteIds)")
    int deleteWithId(int[] noteIds);

    @Insert
    void insertAll(ModelNote... notes);

    @Update
    int update(ModelNote note);

    @Delete
    int delete(ModelNote note);

}
