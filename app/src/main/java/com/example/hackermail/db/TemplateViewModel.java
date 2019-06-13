package com.example.hackermail.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class TemplateViewModel extends AndroidViewModel {

    private TemplateRepository templateRepository;
    private LiveData<List<TemplateTopic>> allTemplateTopics;

    public TemplateViewModel(@NonNull Application application) {
        super(application);
        this.templateRepository = new TemplateRepository(application);
        this.allTemplateTopics = this.templateRepository.getAllTemplateTopics();
    }

    public LiveData<TemplateTopic> getTemplateTopic(int templateTopicId){
        return this.templateRepository.getTemplateTopic(templateTopicId);
    }

    public LiveData<List<TemplateTopic>> getAllTemplateTopics(){
        return this.allTemplateTopics;
    }

    public void insertTemplateTopic(TemplateTopic templateTopic){
        this.templateRepository.insertTemplateTopic(templateTopic);
    }

    public void updateTemplateTopic(TemplateTopic templateTopic){
        this.templateRepository.updateTemplateTopic(templateTopic);
    }
}
