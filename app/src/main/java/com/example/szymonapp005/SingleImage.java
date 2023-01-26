package com.example.szymonapp005;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class SingleImage extends AppCompatActivity {
    private DrawerLayout main;
    private ImageView img;
    private ImageView settingsButton;
    private ImageView brightnessButton;
    private ImageView contrastButton;
    private ImageView saturationButton;
    private ListView settingsListView;
    private EditText ipInput;
    private Button saveIpButton;
    private LinearLayout topMenu;
    private LinearLayout bottomMenu;
    private LinearLayout blackout;
    private TextView blackoutText;
    private SeekBar slider;
    private String mode = "";
    private String ip = "192.168.119.108";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);
//        startActivityForResult();
        main = findViewById(R.id.single_image_main_layout);
        img = findViewById(R.id.imageInFolder);
        settingsListView = findViewById(R.id.single_image_settings_list);
        settingsButton = findViewById(R.id.single_image_settings_button);
        ipInput = findViewById(R.id.single_image_settings_ip);
        saveIpButton = findViewById(R.id.single_image_settings_save_ip_button);
        topMenu = findViewById(R.id.single_image_top_menu);
        bottomMenu = findViewById(R.id.single_image_bottom_menu);
        brightnessButton = findViewById(R.id.single_image_brightness);
        contrastButton = findViewById(R.id.single_image_contrast);
        saturationButton = findViewById(R.id.single_image_saturation);
        blackout = findViewById(R.id.single_image_blackout);
        blackoutText = findViewById(R.id.single_image_blackout_text);
        slider = findViewById(R.id.single_image_slider);
        brightnessButton.setOnClickListener(view -> {
            blackoutText.setText("Brightness");
            mode = "brightness";
            blackout.animate()
                    .alpha(1)
                    .withEndAction(()->{})
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(0)
                    .start();
            blackout.animate()
                    .alpha(0)
                    .withEndAction(()->{
                        slider.animate()
                                .alpha(1)
                                .withEndAction(()->{})
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setDuration(0)
                                .start();
                    })
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(2000)
                    .start();
        });
        contrastButton.setOnClickListener(view -> {
            blackoutText.setText("Contrast");
            mode = "contrast";
            blackout.animate()
                    .alpha(1)
                    .withEndAction(()->{})
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(0)
                    .start();
            blackout.animate()
                    .alpha(0)
                    .withEndAction(()->{
                        slider.animate()
                                .alpha(1)
                                .withEndAction(()->{})
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setDuration(0)
                                .start();
                    })
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(2000)
                    .start();
        });
        saturationButton.setOnClickListener(view -> {
            blackoutText.setText("Saturation");
            mode = "saturation";
            blackout.animate()
                    .alpha(1)
                    .withEndAction(()->{})
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(0)
                    .start();
            blackout.animate()
                    .alpha(0)
                    .withEndAction(()->{
                        slider.animate()
                                .alpha(1)
                                .withEndAction(()->{})
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setDuration(0)
                                .start();
                    })
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(2000)
                    .start();
        });
        topMenu.animate()
                .translationY(-200)
                .withEndAction(()->{})
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(0)
                .start();
        blackout.animate()
                .alpha(0)
                .withEndAction(()->{})
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(0)
                .start();
        slider.animate()
                .alpha(0)
                .withEndAction(()->{})
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(0)
                .start();
        bottomMenu.animate()
                .translationY(200)
                .withEndAction(()->{})
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(0)
                .start();
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // podczas przesuwania podstawiaj bitmap do imageView
                // i to wartość seekBar-a
                Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                if(mode == "brightness"){
                    float brightness = slider.getProgress();
                    float[] brightness_tab = {
                            1, 0, 0, 0, brightness,
                            0, 1, 0, 0, brightness,
                            0, 0, 1, 0, brightness,
                            0, 0, 0, 1, 0 };
                    img.setImageBitmap(Imaging.applyMatrixToBitmap(brightness_tab, bitmap));
                }
                if(mode == "contrast"){
                    float contrast = slider.getProgress();
                    float[] contrast_tab = {
                            contrast, 0, 0, 0, 0,
                            0, contrast, 0, 0, 0,
                            0, 0, contrast, 0, 0,
                            0, 0, 0, 1, 0 };
                    img.setImageBitmap(Imaging.applyMatrixToBitmap(contrast_tab, bitmap));
                }
                if(mode == "saturation"){
                    float saturation = slider.getProgress();
                    img.setImageBitmap(Imaging.applySaturation(saturation, bitmap));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // przy rozpoczęciu przesuwania
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //przy zakończeniu przesuwania
            }
        });
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
                topMenu.animate()
                        .translationY(0)
                        .withEndAction(()->{})
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .setDuration(500)
                        .start();
                bottomMenu.animate()
                        .translationY(0)
                        .withEndAction(()->{})
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .setDuration(500)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
            img.setImageBitmap(Imaging.getImage());
        }
    }
}