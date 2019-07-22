package com.happybaby.kcs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import com.happybaby.kcs.R;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCategory;

@SuppressWarnings("unchecked")
public class CategoriesExpandableListAdapter extends BaseExpandableListAdapter {

    protected List<ResponseCategory> categories;
    public Context context;

    public CategoriesExpandableListAdapter(List<ResponseCategory> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {
        final ResponseCategory category = categories.get(groupPosition).getChildren().get(childPosition);

        View v = convertView;

        if (v == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_subcategory_list, null);
        }

        TextView categoryName = (TextView)  v.findViewById(R.id.subcategory_name);
        categoryName.setText(category.getName());

        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categories.get(groupPosition).getChildren().size();
    }

    @Override
    public ResponseCategory getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int categoryPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_category_list, null);
        }

        final ResponseCategory category = categories.get(categoryPosition);

        TextView categoryName = (TextView) v.findViewById(R.id.category_name);
        categoryName.setText(category.getName());
        return v;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}


