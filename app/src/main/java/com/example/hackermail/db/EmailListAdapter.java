package com.example.hackermail.db;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hackermail.R;

import java.util.List;

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
            holder.toView.setText(current.getTo());
            holder.ccView.setText(current.getCc());
            holder.subjectView.setText(current.getSubject());
            holder.bodyView.setText(current.getBody());
        } else {
            holder.toView.setText(R.string.no_to);
            holder.ccView.setText(R.string.no_cc);
            holder.subjectView.setText(R.string.no_subject);
            holder.bodyView.setText(R.string.no_body);
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

        private final TextView toView;
        private final TextView ccView;
        private final TextView subjectView;
        private final TextView bodyView;

        private EmailViewHolder(View itemView) {
            super(itemView);
            this.toView = itemView.findViewById(R.id.to);
            this.ccView = itemView.findViewById(R.id.cc);
            this.subjectView = itemView.findViewById(R.id.subject);
            this.bodyView = itemView.findViewById(R.id.body);

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
