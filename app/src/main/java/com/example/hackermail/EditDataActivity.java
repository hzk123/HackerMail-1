package com.example.hackermail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hackermail.db.Email;
import com.example.hackermail.db.EmailViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class EditDataActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_MAIL_ID = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_ID";
    public static final String EXTRA_DATA_MAIL_CLOCK = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_CLOCK";
    public static final String EXTRA_DATA_MAIL_CLOCK_IS_NO = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_CLOCK_IS_NO";
    public static final String EXTRA_DATA_MAIL_TOPIC = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_TOPIC";
    public static final String EXTRA_DATA_MAIL_TO = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_TO";
    public static final String EXTRA_DATA_MAIL_CC = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_CC";
    public static final String EXTRA_DATA_MAIL_SUBJECT = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_SUBJECT";
    public static final String EXTRA_DATA_MAIL_BODY = EditDataActivity.class.getName() + ".EXTRA_DATA_MAIL_BODY";

    private TextView clockYearTextView;
    private TextView clockMonthTextView;
    private TextView clockDayTextView;
    private TextView clockHourTextView;
    private TextView clockMinuteTextView;
    private TextView clockSecondTextView;

    private EditText topicEditText;
    private EditText toEditText;
    private EditText ccEditText;
    private EditText subjectEditText;
    private EditText bodyEditText;

    private EmailViewModel emailViewModel;

    private int requestCode;
    private int emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        this.clockYearTextView = this.findViewById(R.id.clock_year);
        this.clockMonthTextView = this.findViewById(R.id.clock_month);
        this.clockDayTextView = this.findViewById(R.id.clock_day);
        this.clockHourTextView = this.findViewById(R.id.clock_hour);
        this.clockMinuteTextView = this.findViewById(R.id.clock_minute);
        this.clockSecondTextView = this.findViewById(R.id.clock_second);

        this.topicEditText = this.findViewById(R.id.edit_text_topic);
        this.toEditText = this.findViewById(R.id.edit_text_to);
        this.ccEditText = this.findViewById(R.id.edit_text_cc);
        this.subjectEditText = this.findViewById(R.id.edit_text_subject);
        this.bodyEditText = this.findViewById(R.id.edit_text_body);

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

                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH) + 1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int hour = cal.get(Calendar.HOUR);
                    int minute = cal.get(Calendar.MINUTE);
                    int second = cal.get(Calendar.SECOND);

                    String yearString = Integer.toString(year);
                    String monthString = month < 10 ? "0" + Integer.toString(month) : Integer.toString(month);
                    String dayString = day < 10 ? "0" + Integer.toString(day) : Integer.toString(day);
                    String hourString = hour < 10 ? "0" + Integer.toString(hour) : Integer.toString(hour);
                    String minuteString = minute < 10 ? "0" + Integer.toString(minute) : Integer.toString(minute);
                    String secondString = second < 10 ? "0" + Integer.toString(second) : Integer.toString(second);

                    EditDataActivity.this.clockYearTextView.setText(yearString);
                    EditDataActivity.this.clockMonthTextView.setText(monthString);
                    EditDataActivity.this.clockDayTextView.setText(dayString);
                    EditDataActivity.this.clockHourTextView.setText(hourString);
                    EditDataActivity.this.clockMinuteTextView.setText(minuteString);
                    EditDataActivity.this.clockSecondTextView.setText(secondString);

                    EditDataActivity.this.topicEditText.setText(email.getTopic());
                    EditDataActivity.this.toEditText.setText(email.getTo());
                    EditDataActivity.this.ccEditText.setText(email.getCc());
                    EditDataActivity.this.subjectEditText.setText(email.getSubject());
                    EditDataActivity.this.bodyEditText.setText(email.getBody());
                }
            });
        }

        Button save = this.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                String topic = EditDataActivity.this.topicEditText.getText().toString();
                String to = EditDataActivity.this.toEditText.getText().toString();
                String cc = EditDataActivity.this.ccEditText.getText().toString();
                String subject = EditDataActivity.this.subjectEditText.getText().toString();
                String body = EditDataActivity.this.bodyEditText.getText().toString();

                Email email = new Email(Calendar.getInstance().getTimeInMillis(),
                        true,
                        topic,
                        to,
                        cc,
                        subject,
                        body);

                if (EditDataActivity.this.requestCode == MainActivity.NEW_EMAIL_ACTIVITY_REQUEST_CODE) {
                    emailViewModel.insert(email);
                } else {
                    email.setEmailId(EditDataActivity.this.emailId);
                    emailViewModel.update(email);
                }

                EditDataActivity.this.setResult(RESULT_OK, replyIntent);
                EditDataActivity.this.finish();
            }
        });
    }
}
