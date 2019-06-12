package com.example.hackermail.db;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hackermail.R;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class EmailListAdapter extends RecyclerView.Adapter<EmailListAdapter.EmailViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Email> emails;
    private static ClickListener clickListener;

    public EmailListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public EmailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = this.layoutInflater.inflate(R.layout.email_recyclerview_item, parent, false);
        return new EmailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmailViewHolder holder, int position) {
        if (this.emails != null) {
            Email current = this.emails.get(position);

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Taipei"));
            cal.setTimeInMillis(current.getClock());

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
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

            holder.clockYearTextView.setText(yearString);
            holder.clockMonthTextView.setText(monthString);
            holder.clockDayTextView.setText(dayString);
            holder.clockHourTextView.setText(hourString);
            holder.clockMinuteTextView.setText(minuteString);
            holder.clockSecondTextView.setText(secondString);

            holder.clockIsOnSwitch.setChecked(current.getClockIsOn());

            holder.topicTextView.setText(current.getTopic());
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
        private final TextView clockSecondTextView;

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
            this.clockSecondTextView = itemView.findViewById(R.id.clock_second);

            this.clockIsOnSwitch = itemView.findViewById(R.id.clock_is_on);

            this.topicTextView = itemView.findViewById(R.id.topic);
            this.toTextView = itemView.findViewById(R.id.to);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    EmailListAdapter.clickListener.onItemClick(view, /*EmailViewHolder.this.*/getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
