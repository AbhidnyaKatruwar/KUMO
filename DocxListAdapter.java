package com.example.desai.kumo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class DocxListAdapter extends ArrayAdapter<DocUpload> {
    private Activity context;
    private int resource;
    private List<DocUpload> listPdf;

    public DocxListAdapter(@NonNull Activity context, int resource, @NonNull List<DocUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listPdf = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource, null);
        TextView tvName = v.findViewById(R.id.tvDocxName);
        tvName.setText(listPdf.get(position).getName());
        return v;
    }
}
