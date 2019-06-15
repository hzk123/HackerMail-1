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
    public void insertTemplateTopic(TemplateTopic... templateTopics);

    @Update
    public void updateTemplateTopic(TemplateTopic... templateTopics);

    @Delete
    public void deleteTemplateTopic(TemplateTopic... templateTopics);

    @Query("SELECT * FROM template_topic")
    LiveData<List<TemplateTopic>> getAllTemplateTopics();

    @Query("SELECT * FROM template_topic LIMIT 1")
    TemplateTopic[] getAnyTemplateTopic();

    @Query("SELECT * FROM template_topic WHERE template_topic_id = :templateTopicId")
    LiveData<TemplateTopic> getTemplateTopic(int templateTopicId);
}
