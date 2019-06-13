package com.example.hackermail.db;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hackermail.R;

import java.util.List;

public class TemplateTopicListAdapter extends RecyclerView.Adapter<TemplateTopicListAdapter.TemplateTopicViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<TemplateTopic> templateTopics;
    private static ClickListener clickListener;

    public TemplateTopicListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TemplateTopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = this.layoutInflater.inflate(R.layout.template_topic_recyclerview_item, parent, false);
        return new TemplateTopicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TemplateTopicViewHolder holder, int position) {
        if (this.templateTopics != null) {
            TemplateTopic current = this.templateTopics.get(position);

            holder.topicTextView.setText(current.getTopic());
        } else {
            holder.topicTextView.setText(R.string.no_topic);
        }
    }

    public void setTemplateTopics(List<TemplateTopic> templateTopics) {
        this.templateTopics = templateTopics;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.templateTopics != null)
            return this.templateTopics.size();
        else return 0;
    }

    public TemplateTopic getTemplateTopicAtPosition(int position) {
        return this.templateTopics.get(position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        TemplateTopicListAdapter.clickListener = clickListener;
    }

    public class TemplateTopicViewHolder extends RecyclerView.ViewHolder {

        private final TextView topicTextView;

        private TemplateTopicViewHolder(View itemView) {
            super(itemView);

            this.topicTextView = itemView.findViewById(R.id.topic);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    TemplateTopicListAdapter.clickListener.onItemClick(view, TemplateTopicViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
