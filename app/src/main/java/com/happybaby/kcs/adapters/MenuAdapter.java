package com.happybaby.kcs.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.happybaby.kcs.models.fixed.MenuItemModel;
import com.happybaby.kcs.R;


public class MenuAdapter extends ArrayAdapter<MenuItemModel> {

    Context mContext;
    int layoutResourceId;
    MenuItemModel[] data;

    public MenuAdapter(Context mContext, int layoutResourceId, MenuItemModel[] data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, parent, false);

        ImageView imageViewIcon = convertView.findViewById(R.id.itemIcon);
        TextView textViewName = convertView.findViewById(R.id.itemName);

        MenuItemModel modelItem = data[position];

        imageViewIcon.setImageResource(modelItem.getIcon());
        textViewName.setText(modelItem.getName());

        return convertView;
    }
}
