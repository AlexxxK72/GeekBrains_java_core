package ru.a777alko.sales777.mvp.model.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Operator {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("f_Name")
    @Expose
    private String fName;
    @SerializedName("s_Name")
    @Expose
    private String sName;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("password_text")
    @Expose
    private String passwordText;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
