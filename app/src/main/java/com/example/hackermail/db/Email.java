package com.example.hackermail.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "email")
public class Email {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "email_id")
    private int emailId;

    @NonNull
    @ColumnInfo(name = "clock")
    private long clock;

    @NonNull
    @ColumnInfo(name = "switch")
    private boolean clockIsOn;



    @NonNull
    @ColumnInfo(name = "to")
    private String to;


    @NonNull
    @ColumnInfo(name = "subject")
    private String subject;

    @NonNull
    @ColumnInfo(name = "body")
    private String body;

    public Email(@NonNull long clock,
                 @NonNull boolean clockIsOn,

                 @NonNull String to,

                 @NonNull String subject,
                 @NonNull String body) {
        this.clock = clock;
        this.clockIsOn = clockIsOn;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public int getEmailId() {
        return this.emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public long getClock() {
        return this.clock;
    }

    public void setClock(long clock) {
        this.clock = clock;
    }

    public boolean getClockIsOn() {
        return this.clockIsOn;
    }

    public void setClockIsOn(boolean clockIsOn) {
        this.clockIsOn = clockIsOn;
    }



    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
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
