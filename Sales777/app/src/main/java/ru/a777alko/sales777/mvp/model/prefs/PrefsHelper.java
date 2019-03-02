package ru.a777alko.sales777.mvp.model.prefs;

/**
 * Created by shkryaba on 28/07/2018.
 */

public interface PrefsHelper {
    String PREFS_NAME = "name_shared_preferences";

    String getSharedPreferences(String keyPref);

    void saveSharedPreferences(String keyPref, String value);

    void deleteSharedPreferences(String keyPref);
}
