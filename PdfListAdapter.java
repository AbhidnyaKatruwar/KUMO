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

/**
 * Created by desai on 11-Apr-18.
 */

public class PdfListAdapter extends ArrayAdapter<PdfUpload> {
    private Activity context;
    private int resource;
    private List<PdfUpload> listPdf;

    public PdfListAdapter(@NonNull Activity context, int resource, @NonNull List<PdfUpload> objects) {
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
        TextView tvName = v.findViewById(R.id.tvPdfName);
        tvName.setText(listPdf.get(position).getName());
        return v;
    }
}
