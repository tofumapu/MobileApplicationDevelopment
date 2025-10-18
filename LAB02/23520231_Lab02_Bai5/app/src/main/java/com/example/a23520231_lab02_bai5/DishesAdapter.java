package com.example.a23520231_lab02_bai5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DishesAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Dishes> dishesList;

    public DishesAdapter(Context context, int layout, ArrayList<Dishes> dishesList) {
        this.context = context;
        this.layout = layout;
        this.dishesList = dishesList;
    }

    @Override
    public int getCount() {
        return dishesList.size();
    }

    @Override
    public Object getItem(int position) {
        return dishesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);

        Dishes dish = dishesList.get(position);
        ImageView img = convertView.findViewById(R.id.imgDish);
        TextView txt = convertView.findViewById(R.id.tvDishName);
        ImageView star = convertView.findViewById(R.id.imgStar);

        txt.setText(dish.getName());
        txt.setSelected(true);
        img.setImageResource(dish.getThumbnail());

        if (dish.isPromotion()) star.setVisibility(View.VISIBLE);
        else star.setVisibility(View.GONE);

        return convertView;
    }
}
