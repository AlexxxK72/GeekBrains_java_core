package ru.a777alko.sales777.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.a777alko.sales777.mvp.model.prefs.PrefsData;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class PrefsModule {

    @Provides
    @Singleton
    public PrefsData prefs() {
        return new PrefsData();
    }
}

