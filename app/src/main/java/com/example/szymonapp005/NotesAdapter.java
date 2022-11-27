package com.example.szymonapp005;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesAdapter extends ArrayAdapter {
    private Context _context;
    private int _resource;
    private ArrayList<Note> _notes;
    private int editingId = -1;
    private String chosenColor = "#ffffff";

    public void setChosenColor(String chosenColor) {
        this.chosenColor = chosenColor;
    }

    public String getChosenColor() {
        return chosenColor;
    }

    public void setEditingId(int editingId) {
        this.editingId = editingId;
    }

    public int getEditingId() {
        return editingId;
    }

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
        if(getEditingId() == position){
            convertView = inflater.inflate(R.layout.edit_note_layout, null);
            TextView tv1 = (TextView) convertView.findViewById(R.id.note_item_id);
            tv1.setText(String.valueOf(_notes.get(position).getId()));

            EditText titleEditText = (EditText) convertView.findViewById(R.id.edit_note_title);
            EditText descriptionEditText = (EditText) convertView.findViewById(R.id.edit_note_description);
            LinearLayout colorsLinearLayout = (LinearLayout) convertView.findViewById(R.id.edit_note_colors);

            titleEditText.setText(_notes.get(position).getTitle());
            descriptionEditText.setText(_notes.get(position).getDescription());
            titleEditText.setTextColor(Color.parseColor(_notes.get(position).getColor()));

            String[] colors = {"#ff0000", "#00ff00", "#102e89","#991120", "#ae0b33", "#e9b339", "#09f4ce", "#964410"};

            for (String color :
                    colors) {
                LinearLayout colorButton = new LinearLayout(_context);
                colorButton.setLayoutParams(new LinearLayout.LayoutParams(70,70));
                colorButton.setBackgroundColor(Color.parseColor(color));
                colorButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setChosenColor(color);
                        titleEditText.setTextColor(Color.parseColor(color));
                    }
                });
                colorsLinearLayout.addView(colorButton);
            }
            Button saveButton = (Button) convertView.findViewById(R.id.edit_note_save);
            Button cancelButton = (Button) convertView.findViewById(R.id.edit_note_cancel);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("XXX", "XXX");
                    DatabaseManager db = new DatabaseManager(
                            _context,
                            "NotatkiKoniecznySzymon.db",
                            null,
                            5
                    );
                    db.update(
                            _notes.get(position).getId(),
                            titleEditText.getText().toString(),
                            descriptionEditText.getText().toString(),
                            getChosenColor()
                    );
                    setEditingId(-1);
                    _notes.get(position).setTitle(titleEditText.getText().toString());
                    _notes.get(position).setDescription(descriptionEditText.getText().toString());
                    _notes.get(position).setColor(getChosenColor());
                    notifyDataSetChanged();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setEditingId(-1);
                    notifyDataSetChanged();
                }
            });

        }else{
            convertView = inflater.inflate(_resource, null);

            TextView tv1 = (TextView) convertView.findViewById(R.id.note_item_id);
            tv1.setText(String.valueOf(_notes.get(position).getId()));

            TextView tv2 = (TextView) convertView.findViewById(R.id.note_item_title);
            tv2.setText(_notes.get(position).getTitle());
            tv2.setTextColor(Color.parseColor(_notes.get(position).getColor()));

            TextView tv3 = (TextView) convertView.findViewById(R.id.note_item_description);
            tv3.setText(_notes.get(position).getDescription());

            TextView tv4 = (TextView) convertView.findViewById(R.id.note_item_imagePath);
            tv4.setText(_notes.get(position).getImagePath());

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                    View main = View.inflate(_context, R.layout.note_edit_alert, null);
                    AlertDialog alert1 = alert.create();

                    TextView deleteNoteBtn = main.findViewById(R.id.note_edit_alert_delete_button);
                    deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatabaseManager db = new DatabaseManager(
                                    _context,
                                    "NotatkiKoniecznySzymon.db",
                                    null,
                                    5
                            );
                            db.delete(_notes.get(position).getId());
                            _notes.remove(position);
                            notifyDataSetChanged();
                            alert1.dismiss();
                        }
                    });
                    TextView updateNoteBtn = main.findViewById(R.id.note_edit_alert_edit_button);
                    updateNoteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setEditingId(position);
                            Log.d("XXX", String.valueOf(getEditingId()));
                            notifyDataSetChanged();
                            alert1.dismiss();
                        }
                    });

                    TextView sortTitleBtn = main.findViewById(R.id.note_edit_alert_sortTitle_button);
                    sortTitleBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // po tytule
                            Collections.sort(_notes, new Comparator<Note>() {
                                @Override
                                public int compare(Note note, Note t1) {
                                    return note.getTitle().compareTo(t1.getTitle());
                                }
                            });
                            notifyDataSetChanged();
                            alert1.dismiss();
                        }
                    });
                    TextView sortColorBtn = main.findViewById(R.id.node_edit_alert_sortColor_button);
                    sortColorBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // po kolorze
                            Collections.sort(_notes, new Comparator<Note>() {
                                @Override
                                public int compare(Note note, Note t1) {
                                    return note.getColor().compareTo(t1.getColor());
                                }
                            });
                            notifyDataSetChanged();
                            alert1.dismiss();
                        }
                    });
//
//                    alert.setView(main);
//                    alert.show();
//
                    alert1.setView(main);
                    alert1.show();
                    return true;
                }
            });
        }
        if(position % 2 == 1){
            LinearLayout main = convertView.findViewById(R.id.note_item_main);
            main.setBackgroundColor(ContextCompat.getColor(_context, R.color.main_gray));
        }
        return convertView;
    }
}
