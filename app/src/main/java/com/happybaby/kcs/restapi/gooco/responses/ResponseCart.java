package com.happybaby.kcs.restapi.gooco.responses;

import java.util.List;

public class ResponseCart {

    private String currency;
    private Integer subtotal;
    private Integer discount;
    private Integer shippingRate;
    private Integer total;

    private List<ResponseProductCart> items;
}
