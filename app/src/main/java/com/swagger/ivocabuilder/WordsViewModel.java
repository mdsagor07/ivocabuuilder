package com.swagger.ivocabuilder;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordsViewModel extends AndroidViewModel {

   private String TAG = this.getClass().getSimpleName();
   private WordDao wordDao;
   private WordRoomDatabse wordDB;

   private LiveData<List<Data>>  mAllnotes;
   private LiveData<List<Data>>  mtodaynotes;
   private LiveData<List<Data>>  mMonthlynotes;
   private LiveData<Integer> getCount;



    public WordsViewModel(@NonNull Application application) {
        super(application);
        wordDB = WordRoomDatabse.getDatabase(application);
        wordDao = wordDB.wordDao();
        mAllnotes = wordDao.getAllNotes();
        mtodaynotes= wordDao.getTodayNotes();
        mMonthlynotes = wordDao.getMonthlyNotes();
        getCount = wordDao.getCount();


    }



    public void insert(Data data){
        new InsertASyncTask(wordDao).execute(data);
    }
    public void delete(Data data){
        new DeleteAsyncTask(wordDao).execute(data);
    }
    public void update(Data data){
        new UpdateAsyncTask(wordDao).execute(data);
    }

    LiveData<Integer> getCount() {
      return getCount;
    }
    LiveData<List<Data>> getAllNotes() {
        return mAllnotes;
    }
    LiveData<List<Data>> getTodayNotes() {
        return mtodaynotes;
    }
    LiveData<List<Data>> getMonthlyNotes() {
        return mMonthlynotes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG,"ViewMOdel Destroyed");
    }




    private static class InsertASyncTask extends AsyncTask<Data, Void, Void> {

        private WordDao mAsyncTaskDao;

        InsertASyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Data... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private class DeleteAsyncTask  extends AsyncTask<Data, Void, Void>{

        WordDao mWordDao;
        public DeleteAsyncTask(WordDao wordDao) {
            this.mWordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Data... data) {
            mWordDao.delete(data[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Data, Void, Void> {

        WordDao dao;

        public UpdateAsyncTask(WordDao wordDao) {
            this.dao=wordDao;
        }

        @Override
        protected Void doInBackground(Data... data) {
            dao.update(data[0]);
            return null;
        }
    }


}
