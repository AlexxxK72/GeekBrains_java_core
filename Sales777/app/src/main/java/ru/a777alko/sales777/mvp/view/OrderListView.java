package ru.a777alko.sales777.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.a777alko.sales777.mvp.model.entities.Order;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface OrderListView extends MvpView {
    void showProgressBar();
    void hideProgressBar();
    void setAdapter(List<Order> orders);
}
