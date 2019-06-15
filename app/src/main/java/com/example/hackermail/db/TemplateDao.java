package com.example.hackermail.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TemplateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertTemplate(Template template);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertTemplateTopic(TemplateTopic templateTopic);

    @Update
    public void updateTemplate(Template template);

    @Update
    public void updateTemplateTopic(TemplateTopic templateTopic);

    @Delete
    public void deleteTemplate(Template template);

    @Delete
    public void deleteTemplateTopic(TemplateTopic templateTopic);

    @Query("SELECT * FROM template")
    LiveData<List<Template>> getAllTemplates();

    @Query("SELECT * FROM template WHERE template_topic_id = :templateTopicId")
    LiveData<List<Template>> getAllTemplates(long templateTopicId);

    @Query("SELECT * FROM template_topic")
    LiveData<List<TemplateTopic>> getAllTemplateTopics();

    @Query("SELECT * FROM template LIMIT 1")
    Template[] getAnyTemplate();

    @Query("SELECT * FROM template WHERE template_topic_id = :templateTopicId LIMIT 1")
    Template[] getAnyTemplate(long templateTopicId);

    @Query("SELECT * FROM template_topic LIMIT 1")
    TemplateTopic[] getAnyTemplateTopic();

    @Query("SELECT * FROM template WHERE template_id = :templateId")
    LiveData<Template> getTemplate(long templateId);

    @Query("SELECT * FROM template_topic WHERE template_topic_id = :templateTopicId")
    LiveData<TemplateTopic> getTemplateTopic(long templateTopicId);
}
