package com.example.hackermail.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class TemplateTopicViewModel extends AndroidViewModel {

    private TemplateRepository templateRepository;

    public TemplateTopicViewModel(@NonNull Application application) {
        super(application);
        this.templateRepository = new TemplateRepository(application);
    }

    public LiveData<TemplateTopic> getTemplateTopic(long templateTopicId) {
        return this.templateRepository.getTemplateTopic(templateTopicId);
    }

    public LiveData<List<TemplateTopic>> getAllTemplateTopics() {
        return this.templateRepository.getAllTemplateTopics();
    }

    public void insertTemplateTopic(TemplateTopic templateTopic) {
        this.templateRepository.insertTemplateTopic(templateTopic);
    }

    public void updateTemplateTopic(TemplateTopic templateTopic) {
        this.templateRepository.updateTemplateTopic(templateTopic);
    }
}
