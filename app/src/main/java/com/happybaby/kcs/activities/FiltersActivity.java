package com.happybaby.kcs.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.happybaby.kcs.R;
import com.happybaby.kcs.activities.interfaces.FilterView;
import com.happybaby.kcs.adapters.ColorsListAdapter;
import com.happybaby.kcs.models.FilterListModel;
import com.happybaby.kcs.presenters.FilterPresenter;

import java.util.ArrayList;
import java.util.List;

public class FiltersActivity extends BaseActivity implements FilterView, View.OnClickListener, ListView.OnItemClickListener {

    final static public String PARAM_AVAILABLE_COLOURS = "availableColours";
    final static public String PARAM_COLOUR_FILTER = "colourFilter";
    final static public String PARAM_MAX_PRICE_FILTER  = "maxPriceFilter";
    final static public String PARAM_MIN_PRICE_FILTER  = "minPriceFilter";

    private ColorsListAdapter colorsListAdapter;

    private EditText maxPrice;
    private EditText minPrice;

    private ArrayList<String> filterColours = null;
    private FilterPresenter filterPresenter;
    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        setupToolbar();
        setTitle(getResources().getString(R.string.tittle_filters));

        ArrayList<String> availableColours = getIntent().getExtras().getStringArrayList(PARAM_AVAILABLE_COLOURS);
        filterColours = getIntent().getExtras().getStringArrayList(PARAM_COLOUR_FILTER);
        Integer filterMaxPrice = getIntent().getExtras().getInt(PARAM_MAX_PRICE_FILTER, FilterPresenter.DEFAULT_PRICES_VALUE);
        Integer filterMinPrice = getIntent().getExtras().getInt(PARAM_MIN_PRICE_FILTER, FilterPresenter.DEFAULT_PRICES_VALUE);

        filterPresenter = new FilterPresenter(this, availableColours, filterColours, filterMaxPrice, filterMinPrice);

        Button applyFilters = findViewById(R.id.apply_filters);
        Button removeFilters = findViewById(R.id.remove_filters);
        maxPrice =findViewById(R.id.max_price);
        minPrice = findViewById(R.id.min_price);

        minPrice.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPresenter.onChangeMinPrice(minPrice.getText().toString());
            }
        });

        maxPrice.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPresenter.onChangeMaxPrice(maxPrice.getText().toString());
            }
        });

        applyFilters.setOnClickListener(this);
        removeFilters.setOnClickListener(this);

        filterPresenter.loadColorList();
        filterPresenter.showPrices();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.apply_filters) {
            resultIntent = new Intent();
            filterPresenter.fillResult();
        } else  if (view.getId() == R.id.remove_filters){
            filterPresenter.removeFilters();
        }
    }

    public void setResults(ArrayList<String> selectedColors, Integer maxPrice, Integer minPrice){

        resultIntent.putExtra(PARAM_COLOUR_FILTER, selectedColors);
        if (maxPrice != null) {
            resultIntent.putExtra(PARAM_MAX_PRICE_FILTER, maxPrice);
        }
        if (minPrice != null) {
            resultIntent.putExtra(PARAM_MIN_PRICE_FILTER, minPrice);
        }
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        filterPresenter.onChangeListColorSelection(i);
    }

    public void setMaxPrice(String maxPriceValue) {
        maxPrice.setText(maxPriceValue);
    }

    public void setMinPrice(String minPriceValue) {
        minPrice.setText(minPriceValue);
    }

    public void setColorList(List<FilterListModel> filterListModelList) {
        ListView colorsList = findViewById(R.id.color_list);
        colorsListAdapter = new ColorsListAdapter(this, filterListModelList);
        colorsList.setAdapter(colorsListAdapter);
        colorsList.setOnItemClickListener(this);
    }

    public void notifyDataSetChanged() {
        colorsListAdapter.notifyDataSetChanged();
    }

}
