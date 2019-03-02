package ru.a777alko.sales777.mvp.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password_text")
    @Expose
    private String passwordText;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("notify_email")
    @Expose
    private Boolean notifyEmail;
    @SerializedName("notify_sms")
    @Expose
    private Boolean notifySms;
    @SerializedName("date_update")
    @Expose
    private String dateUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(Boolean notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    public Boolean getNotifySms() {
        return notifySms;
    }

    public void setNotifySms(Boolean notifySms) {
        this.notifySms = notifySms;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}

