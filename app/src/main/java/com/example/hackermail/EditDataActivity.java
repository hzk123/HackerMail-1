package com.example.hackermail;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.hackermail.db.DateTimeFormat;
import com.example.hackermail.db.Email;
import com.example.hackermail.db.EmailViewModel;
import com.example.hackermail.db.Template;
import com.example.hackermail.db.TemplateViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class EditDataActivity extends AppCompatActivity {


    public static final String EXTRA_DATA_MAIL_ID = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_ID";
    public static final String EXTRA_DATA_MAIL_CLOCK = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_CLOCK";
    public static final String EXTRA_DATA_MAIL_CLOCK_IS_NO = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_CLOCK_IS_NO";
    public static final String EXTRA_DATA_MAIL_TO = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_TO";
    public static final String EXTRA_DATA_MAIL_SUBJECT = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_SUBJECT";
    public static final String EXTRA_DATA_MAIL_BODY = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_BODY";


    private TextView clockYearTextView;
    private TextView clockMonthTextView;
    private TextView clockDayTextView;
    private TextView clockHourTextView;
    private TextView clockMinuteTextView;

    private EditText toEditText;
    private EditText subjectEditText;
    private EditText bodyEditText;
    private EditText EditTime;

    private int year, month, day, hour, minute, second = 0;

    private EmailViewModel emailViewModel;

    private int requestCode;
    private int emailId;


    public void Back_onClick(View v) {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data2);

        this.clockYearTextView = this.findViewById(R.id.clock_year);
        this.clockMonthTextView = this.findViewById(R.id.clock_month);
        this.clockDayTextView = this.findViewById(R.id.clock_day);
        this.clockHourTextView = this.findViewById(R.id.clock_hour);
        this.clockMinuteTextView = this.findViewById(R.id.clock_minute);

        this.toEditText = this.findViewById(R.id.edit_text_to);
        this.subjectEditText = this.findViewById(R.id.edit_text_subject);
        this.bodyEditText = this.findViewById(R.id.edit_text_body);
        this.EditTime = this.findViewById(R.id.editTextTime);
        this.EditTime.setFocusable(false);


        this.emailViewModel = ViewModelProviders.of(this).get(EmailViewModel.class);

        final Bundle extras = this.getIntent().getExtras();
        this.requestCode = extras.getInt(MainActivity.EXTRA_REQUEST_CODE, MainActivity.DEFAULT_EMAIL_ACTIVITY_REQUEST_CODE);

        if (this.requestCode == MainActivity.UPDATE_EMAIL_ACTIVITY_REQUEST_CODE) {
            this.emailId = extras.getInt(EditDataActivity.EXTRA_DATA_MAIL_ID);
            this.emailViewModel.getEmail(emailId).observe(this, new Observer<Email>() {
                @Override
                public void onChanged(@Nullable Email email) {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"));
                    cal.setTimeInMillis(email.getClock());


                    EditDataActivity.this.clockYearTextView.setText(DateTimeFormat.getYearString(cal));
                    EditDataActivity.this.clockMonthTextView.setText(DateTimeFormat.getMonthString(cal));
                    EditDataActivity.this.clockDayTextView.setText(DateTimeFormat.getDayString(cal));
                    EditDataActivity.this.clockHourTextView.setText(DateTimeFormat.getHourString(cal));
                    EditDataActivity.this.clockMinuteTextView.setText(DateTimeFormat.getMinuteString(cal));


                    EditDataActivity.this.toEditText.setText(email.getTo());
                    EditDataActivity.this.subjectEditText.setText(email.getSubject());
                    EditDataActivity.this.bodyEditText.setText(email.getBody());

                    EditDataActivity.this.EditTime.setText(DateTimeFormat.getTimeString(cal));
                }
            });
        }
        else{
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"));

            EditDataActivity.this.clockYearTextView.setText(DateTimeFormat.getYearString(cal));
            EditDataActivity.this.clockMonthTextView.setText(DateTimeFormat.getMonthString(cal));
            EditDataActivity.this.clockDayTextView.setText(DateTimeFormat.getDayString(cal));
            EditDataActivity.this.clockHourTextView.setText(DateTimeFormat.getHourString(cal));
            EditDataActivity.this.clockMinuteTextView.setText(DateTimeFormat.getMinuteString(cal));
            EditDataActivity.this.EditTime.setText(DateTimeFormat.getTimeString(cal));
        }

        Button save = this.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                Calendar cal = Calendar.getInstance();
                cal.set(DateTimeFormat.getYearInteger(EditDataActivity.this.clockYearTextView.getText().toString()),
                        DateTimeFormat.getMonthInteger(EditDataActivity.this.clockMonthTextView.getText().toString()),
                        DateTimeFormat.getDayInteger(EditDataActivity.this.clockDayTextView.getText().toString()),
                        DateTimeFormat.getHourInteger(EditDataActivity.this.clockHourTextView.getText().toString()),
                        DateTimeFormat.getMinuteInteger(EditDataActivity.this.clockMinuteTextView.getText().toString()));

                String to = EditDataActivity.this.toEditText.getText().toString();
                String subject = EditDataActivity.this.subjectEditText.getText().toString();
                String body = EditDataActivity.this.bodyEditText.getText().toString();

                Log.d("TimeSet - save", String.valueOf(cal.getTimeInMillis()));
                Email email = new Email(cal.getTimeInMillis(),
                        true,
                        to,
                        subject,
                        body);


                if (EditDataActivity.this.requestCode == MainActivity.NEW_EMAIL_ACTIVITY_REQUEST_CODE) {
                    emailViewModel.insert(email);
                } else {
                    email.setEmailId(EditDataActivity.this.emailId);
                    emailViewModel.update(email);
                }


                EditDataActivity.this.setResult(RESULT_OK, replyIntent);
                AlarmService_on(email);
                EditDataActivity.this.finish();
            }
        });

        // 为设置时间按钮绑定监听器
        EditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"));
                final int setyear = cal.get(Calendar.YEAR);
                final int setmonth = cal.get(Calendar.MONTH);
                final int setday = cal.get(Calendar.DAY_OF_MONTH);
                final int sethour = cal.get(Calendar.HOUR_OF_DAY);
                final int setminute = cal.get(Calendar.MINUTE);

                new TimePickerDialog(EditDataActivity.this, 3, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        EditDataActivity.this.clockHourTextView.setText(DateTimeFormat.getHourString(hourOfDay));
                        EditDataActivity.this.clockMinuteTextView.setText(DateTimeFormat.getMinuteString(minute));
                        EditTime.setText(EditTime.getText() + " " + DateTimeFormat.getHourString(hourOfDay) + ":" + DateTimeFormat.getMinuteString(minute));
                    }
                }, sethour, setminute, true).show();

                new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String str_year = DateTimeFormat.getYearString(year);
                        String str_month = DateTimeFormat.getMonthString(month);
                        String str_day = DateTimeFormat.getDayString(dayOfMonth);
                        EditDataActivity.this.clockYearTextView.setText(str_year);
                        EditDataActivity.this.clockMonthTextView.setText(str_month);
                        EditDataActivity.this.clockDayTextView.setText(str_day);

                        EditTime.setText(setyear + "/" + str_month + "/" + str_day);
                    }
                }, setyear, setmonth, setday).show();

            }

        });

        Button tBtn = (Button) findViewById(R.id.buttonTemplate);
        tBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }


    private void showDialog() {
        TemplateViewModel templateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        templateViewModel.getAllTemplates().observe(EditDataActivity.this, new Observer<List<Template>>() {
            @Override
            public void onChanged(@Nullable List<Template> templates) {
                final String[] strings = new String[templates.size()];
                for (int i = 0; i < templates.size(); i++) {
                    strings[i] = templates.get(i).getTemplate();
                }

                new AlertDialog.Builder(EditDataActivity.this)
                        .setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String content = strings[which];
                                bodyEditText.setText(content);
                            }
                        })
                        .show();
            }
        });

    }


    public void AlarmService_on(Email current) {

        if (current.getClock() <  Calendar.getInstance().getTimeInMillis() )
            return ;

        Intent emailIntent = new Intent(EditDataActivity.this, SendMailAlarmReceiver.class);
        emailIntent.putExtra(MainActivity.EXTRA_MAIL_DATA, 0);
        emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_TO ,  current.getTo());
        emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_BODY , current.getBody());
        emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_SUBJECT , current.getSubject());

        PendingIntent emailPendingIntent = PendingIntent.getBroadcast(
                EditDataActivity.this,
                0,
                emailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        Log.d( "switch", "system time: " + DateTimeFormat.getTimeString(Calendar.getInstance()) );
        Log.d("switch", "onCheckedChanged: system  " + String.valueOf(Calendar.getInstance().getTimeInMillis()));
        Log.d("switch", "onCheckedChanged: alarm  on " + String.valueOf(current.getClock()));

        AlarmManager am = (AlarmManager) EditDataActivity.this.getSystemService(Context.ALARM_SERVICE);
        am.setExact(AlarmManager.RTC_WAKEUP, current.getClock(), emailPendingIntent);


    }
}
