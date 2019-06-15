package com.example.hackermail.db;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hackermail.EditDataActivity;
import com.example.hackermail.MainActivity;
import com.example.hackermail.R;
import com.example.hackermail.SendMailAlarmReceiver;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.support.v4.content.ContextCompat.getSystemService;

public class EmailListAdapter extends RecyclerView.Adapter<EmailListAdapter.EmailViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Email> emails;
    private static ClickListener clickListener;

    private static Context context;
    public EmailListAdapter(Context _context) {
        this.layoutInflater = LayoutInflater.from(_context);
        this.context = _context;
    }

    @Override
    public EmailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = this.layoutInflater.inflate(R.layout.email_recyclerview_item, parent, false);
        return new EmailViewHolder(itemView);
    }


    public void AlarmService_on(Email current){


        Log.d("MailSent", "From Adapter");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(current.getClock());

        MainActivity main = (MainActivity)context;
        Intent emailIntent = new Intent( main , SendMailAlarmReceiver.class);

        emailIntent.putExtra(main.EXTRA_MAIL_DATA, 0);


        Log.d("MailSent", "subject: " + current.getSubject());
        Log.d( "MailSent" , "body: " + current.getTo());

        emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_TO ,  current.getTo());
        emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_BODY , current.getBody());
        emailIntent.putExtra(EditDataActivity.EXTRA_DATA_MAIL_SUBJECT , current.getSubject());

        PendingIntent emailPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                emailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );


        Log.d( "switch-Triger-check", DateTimeFormat.getTimeString(cal));

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setExact(AlarmManager.RTC_WAKEUP, current.getClock(), emailPendingIntent);

        Log.d( "switch", "system time: " + DateTimeFormat.getTimeString(Calendar.getInstance()) );
        Log.d("switch", "onCheckedChanged: system  " + String.valueOf(Calendar.getInstance().getTimeInMillis()));
        Log.d("switch", "onCheckedChanged: alarm  on " + String.valueOf(current.getClock()));
    }

    public void AlarmServie_off(Email current){
        MainActivity main = (MainActivity)context;
        Intent emailIntent = new Intent( main , SendMailAlarmReceiver.class);
        emailIntent.putExtra(main.EXTRA_MAIL_DATA, 0);
        PendingIntent emailPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                emailIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(emailPendingIntent);

        Log.d("switch", "onCheckedChanged: alarm cancel");
    }
    @Override
    public void onBindViewHolder(EmailViewHolder holder, int position) {
        if (this.emails != null) {
            final Email current = this.emails.get(position);
            Log.d("Time", String.valueOf(current.getClock()));
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(current.getClock());
            holder.clockYearTextView.setText(DateTimeFormat.getYearString(cal));
            holder.clockMonthTextView.setText(DateTimeFormat.getMonthString(cal));
            holder.clockDayTextView.setText(DateTimeFormat.getDayString(cal));
            holder.clockHourTextView.setText(DateTimeFormat.getHourString(cal));
            holder.clockMinuteTextView.setText(DateTimeFormat.getMinuteString(cal));
            holder.clockIsOnSwitch.setChecked(current.getClockIsOn());

            holder.clockIsOnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        AlarmService_on(current);
                    }
                    else {
                        AlarmServie_off(current);
                    }
                }
            });

            holder.toTextView.setText(current.getTo());
        } else {
            holder.toTextView.setText(R.string.no_to);
        }
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.emails != null)
            return this.emails.size();
        else return 0;
    }

    public Email getEmailAtPosition(int position) {
        return this.emails.get(position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        EmailListAdapter.clickListener = clickListener;
    }

    public class EmailViewHolder extends RecyclerView.ViewHolder {

        private final TextView clockYearTextView;
        private final TextView clockMonthTextView;
        private final TextView clockDayTextView;
        private final TextView clockHourTextView;
        private final TextView clockMinuteTextView;

        private final Switch clockIsOnSwitch;

        private final TextView topicTextView;
        private final TextView toTextView;

        private EmailViewHolder(View itemView) {
            super(itemView);
            this.clockYearTextView = itemView.findViewById(R.id.clock_year);
            this.clockMonthTextView = itemView.findViewById(R.id.clock_month);
            this.clockDayTextView = itemView.findViewById(R.id.clock_day);
            this.clockHourTextView = itemView.findViewById(R.id.clock_hour);
            this.clockMinuteTextView = itemView.findViewById(R.id.clock_minute);

            this.clockIsOnSwitch = itemView.findViewById(R.id.clock_is_on);

            this.topicTextView = itemView.findViewById(R.id.topic);
            this.toTextView = itemView.findViewById(R.id.to);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    EmailListAdapter.clickListener.onItemClick(view, EmailViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
