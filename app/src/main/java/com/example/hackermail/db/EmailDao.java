package com.example.hackermail.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface EmailDao {
    @Insert
    void insert(Email email);

    @Update
    public void update(Email... emails);

    @Query("select * from email where emailId = :emailId")
    public Email findEmail(int emailId);
}
