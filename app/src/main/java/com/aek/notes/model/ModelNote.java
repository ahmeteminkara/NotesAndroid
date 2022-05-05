package com.aek.notes.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class ModelNote {

    /**
     * ColumnInfo() default column name is field name
     */

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String title;

    @ColumnInfo
    public String content;

    @ColumnInfo
    public String colorHex;

    @ColumnInfo
    public long createdTime;

    public ModelNote setTitle(String title) {
        this.title = title;
        return this;
    }

    public ModelNote setContent(String content) {
        this.content = content;
        return this;
    }

    public ModelNote setColorHex(String colorHex) {
        this.colorHex = colorHex;
        return this;
    }
}
