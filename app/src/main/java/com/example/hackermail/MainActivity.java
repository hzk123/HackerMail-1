package com.example.hackermail;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MAIL_DATA = MainActivity.class.getName() + ".EXTRA_MAIL_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(MainActivity.this, SendMailAlarmReceiver.class);
                emailIntent.putExtra(MainActivity.EXTRA_MAIL_DATA, 0);

                PendingIntent emailPendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this,
                        0,
                        emailIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);

                AlarmManager am = (AlarmManager)getSystemService(MainActivity.this.ALARM_SERVICE);
                am.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), emailPendingIntent);
            }
        });
    }
}
