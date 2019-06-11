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

    public String getTo() {
        return this.to;
    }

    public String getCc() {
        return this.cc;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getBody() {
        return this.body;
    }
}
