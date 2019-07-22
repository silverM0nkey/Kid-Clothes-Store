package com.happybaby.kcs.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import com.happybaby.kcs.R;
import com.happybaby.kcs.models.fixed.SortOptionItemModel;




public class SortOptionsListAdapter extends BaseAdapter {

    protected Activity activity;
    protected List<SortOptionItemModel> items;

    public SortOptionsListAdapter(Activity activity, List<SortOptionItemModel> items) {
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

    public void addAll(List<SortOptionItemModel> list) {
        for (int i = 0; i < list.size(); i++) {
            items.add(list.get(i));
        }
    }

    public void setItems(List<SortOptionItemModel> list) {
        items = list;
    }

    public List<SortOptionItemModel> getItems(){ return items; }

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
            v = inf.inflate(R.layout.item_sort_options_list, null);
        }

        SortOptionItemModel dir = items.get(position);

        TextView optionName = v.findViewById(R.id.itemName);
        optionName.setText(dir.getNameOption());

        ImageView check = v.findViewById(R.id.checkIcon);
        if (dir.getCheck())
            check.setVisibility(View.VISIBLE);
        else
            check.setVisibility(View.GONE);

        return v;
    }
}