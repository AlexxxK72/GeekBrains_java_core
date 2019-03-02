package ru.a777alko.sales777.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.a777alko.sales777.mvp.model.entities.Order;
import ru.a777alko.sales777.mvp.model.entities.OrderItem;
import ru.a777alko.sales777.mvp.model.message.NetMessage;
import ru.a777alko.sales777.mvp.model.repo.OrderItemRepo;
import ru.a777alko.sales777.mvp.model.repo.OrderRepo;
import ru.a777alko.sales777.mvp.view.OrderPageView;

@InjectViewState
public class OrderPagePresenter extends MvpPresenter<OrderPageView> {
    private Scheduler scheduler;
    private Order order;
    private List<OrderItem> items;
    @Inject OrderItemRepo orderItemRepo;
    @Inject OrderRepo orderRepo;

    public OrderPagePresenter(Scheduler scheduler, Order order) {
        this.scheduler = scheduler;
        this.order = order;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showProgressBar();
        getOrderItems();
    }

    @SuppressLint("CheckResult")
    private void getOrderItems() {
        getViewState().initLayout(order);
        orderItemRepo.getOrder(order.getId())
                .observeOn(scheduler)
                .subscribe(
                        list ->{
                            items = list;
                            getViewState().hideProgressBar();
                            getViewState().setTotalSum(getOrderTotalSum());
                            getViewState().setAdapter(list);
                        }
                );
    }

    public Double getOrderTotalSum() {
        double total = 0d;
        if(items != null){
            for (OrderItem item : items){
                total += item.getCount() * item.getPriceDate();
            }
        }
        return total;
    }

    @SuppressLint("CheckResult")
    public void changeStatusOrder(Order.Status status) {
        order.setStatus(status.toString());
        orderRepo.updateOrder(order)
                .observeOn(scheduler)
                .subscribe(
                        item ->
                                getViewState().changeStatusOrder(status.toString()),
                        error ->
                                getViewState().showMessage(NetMessage.ERROR_UPDATE_ORDER));
    }

    public void callClient(String phone) {
        getViewState().callClient(phone);
    }
}

