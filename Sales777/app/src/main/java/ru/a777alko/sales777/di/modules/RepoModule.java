package ru.a777alko.sales777.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import ru.a777alko.sales777.mvp.model.api.ApiService;
import ru.a777alko.sales777.mvp.model.prefs.PrefsData;
import ru.a777alko.sales777.mvp.model.repo.OperatorRepo;
import ru.a777alko.sales777.mvp.model.repo.OrderItemRepo;
import ru.a777alko.sales777.mvp.model.repo.OrderRepo;
import ru.a777alko.sales777.mvp.model.repo.TokenRepo;


@Module
public class RepoModule {

    @Singleton
    @Provides
    public TokenRepo tokenRepo(ApiService apiService, PrefsData prefs){
        return new TokenRepo(apiService, prefs);
    }

    @Singleton
    @Provides
    public OperatorRepo operatorRepo(ApiService apiService, TokenRepo tokenRepo, PrefsData prefs){
        return new OperatorRepo(apiService, tokenRepo, prefs);
    }

    @Singleton
    @Provides
    public OrderItemRepo orderItemRepo(ApiService apiService, TokenRepo tokenRepo){
        return new OrderItemRepo(apiService, tokenRepo);
    }

    @Singleton
    @Provides
    public OrderRepo orderRepo(ApiService apiService, TokenRepo tokenRepo, OperatorRepo operatorRepo){
        return new OrderRepo(apiService, tokenRepo, operatorRepo);
    }
}
