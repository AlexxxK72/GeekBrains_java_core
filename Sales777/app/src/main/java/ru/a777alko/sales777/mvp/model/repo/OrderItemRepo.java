package ru.a777alko.sales777.mvp.model.repo;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.a777alko.sales777.mvp.model.api.ApiService;
import ru.a777alko.sales777.mvp.model.entities.OrderItem;

public class OrderItemRepo {
    private static final String TAG = "OrderItemRepo";
    private Map<Integer, List<OrderItem>> orders;
    private ApiService apiService;
    private TokenRepo tokenRepo;


    public OrderItemRepo(ApiService apiService, TokenRepo tokenRepo) {
        this.apiService = apiService;
        this.tokenRepo = tokenRepo;
        orders = new HashMap<>();
    }

    @SuppressLint("CheckResult")
    public Single<List<OrderItem>> getOrder(int orderId){
        Single<List<OrderItem>> single;
        if(orders.containsKey(orderId)){
            single = Single.just(orders.get(orderId));
        }else{
            single = apiService
                    .getOrderListItem(tokenRepo.getOAthToken(), orderId)
                    .subscribeOn(Schedulers.io())
                    .map(list -> {
                        orders.put(orderId, list);
                        return list;
                    });
        }
        return single;
    }
}
