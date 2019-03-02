package ru.a777alko.sales777.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.a777alko.sales777.mvp.model.api.ApiService;

@Module
public class ApiModule {

    @Provides
    public ApiService apiService(Gson gson, @Named("baseUriDev") String baseUri) {
        return new Retrofit.Builder()
                .baseUrl(baseUri)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService.class);
    }

    @Provides
    public Gson gson(){
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Named("baseUriDev")
    @Provides
    public String baseUriDev(){
        return "https://777alko.ru/";
    }

    @Named("baseUriProd")
    @Provides
    public String baseUriProd(){
        return "https://3semerki.ru/";
    }
}
