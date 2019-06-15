package com.example.hackermail.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TemplateRepository {

    private TemplateDao templateDao;
    private LiveData<List<TemplateTopic>> allTemplateTopics;

    TemplateRepository(Application application) {
        TemplateRoomDatabase db = TemplateRoomDatabase.getDatabase(application);
        this.templateDao = db.getTemplateDao();
        this.allTemplateTopics = this.templateDao.getAllTemplateTopics();
    }

    LiveData<List<TemplateTopic>> getAllTemplateTopics() {return this.allTemplateTopics;}

    public LiveData<TemplateTopic> getTemplateTopic(int templateTopicId){
        return this.templateDao.getTemplateTopic(templateTopicId);
    }

    public void insertTemplateTopic(TemplateTopic templateTopic) {
        new InsertTemplateTopicAsyncTask(this.templateDao).execute(templateTopic);
    }

    public void updateTemplateTopic(TemplateTopic templateTopic) {
        new UpdateTemplateTopicAsyncTask(this.templateDao).execute(templateTopic);
    }

    private static class InsertTemplateTopicAsyncTask extends AsyncTask<TemplateTopic, Void, Void> {
        private TemplateDao templateDao;

        InsertTemplateTopicAsyncTask(TemplateDao templateDao) {
            this.templateDao = templateDao;
        }

        @Override
        protected Void doInBackground(TemplateTopic... templateTopics) {
            this.templateDao.insertTemplateTopic(templateTopics);
            return null;
        }
    }

    private static class UpdateTemplateTopicAsyncTask extends AsyncTask<TemplateTopic, Void, Void> {
        private TemplateDao templateDao;

        UpdateTemplateTopicAsyncTask(TemplateDao templateDao) {
            this.templateDao = templateDao;
        }


        @Override
        protected Void doInBackground(TemplateTopic... templateTopics) {
            this.templateDao.updateTemplateTopic(templateTopics);
            return null;
        }
    }
}
