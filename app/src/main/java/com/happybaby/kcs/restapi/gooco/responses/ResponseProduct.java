package com.happybaby.kcs.restapi.gooco.responses;

import java.util.List;

public class ResponseProduct {

    private String modelId;
    private String sku;
    private String name;
    private String description;
    private String color;
    private String composition;
    private Integer originalPrice;
    private Integer finalPrice;
    private String finalPriceType;
    private String currency;
    private String url;
    private String care;
    private List<String> images;
    private List<ResponseSize> sizes;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getSku() { return sku; }

    public void setSku(String sku) { this.sku = sku; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getColor() { return color; }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getFinalPriceType() {
        return finalPriceType;
    }

    public void setFinalPriceType(String finalPriceType) {
        this.finalPriceType = finalPriceType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getCare() { return care; }

    public void setCare(String care) {
        this.care = care;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<ResponseSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<ResponseSize> sizes) {
        this.sizes = sizes;
    }
}
