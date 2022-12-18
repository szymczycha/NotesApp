package com.example.szymonapp005;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bikomobile.multipart.Multipart;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class SingleImageSettingsAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<String> settingsList;
    private String ip;
    private File image;
    public SingleImageSettingsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> settingsList, File image) {
        super(context, resource, settingsList);
        this.context = context;
        this.resource = resource;
        this.settingsList = settingsList;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.getString("ip", null) != null){
            Log.d("xxx",preferences.getString("ip", null));
            ip = preferences.getString("ip",null);
        }

        this.image = image;
    }

    @Override
    public int getCount() {
        return settingsList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource, null);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.getString("ip", null) != null){
            Log.d("xxx",preferences.getString("ip", null));
            ip = preferences.getString("ip",null);
        }
        ImageView icon = convertView.findViewById(R.id.single_image_settings_item_icon);
        TextView label = convertView.findViewById(R.id.single_image_settings_item_label);
        LinearLayout main = convertView.findViewById(R.id.single_image_settings_main);
        label.setText(settingsList.get(position));
        switch (position){

            case 0:
                icon.setImageResource(R.drawable.upload_icon);
                main.setOnClickListener(view -> {
                    if (preferences.getString("ip", null) != null){
                        Log.d("xxx",preferences.getString("ip", null));
                        ip = preferences.getString("ip",null);
                    }
                    if(Networking.isWifiOn(context)){

                        Bitmap bmp = BitmapFactory.decodeFile(image.getPath());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        Multipart multipart = new Multipart(context);
                        multipart.addFile("image/jpeg", "file", image.getName(), byteArray);
                        Log.d("xxx", "http://"+ip+"/uploadImage");
                        multipart.launchRequest("http://"+ip+"/uploadImage",
                                response -> {
                                    Log.d("xxx", "success");
                                },
                                error -> {
                                    Log.d("XXX", error.toString());
                                    Log.d("xxx", "error");
                                });
                    }else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("INTERNET");
                        alert.setMessage("Brak internetu - nie można wysłać pliku na serwer");
                        alert.setNeutralButton("OK",null);
                        alert.show();
                    }
                });
        }
        return convertView;
    }
}
