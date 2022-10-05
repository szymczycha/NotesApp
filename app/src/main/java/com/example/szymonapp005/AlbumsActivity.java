package com.example.szymonapp005;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class AlbumsActivity extends AppCompatActivity {
    private ListView folderList;
    private LinearLayout addFolderButton;
    private ArrayList<String> array;
    private File pic;
    private void reloadArray(){
        array.clear();
        File[] files = pic.listFiles(); // tablica plików
        for (File file : files){
            array.add(file.getName());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        array = new ArrayList<>();
        folderList = findViewById(R.id.folderList);
        addFolderButton = findViewById(R.id.add_folder);
        addFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumsActivity.this);
                alert.setTitle("Dodaj folder");
                alert.setMessage("Nazwa:");
                //tutaj input
                EditText input = new EditText(AlbumsActivity.this);
                alert.setView(input);
                //teraz butony jak poprzednio i
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File newDir = new File(pic, input.getText().toString());
                        newDir.mkdir();
//                        reloadArray();
                        array.add(input.getText().toString());
                    }

                });
                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyświetl which
                    }
                });
                alert.show();
            }
        });
        File dir = new File(pic, "Ludzie");
        dir.mkdir();
        File dir2 = new File(pic, "Miejsca");
        dir2.mkdir();
        File dir3 = new File(pic, "Rzeczy");
        dir3.mkdir();
        File[] files = pic.listFiles(); // tablica plików
        for (File file : files){
            array.add(file.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AlbumsActivity.this, // tzw Context
                R.layout.folder_item, // nazwa pliku xml naszego wiersza na liście
                R.id.folderText, // id pola txt w wierszu
                array ); // tablica przechowująca testowe dane
        folderList.setAdapter(adapter);

        folderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("tag", "click");

            }
        });

        folderList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("tag", "long");

                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumsActivity.this);
                alert.setTitle("Uwaga!");
                alert.setMessage("Na pewno usunąć?");
                //teraz butony jak poprzednio i
                alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("tag", i+"");
                        File delFile = new File(pic, array.get(i));
                        delFile.delete();
//                        reloadArray();
                        array.remove(array.get(i));
                        adapter.notifyDataSetChanged();
                    }

                });
                alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                return true;
            }
        });
    }
}