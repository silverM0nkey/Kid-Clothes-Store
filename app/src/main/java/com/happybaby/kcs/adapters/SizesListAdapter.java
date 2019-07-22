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

    protected Activity activity;
    protected List<SizeModel> items;
    protected boolean fromAdd;

    public SizesListAdapter(Activity activity, List<SizeModel> items, boolean fromAdd) {
        this.activity = activity;
        this.items = items;
        this.fromAdd = fromAdd;
    }

    public boolean isFromAdd() {
        return fromAdd;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(List<SizeModel> list) {
        for (int i = 0; i < list.size(); i++) {
            items.add(list.get(i));
        }
    }

    public void setItems(List<SizeModel> list) {
        items = list;
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
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_selectable_list, null);
        }

        SizeModel dir = items.get(position);

        TextView sizeName = v.findViewById(R.id.name);
        sizeName.setText(dir.getName());

        if (dir.getStockQty() > 0 ){
            sizeName.setPaintFlags( sizeName.getPaintFlags() & Paint.LINEAR_TEXT_FLAG);
        } else {
            sizeName.setPaintFlags(sizeName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        ImageView check = v.findViewById(R.id.checkIcon);
        if (dir.getCheck())
            check.setVisibility(View.VISIBLE);
        else
            check.setVisibility(View.GONE);

        return v;
    }
}