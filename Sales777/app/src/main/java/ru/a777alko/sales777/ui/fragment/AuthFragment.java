package ru.a777alko.sales777.ui.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.a777alko.sales777.App;
import ru.a777alko.sales777.R;
import ru.a777alko.sales777.mvp.presenter.AuthPresenter;
import ru.a777alko.sales777.mvp.view.AuthView;
import ru.a777alko.sales777.ui.activity.MainActivity;

public class AuthFragment extends MvpAppCompatFragment implements AuthView {
    @InjectPresenter AuthPresenter presenter;

    public static final int PERMISSION_REQUEST_CODE = 1;
    @BindView(R.id.layout_txt_phone) TextInputLayout layouotTxtPhone;
    @BindView(R.id.txt_phone) TextInputEditText txtPhone;
    @BindView(R.id.btn_auth) Button btnAuth;

    public AuthFragment() {

    }

    public static AuthFragment newInstance() {
        AuthFragment fragment = new AuthFragment();
        return fragment;
    }

    @ProvidePresenter
    public AuthPresenter provideMainPresenter() {
        AuthPresenter presenter = new  AuthPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        ButterKnife.bind(this, view);
        initLayout();
        return view;
    }

    private void initLayout(){
        txtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 10){
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txtPhone.getWindowToken(), 0);
                }
            }
        });
        btnAuth.setOnClickListener(v -> {
            if(txtPhone.getText().length() == 10) presenter.auth(String.valueOf(txtPhone.getText()));
            else layouotTxtPhone.setError(getString(R.string.bad_phone_format));
        });
    }
    @Override
    public void setPhoneText(String phone) {
        txtPhone.setText(phone);
    }

    @Override
    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_PHONE_STATE)) {

                } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSION_REQUEST_CODE);
            }
        } else {
            presenter.permissionGranted(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.permissionGranted(true);
                } else {
                    presenter.permissionGranted(false);
                }
            }
        }
    }

    @Override
    public void showMessage(String message) {
        ((MainActivity)getActivity()).showMessage(message);
    }
}
