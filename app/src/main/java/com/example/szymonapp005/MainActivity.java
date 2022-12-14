package com.example.szymonapp005;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private LinearLayout cameraButton;
    private LinearLayout newAlbumsButton;
    private LinearLayout albumsButton;
    private LinearLayout collageButton;
    private LinearLayout networkButton;
    private LinearLayout notesButton;
    public void checkPermission(String permission, int requestCode) {
        // jeśli nie jest przyznane to zażądaj
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File d = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        new File(d, "Konieczny").mkdir();
        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES + File.separator + "Konieczny" );
        File dir = new File(pic, "Ludzie");
        dir.mkdir();
        File dir2 = new File(pic, "Miejsca");
        dir2.mkdir();
        File dir3 = new File(pic, "Rzeczy");
        dir3.mkdir();
        newAlbumsButton = findViewById(R.id.albums_new_button);
        newAlbumsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewGalleriesActivity.class);
                startActivity(intent);
            }
        });
        notesButton = findViewById(R.id.notes_button);
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });
        cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
//                intent.putExtra("name", "camera");
//                startActivity(intent);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Wybierz źródło zdjęcia!");
                TextView aparatButton = new TextView(MainActivity.this);
                aparatButton.setTextSize(20);
                aparatButton.setText("aparat");
                aparatButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //jeśli jest dostępny aparat
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, 200); // 200 - stała wartość, która później posłuży do identyfikacji tej akcji
                        }
                    }
                });
                TextView galeriaButton = new TextView(MainActivity.this);
                galeriaButton.setTextSize(20);
                galeriaButton.setText("galeria");
                galeriaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 100); // 100 - stała wartość, która później posłuży do identyfikacji tej akcji
                    }
                });
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(20, 20, 20, 20);
                layout.addView(aparatButton);
                layout.addView(galeriaButton);
                alert.setView(layout);
                alert.show();
            }
        });
        albumsButton = findViewById(R.id.albums_button);
        albumsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AlbumsActivity.class);
                intent.putExtra("name", "albums");
                startActivity(intent);
            }
        });
        collageButton = findViewById(R.id.collage_button);
        collageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CollageActivity.class);
                startActivity(intent);
            }
        });
        networkButton = findViewById(R.id.network_button);
        networkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("name", "network");
                startActivity(intent);
            }
        });
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 100);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 100);
        checkPermission(Manifest.permission.CAMERA, 100);
        checkPermission(Manifest.permission.INTERNET, 100);
        checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
            // tutaj są dostępne dane zdjęcia z aparatu, można je konwertować i zapisywać do pliku
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Uwaga!");
                alert.setMessage("Gdzie zapisać");
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50,50,50,50);
                File d = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
                new File(d, "Konieczny").mkdir();
                File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES + File.separator + "Konieczny" );
                File[] files = pic.listFiles(); // tablica plików
                for (File file : files){
                    TextView aparatButton = new TextView(MainActivity.this);
                    aparatButton.setTextSize(20);
                    aparatButton.setText(file.getName());

                    aparatButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // onclick buttona w tej liscie
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            b.compress(Bitmap.CompressFormat.JPEG, 100, stream); // kompresja, typ pliku jpg, png
                            byte[] byteArray = stream.toByteArray();
                            FileOutputStream fs = null;
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String fileName = df.format(new Date());
//                            Log.d("plz", Environment.DIRECTORY_PICTURES + File.separator + file.getName() + File.separator + fileName + ".jpg");
                            Log.d("plz", Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ).getPath() + File.separator + "Konieczny" + File.separator + file.getName() + File.separator + fileName + ".jpg");
                            try {
                                Log.d("plz", "udalo sie1");
                                File f = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
//                                f.getPath()
                                fs = new FileOutputStream(f.getPath() + File.separator + "Konieczny" + File.separator + file.getName() + File.separator + fileName + ".jpg");
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
                            Intent intent = new Intent(MainActivity.this, AlbumsActivity.class);
                            //jeśli jest dostępny aparat
                            startActivity(intent);
                        }
                    });
                    layout.addView(aparatButton);
                }
                ImageView img = new ImageView(MainActivity.this);
                img.setImageBitmap(b);
                layout.addView(img);
                alert.setView(layout);
                alert.show();
            }
        }

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
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Uwaga!");
                alert.setMessage("Na pewno usunąć?");
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50,50,50,50);
                File d = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
                new File(d, "Konieczny").mkdir();
                File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES + File.separator + "Konieczny" );
                File[] files = pic.listFiles(); // tablica plików
                for (File file : files){
                    TextView aparatButton = new TextView(MainActivity.this);
                    aparatButton.setTextSize(20);
                    aparatButton.setText(file.getName());

                    aparatButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // onclick buttona w tej liscie
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            b.compress(Bitmap.CompressFormat.JPEG, 100, stream); // kompresja, typ pliku jpg, png
                            byte[] byteArray = stream.toByteArray();
                            FileOutputStream fs = null;
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String fileName = df.format(new Date());
//                            Log.d("plz", Environment.DIRECTORY_PICTURES + File.separator + file.getName() + File.separator + fileName + ".jpg");
                            Log.d("plz", Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ).getPath() + File.separator + "Konieczny" + File.separator + file.getName() + File.separator + fileName + ".jpg");
                            try {
                                Log.d("plz", "udalo sie1");
                                File f = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
//                                f.getPath()
                                fs = new FileOutputStream(f.getPath() + File.separator + "Konieczny" + File.separator + file.getName() + File.separator + fileName + ".jpg");
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
                            Intent intent = new Intent(MainActivity.this, AlbumsActivity.class);
                            //jeśli jest dostępny aparat
                            startActivity(intent);
                        }
                    });
                    layout.addView(aparatButton);
                }
                ImageView img = new ImageView(MainActivity.this);
                img.setImageBitmap(b);
                layout.addView(img);
                alert.setView(layout);
                alert.show();
            }
        }

    }
}