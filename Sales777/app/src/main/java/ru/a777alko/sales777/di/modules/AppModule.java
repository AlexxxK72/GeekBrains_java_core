package ru.a777alko.sales777.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.a777alko.sales777.App;

@Module
public class AppModule {
    private App app;

    public AppModule(App app){
        this.app = app;
    }

    @Provides
    public App app(){
        return app;
    }
}
