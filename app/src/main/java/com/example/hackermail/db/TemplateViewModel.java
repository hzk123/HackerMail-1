package com.example.hackermail.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class TemplateViewModel extends AndroidViewModel {

    private TemplateRepository templateRepository;

    public TemplateViewModel(@NonNull Application application) {
        super(application);
        this.templateRepository = new TemplateRepository(application);
    }

    public LiveData<Template> getTemplate(long templateId) {
        return this.templateRepository.getTemplate(templateId);
    }

    public LiveData<List<Template>> getAllTemplates() {
        return this.templateRepository.getAllTemplates();
    }

    public LiveData<List<Template>> getAllTemplates(long templateTopicId) {
        return this.templateRepository.getAllTemplates(templateTopicId);
    }

    public void insertTemplate(Template template) {
        this.templateRepository.insertTemplate(template);
    }

    public void updateTemplate(Template template) {
        this.templateRepository.updateTemplate(template);
    }

    public void deleteTemplate(Long template) {
        this.templateRepository.deleteTemplate(template);
    }
}
