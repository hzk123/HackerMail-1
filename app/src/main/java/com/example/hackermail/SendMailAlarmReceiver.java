package com.example.hackermail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.hackermail.db.EmailDao;
import com.example.hackermail.db.Template;
import com.example.hackermail.db.TemplateRepository;
import com.example.hackermail.db.TemplateTopic;
import com.example.hackermail.db.TemplateTopicViewModel;
import com.example.hackermail.db.TemplateViewModel;

import java.util.List;
import java.util.Random;

public class SendMailAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String mailto = intent.getStringExtra(EditDataActivity.EXTRA_DATA_MAIL_TO );
        String mailsubject = intent.getStringExtra(EditDataActivity.EXTRA_DATA_MAIL_SUBJECT);
        String mailtext = intent.getStringExtra(EditDataActivity.EXTRA_DATA_MAIL_BODY);

        Log.d( "MailReceive", "onReceive: " + mailtext);
        final Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mailto, null));
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, mailsubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT , mailtext);

        context.startActivity(emailIntent);
    }
}
