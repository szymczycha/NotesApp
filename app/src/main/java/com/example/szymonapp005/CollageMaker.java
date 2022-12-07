package com.example.szymonapp005;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.number.Scale;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class CollageMaker extends AppCompatActivity {
    private FrameLayout main;
    private ImageView currentImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_maker);
        main = findViewById(R.id.collage_maker_main);
        ArrayList<ImageData> list = (ArrayList<ImageData>) getIntent().getExtras().getSerializable("list");
        for (ImageData image :
                list) {
            ImageView imageView = new ImageView(this);
            imageView.setX(image.getX());
            imageView.setY(image.getY());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(image.getWidth(),image.getHeight()));
            imageView.setBackgroundColor(0xff00ff00+image.getHeight()+image.getWidth()+image.getX()+image.getY());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(view -> {
                currentImage = imageView;
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100); // 100 - stała wartość, która później posłuży do identyfikacji tej akcji
            });
            main.addView(imageView);
        }
        // frameLayout.setDrawingCacheEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                Uri imgData = data.getData();
                InputStream stream = null;
                try {
                    stream = getContentResolver().openInputStream(imgData);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap b = BitmapFactory.decodeStream(stream);
                currentImage.setImageBitmap(b);
            }
        }
    }
}