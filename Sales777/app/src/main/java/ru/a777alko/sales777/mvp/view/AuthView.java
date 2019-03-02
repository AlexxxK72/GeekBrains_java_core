package ru.a777alko.sales777.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface AuthView extends MvpView {

    void checkPermission();
    void setPhoneText(String phone);
    void showMessage(String message);
}
