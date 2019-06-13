package com.example.hackermail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToEmailList = this.findViewById(R.id.go_to_email_list);
        goToEmailList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmailListActivity.class);
                startActivity(intent);
            }
        });

        Button goToTemplateList = this.findViewById(R.id.go_to_template_list);

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
