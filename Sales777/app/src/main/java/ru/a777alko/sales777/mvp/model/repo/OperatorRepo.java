package ru.a777alko.sales777.mvp.model.repo;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.a777alko.sales777.mvp.model.api.ApiService;
import ru.a777alko.sales777.mvp.model.entities.Operator;
import ru.a777alko.sales777.mvp.model.prefs.PrefsData;


public class OperatorRepo {
    private final static String TAG = "OperatorRepo";
    //private static OperatorRepo instance;
    private OperatorReceivedListener onReceiveListener;
    private Operator authOperator;
    private ApiService apiService;
    private TokenRepo tokenRepo;
    private PrefsData prefs;

    public OperatorRepo(ApiService apiService, TokenRepo tokenRepo, PrefsData prefs) {
        this.apiService = apiService;
        this.tokenRepo = tokenRepo;
        this.prefs = prefs;
    }

    public void setOnReceiveListener(OperatorReceivedListener onReceiveListener) {
        this.onReceiveListener = onReceiveListener;
    }

    public Operator getAuthOperator() {
        return authOperator;
    }

    @SuppressLint("CheckResult")
    public void setOperator() {
        String phone = "7" + prefs.getSharedPreferences(PrefsData.PHONE);
        apiService.getOperator(tokenRepo.getOAthToken(), phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        operator -> {
                            authOperator = operator;
                            if (onReceiveListener != null) onReceiveListener.onReceive(authOperator.getShopId());
                        },
                        error -> {
                            Log.d(TAG, error.getMessage());
                            if (onReceiveListener != null) onReceiveListener.onNotReceive();
                        }
                );
    }

    public interface OperatorReceivedListener {
        void onReceive(int shopId);

        void onNotReceive();
    }


}
