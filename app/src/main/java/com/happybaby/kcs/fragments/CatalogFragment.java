package com.happybaby.kcs.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.happybaby.kcs.activities.FiltersActivity;
import com.happybaby.kcs.activities.ProductActivity;
import com.happybaby.kcs.R;
import com.happybaby.kcs.adapters.HomeRecyclerListAdapter;
import com.happybaby.kcs.adapters.ProductsRecyclerListAdapter;
import com.happybaby.kcs.adapters.SortOptionsListAdapter;
import com.happybaby.kcs.fragments.interfaces.CatalogListener;
import com.happybaby.kcs.fragments.interfaces.CatalogView;
import com.happybaby.kcs.presenters.BasePresenter;
import com.happybaby.kcs.presenters.CatalogPresenter;
import com.happybaby.kcs.restapi.gooco.responses.ResponseHome;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProduct;

import java.util.ArrayList;

public class CatalogFragment extends Fragment implements LinearLayout.OnClickListener,
        ListView.OnItemClickListener, CatalogListener, CatalogView {

    final public static String PARAM_STORE_ID = "storeId";
    private static final int FILTER_REQUEST_CODE = 1000;

    private HomeRecyclerListAdapter mHomeRecyclerListAdapter;
    private RecyclerView mHomeRecyclerView;

    private ProductsRecyclerListAdapter mProductsRecyclerListAdapter;
    private RecyclerView mProductsRecyclerView;

    private ListView sortByListOptions;
    private SortOptionsListAdapter sortOptionsListAdapter;

    private View mProductsContainer;
    private ImageView activeFilters;
    private ProgressBar mProgressBar;

    private CatalogPresenter catalogPresenter;

    public CatalogFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param storeId Parameter 1.
     * @return A new instance of fragment CatalogFragment.
     */
    public static CatalogFragment newInstance(String storeId) {
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_STORE_ID, storeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            catalogPresenter = new CatalogPresenter(this, getArguments().getString(PARAM_STORE_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalog, container, false);
        mProgressBar = rootView.findViewById(R.id.progress_bar);
        mHomeRecyclerView = rootView.findViewById(R.id.home);
        mProductsRecyclerView = rootView.findViewById(R.id.products);
        mProductsContainer = rootView.findViewById(R.id.products_container);
        sortByListOptions = rootView.findViewById(R.id.sort_by_options);
        activeFilters = rootView.findViewById(R.id.active_filters);

        TextView sortBy = rootView.findViewById(R.id.sort_button);
        TextView filterBy = rootView.findViewById(R.id.filter_button);
        sortBy.setOnClickListener(this);
        filterBy.setOnClickListener(this);

        sortOptionsListAdapter = new SortOptionsListAdapter(getActivity(), catalogPresenter.getSortOptions());
        sortByListOptions.setAdapter(sortOptionsListAdapter);
        sortByListOptions.setOnItemClickListener(this);
        catalogPresenter.getHomeCategories();

        return rootView;
    }

    public void onChangeCategory(String categoryId, String categoryName) {
        changeCategory(categoryId, categoryName);
    }

    public void changeCategory(String categoryId, String categoryName) {
        getActivity().setTitle(categoryName);
        mProgressBar.setVisibility(View.VISIBLE);
        sortByListOptions.setVisibility(View.GONE);
        catalogPresenter.getProducts(categoryId, categoryName);
    }

    public String getCategoryName(){
        if (catalogPresenter != null) {
            return catalogPresenter.getCategoryName();
        } else {
            return null;
        }
    }

    public void setHome() {
        catalogPresenter.setCategoryName(null);
        getActivity().setTitle(getActivity().getResources().getString(R.string.app_name));
        mHomeRecyclerView.setVisibility(View.VISIBLE);
        mProductsContainer.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sort_button) {
            if (sortByListOptions.getVisibility() == View.VISIBLE) {
                sortByListOptions.setVisibility(View.GONE);
            } else {
                sortByListOptions.setVisibility(View.VISIBLE);
            }
        } else if (view.getId() == R.id.filter_button){
            Intent intent = new Intent(getActivity(), FiltersActivity.class);
            startActivityForResult(catalogPresenter.addFilterParams(intent), FILTER_REQUEST_CODE);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        catalogPresenter.sortResult(i);
    }

    public void onShowProduct(String modelId) {
        Intent intent = new Intent(getContext(), ProductActivity.class);
        startActivity(catalogPresenter.addProductParams(intent, modelId));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (FILTER_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    catalogPresenter.filterResult(
                            data.getStringArrayListExtra(FiltersActivity.PARAM_COLOUR_FILTER),
                            data.getIntExtra(FiltersActivity.PARAM_MAX_PRICE_FILTER, 0),
                            data.getIntExtra(FiltersActivity.PARAM_MIN_PRICE_FILTER, 0));
                }
            }
        }
    }

    public void setFilterVisibility(boolean isVisible) {
        if (isVisible){
            this.activeFilters.setVisibility(View.VISIBLE);
        } else {
            this.activeFilters.setVisibility(View.GONE);
        }
    }

    public void onHomeResponse(ResponseHome responseHome) {
        mHomeRecyclerListAdapter = new HomeRecyclerListAdapter(getActivity(), this, responseHome);
        mHomeRecyclerView.setAdapter(mHomeRecyclerListAdapter);
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeRecyclerListAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
        mHomeRecyclerView.setVisibility(View.VISIBLE);
    }

    public void onProductsResponse(ArrayList<ResponseProduct> products) {
        mProductsRecyclerListAdapter = new ProductsRecyclerListAdapter(getActivity(), this, products);
        mProductsRecyclerView.setAdapter(mProductsRecyclerListAdapter);
        mProductsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        mProductsRecyclerListAdapter.notifyDataSetChanged();
        mProductsContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mHomeRecyclerView.setVisibility(View.GONE);
    }

    public void updateProductsRecyclerListAdapter(ArrayList<ResponseProduct> products) {
        mProductsRecyclerListAdapter.setProducts(products);
        mProductsRecyclerListAdapter.notifyDataSetChanged();
    }

    public void updateSortOptionsListAdapter() {
        sortOptionsListAdapter.notifyDataSetChanged();
    }

    public void setProgressBarGone(){
        mProgressBar.setVisibility(View.GONE);
    }

    public void setSortByListOptionsGone(){
        sortByListOptions.setVisibility(View.GONE);
    }

    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        catalogPresenter = null;
    }
}
