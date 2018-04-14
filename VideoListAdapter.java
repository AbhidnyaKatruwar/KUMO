package com.example.hp.app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hp on 13/4/18.
 */

public class VideoListAdapter  extends ArrayAdapter<VideoUpload> {

    private Activity context;
    private int resource;
    private List<VideoUpload> listVid;

    public VideoListAdapter(@NonNull Activity context, int resource, @NonNull List<VideoUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listVid = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource, null);
        TextView tvName = v.findViewById(R.id.tvVideoName);

        tvName.setText(listVid.get(position).getName());
        return v;
    }
}
