package ru.a777alko.sales777.mvp.model.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.a777alko.sales777.mvp.model.entities.Operator;
import ru.a777alko.sales777.mvp.model.entities.Order;
import ru.a777alko.sales777.mvp.model.entities.OrderItem;
import ru.a777alko.sales777.mvp.model.entities.Shop;
import ru.a777alko.sales777.mvp.model.entities.Token;

public interface ApiService {

    //token
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/token")
    Single<Token> getToken(@Field("username") String username, @Field("password") String hashPassword,
                           @Field("grant_type") String grant_type);

    //category
    @Headers("Accept: application/json")
    @GET("api/User")
    Single<Operator> getOperator(@Header("Authorization") String authorization,
                                 @Query("login") String phone);

    //order
    @Headers("Accept: application/json")
    @GET("api/Order")
    Single<List<Order>> getOrderList(@Header("Authorization") String authorization,
                                     @Query("shopId") Integer shopId);

    @Headers("Accept: application/json")
    @PUT("api/Order/{id}")
    Single<Order> updateOrder(@Header("Authorization") String authorization,
                              @Path("id") Integer id, @Body Order client);

    @Headers("Accept: application/json")
    @GET("api/OrderList/Mobile")
    Single<List<OrderItem>> getOrderListItem(@Header("Authorization") String authorization,
                                             @Query("orderId") Integer orderId);

    //shop
    @Headers("Accept: application/json")
    @GET("api/Shop/{id}")
    Single<Shop> getShop(@Header("Authorization") String authorization,
                         @Path("id") Integer id);

}