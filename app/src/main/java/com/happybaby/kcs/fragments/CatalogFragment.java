package com.happybaby.kcs.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
import android.widget.Toast;

import com.happybaby.kcs.activities.FiltersActivity;
import com.happybaby.kcs.activities.ProductActivity;
import com.happybaby.kcs.R;
import com.happybaby.kcs.adapters.HomeRecyclerListAdapter;
import com.happybaby.kcs.adapters.ProductsRecyclerListAdapter;
import com.happybaby.kcs.adapters.SortOptionsListAdapter;
import com.happybaby.kcs.fragments.interfaces.CatalogListener;
import com.happybaby.kcs.models.FilterQueryModel;
import com.happybaby.kcs.models.fixed.SortOptionItemModel;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.ConnectionsProfile;
import com.happybaby.kcs.restapi.gooco.responses.ResponseHome;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProduct;
import com.happybaby.kcs.restapi.gooco.responses.ResponseProductsResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;


public class CatalogFragment extends BaseFragment implements ListView.OnItemClickListener, LinearLayout.OnClickListener, CatalogListener {

    final public static String PARAM_STORE_ID = "storeId";
    private static final int FILTER_REQUEST_CODE = 1000;
    private ProgressBar mProgressBar;
    private HomeRecyclerListAdapter mHomeRecyclerListAdapter;
    private RecyclerView mHomeRecyclerView;

    private ProductsRecyclerListAdapter mProductsRecyclerListAdapter;
    private RecyclerView mProductsRecyclerView;
    private View mProductsContainer;

    private ImageView activeFilters;

    private ListView sortByListOptions;
    private SortOptionsListAdapter sortOptionsListAdapter;

    private ArrayList<ResponseProduct> responseResults;
    private ArrayList<ResponseProduct> copyResponseResults;
    private String storeId;

    private FilterQueryModel filterQueryModel;

    private ArrayList<String> availableColors;

    private String categoryName;

    public CatalogFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param storeId Parameter 1.
     * @return A new instance of fragment CatalogFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        setupRestClient();
        if (getArguments() != null) {
            this.storeId = getArguments().getString(PARAM_STORE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalog, container, false);
        CatalogFragment fragment = this;
        mProgressBar = rootView.findViewById(R.id.progress_bar);
        mHomeRecyclerView = rootView.findViewById(R.id.home);
        mProductsRecyclerView = rootView.findViewById(R.id.products);
        mProductsContainer = rootView.findViewById(R.id.products_container);
        sortByListOptions = rootView.findViewById(R.id.sort_by_options);

        TextView sortBy = rootView.findViewById(R.id.sort_button);
        TextView filterBy = rootView.findViewById(R.id.filter_button);

        activeFilters = rootView.findViewById(R.id.active_filters);

        sortBy.setOnClickListener(this);
        filterBy.setOnClickListener(this);

        Call<ResponseHome> call = restClient.getHome(this.storeId);
        call.enqueue(new CallbackWithRetry<ResponseHome>(getActivity()) {

            @Override
            public void onResponse(Call<ResponseHome> call, Response<ResponseHome> response) {
                if (response.isSuccessful()) {
                    ResponseHome responseHome = response.body();
                    mHomeRecyclerListAdapter = new HomeRecyclerListAdapter(getActivity(), fragment, responseHome);
                    mHomeRecyclerView.setAdapter(mHomeRecyclerListAdapter);
                    mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mHomeRecyclerListAdapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                    mHomeRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        String[] options = getResources().getStringArray(R.array.sort_options_array);
        List<SortOptionItemModel> sortOptions = Arrays.stream(options).map(SortOptionItemModel::new).collect(Collectors.toList());
        sortOptionsListAdapter = new SortOptionsListAdapter(getActivity(), sortOptions);
        sortByListOptions.setAdapter(sortOptionsListAdapter);
        sortByListOptions.setOnItemClickListener(this);
        return rootView;
    }

    public void onChangeCategory(String categoryId, String categoryName) {
        changeCategory(categoryId, categoryName);
    }

    public void changeCategory(String categoryId, String categoryName) {
        getActivity().setTitle(categoryName);
        this.categoryName = categoryName;
        mProgressBar.setVisibility(View.VISIBLE);
        sortByListOptions.setVisibility(View.GONE);
        HashMap<String, String> params = new HashMap<>();
        params.put(restClient.PARAM_CATEGORY_ID, categoryId);

        this.filterQueryModel = new FilterQueryModel();

        CatalogFragment catalogFragment = this;
        Call<ResponseProductsResults> call = restClient.getProducts(this.storeId, params);
        call.enqueue(new CallbackWithRetry<ResponseProductsResults>(getActivity()) {

            @Override
            public void onResponse(Call<ResponseProductsResults> call, Response<ResponseProductsResults> response) {
                if (response.isSuccessful()) {
                    responseResults = new ArrayList<>(response.body().getResults());
                    copyResponseResults = (ArrayList<ResponseProduct>)responseResults.clone();
                    mProductsRecyclerListAdapter = new ProductsRecyclerListAdapter(getActivity(), catalogFragment, copyResponseResults);
                    mProductsRecyclerView.setAdapter(mProductsRecyclerListAdapter);
                    mProductsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
                    mProductsRecyclerListAdapter.notifyDataSetChanged();
                    mProductsContainer.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    mHomeRecyclerView.setVisibility(View.GONE);

                    availableColors = getAvailableColours();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                }
            }
            public void onFinalFailure() {
                super.onFinalFailure();
                mProgressBar.setVisibility(View.GONE);
            }


        });
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public void setHome() {
        this.categoryName = null;
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
        } else if   (view.getId() == R.id.filter_button){
            Intent intent = new Intent(getActivity(), FiltersActivity.class);
            intent.putExtra(FiltersActivity.PARAM_MAX_PRICE_FILTER, this.filterQueryModel.getMaxPriceFilter());
            intent.putExtra(FiltersActivity.PARAM_MIN_PRICE_FILTER, this.filterQueryModel.getMinPriceFilter());
            intent.putExtra(FiltersActivity.PARAM_COLOUR_FILTER, this.filterQueryModel.getColourFilter());
            intent.putExtra(FiltersActivity.PARAM_AVAILABLE_COLOURS, availableColors);
            startActivityForResult(intent, FILTER_REQUEST_CODE);
        }
    }

    private ArrayList<String> getAvailableColours() {
        List<String> colors;
        if (ConnectionsProfile.FIXED_COLOURS_AND_SIZES){
                String [] arrayColor = getResources().getStringArray(R.array.color_array);
                colors = Arrays.asList(arrayColor);
        } else {
            colors = mProductsRecyclerListAdapter.getItems().stream().map(p -> p.getColor()).collect(Collectors.toList());
            Set<String> set = new HashSet<>(colors);
            colors.clear();
            colors.addAll(set);

        }
        return new ArrayList<>(colors);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        for (SortOptionItemModel item: sortOptionsListAdapter.getItems()){
            item.setCheck(false);
        }
        ((SortOptionItemModel)sortOptionsListAdapter.getItem(i)).setCheck(true);
        sortOptionsListAdapter.notifyDataSetChanged();
        sortByListOptions.setVisibility(View.GONE);
        sortBy(i);
    }

    public void onShowProduct(String modelId) {
        Intent intent = new Intent(getContext(), ProductActivity.class);
        ResponseProduct responseProduct = copyResponseResults.stream().filter(p -> p.getModelId().equals(modelId)).findAny().orElse(null);
        intent.putExtra(ProductActivity.PARAM_STORE_ID, this.storeId);
        intent.putExtra(ProductActivity.PARAM_PRODUCT_NAME, responseProduct.getName());
        intent.putExtra(ProductActivity.PARAM_MODEL_ID, responseProduct.getModelId());
        intent.putExtra(ProductActivity.PARAM_FINAL_PRICE, responseProduct.getFinalPrice());
        intent.putExtra(ProductActivity.PARAM_ORIGINAL_PRICE, responseProduct.getOriginalPrice());
        intent.putExtra(ProductActivity.PARAM_FINAL_PRICE_TYPE, responseProduct.getFinalPriceType());
        intent.putExtra(ProductActivity.PARAM_CURRENCY, responseProduct.getCurrency());
        intent.putExtra(ProductActivity.PARAM_SKU, responseProduct.getSku());
        intent.putExtra(ProductActivity.PARAM_COMPOSITION, responseProduct.getComposition());
        intent.putExtra(ProductActivity.PARAM_CARE, responseProduct.getCare());
        intent.putExtra(ProductActivity.PARAM_URL, responseProduct.getUrl());
        intent.putExtra(ProductActivity.PARAM_COLOR, responseProduct.getColor());

        ArrayList<String> listOfStrings = new ArrayList<>(responseProduct.getImages().size());
        listOfStrings.addAll(responseProduct.getImages());

        intent.putExtra(ProductActivity.PARAM_IMAGES, listOfStrings);
        intent.putParcelableArrayListExtra(ProductActivity.PARAM_SIZES, new ArrayList<>(responseProduct.getSizes()));
        startActivity(intent);
    }

    private void sortBy(int sortBy){
        switch (sortBy) {
            //EXPENSIVE TO CHEAP
            case 0:
                Collections.sort(mProductsRecyclerListAdapter.getItems(), (p1, p2) -> p1.getFinalPrice().compareTo(p2.getFinalPrice()));
                break;
            //CHEAP TO EXPENSIVE
            case 1:
                Collections.sort(mProductsRecyclerListAdapter.getItems(), (p1, p2) -> p2.getFinalPrice().compareTo(p1.getFinalPrice()));
                break;
            //A-Z
            case 2:
                Collections.sort(mProductsRecyclerListAdapter.getItems(), (p1, p2) -> p1.getName().compareTo(p2.getName()));
                break;
            //Z-A
            case 3:
                Collections.sort(mProductsRecyclerListAdapter.getItems(), (p1, p2) -> p2.getName().compareTo(p1.getName()));
                break;
        }
        mProductsRecyclerListAdapter.notifyDataSetChanged();
    }

    private void filterBy(FilterQueryModel filterQueryModel){
        if (filterQueryModel.anyFilter()){
            this.activeFilters.setVisibility(View.VISIBLE);
        } else {
            this.activeFilters.setVisibility(View.GONE);
        }
        copyResponseResults = responseResults.
                stream().filter(p -> (filterQueryModel.getColourFilter().size() == 0 || filterQueryModel.getColourFilter().stream().anyMatch(c -> c.equals(p.getColor()))) &&
                        (filterQueryModel.getMinPriceFilter() == null || p.getFinalPrice() >= filterQueryModel.getMinPriceFilter() * 100) &&
                        (filterQueryModel.getMaxPriceFilter() == null || p.getFinalPrice() <= filterQueryModel.getMaxPriceFilter() * 100)).collect(Collectors.toCollection(ArrayList::new));

        mProductsRecyclerListAdapter.setProducts(copyResponseResults);
        mProductsRecyclerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (FILTER_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList <String> colourFilter = data.getStringArrayListExtra(FiltersActivity.PARAM_COLOUR_FILTER);
                    int maxPriceFilter = data.getIntExtra(FiltersActivity.PARAM_MAX_PRICE_FILTER, 0);
                    int minPriceFilter = data.getIntExtra(FiltersActivity.PARAM_MIN_PRICE_FILTER, 0);

                    this.filterQueryModel = new FilterQueryModel(colourFilter, maxPriceFilter == 0 ? null: maxPriceFilter, minPriceFilter == 0 ? null : minPriceFilter);
                    filterBy(this.filterQueryModel);
                }
            }
        }
    }

}
