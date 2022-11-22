package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.File;

public class FolderBrowserActivity extends AppCompatActivity {
    private ListView imagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_browser);
//        imagesList = findViewById(R.id.images_list_view);


        LinearLayout mainLayout = findViewById(R.id.main_layout);
        Bundle bundle = getIntent().getExtras();
        String folderName = bundle.get("src").toString();
        File fils = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES +File.separator + "Konieczny" + File.separator +folderName);
        File[] files = fils.listFiles(); // tablica plików

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                FolderBrowserActivity.this, // tzw Context
//                R.layout.folder_item, // nazwa pliku xml naszego wiersza na liście
//                R.id.folderText, // id pola txt w wierszu
//                array ); // tablica przechowująca testowe dane
//        imagesList.setAdapter(adapter);

        for (File file : files){
            Log.d("plz",file.getName());
            String imagepath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + File.separator + "Konieczny" + File.separator +folderName+File.separator+file.getName()));                               // pobierz scieżkę z obiektu File    
            Bitmap bmp = betterImageDecode(imagepath);    // własna funkcja betterImageDecode opisana jest poniżej
            ImageView img = new ImageView(FolderBrowserActivity.this);
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
            img.setImageBitmap(bmp);                // wstawienie bitmapy do ImageView
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FolderBrowserActivity.this, SingleImage.class);
                    intent.putExtra("src", file.getAbsolutePath());
                    startActivity(intent);
                }
            });
            mainLayout.addView(img);
        }
    }

    private Bitmap betterImageDecode(String filePath) {
        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4x
        //
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }
}