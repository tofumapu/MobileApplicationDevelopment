package com.example.a23520231_lab02_bai5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Dishes> thumbnails;

    public SpinnerAdapter(Context context, int layout, ArrayList<Dishes> thumbnails) {
        this.context = context;
        this.layout = layout;
        this.thumbnails = thumbnails;
    }

    @Override
    public int getCount() {
        return thumbnails.size();
    }

    @Override
    public Object getItem(int position) {
        return thumbnails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // View hiển thị trong danh sách dialog
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.my_dropdown_item, parent, false);
        ImageView img = convertView.findViewById(R.id.spinnerImage);
        TextView txt = convertView.findViewById(R.id.spinnerText);

        Dishes d = thumbnails.get(position);
        img.setImageResource(d.getThumbnail());
        txt.setText(d.getName());
        return convertView;
    }

    // View hiển thị hình đã chọn trên spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.my_selected_item, parent, false);
        ImageView img = convertView.findViewById(R.id.imgSelected);
        img.setImageResource(thumbnails.get(position).getThumbnail());
        return convertView;
    }
}
