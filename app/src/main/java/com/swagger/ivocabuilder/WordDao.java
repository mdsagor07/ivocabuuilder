package com.swagger.ivocabuilder;


import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Data data);


    @Query("SELECT * FROM wordsTable")
    LiveData<List<Data>> getAllNotes();


    String currentDateTimeString = DateFormat.getDateInstance().format(new Date());



    @Query("SELECT * FROM wordsTable")
    LiveData<List<Data>> getTodayNotes();

    @Query("SELECT * FROM wordsTable")
    LiveData<List<Data>> getMonthlyNotes();

    @Delete
    int delete(Data data);

    @Query("SELECT * FROM wordsTable WHERE id=:noteid")
    LiveData<Data> getNotes(int noteid);

    @Update
    void update(Data data);

    @Query("SELECT COUNT(id) FROM wordsTable;")
    LiveData<Integer> getCount();

}
