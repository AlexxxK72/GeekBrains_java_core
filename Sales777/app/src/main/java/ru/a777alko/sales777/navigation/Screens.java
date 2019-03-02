package ru.a777alko.sales777.navigation;

import android.support.v4.app.Fragment;

import ru.a777alko.sales777.mvp.model.entities.Order;
import ru.a777alko.sales777.ui.fragment.AuthFragment;
import ru.a777alko.sales777.ui.fragment.OrderListFragment;
import ru.a777alko.sales777.ui.fragment.OrderPageFragment;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class AuthScreen extends SupportAppScreen {

        public AuthScreen() {
        }

        @Override
        public Fragment getFragment() {
            return AuthFragment.newInstance();
        }
    }

    public static class ListOrderScreen extends SupportAppScreen {

        public ListOrderScreen() {
        }

        @Override
        public Fragment getFragment() {
            return OrderListFragment.newInstance();
        }
    }

    public static class OrderPageScreen extends SupportAppScreen {
        private Order order;

        public OrderPageScreen(Order order) {
            this.order = order;
        }

        @Override
        public Fragment getFragment() {
            return OrderPageFragment.newInstance(order);
        }
    }
}
