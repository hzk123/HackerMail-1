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
import android.widget.Toast;

import com.example.hackermail.db.Email;
import com.example.hackermail.db.EmailListAdapter;
import com.example.hackermail.db.EmailViewModel;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_REQUEST_CODE = MainActivity.class.getName() + ".EXTRA_REQUEST_CODE";

    public static final int DEFAULT_EMAIL_ACTIVITY_REQUEST_CODE = 0;
    public static final int NEW_EMAIL_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_EMAIL_ACTIVITY_REQUEST_CODE = 2;

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

                Intent editDataIntent = new Intent(MainActivity.this, EditDataActivity.class);

                editDataIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_ID, email.getEmailId());
                editDataIntent.putExtra(MainActivity.EXTRA_REQUEST_CODE, UPDATE_EMAIL_ACTIVITY_REQUEST_CODE);

                MainActivity.this.startActivityForResult(editDataIntent, MainActivity.UPDATE_EMAIL_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = this.findViewById(R.id.email_recyclerview);
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
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent editDataIntent = new Intent(MainActivity.this, EditDataActivity.class);
                editDataIntent.putExtra(MainActivity.EXTRA_REQUEST_CODE, NEW_EMAIL_ACTIVITY_REQUEST_CODE);
                startActivityForResult(editDataIntent, MainActivity.NEW_EMAIL_ACTIVITY_REQUEST_CODE);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Error handling.
        if (resultCode != RESULT_OK) {
            String failedToastMessage;
            switch (requestCode) {
                case NEW_EMAIL_ACTIVITY_REQUEST_CODE:
                    failedToastMessage = "failed to create new email";
                    break;
                case UPDATE_EMAIL_ACTIVITY_REQUEST_CODE:
                    failedToastMessage = "failed to update new email";
                    break;
                default:
                    failedToastMessage = "failed with mysterious reason";
            }
            Toast.makeText(this, failedToastMessage, Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
