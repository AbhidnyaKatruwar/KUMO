package com.example.desai.kumo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by desai on 13-Apr-18.
 */

public class AppListAdapter extends ArrayAdapter<AppUpload> {
    private Activity context;
    private int resource;
    private List<AppUpload> listPdf;
    public AppListAdapter(@NonNull Activity context, int resource, @NonNull List<AppUpload> objects) {
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
        TextView tvName = v.findViewById(R.id.tvAppName);

        tvName.setText(listPdf.get(position).getName());
        return v;
    }
}

