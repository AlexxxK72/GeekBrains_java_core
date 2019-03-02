package ru.a777alko.sales777.mvp.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.a777alko.sales777.App;
import ru.a777alko.sales777.mvp.model.message.NetMessage;
import ru.a777alko.sales777.mvp.model.net.INetworkAvailable;
import ru.a777alko.sales777.mvp.model.prefs.PrefsData;
import ru.a777alko.sales777.mvp.model.message.AuthMessage;
import ru.a777alko.sales777.mvp.model.repo.OperatorRepo;
import ru.a777alko.sales777.mvp.model.repo.TokenRepo;
import ru.a777alko.sales777.mvp.view.AuthView;
import ru.a777alko.sales777.navigation.Screens;
import ru.a777alko.sales777.net.NetworkAvailable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class AuthPresenter extends MvpPresenter<AuthView>
        implements TokenRepo.TokenReceivedListener, OperatorRepo.OperatorReceivedListener {
    private Scheduler scheduler;
    private INetworkAvailable<Context> net;
    @Inject TokenRepo tokenRepo;
    @Inject OperatorRepo operatorRepo;
    @Inject Router router;
    @Inject PrefsData prefs;

    public AuthPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
        net = new NetworkAvailable();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
       // router.replaceScreen(new Screens.AuthScreen());
        getViewState().checkPermission();
    }

    public void permissionGranted(boolean isPermission) {
        if(isPermission) {
            String phone = prefs.getSharedPreferences(PrefsData.PHONE);
            if(phone != null) getViewState().setPhoneText(phone);
        }
    }

    public void auth(String phone) {
        prefs.saveSharedPreferences(PrefsData.PHONE, phone);
        if (net.isNetworkAvailable(App.getInstance())) {
            tokenRepo.setOnTokenListener(this);
            tokenRepo.setOAthToken(scheduler);
        }else{
            getViewState().showMessage(NetMessage.NOT_AVAILABLE);
        }
    }

    @Override
    public void onAuth() {
        operatorRepo.setOnReceiveListener(this);
        operatorRepo.setOperator();
    }

    @Override
    public void onDenied() {
        getViewState().showMessage(AuthMessage.DENIED_AUTH);
    }

    @Override
    public void onError(String message) {
        getViewState().showMessage(AuthMessage.ERROR_AUTH);
    }

    @Override
    public void onReceive(int shopId) {
        router.replaceScreen(new Screens.ListOrderScreen());
    }

    @Override
    public void onNotReceive() {
        getViewState().showMessage(AuthMessage.ERROR_AUTH);
    }
}
