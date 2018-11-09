package com.games.user.diagnostico;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterCategory extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Category> items;
    protected String[] gravedad;

    public AdapterCategory(Activity activity, ArrayList<Category> items, String[] gravedad) {
        this.activity = activity;
        this.items = items;
        this.gravedad = gravedad;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Category> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.listview_item, null);
        }
        Category dir = items.get(position);
        //TextView title = (TextView) v.findViewById(R.id.item_ImageView);
        //title.setText("holi");
        TextView description = v.findViewById(R.id.item_TextView);
        switch (gravedad[position]) {
            case "AMARILLO":
                 description.setTextColor(activity.getResources().getColor(R.color.amarillo));
                break;
            case "VERDE":
                 description.setTextColor(activity.getResources().getColor(R.color.verde));
                break;
            case "ROJO":
                 description.setTextColor(activity.getResources().getColor(R.color.rojo));
                break;
        }
        description.setText(dir.getDescription());
        ImageView imagen = v.findViewById(R.id.item_ImageView);
        imagen.setImageBitmap(dir.getImage());
        return v;
    }
}