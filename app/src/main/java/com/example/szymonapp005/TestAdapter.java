package com.example.szymonapp005;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends ArrayAdapter {
    private List<File> _list;
    private Context _context;
    private int _resource;
    public TestAdapter(@NonNull Context context, int resource, @NonNull List<File> objects) {
        super(context, resource, objects);
        this._list = objects;
        this._context = context;
        this._resource = resource;

    }

    @Override
    public int getCount() {
        return _list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(_resource, null);
        ImageView iv1 = (ImageView) convertView.findViewById(R.id.new_folder_item_image);
        Uri uri = Uri.fromFile(_list.get(position));
        iv1.setImageURI(uri);
        iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXX", String.valueOf(position));
                Log.d("XXX", _list.get(position).getAbsolutePath());
                Log.d("XXX", "klik w obrazek");
            }
        });
        ImageView iv2 = (ImageView) convertView.findViewById(R.id.new_folder_item_delete_button);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                alert.setTitle("USUWANIE!");
                alert.setMessage("Usuwać?");
                alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        _list.get(position).delete();
                        _list.remove(position);
                        notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }
        });
        ImageView iv3 = (ImageView) convertView.findViewById(R.id.new_folder_item_edit_button);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                alert.setTitle("EDYCJA");
                alert.setMessage("Edycja zdjęcia: " + _list.get(position).getAbsolutePath());
                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }
        });
        ImageView iv4 = (ImageView) convertView.findViewById(R.id.new_folder_item_info_button);
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                alert.setTitle("INFO");
                alert.setMessage("Info o zdjęciu: " + _list.get(position).getAbsolutePath());
                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alert.show();
            }
        });

        return convertView;
    }
}
