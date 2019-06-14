package com.example.hackermail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class SendMailAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String [] mailAddress = {"hzk1201@gmail.com"};
        String [] subject = {"Hello I am subject."};
        String [] text = {"Here is your text body."};

        Log.d("switch", "broadcast reveived");

        int dataId = intent.getIntExtra(EditDataActivity.EXTRA_DATA_MAIL_ID, 0);

        Log.d("switch", "intend get");

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mailAddress[dataId], null));

        Log.d("switch", "intend create");

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject[dataId]);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text[dataId]);

        Log.d("switch", "extra sent");
        context.startActivity(emailIntent);

        Log.d("switch", "emailIntent sent");
    }
}
