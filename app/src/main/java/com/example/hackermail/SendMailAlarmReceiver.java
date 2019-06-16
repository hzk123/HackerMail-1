package com.example.hackermail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class SendMailAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String mailto = intent.getStringExtra(EditDataActivity.EXTRA_DATA_MAIL_TO );
        String mailbody = intent.getStringExtra(EditDataActivity.EXTRA_DATA_MAIL_BODY);
        String mailsubject = intent.getStringExtra(EditDataActivity.EXTRA_DATA_MAIL_SUBJECT);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mailto, null));

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, mailsubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mailbody);

        context.startActivity(emailIntent);
    }
}
