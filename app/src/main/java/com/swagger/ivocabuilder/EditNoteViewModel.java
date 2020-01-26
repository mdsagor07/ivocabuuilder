package com.swagger.ivocabuilder;

import android.app.Application;
        import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EditNoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private WordDao wordDao;
    private WordRoomDatabse db;

    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "Edit ViewModel");
        db = WordRoomDatabse.getDatabase(application);
        wordDao = db.wordDao();
    }

    public LiveData<Data> getNotes(int noteId) {
        return wordDao.getNotes(noteId);
    }
}