package com.example.hackermail.db;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hackermail.R;

import java.util.List;

public class TemplateListAdapter extends RecyclerView.Adapter<TemplateListAdapter.TemplateViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Template> templates;
    private static ClickListener clickListener;

    public TemplateListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = this.layoutInflater.inflate(R.layout.template_recyclerview_item, parent, false);
        return new TemplateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TemplateViewHolder holder, int position) {
        if (this.templates != null) {
            Template current = this.templates.get(position);

            holder.templateTextView.setText(current.getTemplate());
        } else {
            holder.templateTextView.setText(R.string.no_topic);
        }
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.templates != null)
            return this.templates.size();
        else return 0;
    }

    public Template getTemplateAtPosition(int position) {
        return this.templates.get(position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        TemplateListAdapter.clickListener = clickListener;
    }

    public class TemplateViewHolder extends RecyclerView.ViewHolder {

        private final TextView templateTextView;

        private TemplateViewHolder(View itemView) {
            super(itemView);

            this.templateTextView = itemView.findViewById(R.id.template);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    TemplateListAdapter.clickListener.onItemClick(view, TemplateViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
