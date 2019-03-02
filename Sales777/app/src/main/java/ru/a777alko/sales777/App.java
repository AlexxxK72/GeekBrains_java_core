package ru.a777alko.sales777;

import android.app.Application;

import ru.a777alko.sales777.di.AppComponent;
import ru.a777alko.sales777.di.DaggerAppComponent;
import ru.a777alko.sales777.di.modules.AppModule;


public class App extends Application
{
    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static App getInstance()
    {
        return instance;
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}

