package com.example.szymonapp005;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.icu.number.Scale;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CollageMaker extends AppCompatActivity {
    private FrameLayout main;
    private ImageView currentImage;
    private ImageView flipButton;
    private ImageView rotateButton;
    private ImageView saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_maker);
        main = findViewById(R.id.collage_maker_main);
        main.setDrawingCacheEnabled(true);
        flipButton = findViewById(R.id.collage_flip_button);
        rotateButton = findViewById(R.id.collage_rotate_button);
        saveButton = findViewById(R.id.collage_save_button);
        flipButton.setOnClickListener(v -> {
            Matrix matrix = new Matrix();
            matrix.postScale(-1.0f, 1.0f);

            Bitmap oryginal = ((BitmapDrawable) currentImage.getDrawable()).getBitmap();
            Bitmap rotated = Bitmap.createBitmap(oryginal, 0, 0, oryginal.getWidth(), oryginal.getHeight(), matrix, true);

            currentImage.setImageBitmap(rotated);
        });
        rotateButton.setOnClickListener(v -> {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);

            Bitmap oryginal = ((BitmapDrawable) currentImage.getDrawable()).getBitmap();
            Bitmap rotated = Bitmap.createBitmap(oryginal, 0, 0, oryginal.getWidth(), oryginal.getHeight(), matrix, true);

            currentImage.setImageBitmap(rotated);
        });
        saveButton.setOnClickListener(v -> {
            Bitmap b = main.getDrawingCache(true);
            File root = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES + File.separator + "Konieczny");
            File collagesDirectory = new File(root, "Collages");
            collagesDirectory.mkdir();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, stream); // kompresja, typ pliku jpg, png
            byte[] byteArray = stream.toByteArray();
            FileOutputStream fs = null;
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fileName = df.format(new Date());
//                            Log.d("plz", Environment.DIRECTORY_PICTURES + File.separator + file.getName() + File.separator + fileName + ".jpg");
            Log.d("plz", Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ).getPath() + File.separator + "Konieczny" + File.separator + collagesDirectory.getName() + File.separator + fileName + ".jpg");
            try {
                Log.d("plz", "udalo sie1");
                File f = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
//                                f.getPath()
                fs = new FileOutputStream(f.getPath() + File.separator + "Konieczny" + File.separator + collagesDirectory.getName() + File.separator + fileName + ".jpg");
                Log.d("plz", "udalo sie2");
                fs.write(byteArray);
                Log.d("plz", "udalo sie3");
                fs.close();
                Log.d("plz", "udalo sie4");
                Log.d("plz", byteArray.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(CollageMaker.this, NewGalleriesActivity.class);
            //jeśli jest dostępny aparat
            startActivity(intent);
        });
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