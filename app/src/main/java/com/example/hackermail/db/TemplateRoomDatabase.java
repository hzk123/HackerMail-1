package com.example.hackermail.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {TemplateTopic.class, Template.class}, version = 1, exportSchema = false)
public abstract class TemplateRoomDatabase extends RoomDatabase {

    private static TemplateRoomDatabase INSTANCE;

    private static Callback templateRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(TemplateRoomDatabase.INSTANCE).execute();
        }
    };

    public abstract TemplateDao getTemplateDao();

    public static TemplateRoomDatabase getDatabase(final Context context) {
        if (TemplateRoomDatabase.INSTANCE == null) {
            synchronized (TemplateRoomDatabase.class) {
                if (TemplateRoomDatabase.INSTANCE == null) {
                    // create database here
                    TemplateRoomDatabase.INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TemplateRoomDatabase.class,
                            "template_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(templateRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return TemplateRoomDatabase.INSTANCE;
    }

    public static void deleteInstance() {
        TemplateRoomDatabase.INSTANCE = null;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TemplateDao templateDao;

        // Initial data set
        private String[] topic = {"topic 1", "topic 2"};
        private String[][] template = {
                {"template 1 in topic 1", "template 2 in topic 1"},
                {"template 1 in topic 2", "template 2 in topic 2"}};

        PopulateDbAsync(TemplateRoomDatabase db) {
            this.templateDao = db.getTemplateDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // If we have no words, then create the initial list of emails
            if (this.templateDao.getAnyTemplateTopic().length < 1) {
                for (int i = 0; i < this.topic.length; ++i) {
                    TemplateTopic templateTopic = new TemplateTopic(this.topic[i]);
                    this.templateDao.insertTemplateTopic(templateTopic);
                }
            }
            return null;
        }
    }
}
