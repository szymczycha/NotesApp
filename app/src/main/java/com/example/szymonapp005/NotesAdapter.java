package com.example.szymonapp005;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends ArrayAdapter {
    private Context _context;
    private int _resource;
    private ArrayList<Note> _notes;
    public NotesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> objects) {
        super(context, resource, objects);
        _context = context;
        _resource = resource;
        _notes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(_resource, null);

        TextView tv1 = (TextView) convertView.findViewById(R.id.note_item_id);
        tv1.setText(String.valueOf(_notes.get(position).getId()));

        TextView tv2 = (TextView) convertView.findViewById(R.id.note_item_title);
        tv2.setText(_notes.get(position).getTitle());
        tv2.setTextColor(Color.parseColor(_notes.get(position).getColor()));

        TextView tv3 = (TextView) convertView.findViewById(R.id.note_item_description);
        tv3.setText(_notes.get(position).getDescription());

        return convertView;
    }
}
