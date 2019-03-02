package ru.a777alko.sales777.mvp.model.image;

public interface IImageLoader<T> {
    String ROOT = "https://777alko.ru/Content/Images/";
    void loadInto(String url, T container);
}
