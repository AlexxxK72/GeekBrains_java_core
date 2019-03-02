package ru.a777alko.sales777.mvp.model.prefs;

import android.content.SharedPreferences;
import android.support.annotation.StringDef;


import ru.a777alko.sales777.App;

import static android.content.Context.MODE_PRIVATE;


public class PrefsData implements PrefsHelper {

    public static final String CURRENT_SHOP = "current_shop_id";
    public static final String PHONE = "operator_phone";

    private SharedPreferences sharedPreferences;

    public PrefsData() {
        sharedPreferences = App.getInstance().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    @Override
    public String getSharedPreferences(@Key String keyPref) {
        return sharedPreferences.getString(keyPref, null);
    }

    @Override
    public void saveSharedPreferences(@Key String keyPref, String value) {
        sharedPreferences.edit().putString(keyPref, value).apply();
    }

    @Override
    public void deleteSharedPreferences(@Key String keyPref) {
        sharedPreferences.edit().remove(keyPref).apply();
    }

    @StringDef({CURRENT_SHOP, PHONE})
    @interface Key {
    }

}
