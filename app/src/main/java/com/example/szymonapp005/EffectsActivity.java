package com.example.szymonapp005;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EffectsActivity extends AppCompatActivity {
    private ImageView image;
    private ImageView confirmButton;
    private LinearLayout effectsContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);
        image = findViewById(R.id.effects_image);
        confirmButton = findViewById(R.id.effects_confirm);
        effectsContainer = findViewById(R.id.effects_container);
        float[][] matrixes = {
                {//normal
                        1.0f, 0, 0, 0, 0,
                        0, 1.0f, 0, 0, 0,
                        0, 0, 1.0f, 0, 0,
                        0, 0, 0, 1.0f, 0
                },
                {//negative
                        -1, 0, 0, 1, 0,
                        0, -1, 0, 1, 0,
                        0, 0, -1, 1, 0,
                        0, 0, 0, 1, 0

                },
                {//red
                        2, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 1, 0
                },
                {//green
                        0, 0, 0, 0, 0,
                        0, 2, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 1, 0
                },
                {//blue
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 2, 0, 0,
                        0, 0, 0, 1, 0
                },
                {//custom1
                        1, 0, 0, 0, 0,
                        -0.2f, 1.0f, 0.3f, 0.1f, 0,
                        -0.1f, 0, 1, 0, 0,
                        0, 0, 0, 1, 0,
                },
                {//custom2
                        -1, 0, 2, 0, 0,
                        2, -1, 0, 0, 0,
                        0, -2, 1, 0, 0,
                        0, 0, 0, 1, 0
                },
                {//bw
                        0, 1, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0, 1, 0, 1, 0
                },
                {//sepium
                        1.3f, -0.3f, 1.1f, 0, 0,
                        0, 1.3f, 0.2f, 0, 0,
                        0, 0, 0.8f, 0.2f, 0,
                        0, 0, 0, 1, 0,
                }
        };
        String[] labels = {
                "normal",
                "negative",
                "red",
                "green",
                "blue",
                "yellow",
                "custom",
                "bw",
                "sepium"
        };
        for(int i = 0; i<matrixes.length; i++) {
            float[] matrix = matrixes[i];
            LinearLayout layout = new LinearLayout(EffectsActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(EffectsActivity.this);
            imageView.setImageBitmap(Imaging.applyMatrix(matrix));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            TextView textView = new TextView(EffectsActivity.this);
            textView.setText(labels[i]);
            layout.addView(imageView);
            layout.addView(textView);
            layout.setOnClickListener(view -> {
                image.setImageBitmap(Imaging.applyMatrix(matrix));
            });
            effectsContainer.addView(layout);
        }
        String path = (String) getIntent().getExtras().get("imagePath");
        Log.d("xxx", path);
        image.setImageBitmap(Imaging.getImage());
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        confirmButton.setImageResource(R.drawable.check_icon);

        confirmButton.setOnClickListener(view -> {

        });
    }
}