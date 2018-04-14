package com.example.desai.kumo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.example.desai.kumo.MainActivity.REQUEST_CODE;


public class ImageListAdapter extends ArrayAdapter<ImageUpload> {

    private Activity context;
    private int resource;
    private List<ImageUpload> listImage;
    private FirebaseStorage mFirebaseStorage;


    public ImageListAdapter(@NonNull Activity context, int resource, @NonNull List<ImageUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource, null);

        TextView tvName = (TextView) v.findViewById(R.id.tvImageName);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);
        tvName.setText(listImage.get(position).getName());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);
        //Glide.with(context).load("https://firebasestorage.googleapis.com/v0/b/kum...").into(img)

        return v;
    }

}

