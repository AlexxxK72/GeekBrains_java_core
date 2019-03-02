package ru.a777alko.sales777.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;

import ru.a777alko.sales777.App;
import ru.a777alko.sales777.R;
import ru.a777alko.sales777.navigation.Screens;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends AppCompatActivity {

    @Inject NavigatorHolder navigatorHolder;

    private Navigator navigator = new SupportAppNavigator(this, R.id.fragment_container) { };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getInstance().getAppComponent().inject(this);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (savedInstanceState == null && fragment == null) {
            navigator.applyCommands(new Command[]{new Replace(new Screens.AuthScreen())});
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }


    public void showMessage(String message) {
        Snackbar mSnack = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        View mSnackView = mSnack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mSnackView.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        mSnackView.setLayoutParams(params);
        mSnackView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView txt = mSnackView.findViewById(android.support.design.R.id.snackbar_text);
        txt.setMaxLines(3);
        txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_menu_info, 0, 0, 0);
        txt.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snack_margin_left_icon));
        txt.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        txt.setTextSize(getResources().getDimension(R.dimen.snack_text_size));
        mSnack.show();
    }
}
