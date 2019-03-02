package ru.a777alko.sales777.mvp.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("price_date")
    @Expose
    private Double priceDate;
    @SerializedName("product")
    @Expose
    private Product product;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Double priceDate) {
        this.priceDate = priceDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
