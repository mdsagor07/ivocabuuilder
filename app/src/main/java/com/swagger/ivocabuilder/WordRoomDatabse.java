package com.swagger.ivocabuilder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = Data.class,version = 1)
@TypeConverters({Converters.class})
public abstract class WordRoomDatabse extends RoomDatabase {

    public abstract WordDao wordDao();

    public static volatile WordRoomDatabse wordRoomDatabse;

    static WordRoomDatabse getDatabase(final Context context ){
      if (wordRoomDatabse == null){
         synchronized (WordRoomDatabse.class){
             if (wordRoomDatabse == null){
                 wordRoomDatabse = Room.databaseBuilder(context.getApplicationContext(),
                         WordRoomDatabse.class,
                         "word_datbase").build();
             }

         }
      }
      return wordRoomDatabse;
    }


}

