package com.example.hackermail.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TemplateRepository {

    private TemplateDao templateDao;

    public TemplateRepository(Application application) {
        TemplateRoomDatabase db = TemplateRoomDatabase.getDatabase(application);
        this.templateDao = db.getTemplateDao();
    }

    public LiveData<List<Template>> getAllTemplates() {
        return this.templateDao.getAllTemplates();
    }

    public LiveData<List<Template>> getAllTemplates(long templateTopicId) {
        return this.templateDao.getAllTemplates(templateTopicId);
    }

    public LiveData<List<TemplateTopic>> getAllTemplateTopics() {
        return this.templateDao.getAllTemplateTopics();
    }

    public LiveData<Template> getTemplate(long templateId) {
        return this.templateDao.getTemplate(templateId);
    }

    public LiveData<TemplateTopic> getTemplateTopic(long templateTopicId) {
        return this.templateDao.getTemplateTopic(templateTopicId);
    }

    public void insertTemplate(Template template) {
        new InsertTemplateAsyncTask(this.templateDao).execute(template);
    }

    public void insertTemplateTopic(TemplateTopic templateTopic) {
        new InsertTemplateTopicAsyncTask(this.templateDao).execute(templateTopic);
    }

    public void updateTemplate(Template template) {
        new UpdateTemplateAsyncTask(this.templateDao).execute(template);
    }

    public void updateTemplateTopic(TemplateTopic templateTopic) {
        new UpdateTemplateTopicAsyncTask(this.templateDao).execute(templateTopic);
    }

    private static class InsertTemplateAsyncTask extends AsyncTask<Template, Void, Void> {
        private TemplateDao templateDao;

        InsertTemplateAsyncTask(TemplateDao templateDao) {
            this.templateDao = templateDao;
        }

        @Override
        protected Void doInBackground(Template... templates) {
            this.templateDao.insertTemplate(templates[0]);
            return null;
        }
    }

    private static class InsertTemplateTopicAsyncTask extends AsyncTask<TemplateTopic, Void, Void> {
        private TemplateDao templateDao;

        InsertTemplateTopicAsyncTask(TemplateDao templateDao) {
            this.templateDao = templateDao;
        }

        @Override
        protected Void doInBackground(TemplateTopic... templateTopics) {
            this.templateDao.insertTemplateTopic(templateTopics[0]);
            return null;
        }
    }

    private static class UpdateTemplateAsyncTask extends AsyncTask<Template, Void, Void> {
        private TemplateDao templateDao;

        UpdateTemplateAsyncTask(TemplateDao templateDao) {
            this.templateDao = templateDao;
        }


        @Override
        protected Void doInBackground(Template... templates) {
            this.templateDao.updateTemplate(templates[0]);
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
            this.templateDao.updateTemplateTopic(templateTopics[0]);
            return null;
        }
    }
}
