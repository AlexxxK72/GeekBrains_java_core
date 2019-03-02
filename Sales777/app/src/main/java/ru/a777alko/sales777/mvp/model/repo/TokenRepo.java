package ru.a777alko.sales777.mvp.model.repo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import ru.a777alko.sales777.App;
import ru.a777alko.sales777.mvp.model.api.ApiService;
import ru.a777alko.sales777.mvp.model.entities.Token;
import ru.a777alko.sales777.mvp.model.message.AuthMessage;
import ru.a777alko.sales777.mvp.model.prefs.PrefsData;

import static android.content.Context.TELEPHONY_SERVICE;

public class TokenRepo {
    private final static String TAG = "TokenRepo";
    private Token _token;
    private String _username;
    private String _password;
    private String _uuid;
    private int repeat;
    private TokenReceivedListener onTokenListener;
    private Scheduler mainThreadScheduler;
    private ApiService apiService;
    private PrefsData prefs;

    public TokenRepo(ApiService apiService, PrefsData prefs) {
        repeat = 0;
        this.apiService = apiService;
        this.prefs = prefs;
        if (ActivityCompat.checkSelfPermission(App.getInstance(),
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            _uuid = getIMEINumber();
        }
    }

    private void init(){
        String phone = prefs.getSharedPreferences(PrefsData.PHONE);
        if (phone != null && _uuid != null) {
            _username = "7" + phone;
            _password = createMd5(String.format("%s:%s", _username, _uuid));
        }
    }

    public void setOnTokenListener(TokenReceivedListener onTokenListener) {
        this.onTokenListener = onTokenListener;
    }

    @SuppressLint("CheckResult")
    public void setOAthToken(Scheduler mainThreadScheduler) {
        init();
        this.mainThreadScheduler = mainThreadScheduler;
        apiService.getToken(_username, _password, "password")
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe(
                        token -> {
                            repeat = 0;
                            _token = token;
                            _token.setExpiresDate(new Date(System.currentTimeMillis() + (_token.getExpiresIn() - 3600) * 1000));
                            if (onTokenListener != null) onTokenListener.onAuth();
                        },
                        error -> {
                            Log.d(TAG, error.getMessage());
                            if (error instanceof HttpException) {
                                if (((HttpException) error).code() != 400) {
                                    if (repeat < 2) {
                                        repeat++;
                                        setOAthToken(mainThreadScheduler);
                                    } else {
                                        if (onTokenListener != null)
                                            onTokenListener.onError(error.getMessage());
                                    }
                                } else {
                                    if (onTokenListener != null)
                                        onTokenListener.onDenied();
                                }
                            } else {
                                if (onTokenListener != null)
                                    onTokenListener.onError(AuthMessage.ERROR_AUTH);
                            }
                        }
                );
    }

    public boolean isValidToken() {
        if (_token == null) return false;
        return (_token.getExpiresDate().getTime() > System.currentTimeMillis());
    }

    public String getOAthToken() {
        if (!isValidToken()) setOAthToken(mainThreadScheduler);
        return _token.toString();
    }

    public interface TokenReceivedListener {
        void onAuth();

        void onDenied();

        void onError(String message);
    }

    @SuppressLint("HardwareIds")
    @RequiresPermission(value = Manifest.permission.READ_PHONE_STATE)
    private String getIMEINumber() {
        String IMEINumber;
        TelephonyManager telephonyMgr = (TelephonyManager) App.getInstance().getSystemService(TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            IMEINumber = telephonyMgr.getImei();
        } else {
            IMEINumber = telephonyMgr.getDeviceId();
        }
        return IMEINumber;
    }

    private static String createMd5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                sb.append(String.format("%02x", array[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return null;
        }
    }

}
