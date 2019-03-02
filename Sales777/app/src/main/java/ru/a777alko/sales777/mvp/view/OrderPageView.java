package ru.a777alko.sales777.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.a777alko.sales777.mvp.model.entities.Order;
import ru.a777alko.sales777.mvp.model.entities.OrderItem;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface OrderPageView extends MvpView {
    void showProgressBar();
    void hideProgressBar();
    void initLayout(Order order);
    void setAdapter(List<OrderItem> list);
    void setTotalSum(Double orderTotalSum);
    void changeStatusOrder(String status);
    void showMessage(String s);
    void callClient(String phone);
}
