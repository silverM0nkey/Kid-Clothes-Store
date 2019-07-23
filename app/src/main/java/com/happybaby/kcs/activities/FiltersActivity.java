package com.happybaby.kcs.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.happybaby.kcs.R;
import com.happybaby.kcs.adapters.ColorsListAdapter;
import com.happybaby.kcs.models.FilterListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FiltersActivity extends BaseActivity implements View.OnClickListener, ListView.OnItemClickListener {

    final static public String PARAM_AVAILABLE_COLOURS = "availableColours";
    final static public String PARAM_COLOUR_FILTER = "colourFilter";
    final static public String PARAM_MAX_PRICE_FILTER  = "maxPriceFilter";
    final static public String PARAM_MIN_PRICE_FILTER  = "minPriceFilter";
    final static public int DEFAULT_PRICES_VALUE =  0;

    private ColorsListAdapter colorsListAdapter;

    private EditText maxPrice;
    private EditText minPrice;

    private ArrayList<String> filterColours = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        setupToolbar();
        setTitle(getResources().getString(R.string.tittle_filters));

        ArrayList<String> availableColours = getIntent().getExtras().getStringArrayList(PARAM_AVAILABLE_COLOURS);
        filterColours = getIntent().getExtras().getStringArrayList(PARAM_COLOUR_FILTER);
        Integer filterMaxPrice = getIntent().getExtras().getInt(PARAM_MAX_PRICE_FILTER, DEFAULT_PRICES_VALUE);
        Integer filterMinPrice = getIntent().getExtras().getInt(PARAM_MIN_PRICE_FILTER, DEFAULT_PRICES_VALUE);

        Button applyFilters = findViewById(R.id.apply_filters);
        Button removeFilters = findViewById(R.id.remove_filters);
        maxPrice =findViewById(R.id.max_price);
        minPrice = findViewById(R.id.min_price);

        applyFilters.setOnClickListener(this);
        removeFilters.setOnClickListener(this);

        if (filterMaxPrice != DEFAULT_PRICES_VALUE) {
            maxPrice.setText(filterMaxPrice.toString());
        }

        if (filterMinPrice != DEFAULT_PRICES_VALUE) {
            minPrice.setText(filterMinPrice.toString());
        }

        List<FilterListModel> filterListModelList= availableColours.
                stream().map(availableColour -> new FilterListModel(availableColour, filterColours != null && filterColours.stream().anyMatch(filterColour -> filterColour.equals(availableColour)))).
                collect(Collectors.toList());

        ListView colorsList = findViewById(R.id.color_list);
        colorsListAdapter = new ColorsListAdapter(this, filterListModelList);
        colorsList.setAdapter(colorsListAdapter);
        colorsList.setOnItemClickListener(this);
    }

    public void onClick(View view) {
        String EMPTY_PRICES_VALUE =  "";
        if (view.getId() == R.id.apply_filters) {
            Intent resultIntent = new Intent();
            ArrayList<String> selectedColors = colorsListAdapter.getItems().
                            stream().filter(c -> c.getCheck()).map(c -> c.getName()).collect(Collectors.toCollection(ArrayList::new));

            resultIntent.putExtra(PARAM_COLOUR_FILTER, new ArrayList<>(selectedColors));

            if (!maxPrice.getText().toString().matches(EMPTY_PRICES_VALUE))
                resultIntent.putExtra(PARAM_MAX_PRICE_FILTER, Integer.valueOf(maxPrice.getText().toString()));
            if (!minPrice.getText().toString().matches(EMPTY_PRICES_VALUE))
                resultIntent.putExtra(PARAM_MIN_PRICE_FILTER, Integer.valueOf(minPrice.getText().toString()));
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else  if (view.getId() == R.id.remove_filters){
            maxPrice.setText(EMPTY_PRICES_VALUE);
            minPrice.setText(EMPTY_PRICES_VALUE);
            colorsListAdapter.getItems().forEach(c -> c.setCheck(false));
            colorsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        colorsListAdapter.getItems().get(i).setCheck(!colorsListAdapter.getItems().get(i).getCheck());
        colorsListAdapter.notifyDataSetChanged();
    }
}
