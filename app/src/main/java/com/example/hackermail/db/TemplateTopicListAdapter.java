package com.example.hackermail.db;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
            final TemplateTopic current = this.templateTopics.get(position);

            holder.templateTopicTextView.setText(current.getTopic());

            final Context context = this.layoutInflater.getContext();

            final TemplateListAdapter templateListAdapter = new TemplateListAdapter(context);

            holder.templateRecyclerView.setAdapter(templateListAdapter);
            holder.templateRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            templateListAdapter.setOnItemClickListener(new TemplateListAdapter.ClickListener() {
                @Override
                public void onItemClick(View v, Template template) {
                    showListDialog(context, template);

                }
            });

            holder.addTemplate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText input = new EditText(context);
                    new AlertDialog.Builder(context)
                            .setView(input)
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String content = input.getText().toString();
                                    TemplateViewModel templateViewModel = ViewModelProviders.of((AppCompatActivity) context).get(TemplateViewModel.class);
                                    Template template = new Template(current.getTemplateTopicId(), content);
                                    templateViewModel.insertTemplate(template);
                                }
                            })
                            .show();
                }
            });

            refreshTemplate(context, current, templateListAdapter);
        } else {
            holder.templateTopicTextView.setText(R.string.no_topic);
        }
    }


    private void refreshTemplate(Context context, TemplateTopic topic, final TemplateListAdapter adapter) {
        TemplateViewModel templateViewModel = ViewModelProviders.of((AppCompatActivity) context).get(TemplateViewModel.class);
        templateViewModel.getAllTemplates(topic.getTemplateTopicId()).observe((AppCompatActivity) context, new Observer<List<Template>>() {
            @Override
            public void onChanged(@Nullable List<Template> templates) {
                adapter.setTemplates(templates);
            }
        });
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

        private final TextView templateTopicTextView;
        private RecyclerView templateRecyclerView;
        private Button addTemplate;

        private TemplateTopicViewHolder(View itemView) {
            super(itemView);

            this.templateTopicTextView = itemView.findViewById(R.id.template_topic);
            this.templateRecyclerView = itemView.findViewById(R.id.template_recyclerview);
            this.addTemplate = itemView.findViewById(R.id.add_template);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    TemplateTopicListAdapter.clickListener.onItemClick(view, templateTopics.get(TemplateTopicViewHolder.this.getAdapterPosition()));
                    return true;
                }

            });
        }
    }

    public interface ClickListener {
        void onItemClick(View v, TemplateTopic templateTopic);
    }


    public void showListDialog(final Context context, final Template template) {
        final String[] items = {"Edit", "Delete"};

        new AlertDialog.Builder(context)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            final EditText input = new EditText(context);
                            new AlertDialog.Builder(context)
                                    .setView(input)
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String content = input.getText().toString();
                                            TemplateViewModel templateViewModel = ViewModelProviders.of((AppCompatActivity) context).get(TemplateViewModel.class);
                                            template.setTemplate(content);
                                            templateViewModel.updateTemplate(template);
                                        }
                                    })
                                    .show();
                        } else {
                            TemplateViewModel templateViewModel = ViewModelProviders.of((AppCompatActivity) context).get(TemplateViewModel.class);
                            templateViewModel.deleteTemplate(template.getTemplateId());
                        }
                    }
                })
                .show();
    }
}
