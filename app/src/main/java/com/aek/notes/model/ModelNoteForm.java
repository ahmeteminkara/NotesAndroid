package com.aek.notes.model;

import androidx.room.ColumnInfo;

public class ModelNoteForm {

    public String title;
    public String content;
    public String colorHex;

    public ModelNoteForm setTitle(String title) {
        this.title = title;
        return this;
    }

    public ModelNoteForm setContent(String content) {
        this.content = content;
        return this;
    }

    public ModelNoteForm setColorHex(String colorHex) {
        this.colorHex = colorHex;
        return this;
    }
}
