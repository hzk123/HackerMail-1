package com.example.hackermail.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "template_topic")
public class TemplateTopic {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "template_topic_id")
    private long templateTopicId;

    @NonNull
    @ColumnInfo(name = "topic")
    private String topic;

    public TemplateTopic(@NonNull String topic) {
        this.topic = topic;
    }

    public long getTemplateTopicId() {
        return this.templateTopicId;
    }

    public void setTemplateTopicId(long templateTopicId) {
        this.templateTopicId = templateTopicId;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
