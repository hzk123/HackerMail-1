package com.example.hackermail.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class EmailRepository {

    private EmailDao emailDao;
    private LiveData<List<Email>> allEmails;

    EmailRepository(Application application) {
        EmailRoomDatabase db = EmailRoomDatabase.getDatabase(application);
        this.emailDao = db.getEmailDao();
        this.allEmails = this.emailDao.getAllEmails();
    }

    LiveData<List<Email>> getAllEmails() {
        return this.allEmails;
    }

    public void insert(Email email) {
        new insertAsyncTask(this.emailDao).execute(email);
    }

    public void update(Email email) {
        new updateAsyncTask(this.emailDao).execute(email);
    }

    private static class insertAsyncTask extends AsyncTask<Email, Void, Void> {
        private EmailDao insertAsyncTaskEmailDao;

        insertAsyncTask(EmailDao emailDao) {
            this.insertAsyncTaskEmailDao = emailDao;
        }


        @Override
        protected Void doInBackground(Email... emails) {
            this.insertAsyncTaskEmailDao.insert(emails[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Email, Void, Void> {
        private EmailDao updateAsyncTaskEmailDao;

        updateAsyncTask(EmailDao emailDao) {
            this.updateAsyncTaskEmailDao = emailDao;
        }


        @Override
        protected Void doInBackground(Email... emails) {
            this.updateAsyncTaskEmailDao.update(emails);
            return null;
        }
    }
}
