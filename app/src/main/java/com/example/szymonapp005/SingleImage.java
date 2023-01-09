package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class SingleImage extends AppCompatActivity {
    private DrawerLayout main;
    private ImageView img;
    private ImageView settingsButton;
    private ListView settingsListView;
    private EditText ipInput;
    private Button saveIpButton;
    private String ip = "192.168.119.108";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);

        main = findViewById(R.id.single_image_main_layout);
        img = findViewById(R.id.imageInFolder);
        settingsListView = findViewById(R.id.single_image_settings_list);
        settingsButton = findViewById(R.id.single_image_settings_button);
        ipInput = findViewById(R.id.single_image_settings_ip);
        saveIpButton = findViewById(R.id.single_image_settings_save_ip_button);
        SharedPreferences preferencess = PreferenceManager.getDefaultSharedPreferences(SingleImage.this);

        if (preferencess.getString("ip", null) != null){
            ipInput.setText(preferencess.getString("ip", null));
        }
        saveIpButton.setOnClickListener(v -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SingleImage.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("ip", ipInput.getText().toString());
            editor.commit();
            ip = ipInput.getText().toString();
        });
        Bundle bundle = getIntent().getExtras();
        String imagePath = bundle.get("src").toString();
        File imageFile = new File(imagePath);
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        settingsButton.setOnClickListener(v -> {
            main.openDrawer(GravityCompat.START);
        });
        ArrayList<String> settings = new ArrayList<>();
        settings.add("Upload");
        settings.add("Share");
        settings.add("Crop");
        settings.add("Effects");

        SingleImageSettingsAdapter adapter = new SingleImageSettingsAdapter(
                SingleImage.this,
                R.layout.single_image_settings_item,
                settings,
                imageFile
        );
        settingsListView.setAdapter(adapter);
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