package com.example.hackermail.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "template",
        foreignKeys = @ForeignKey(entity = TemplateTopic.class,
                parentColumns = "template_topic_id",
                childColumns = "template_topic_id",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = {"template_topic_id"}, unique = true)})
public class Template {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "template_id")
    private long templateId;

    @NonNull
    @ColumnInfo(name = "template_topic_id")
    private long templateTopicId;

    @NonNull
    @ColumnInfo(name = "template")
    private String template;

    public Template(@NonNull long templateTopicId, @NonNull String template) {
        this.templateTopicId = templateTopicId;
        this.template = template;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    public long getTemplateTopicId() {
        return templateTopicId;
    }

    public void setTemplateTopicId(int templateTopicId) {
        this.templateTopicId = templateTopicId;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
