package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AlbumsActivity extends AppCompatActivity {
    private ListView folderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        folderList = findViewById(R.id.folderList);
        String[] array = new String[]{"wynik 1","wynik 2","wynik 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AlbumsActivity.this, // tzw Context
                R.layout.folder_item, // nazwa pliku xml naszego wiersza na liście
                R.id.folderText, // id pola txt w wierszu
                array ); // tablica przechowująca testowe dane
        folderList.setAdapter(adapter);

        folderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}