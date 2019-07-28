package com.happybaby.kcs.presenters;

import com.happybaby.kcs.activities.GeneralInfoActivity;
import com.happybaby.kcs.activities.interfaces.GeneralInfoView;
import com.happybaby.kcs.restapi.gooco.CallbackWithRetry;
import com.happybaby.kcs.restapi.gooco.responses.ResponseGeneralInfo;

import retrofit2.Call;
import retrofit2.Response;

public class GeneralInfoPresenter extends BasePresenter {

    public static String shippingCostsContent = "<html><header><title>Shipping Costs</title></header><body><h1 ALIGN=\"center\" STYLE=\"font:36pt/40pt courier;\">Hello world!</h1></body></html>";
    private GeneralInfoView generalInfoView;

    public GeneralInfoPresenter(GeneralInfoView generalInfoView) {
        super();
        this.generalInfoView = generalInfoView;
    }

    public void loadContent(String storeId, int selectedItem){
        Call<ResponseGeneralInfo> call = null;
        if (selectedItem == GeneralInfoActivity.Types.TYPE_FAQ.ordinal()){
            call = restClient.getFaq(storeId);        } else  if  (selectedItem == GeneralInfoActivity.Types.TYPE_SHOPPING_GUIDE.ordinal()) {
            call = restClient.getShoppingGuide(storeId);
        } else  if  (selectedItem == GeneralInfoActivity.Types.TYPE_SHIPPING_COSTS.ordinal()) {
            //Endpoint returns malformed json
            //call = restClient.getShippingCosts(storeId);
        }  else  if  (selectedItem == GeneralInfoActivity.Types.TYPE_CONTACT.ordinal()) {
            call = restClient.getContact(storeId);
        }

        if (call != null) {
            call.enqueue(new CallbackWithRetry<ResponseGeneralInfo>(generalInfoView.getContext()) {

                @Override
                public void onResponse(Call<ResponseGeneralInfo> call, Response<ResponseGeneralInfo> response) {
                    if (response.isSuccessful()) {
                        ResponseGeneralInfo responseGeneralInfo = response.body();
                        if (generalInfoView != null) {
                            generalInfoView.loadInfoFinished(responseGeneralInfo.getContent());
                        }
                    } else {
                        if (generalInfoView != null) {
                            generalInfoView.loadInfoFail();
                        }
                    }
                }
            });
        } else {
            generalInfoView.loadInfoFinished(shippingCostsContent);
        }

    }

    public void unbindView(){
        this.generalInfoView = null;
    }

}
