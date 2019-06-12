package com.example.hackermail.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Email.class}, version = 1, exportSchema = false)
public abstract class EmailRoomDatabase extends RoomDatabase {

    private static EmailRoomDatabase INSTANCE;

    private static RoomDatabase.Callback emailRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(EmailRoomDatabase.INSTANCE).execute();
        }
    };

    public abstract EmailDao getEmailDao();

    public static EmailRoomDatabase getDatabase(final Context context) {
        if (EmailRoomDatabase.INSTANCE == null) {
            synchronized (EmailRoomDatabase.class) {
                if (EmailRoomDatabase.INSTANCE == null) {
                    // create database here
                    EmailRoomDatabase.INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            EmailRoomDatabase.class,
                            "email_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(emailRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return EmailRoomDatabase.INSTANCE;
    }

    public static void deleteInstance() {
        EmailRoomDatabase.INSTANCE = null;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final EmailDao emailDao;

        // Initial data set
        private long[] clock = {1560358264809L, 1560528000000L};
        private boolean[] clockIsOn = {false, true};
        private String[] topic = {"Notifying my wife I will be late", "Go to night club with my buddy"};
        private String[] to = {"kaeteyaruyo@gmail.com", "profatxuanall@gmail.com"};
        private String[] cc = {"profatxuanall@gmail.com", "kaeteyaruyo@gmail.com"};
        private String[] subject = {"subject for kaeteyaruyo", "subject for profatxuanall"};
        private String[] body = {"body for kaeteyaruyo", "body for profatxuanall"};

        PopulateDbAsync(EmailRoomDatabase db) {
            this.emailDao = db.getEmailDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // If we have no words, then create the initial list of emails
            if (this.emailDao.getAnyEmail().length < 1) {
                for (int i = 0; i < this.to.length; ++i) {
                    Email email = new Email(
                            this.clock[i],
                            this.clockIsOn[i],
                            this.topic[i],
                            this.to[i],
                            this.cc[i],
                            this.subject[i],
                            this.body[i]);
                    emailDao.insert(email);
                }
            }
            return null;
        }
    }
}
