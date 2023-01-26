package com.example.szymonapp005;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class Imaging {
    public static byte[] image;
    public static Bitmap matrixImage;
    public static Bitmap getImage(){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public static Bitmap getNegative(){
        float[] neg_tab = {
                -1, 0, 0, 1, 0,
                0, -1, 0, 1, 0,
                0, 0, -1, 1, 0,
                0, 0, 0, 1, 0

        };
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(neg_tab);
        Paint paint = new Paint();
        Bitmap inputBmp = getImage();
        Bitmap b = Bitmap.createBitmap(inputBmp.getWidth(), inputBmp.getHeight(),inputBmp.getConfig());
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(b);
        canvas.drawBitmap(inputBmp, 0, 0, paint);
        return b;
    }
    public static Bitmap applyMatrix(float[] matrix){
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(matrix);
        Paint paint = new Paint();
        Bitmap inputBmp = getImage();
        Bitmap b = Bitmap.createBitmap(inputBmp.getWidth(), inputBmp.getHeight(),inputBmp.getConfig());
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(b);
        canvas.drawBitmap(inputBmp, 0, 0, paint);
        matrixImage = b;
        return b;
    }
    public static Bitmap applyMatrixToBitmap(float[] matrix, Bitmap inputBmp){
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(matrix);
        Paint paint = new Paint();
        Bitmap b = Bitmap.createBitmap(inputBmp.getWidth(), inputBmp.getHeight(),inputBmp.getConfig());
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(b);
        canvas.drawBitmap(inputBmp, 0, 0, paint);
        matrixImage = b;
        return b;
    }
    public static Bitmap applySaturation(float saturation, Bitmap inputBmp){
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.setSaturation(saturation);
        Paint paint = new Paint();
        Bitmap b = Bitmap.createBitmap(inputBmp.getWidth(), inputBmp.getHeight(),inputBmp.getConfig());
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(b);
        canvas.drawBitmap(inputBmp, 0, 0, paint);
        matrixImage = b;
        return b;
    }
}


