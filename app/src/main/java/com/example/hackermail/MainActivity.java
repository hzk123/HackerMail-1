package com.example.hackermail;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hackermail.db.Email;
import com.example.hackermail.db.EmailListAdapter;
import com.example.hackermail.db.EmailViewModel;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MAIL_DATA = MainActivity.class.getName() + ".EXTRA_MAIL_DATA";

    private EmailViewModel emailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EmailListAdapter emailListAdapter = new EmailListAdapter(this);
        emailListAdapter.setOnItemClickListener(new EmailListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Email email = emailListAdapter.getEmailAtPosition(position);
                Log.d("email", "to: "+email.getTo());
                Log.d("email", "cc: "+email.getCc());
                Log.d("email", "subject: "+email.getSubject());
                Log.d("email", "body: "+email.getBody());
            }
        });

        RecyclerView recyclerView = findViewById(R.id.email_recyclerview);
        recyclerView.setAdapter(emailListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emailViewModel = ViewModelProviders.of(this).get(EmailViewModel.class);
        emailViewModel.getAllEmails().observe(this, new Observer<List<Email>>() {
            @Override
            public void onChanged(@Nullable List<Email> emails) {
                emailListAdapter.setEmails(emails);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

//        Button btn = findViewById(R.id.button);
//        btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent emailIntent = new Intent(MainActivity.this, SendMailAlarmReceiver.class);
//                emailIntent.putExtra(MainActivity.EXTRA_MAIL_DATA, 0);
//
//                PendingIntent emailPendingIntent = PendingIntent.getBroadcast(
//                        MainActivity.this,
//                        0,
//                        emailIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.SECOND, 5);
//
//                AlarmManager am = (AlarmManager)getSystemService(MainActivity.this.ALARM_SERVICE);
//                am.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), emailPendingIntent);
//            }
//        });
    }
}
