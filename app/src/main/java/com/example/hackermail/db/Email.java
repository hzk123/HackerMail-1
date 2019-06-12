package com.example.hackermail.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "email")
public class Email {

    @PrimaryKey(autoGenerate = true)
    private int emailId;

    @NonNull
    @ColumnInfo(name = "to")
    private String to;

    @ColumnInfo(name = "cc")
    private String cc;

    @NonNull
    @ColumnInfo(name = "subject")
    private String subject;

    @NonNull
    @ColumnInfo(name = "body")
    private String body;

    public Email(@NonNull String to, String cc, @NonNull String subject, @NonNull String body) {
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.body = body;
    }

    public int getEmailId() {
        return this.emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return this.cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
