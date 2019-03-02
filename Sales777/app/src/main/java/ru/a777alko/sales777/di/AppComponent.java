package ru.a777alko.sales777.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.a777alko.sales777.di.modules.ApiModule;
import ru.a777alko.sales777.di.modules.AppModule;
import ru.a777alko.sales777.di.modules.CiceroneModule;
import ru.a777alko.sales777.di.modules.PrefsModule;
import ru.a777alko.sales777.di.modules.RepoModule;
import ru.a777alko.sales777.mvp.presenter.AuthPresenter;
import ru.a777alko.sales777.mvp.presenter.OrderListPresenter;
import ru.a777alko.sales777.mvp.presenter.OrderPagePresenter;
import ru.a777alko.sales777.ui.activity.MainActivity;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        CiceroneModule.class,
        RepoModule.class,
        PrefsModule.class
})
public interface AppComponent {
    void inject(AuthPresenter authPresenter);
    void inject(MainActivity mainActivity);
    void inject(OrderListPresenter orderListPresenter);
    void inject(OrderPagePresenter orderPagePresenter);

}
