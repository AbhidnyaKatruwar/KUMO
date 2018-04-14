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

public class AudioListAdapter extends ArrayAdapter<AudioUpload> {

    private Activity context;
    private int resource;
    private List<AudioUpload> listAud;

    public AudioListAdapter(@NonNull Activity context, int resource, @NonNull List<AudioUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listAud = objects;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource, null);
        TextView tvName = v.findViewById(R.id.tvAudioName);

        tvName.setText(listAud.get(position).getName());
        return v;
    }
}
