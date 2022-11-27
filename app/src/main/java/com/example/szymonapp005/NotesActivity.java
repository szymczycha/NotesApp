package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        DatabaseManager db = new DatabaseManager (
                NotesActivity.this, // activity z galerią zdjęć
                "NotatkiKoniecznySzymon.db", // nazwa bazy
                null,
                4 //wersja bazy, po zmianie schematu bazy należy ją zwiększyć
        );
        ArrayList<Note> notes = db.getAll();
        ListView notesListView = findViewById(R.id.notes_listView);
        NotesAdapter notesAdapter = new NotesAdapter(
                NotesActivity.this,
                R.layout.note_item,
                notes
        );
        notesListView.setAdapter(notesAdapter);
    }
}