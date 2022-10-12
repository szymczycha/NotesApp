package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.File;

public class FolderBrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_browser);
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        Bundle bundle = getIntent().getExtras();
        String folderName = bundle.get("src").toString();
        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        File fils = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES +"/"+folderName);
        File[] files = fils.listFiles(); // tablica plików
        for (File file : files){
            Log.d("plz",file.getName());
        }
    }
}