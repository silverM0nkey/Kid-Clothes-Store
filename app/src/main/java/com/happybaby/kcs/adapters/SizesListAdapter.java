package com.happybaby.kcs.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.happybaby.kcs.R;
import com.happybaby.kcs.models.SizeModel;

import java.util.List;


public class SizesListAdapter extends BaseAdapter {

    protected Context context;
    protected List<SizeModel> items;

    public SizesListAdapter(Context context, List<SizeModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addAll(List<SizeModel> list) {
        items.addAll(list);
    }

    public List<SizeModel> getItems(){ return items; }

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
            v = LayoutInflater.from(context).inflate(R.layout.item_selectable_list, parent, false);
        }

        SizeModel sizeModel = items.get(position);

        TextView sizeName = v.findViewById(R.id.name);
        sizeName.setText(sizeModel.getName());

        if (sizeModel.getStockQty() > 0 ){
            sizeName.setPaintFlags( sizeName.getPaintFlags() & Paint.LINEAR_TEXT_FLAG);
        } else {
            sizeName.setPaintFlags(sizeName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        ImageView check = v.findViewById(R.id.checkIcon);
        if (sizeModel.getCheck())
            check.setVisibility(View.VISIBLE);
        else
            check.setVisibility(View.GONE);

        return v;
    }
}