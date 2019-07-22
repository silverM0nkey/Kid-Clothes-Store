package com.happybaby.kcs.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.happybaby.kcs.R;
import com.happybaby.kcs.models.FilterListModel;

import java.util.List;


public class ColorsListAdapter extends BaseAdapter {

    protected Activity activity;
    protected List<FilterListModel> items;

    public ColorsListAdapter(Activity activity, List<FilterListModel> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(List<FilterListModel> list) {
        for (int i = 0; i < list.size(); i++) {
            items.add(list.get(i));
        }
    }

    public void setItems(List<FilterListModel> list) {
        items = list;
    }

    public List<FilterListModel> getItems(){ return items; }

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

        FilterListModel dir = items.get(position);

        TextView sizeName = v.findViewById(R.id.name);
        sizeName.setText(dir.getName());

        ImageView check = v.findViewById(R.id.checkIcon);
        if (dir.getCheck())
            check.setVisibility(View.VISIBLE);
        else
            check.setVisibility(View.GONE);

        return v;
    }
}