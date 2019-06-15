package com.example.hackermail;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    public static final String EXTRA_MAIL_DATA = MainActivity.class.getName() + ".EXTRA_SEND_MAIL";

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


        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a word,
                    // delete that word from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Email del_Email = emailListAdapter.getEmailAtPosition(position);
                        // Delete
                        emailViewModel.delete(del_Email);
                    }
                });
            helper.attachToRecyclerView(recyclerView);




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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_template , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
            case R.id.action_edit_template:
                //jump to Template activity
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent jumpIntent = new Intent(MainActivity.this,
                                TemplateListActivity.class);
                        MainActivity.this.startActivity(jumpIntent);
                        MainActivity.this.finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }, 1000);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
