package com.example.hackermail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.hackermail.db.TemplateTopic;
import com.example.hackermail.db.TemplateTopicListAdapter;
import com.example.hackermail.db.TemplateTopicViewModel;

import java.util.List;

public class TemplateListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_list);

        final TemplateTopicListAdapter templateTopicListAdapter = new TemplateTopicListAdapter(this);
//        templateTopicListAdapter.setOnItemClickListener(new TemplateTopicListAdapter.ClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                TemplateTopic templateTopic = templateTopicListAdapter.getTemplateTopicAtPosition(position);
//            }
//        });

        RecyclerView templateTopicRecyclerView = this.findViewById(R.id.template_topic_recyclerview);
        templateTopicRecyclerView.setAdapter(templateTopicListAdapter);
        templateTopicRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TemplateTopicViewModel templateTopicViewModel = ViewModelProviders.of(this).get(TemplateTopicViewModel.class);
        templateTopicViewModel.getAllTemplateTopics().observe(this, new Observer<List<TemplateTopic>>() {

            @Override
            public void onChanged(@Nullable List<TemplateTopic> templateTopics) {
                templateTopicListAdapter.setTemplateTopics(templateTopics);
            }
        });
    }
}