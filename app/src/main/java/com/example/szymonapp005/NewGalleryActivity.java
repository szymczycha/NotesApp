package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewGalleryActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gallery);
        listView = findViewById(R.id.new_gallery);

        Bundle bundle = getIntent().getExtras();
        String folderName = bundle.get("src").toString();
        File fils = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES +File.separator + "Konieczny" + File.separator +folderName);
        File[] files = fils.listFiles();
        List<File> array = new ArrayList<File>();
        for (File file :
                files) {
            array.add(file);
        }
        TestAdapter adapter = new TestAdapter(
                NewGalleryActivity.this,
                R.layout.new_folder_item,
                array
        );
        listView.setAdapter(adapter);

    }
}