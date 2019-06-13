package com.example.hackermail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SendMailAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String [] mailAddress = {"kaeteyaruyo@gmail.com"};
        String [] subject = {"Hello I am subject."};
        String [] text = {"Here is your text body."};

        int dataId = intent.getIntExtra(EditEmailActivity.EXTRA_DATA_MAIL_ID, 0);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mailAddress[dataId], null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject[dataId]);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text[dataId]);

        context.startActivity(emailIntent);
    }
}
