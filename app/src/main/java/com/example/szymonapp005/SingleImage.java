package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class SingleImage extends AppCompatActivity {
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);
        img = findViewById(R.id.imageInFolder);
        Bundle bundle = getIntent().getExtras();
        String imagePath = bundle.get("src").toString();
        File imageFile = new File(imagePath);
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        img.setImageBitmap(myBitmap);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(img.getScaleType().equals(ImageView.ScaleType.CENTER_CROP)){
                    img.setScaleType(ImageView.ScaleType.CENTER);
                }else{
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
        });
    }
}