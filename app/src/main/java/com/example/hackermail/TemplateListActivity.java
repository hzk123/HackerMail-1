package com.example.hackermail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.hackermail.db.TemplateTopic;
import com.example.hackermail.db.TemplateTopicListAdapter;
import com.example.hackermail.db.TemplateTopicViewModel;

import java.util.List;

public class TemplateListActivity extends AppCompatActivity {

    private TemplateTopicViewModel templateTopicViewModel;


    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_list);

        final TemplateTopicListAdapter templateTopicListAdapter = new TemplateTopicListAdapter(this);
        templateTopicListAdapter.setOnItemClickListener(new TemplateTopicListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, TemplateTopic templateTopic) {
                showListDialog(TemplateListActivity.this,templateTopic);
            }
        });

        RecyclerView templateTopicRecyclerView = this.findViewById(R.id.template_topic_recyclerview);
        templateTopicRecyclerView.setAdapter(templateTopicListAdapter);
        templateTopicRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        templateTopicViewModel = ViewModelProviders.of(this).get(TemplateTopicViewModel.class);
        templateTopicViewModel.getAllTemplateTopics().observe(this, new Observer<List<TemplateTopic>>() {

            @Override
            public void onChanged(@Nullable List<TemplateTopic> templateTopics) {
                templateTopicListAdapter.setTemplateTopics(templateTopics);
            }
        });
    }


    public void addNewTem(View view) {
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle("Input new topic name:")
                .setView(et)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TemplateTopic templateTopic = new TemplateTopic(et.getText().toString());
                        templateTopicViewModel.insertTemplateTopic(templateTopic);
                    }
                }).setNegativeButton("NO", null).show();
    }


    public void showListDialog(final Context context, final TemplateTopic templateTopic) {
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
                                            templateTopic.setTopic(content);
                                            templateTopicViewModel.updateTemplateTopic(templateTopic);
                                        }
                                    })
                                    .show();
                        } else {
                            templateTopicViewModel.deleteTemplateTopic(templateTopic);
                        }
                    }
                })
                .show();
    }

}