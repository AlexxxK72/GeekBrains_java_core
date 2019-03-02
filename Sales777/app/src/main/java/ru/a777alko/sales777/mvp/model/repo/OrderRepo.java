package ru.a777alko.sales777.mvp.model.repo;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.a777alko.sales777.mvp.model.api.ApiService;
import ru.a777alko.sales777.mvp.model.entities.Order;

public class OrderRepo {
    private static Scheduler scheduler;
    private int shopId;
    private List<Order> orders;
    private ApiService apiService;
    private TokenRepo tokenRepo;


    public OrderRepo(ApiService apiService, TokenRepo tokenRepo, OperatorRepo operatorRepo) {
        this.apiService = apiService;
        this.tokenRepo = tokenRepo;
        shopId = operatorRepo.getAuthOperator().getShopId();
    }

    public Single<List<Order>> getOrders(){
        Single<List<Order>> single;
        if(orders != null){
            single = Single.just(orders);
        }else{
            single = apiService
                    .getOrderList(tokenRepo.getOAthToken(), shopId)
                    .subscribeOn(Schedulers.io())
                    .map(list -> {
                        orders = list;
                        return orders;
                    });
        }
        return single;
    }

    public Single<Order> updateOrder(Order order){
        return apiService.updateOrder(tokenRepo.getOAthToken(), order.getId(), order)
                .subscribeOn(Schedulers.io());
    }
}
