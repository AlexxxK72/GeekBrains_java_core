package ru.a777alko.sales777.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.a777alko.sales777.App;
import ru.a777alko.sales777.R;
import ru.a777alko.sales777.mvp.model.entities.Order;
import ru.a777alko.sales777.mvp.presenter.OrderListPresenter;
import ru.a777alko.sales777.mvp.view.OrderListView;
import ru.a777alko.sales777.ui.adapter.OrderListAdapter;

public class OrderListFragment extends MvpAppCompatFragment implements OrderListView {
    @InjectPresenter OrderListPresenter presenter;
    @BindView(R.id.recycler_order) RecyclerView recycler;
    @BindView(R.id.progress_bar_container) RelativeLayout layoutProgressBar;
    private OrderListAdapter listAdapter;

    public OrderListFragment() {
    }

    @ProvidePresenter
    public OrderListPresenter provideMainPresenter() {
        OrderListPresenter presenter = new OrderListPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static OrderListFragment newInstance() {
        OrderListFragment fragment = new OrderListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setAdapter(List<Order> orders) {
        listAdapter = new OrderListAdapter(orders);
        recycler.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(item -> presenter.onItemClick(item));
    }

    @Override
    public void showProgressBar() {
        layoutProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        layoutProgressBar.setVisibility(View.GONE);
    }
}
