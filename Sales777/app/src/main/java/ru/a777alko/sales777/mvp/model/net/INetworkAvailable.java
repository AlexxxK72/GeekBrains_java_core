package ru.a777alko.sales777.mvp.model.net;

public interface INetworkAvailable<T> {
    boolean isNetworkAvailable(T context);
}
