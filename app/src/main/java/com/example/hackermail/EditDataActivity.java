package com.example.hackermail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDataActivity extends AppCompatActivity {

    private EditText toEditTextView;
    private EditText ccEditTextView;
    private EditText subjectEditTextView;
    private EditText bodyEditTextView;

    private int emailId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        this.toEditTextView = findViewById(R.id.edit_text_to);
        this.ccEditTextView = findViewById(R.id.edit_text_cc);
        this.subjectEditTextView = findViewById(R.id.edit_text_subject);
        this.bodyEditTextView = findViewById(R.id.edit_text_body);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.emailId = extras.getInt(MainActivity.EXTRA_DATA_MAIL_ID);
            this.toEditTextView.setText(extras.getString(MainActivity.EXTRA_DATA_MAIL_TO));
            this.ccEditTextView.setText(extras.getString(MainActivity.EXTRA_DATA_MAIL_CC));
            this.subjectEditTextView.setText(extras.getString(MainActivity.EXTRA_DATA_MAIL_SUBJECT));
            this.bodyEditTextView.setText(extras.getString(MainActivity.EXTRA_DATA_MAIL_BODY));
        }

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                String to = EditDataActivity.this.toEditTextView.getText().toString();
                String cc = EditDataActivity.this.ccEditTextView.getText().toString();
                String subject = EditDataActivity.this.subjectEditTextView.getText().toString();
                String body = EditDataActivity.this.bodyEditTextView.getText().toString();

                replyIntent.putExtra(MainActivity.EXTRA_DATA_MAIL_ID, EditDataActivity.this.emailId);
                replyIntent.putExtra(MainActivity.EXTRA_DATA_MAIL_TO, to);
                replyIntent.putExtra(MainActivity.EXTRA_DATA_MAIL_CC, cc);
                replyIntent.putExtra(MainActivity.EXTRA_DATA_MAIL_SUBJECT, subject);
                replyIntent.putExtra(MainActivity.EXTRA_DATA_MAIL_BODY, body);

                EditDataActivity.this.setResult(RESULT_OK, replyIntent);
                EditDataActivity.this.finish();
            }
        });
    }
}
