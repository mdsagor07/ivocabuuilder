package com.swagger.ivocabuilder;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;


@Entity(tableName = "wordsTable" , indices = {@Index(value = {"word"},
        unique = true)})
public class Data {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @OnConflictStrategy
    @ColumnInfo(name = "word")
    private String word;

    @ColumnInfo(name = "word_meaning")
    private  String meaning;

    @ColumnInfo(name = "word_explanation")
    private String explanation;


    @ColumnInfo(name = "date")
    @TypeConverters(Converters.class)
    private Date date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
