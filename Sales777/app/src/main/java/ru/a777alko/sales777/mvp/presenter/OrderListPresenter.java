package ru.a777alko.sales777.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.a777alko.sales777.mvp.model.api.ApiService;
import ru.a777alko.sales777.mvp.model.entities.Order;
import ru.a777alko.sales777.mvp.model.entities.Shop;
import ru.a777alko.sales777.mvp.model.repo.OperatorRepo;
import ru.a777alko.sales777.mvp.model.repo.OrderRepo;
import ru.a777alko.sales777.mvp.model.repo.TokenRepo;
import ru.a777alko.sales777.mvp.view.OrderListView;
import ru.a777alko.sales777.navigation.Screens;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class OrderListPresenter extends MvpPresenter<OrderListView> {
    private Scheduler scheduler;
    private List<Order> orders;
    private Shop shop;
    @Inject ApiService apiService;
    @Inject OperatorRepo operatorRepo;
    @Inject OrderRepo orderRepo;
    @Inject TokenRepo tokenRepo;
    @Inject Router router;

    public OrderListPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showProgressBar();
        getShop(operatorRepo.getAuthOperator().getShopId());
        getOrderList();
    }

    @SuppressLint("CheckResult")
    private void getOrderList() {
        orderRepo.getOrders()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            orders = list;
                            if(shop != null){
                                getViewState().hideProgressBar();
                                getViewState().setAdapter(orders);
                            }
                        },
                        error -> {

                        }
                );
    }

    @SuppressLint("CheckResult")
    private void getShop(int shopId){
        apiService.getShop(tokenRepo.getOAthToken(), shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(
                        item -> {
                            shop = item;
                            if(orders != null){
                                getViewState().hideProgressBar();
                                getViewState().setAdapter(orders);
                            }
                        },
                        error -> {

                        }
                );
    }

    public void onItemClick(Order item) {
        router.navigateTo(new Screens.OrderPageScreen(item));
    }
}
