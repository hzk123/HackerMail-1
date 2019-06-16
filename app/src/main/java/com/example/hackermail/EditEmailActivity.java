package com.example.hackermail;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.hackermail.db.DateTimeFormat;
import com.example.hackermail.db.Email;
import com.example.hackermail.db.EmailViewModel;

import java.util.Calendar;
import java.util.TimeZone;

public class EditEmailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_MAIL_ID = EditEmailActivity.class.getName() + ".EXTRA_DATA_MAIL_ID";
    public static final String EXTRA_DATA_MAIL_CLOCK = EditEmailActivity.class.getName() + ".EXTRA_DATA_MAIL_CLOCK";
    public static final String EXTRA_DATA_MAIL_CLOCK_IS_NO = EditEmailActivity.class.getName() + ".EXTRA_DATA_MAIL_CLOCK_IS_NO";
    public static final String EXTRA_DATA_MAIL_TOPIC = EditEmailActivity.class.getName() + ".EXTRA_DATA_MAIL_TOPIC";
    public static final String EXTRA_DATA_MAIL_TO = EditEmailActivity.class.getName() + ".EXTRA_DATA_MAIL_TO";
    public static final String EXTRA_DATA_MAIL_CC = EditEmailActivity.class.getName() + ".EXTRA_DATA_MAIL_CC";
    public static final String EXTRA_DATA_MAIL_SUBJECT = EditEmailActivity.class.getName() + ".EXTRA_DATA_MAIL_SUBJECT";
    public static final String EXTRA_DATA_MAIL_BODY = EditEmailActivity.class.getName() + ".EXTRA_DATA_MAIL_BODY";

    private TextView clockYearTextView;
    private TextView clockMonthTextView;
    private TextView clockDayTextView;
    private TextView clockHourTextView;
    private TextView clockMinuteTextView;

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
        setContentView(R.layout.activity_edit_email);

        /**
         * Date & Time related views initialization.
         * */
        this.clockYearTextView = this.findViewById(R.id.clock_year);
        this.clockMonthTextView = this.findViewById(R.id.clock_month);
        this.clockDayTextView = this.findViewById(R.id.clock_day);
        this.clockHourTextView = this.findViewById(R.id.clock_hour);
        this.clockMinuteTextView = this.findViewById(R.id.clock_minute);


        /**
         * Text related views initialization.
         * */
        this.topicEditText = this.findViewById(R.id.edit_text_topic);
        this.toEditText = this.findViewById(R.id.edit_text_to);
        this.ccEditText = this.findViewById(R.id.edit_text_cc);
        this.subjectEditText = this.findViewById(R.id.edit_text_subject);
        this.bodyEditText = this.findViewById(R.id.edit_text_body);

        this.emailViewModel = ViewModelProviders.of(this).get(EmailViewModel.class);

        final Bundle extras = this.getIntent().getExtras();
        this.requestCode = extras.getInt(EmailListActivity.EXTRA_REQUEST_CODE, EmailListActivity.DEFAULT_EMAIL_ACTIVITY_REQUEST_CODE);

        if (this.requestCode == EmailListActivity.UPDATE_EMAIL_ACTIVITY_REQUEST_CODE) {
            this.emailId = extras.getInt(EditEmailActivity.EXTRA_DATA_MAIL_ID);
            this.emailViewModel.getEmail(emailId).observe(this, new Observer<Email>() {
                @Override
                public void onChanged(@Nullable Email email) {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"));
                    cal.setTimeInMillis(email.getClock());

                    EditEmailActivity.this.clockYearTextView.setText(DateTimeFormat.getYearString(cal));
                    EditEmailActivity.this.clockMonthTextView.setText(DateTimeFormat.getMonthString(cal));
                    EditEmailActivity.this.clockDayTextView.setText(DateTimeFormat.getDayString(cal));
                    EditEmailActivity.this.clockHourTextView.setText(DateTimeFormat.getHourString(cal));
                    EditEmailActivity.this.clockMinuteTextView.setText(DateTimeFormat.getMinuteString(cal));

                    EditEmailActivity.this.toEditText.setText(email.getTo());
                    EditEmailActivity.this.subjectEditText.setText(email.getSubject());
                    EditEmailActivity.this.bodyEditText.setText(email.getBody());
                }
            });
        } else {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"));

            EditEmailActivity.this.clockYearTextView.setText(DateTimeFormat.getYearString(cal));
            EditEmailActivity.this.clockMonthTextView.setText(DateTimeFormat.getMonthString(cal));
            EditEmailActivity.this.clockDayTextView.setText(DateTimeFormat.getDayString(cal));
            EditEmailActivity.this.clockHourTextView.setText(DateTimeFormat.getHourString(cal));
            EditEmailActivity.this.clockMinuteTextView.setText(DateTimeFormat.getMinuteString(cal));
        }

        Button chooseDatetime = this.findViewById(R.id.choose_datetime);
        chooseDatetime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"));

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                new TimePickerDialog(EditEmailActivity.this, 3, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        EditEmailActivity.this.clockHourTextView.setText(DateTimeFormat.getHourString(hourOfDay));
                        EditEmailActivity.this.clockMinuteTextView.setText(DateTimeFormat.getMinuteString(minute));
                    }
                }, hour, minute, true).show();

                new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        EditEmailActivity.this.clockYearTextView.setText(DateTimeFormat.getYearString(year));
                        EditEmailActivity.this.clockMonthTextView.setText(DateTimeFormat.getMonthString(month));
                        EditEmailActivity.this.clockDayTextView.setText(DateTimeFormat.getDayString(dayOfMonth));
                    }
                }, year, month, day).show();
            }
        });

        Button save = this.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                Calendar cal = Calendar.getInstance();
                cal.set(DateTimeFormat.getYearInteger(EditEmailActivity.this.clockYearTextView.getText().toString()),
                        DateTimeFormat.getMonthInteger(EditEmailActivity.this.clockMonthTextView.getText().toString()),
                        DateTimeFormat.getDayInteger(EditEmailActivity.this.clockDayTextView.getText().toString()),
                        DateTimeFormat.getHourInteger(EditEmailActivity.this.clockHourTextView.getText().toString()),
                        DateTimeFormat.getMinuteInteger(EditEmailActivity.this.clockMinuteTextView.getText().toString()));

                String topic = EditEmailActivity.this.topicEditText.getText().toString();
                String to = EditEmailActivity.this.toEditText.getText().toString();
                String cc = EditEmailActivity.this.ccEditText.getText().toString();
                String subject = EditEmailActivity.this.subjectEditText.getText().toString();
                String body = EditEmailActivity.this.bodyEditText.getText().toString();

                Email email = new Email(cal.getTimeInMillis(),
                        true,
                        to,
                        subject,
                        body);

                if (EditEmailActivity.this.requestCode == EmailListActivity.NEW_EMAIL_ACTIVITY_REQUEST_CODE) {
                    emailViewModel.insert(email);
                } else {
                    email.setEmailId(EditEmailActivity.this.emailId);
                    emailViewModel.update(email);
                }

                EditEmailActivity.this.setResult(RESULT_OK, replyIntent);
                EditEmailActivity.this.finish();
            }
        });
    }
}
