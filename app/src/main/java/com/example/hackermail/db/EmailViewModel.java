package com.example.hackermail.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class EmailViewModel extends AndroidViewModel {

    private EmailRepository emailRepository;
    private LiveData<List<Email>> allEmails;

    public EmailViewModel(@NonNull Application application) {
        super(application);
        this.emailRepository = new EmailRepository(application);
        this.allEmails = this.emailRepository.getAllEmails();
    }

    public LiveData<List<Email>> getAllEmails(){
        return this.allEmails;
    }

    public void insert(Email email){
        this.emailRepository.insert(email);
    }
}
