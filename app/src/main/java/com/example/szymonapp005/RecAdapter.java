package com.example.szymonapp005;

import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder>{
    private ArrayList<SliderItem> list;
    public RecAdapter(ArrayList<SliderItem> list) {
        this.list = list;
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
        holder.aTxt.setText(listItem.getA());
        holder.bTxt.setText(listItem.getB());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView aTxt;
        private TextView bTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aTxt = itemView.findViewById(R.id.main_slider_item_text1);
            bTxt = itemView.findViewById(R.id.main_slider_item_text2);
        }
    }
}
