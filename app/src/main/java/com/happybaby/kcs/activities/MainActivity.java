package com.happybaby.kcs.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.adapters.CategoriesExpandableListAdapter;
import com.happybaby.kcs.adapters.MenuAdapter;
import com.happybaby.kcs.adapters.SimpleFragmentPagerAdapter;
import com.happybaby.kcs.bd.room.AppDatabase;
import com.happybaby.kcs.bd.room.entities.ShoppingCartProduct;
import com.happybaby.kcs.components.NonSwipeableViewPager;
import com.happybaby.kcs.fragments.CatalogFragment;
import com.happybaby.kcs.fragments.ProfileFragment;
import com.happybaby.kcs.fragments.StoresFragment;
import com.happybaby.kcs.models.fixed.MenuItemModel;
import com.happybaby.kcs.models.CustomerProfile;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.responses.ResponseCategory;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements ListView.OnItemClickListener, View.OnClickListener {

    final public int PROFILE_POSITION = 0;
    final public int CATALOG_POSITION = 1;
    final public int STORE_POSITION  = 2;

    final public static String PARAM_STORE_ID = "storeId";
    private ArrayList <Fragment> mainFragments;
    private NonSwipeableViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private ExpandableListView expandableListView;
    private BottomNavigationView navView;
    private int storeId;
    private CatalogFragment catalogFragment;
    private ProfileFragment profileFragment;
    protected MenuItem itemCart;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        itemCart = menu.findItem(R.id.action_cart);
        shoppingCartInActionbarUpdate();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mMenuList = (ListView) findViewById(R.id.menu_list);
        TextView menuHome = (TextView) findViewById(R.id.home_text);

        menuHome.setOnClickListener(this);

        expandableListView = findViewById(R.id.data_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        setupRestClient();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        MenuItemModel[] menu = new MenuItemModel[4];
        String[] drawerItems = getResources().getStringArray(R.array.navigation_menu_items_array);

        menu[0] = new MenuItemModel(R.drawable.ic_faq_grey_24dp, drawerItems[0]);
        menu[1] = new MenuItemModel(R.drawable.ic_faq_grey_24dp, drawerItems[1]);
        menu[2] = new MenuItemModel(R.drawable.ic_faq_grey_24dp, drawerItems[2]);
        menu[3] = new MenuItemModel(R.drawable.ic_faq_grey_24dp, drawerItems[3]);

        MenuAdapter menuAdapter = new MenuAdapter(this, R.layout.item_list_view_row, menu);
        mMenuList.setAdapter(menuAdapter);
        mMenuList.setOnItemClickListener(this);

        Context context = this;
        this.storeId = getIntent().getExtras().getInt(PARAM_STORE_ID);
        Call<List<ResponseCategory>> call = restClient.getCategories(new Integer(this.storeId).toString());
        call.enqueue(new CallbackWithRetry<List<ResponseCategory>>(this) {

            @Override
            public void onResponse(Call<List<ResponseCategory>> call, Response<List<ResponseCategory>> response) {
                if (response.isSuccessful()) {
                    List<ResponseCategory> categories = response.body();
                    final CategoriesExpandableListAdapter categoriesExpandableListAdapter = new CategoriesExpandableListAdapter(categories, context);
                    expandableListView.setAdapter(categoriesExpandableListAdapter);

                    expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            CategoriesExpandableListAdapter adapter = (CategoriesExpandableListAdapter)expandableListView.getExpandableListAdapter();
                            String categoryId = adapter.getGroup(groupPosition).getChildren().get(childPosition).getCategoryId();
                            String categoryName = adapter.getGroup(groupPosition).getChildren().get(childPosition).getName();
                            mDrawerLayout.closeDrawer(Gravity.LEFT, true);
                            catalogFragment.setCategory(categoryId, categoryName);
                            navView.setSelectedItemId(R.id.navigation_catalog);
                            return true;
                        }
                    });
                } else {
                    System.out.print(response.errorBody());
                    Toast.makeText(context, SERVER_ERROR_MENSSAGE, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewPager =  findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        catalogFragment = CatalogFragment.newInstance(new Integer(storeId).toString());
        profileFragment = new ProfileFragment();

        mainFragments = new ArrayList();
        mainFragments.add(profileFragment);
        mainFragments.add(catalogFragment);
        mainFragments.add(new StoresFragment());


        SimpleFragmentPagerAdapter simpleFragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), mainFragments);
        viewPager.setAdapter(simpleFragmentPagerAdapter);

        navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                        viewPager.setCurrentItem(PROFILE_POSITION);
                        profileFragment.setUp();
                        break;
                    case R.id.navigation_catalog:
                        viewPager.setCurrentItem(CATALOG_POSITION);

                        break;
                    case R.id.navigation_store:
                        viewPager.setCurrentItem(STORE_POSITION);
                        break;
                }
                return true;
            }
        });

        navView.setSelectedItemId(R.id.navigation_catalog);
        shoppingCartInActionbarUpdate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        shoppingCartInActionbarUpdate();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, GeneralInfoActivity.class);
        intent.putExtra(GeneralInfoActivity.PARAM_TYPE, position);
        intent.putExtra(GeneralInfoActivity.PARAM_STORE_ID, this.storeId);
        startActivity(intent);
        mDrawerLayout.closeDrawer(Gravity.LEFT, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cart){
            if (AppDatabase.getInstance(this).shoppingCartDao().countProductsByCustomer(CustomerProfile.getCustomerProfile().getEmail()) > 0) {
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, getResources().getString(R.string.empty_cart), Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        catalogFragment.setHome();
        mDrawerLayout.closeDrawer(Gravity.LEFT, true);
    }

    @Override
    public void onBackPressed() {

    }

    public void shoppingCartInActionbarUpdate() {
        if (itemCart != null) {
            LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
            setBadgeCount(this, icon, getNumberOfProducts(CustomerProfile.getCustomerProfile().getEmail()).toString());
        }
    }

    private Integer getNumberOfProducts(String customer) {
        List<ShoppingCartProduct> products =  AppDatabase.getInstance(this).shoppingCartDao().findProductsByCustomer(customer);
        Integer totalNumberOfProducts= 0;
        for (ShoppingCartProduct product: products){
            totalNumberOfProducts = totalNumberOfProducts + product.getQty();
        }
        return totalNumberOfProducts;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
