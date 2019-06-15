package com.example.hackermail.db;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
    //    private static ClickListener clickListener;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

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

            holder.templateTopicTextView.setText(current.getTopic());

            Context context = this.layoutInflater.getContext();

            final TemplateListAdapter templateListAdapter = new TemplateListAdapter(context);

            holder.templateRecyclerView.setAdapter(templateListAdapter);
            holder.templateRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            templateListAdapter.setOnItemClickListener(new TemplateListAdapter.ClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Template template = templateListAdapter.getTemplateAtPosition(position);
                }
            });

            TemplateViewModel templateViewModel = ViewModelProviders.of((AppCompatActivity) context).get(TemplateViewModel.class);
            templateViewModel.getAllTemplates(current.getTemplateTopicId()).observe((AppCompatActivity) context, new Observer<List<Template>>() {

                @Override
                public void onChanged(@Nullable List<Template> templates) {
                    templateListAdapter.setTemplates(templates);
                }
            });
        } else {
            holder.templateTopicTextView.setText(R.string.no_topic);
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

//    public void setOnItemClickListener(ClickListener clickListener) {
//        TemplateTopicListAdapter.clickListener = clickListener;
//    }

    public class TemplateTopicViewHolder extends RecyclerView.ViewHolder {

        private final TextView templateTopicTextView;
        private RecyclerView templateRecyclerView;

        private TemplateTopicViewHolder(View itemView) {
            super(itemView);

            this.templateTopicTextView = itemView.findViewById(R.id.template_topic);
            this.templateRecyclerView = itemView.findViewById(R.id.template_recyclerview);

//            itemView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    TemplateTopicListAdapter.clickListener.onItemClick(view, TemplateTopicViewHolder.this.getAdapterPosition());
//                }
//            });
        }
    }

//    public interface ClickListener {
//        void onItemClick(View v, int position);
//    }
}
