package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CollageActivity extends AppCompatActivity {
    private LinearLayout collageLayout1;
    private LinearLayout collageLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        collageLayout1 = findViewById(R.id.collage_layout_1);
        collageLayout2 = findViewById(R.id.collage_layout_2);
        collageLayout1.setOnClickListener(v -> {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Log.d("xxx","szerokość ekranu " + size.x);
            Log.d("xxx","wysokość ekranu " +size.y);
            ArrayList<ImageData> list = new ArrayList<>();
            list.add(new ImageData(0,0, size.x/2, (size.y/3)*2));
            list.add(new ImageData(size.x/2,0, size.x/2, (size.y/3)*2));
            list.add(new ImageData(0,(size.y/3)*2, size.x, size.y/3));
            Intent intent = new Intent(CollageActivity.this, CollageMaker.class);
            intent.putExtra("list", list);
            startActivity(intent);
        });
        collageLayout2.setOnClickListener(v -> {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Log.d("xxx","szerokość ekranu " + size.x);
            Log.d("xxx","wysokość ekranu " +size.y);
            ArrayList<ImageData> list = new ArrayList<>();
            list.add(new ImageData(0,0, size.x/2, size.y));
            list.add(new ImageData(size.x/2,0, size.x/2, size.y/3));
            list.add(new ImageData(size.x/2,size.y/3, size.x/2, size.y/3));
            list.add(new ImageData(size.x/2,(size.y/3)*2, size.x/2, size.y/3));
            Intent intent = new Intent(CollageActivity.this, CollageMaker.class);
            intent.putExtra("list", list);
            startActivity(intent);
        });
    }
}