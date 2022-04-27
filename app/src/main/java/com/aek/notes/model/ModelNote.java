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

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    public String createdTime;
}
