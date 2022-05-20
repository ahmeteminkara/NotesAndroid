package com.aek.notes.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "notes")
public class ModelNote {

    /**
     * ColumnInfo() default column name is field name
     */

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    public int id;

    @ColumnInfo
    @SerializedName("title")
    public String title;

    @ColumnInfo
    @SerializedName("content")
    public String content;

    @ColumnInfo
    @SerializedName("colorHex")
    public String colorHex;

    @ColumnInfo
    @SerializedName("createdTime")
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

    @Override
    public String toString() {
        return "ModelNote{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", colorHex='" + colorHex + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
