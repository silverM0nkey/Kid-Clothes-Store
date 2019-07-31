package com.happybaby.kcs.activities;

import android.os.Bundle;
import android.support.v4.text.HtmlCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.activities.interfaces.GeneralInfoView;
import com.happybaby.kcs.presenters.GeneralInfoPresenter;


public class GeneralInfoActivity extends BaseActivity implements GeneralInfoView {

    private TextView contentText;
    public static String PARAM_TYPE = "type";
    public static String PARAM_STORE_ID = "storeId";
    public enum Types {TYPE_FAQ, TYPE_SHOPPING_GUIDE, TYPE_SHIPPING_COSTS, TYPE_CONTACT}

    private GeneralInfoPresenter generalInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_information);
        setupToolbar();

        generalInfoPresenter = new GeneralInfoPresenter(this);

        int selectedItem = getIntent().getExtras().getInt(PARAM_TYPE);
        String storeId = Integer.valueOf(getIntent().getExtras().getInt(PARAM_STORE_ID)).toString();
        setTitle(getResources().getStringArray(R.array.navigation_menu_items_array)[selectedItem]);

        contentText = findViewById(R.id.content_text);
        generalInfoPresenter.loadContent(storeId, selectedItem);
    }

    public void loadInfoFinished(String content) {
        if (content !=null) {
            contentText.setText(HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else {
            Toast.makeText(this, getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        generalInfoPresenter.unbindView();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
