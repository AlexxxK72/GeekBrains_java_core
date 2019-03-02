package ru.a777alko.sales777.mvp.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;


public class Product implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("vendor_code")
    @Expose
    private Integer vendorCode;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("country_icon")
    @Expose
    private String countryIcon;
    @SerializedName("main_cat_id")
    @Expose
    private Integer mainCatId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("volume")
    @Expose
    private Double volume;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("is_check")
    @Expose
    private Boolean isCheck;
    @SerializedName("ranking")
    @Expose
    private Float ranking;
    @SerializedName("count_ranking")
    @Expose
    private Integer countRanking;
    @SerializedName("pathList")
    @Expose
    private String pathList;
    @SerializedName("pathPage")
    @Expose
    private String pathPage;
    private Integer relevant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(Integer vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryIcon() {
        return countryIcon;
    }

    public void setCountryIcon(String countryIcon) {
        this.countryIcon = countryIcon;
    }

    public Integer getMainCatId() {
        return mainCatId;
    }

    public void setMainCatId(Integer mainCatId) {
        this.mainCatId = mainCatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean isCheck) {
        this.isCheck = isCheck;
    }

    public Float getRanking() {
        return ranking;
    }

    public void setRanking(Float ranking) {
        this.ranking = ranking;
    }

    public Integer getCountRanking() {
        return countRanking;
    }

    public void setCountRanking(Integer countRanking) {
        this.countRanking = countRanking;
    }

    public String getPathList() {
        return pathList;
    }

    public void setPathList(String pathList) {
        this.pathList = pathList;
    }

    public String getPathPage() {
        return pathPage;
    }

    public void setPathPage(String pathPage) {
        this.pathPage = pathPage;
    }

    public int getRelevant() {
        return relevant;
    }

    public void setRelevant(int relevant) {
        this.relevant = relevant;
    }

    @Override
    public String toString() {
        String s = name;
        if (volume != 0d && measure != null) {
            s += String.format(", %.3f", volume);
            s += String.format(" %s", measure);
        }
        return s;
    }

    public static class PriceComp implements Comparator<Product> {
        public int compare(Product firstProduct, Product secondProduct) {
            return Double.compare(firstProduct.price, secondProduct.price);
        }
    }

    public static class PriceDescComp implements Comparator<Product> {
        public int compare(Product firstProduct, Product secondProduct) {
            return Double.compare(secondProduct.price, firstProduct.price);
        }
    }

    public static class RelevantComp implements Comparator<Product> {
        public int compare(Product firstProduct, Product secondProduct) {
            return Double.compare(secondProduct.relevant, firstProduct.relevant);
        }
    }

    public static class ProductComparator implements Comparator<Product> {

        public enum SortOrder {ASCENDING, DESCENDING}

        private SortOrder sortOrder;

        public ProductComparator(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
        }

        @Override
        public int compare(Product product1, Product product2) {
            int compare = Double.compare(product1.getPrice(), product2.price);
            if(compare == 0){
                compare = Integer.compare(product1.id, product2.id);
            }
            if (sortOrder == SortOrder.ASCENDING) {
                return compare;
            } else {
                return compare * (-1);
            }
        }
    }
}
