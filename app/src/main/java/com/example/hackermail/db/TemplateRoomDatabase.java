package com.example.hackermail.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {TemplateTopic.class, Template.class}, version = 1, exportSchema = false)
public abstract class TemplateRoomDatabase extends RoomDatabase {

    private static TemplateRoomDatabase INSTANCE;

    private static RoomDatabase.Callback templateRoomDatabaseCallback = new RoomDatabase.Callback() {
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
        private String[] topic = {"公事", "工作應酬", "親友聚會"};
        private String[][] template = {
                {"今晚老闆突然要meeting,必須要加班到很晚", "工作沒有做完，馬上要到DDL了快要死了"},
                {"客戶來了逃不掉了實在沒有辦法很抱歉", "老闆請客吃飯必須要給面子不然要被開除了"},
                {"二十年交情的老朋友突然來找我了，我實在太高興了，今天必須要請他吃飯。非常抱歉晚上不回來了。", "我突然想起來今天同事結婚"}};

        PopulateDbAsync(TemplateRoomDatabase db) {
            this.templateDao = db.getTemplateDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // If we have no words, then create the initial list of emails
            if (this.templateDao.getAnyTemplateTopic().length < 1) {
                for (int i = 0; i < this.topic.length; ++i) {
                    TemplateTopic templateTopic = new TemplateTopic(this.topic[i]);
                    long templateTopicId = this.templateDao.insertTemplateTopic(templateTopic);

                    for (int j = 0; j < this.template[i].length; ++j) {
                        Template template = new Template(templateTopicId, this.template[i][j]);
                        this.templateDao.insertTemplate(template);
                    }
                }
            }
            return null;
        }
    }
}
