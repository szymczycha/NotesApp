package com.example.szymonapp005;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends ArrayAdapter {
    private List<File> _list;
    private Context _context;
    private int _resource;
    private String selectedColor;

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

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
                View main = View.inflate(_context, R.layout.note_inputs_xml, null);
                LinearLayout colorsView = main.findViewById(R.id.note_colors);
                EditText titleEditText = main.findViewById(R.id.note_title);
                EditText descriptionEditText = main.findViewById(R.id.note_description);
                String[] colors = {"#ff0000", "#00ff00", "#102e89","#991120", "#ae0b33"};
                for (String color :
                        colors) {
                    LinearLayout colorView = new LinearLayout(_context);
                    colorView.setBackgroundColor(Color.parseColor(color));
                    colorView.setMinimumHeight(70);
                    colorView.setMinimumWidth(70);
                    colorView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            titleEditText.setHintTextColor(Color.parseColor(color));
                            titleEditText.setTextColor(Color.parseColor(color));
                            setSelectedColor(color);
                        }
                    });
                    colorsView.addView(colorView);
                }

                alert.setView(main);
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseManager db = new DatabaseManager(
                                _context,
                                "NotatkiKoniecznySzymon.db",
                                null,
                                1
                        );
                        Log.d("XXX", titleEditText.getText().toString() + descriptionEditText.getText().toString() + getSelectedColor());
                        db.insert(titleEditText.getText().toString(), descriptionEditText.getText().toString(), getSelectedColor());
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
