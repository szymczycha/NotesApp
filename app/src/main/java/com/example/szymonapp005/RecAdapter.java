package com.example.szymonapp005;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder>{
    private ArrayList<SliderItem> list;
    private Context context;
    public RecAdapter(ArrayList<SliderItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_slider_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SliderItem listItem = list.get(position);
        Log.d("xxx", listItem.toString());
        holder.aTxt.setText(String.valueOf(listItem.getCreationTime()));
        holder.bTxt.setText(String.valueOf(listItem.getSize()));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = "";
        if (preferences.getString("ip", null) != null){
            Log.d("xxx",preferences.getString("ip", null));
            ip = preferences.getString("ip",null);
        }
        String url = "http://"+ip+":3000/photo/?imgName="+listItem.getName();
        Log.d("xxx", url);
        Picasso
            .get()
            .load(url)
            .into(holder.img);
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView aTxt;
        private TextView bTxt;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aTxt = itemView.findViewById(R.id.main_slider_item_text1);
            bTxt = itemView.findViewById(R.id.main_slider_item_text2);
            img = itemView.findViewById(R.id.main_slider_item_image);
        }
    }
}
