package ru.a777alko.sales777.ui.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.a777alko.sales777.App;
import ru.a777alko.sales777.R;
import ru.a777alko.sales777.mvp.model.entities.Order;
import ru.a777alko.sales777.mvp.model.entities.OrderItem;
import ru.a777alko.sales777.mvp.presenter.OrderPagePresenter;
import ru.a777alko.sales777.mvp.view.OrderPageView;
import ru.a777alko.sales777.ui.activity.MainActivity;
import ru.a777alko.sales777.ui.adapter.OrderItemListAdapter;

public class OrderPageFragment extends MvpAppCompatFragment implements OrderPageView {
    @InjectPresenter OrderPagePresenter presenter;
    @BindView(R.id.txt_order_date) TextView txtOrderDate;
    @BindView(R.id.txt_client_name) TextView txtClientName;
    @BindView(R.id.txt_order_number) TextView txtOrderNumber;
    @BindView(R.id.txt_client_phone) TextView txtClientPhone;
    @BindView(R.id.txt_order_status) TextView txtOrderStatus;
    @BindView(R.id.txt_order_comment) TextView txtOrderComment;
    @BindView(R.id.txt_order_total_sum) TextView txtOrderTotalSum;
    @BindView(R.id.btn_call) FloatingActionButton btnCallClient;
    @BindView(R.id.btn_accepted) Button btnAccepted;
    @BindView(R.id.btn_shot) Button btnShot;
    @BindView(R.id.btn_rejected) Button btnRejected;
    @BindView(R.id.btn_completed) Button btnCompleted;
    @BindView(R.id.recycler) RecyclerView recycler;
    @BindView(R.id.progress_bar_container) RelativeLayout layoutProgressBar;
    private OrderItemListAdapter listAdapter;
    public static final int PERMISSION_REQUEST_CODE = 1;
    public static final String ORDER_ITEM = "order_item";

    public OrderPageFragment() {
        // Required empty public constructor
    }

    @ProvidePresenter
    public OrderPagePresenter provideOrderPagePresenter() {
        Order order = (Order) getArguments().getSerializable(ORDER_ITEM);
        OrderPagePresenter presenter = new OrderPagePresenter(AndroidSchedulers.mainThread(), order);
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static OrderPageFragment newInstance(Order item) {
        OrderPageFragment fragment = new OrderPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ORDER_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_page, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initLayout(Order order) {
        txtOrderDate.setText(Html.fromHtml(convertDateToString(order.getDateOrder())),
                TextView.BufferType.SPANNABLE);
        txtClientName.setText(order.getName());
        txtOrderNumber.setText(String.format("№%s", order.getId()));
        txtClientPhone.setText(order.getPhone());
        changeStatusOrder(order.getStatus());
        txtOrderComment.setText(order.getComment());
        btnAccepted.setOnClickListener(btnChangeStatusListener);
        btnRejected.setOnClickListener(btnChangeStatusListener);
        btnShot.setOnClickListener(btnChangeStatusListener);
        btnCompleted.setOnClickListener(btnChangeStatusListener);
        btnCallClient.setOnClickListener(v ->
        {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.CALL_PHONE)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            PERMISSION_REQUEST_CODE);
                }

            } else {
                try {
                    presenter.callClient(String.valueOf(txtClientPhone.getText()));
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    View.OnClickListener btnChangeStatusListener = v -> {
        switch (v.getId()){
            case R.id.btn_accepted:
                presenter.changeStatusOrder(Order.Status.Accepted);
                break;
            case R.id.btn_rejected:
                presenter.changeStatusOrder(Order.Status.Rejected);
                break;
            case R.id.btn_shot:
                presenter.changeStatusOrder(Order.Status.Shot);
                break;
            case R.id.btn_completed:
                presenter.changeStatusOrder(Order.Status.Completed);
                break;
        }

    };

    @Override
    public void setAdapter(List<OrderItem> items) {
        listAdapter = new OrderItemListAdapter(items);
        recycler.setAdapter(listAdapter);
    }

    @Override
    public void setTotalSum(Double orderTotalSum) {
        txtOrderTotalSum.setText(String.format("Общая сумма: %.2f р.", orderTotalSum));
    }

    @Override
    public void changeStatusOrder(String status) {
        txtOrderStatus.setText(getStatusOrderRu(status));
        txtOrderStatus.setBackgroundColor(Color.parseColor(getColor(status)));
    }

    @Override
    public void showMessage(String message) {
        ((MainActivity)getActivity()).showMessage(message);
    }

    @Override
    public void callClient(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+" + phone));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    presenter.callClient(String.valueOf(txtClientPhone.getText()));
                }
            }
        }
    }

    @Override
    public void showProgressBar() {
        layoutProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        layoutProgressBar.setVisibility(View.GONE);
    }

    private String convertDateToString(Date date){
        return "<b>" + DateUtils.formatDateTime(App.getInstance(),
                date.getTime(),
                DateUtils.FORMAT_SHOW_TIME) + " </b>" +
                DateUtils.formatDateTime(App.getInstance(),
                        date.getTime(),
                        DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_NUMERIC_DATE);
    }

    private String getStatusOrderRu(String status){
        for (Order.Status s : Order.Status.values()) {
            if(s.toString().equals(status))
                return s.getRu();
        }
        return "";
    }

    private String getColor(String status) {
        for (Order.Status s : Order.Status.values()) {
            if(s.toString().equals(status))
                return s.getColor();
        }
        return "#ffffff";
    }

}
