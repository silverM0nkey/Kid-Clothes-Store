package com.happybaby.kcs.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.text.HtmlCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.happybaby.kcs.R;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.responses.ResponseGeneralInfo;
import retrofit2.Call;
import retrofit2.Response;

public class GeneralInfoActivity extends BaseActivity {

    private TextView contentText;
    public static String PARAM_TYPE = "type";
    public static String PARAM_STORE_ID = "storeId";
    public enum Types {TYPE_FAQ, TYPE_SHOPPING_GUIDE, TYPE_SHIPPING_COSTS, TYPE_CONTACT}
    public static String shippingCostsContent = "<html><header><title>Shipping Costs</title></header><body><h1 ALIGN=\"center\" STYLE=\"font:36pt/40pt courier;\">Hello world!</h1></body></html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_information);
        setupToolbar();


        int selectedItem = getIntent().getExtras().getInt(PARAM_TYPE);
        String storeId = Integer.valueOf(getIntent().getExtras().getInt(PARAM_STORE_ID)).toString();
        setTitle(getResources().getStringArray(R.array.navigation_menu_items_array)[selectedItem]);

        contentText = findViewById(R.id.content_text);
        Call<ResponseGeneralInfo> call = null;
        if (selectedItem == Types.TYPE_FAQ.ordinal()){
            call = restClient.getFaq(storeId);        } else  if  (selectedItem == Types.TYPE_SHOPPING_GUIDE.ordinal()) {
            call = restClient.getShoppingGuide(storeId);
        } else  if  (selectedItem == Types.TYPE_SHIPPING_COSTS.ordinal()) {
            //Endpoint returns malformed json
            //call = restClient.getShippingCosts(storeId);
        }  else  if  (selectedItem == Types.TYPE_CONTACT.ordinal()) {
            call = restClient.getContact(storeId);
        }

        if (call != null) {
            Context context = this;
            call.enqueue(new CallbackWithRetry<ResponseGeneralInfo>(this) {

                @Override
                public void onResponse(Call<ResponseGeneralInfo> call, Response<ResponseGeneralInfo> response) {
                    if (response.isSuccessful()) {
                        ResponseGeneralInfo responseGeneralInfo = response.body();
                        contentText.setText(HtmlCompat.fromHtml(responseGeneralInfo.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            contentText.setText(HtmlCompat.fromHtml(shippingCostsContent, HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}
