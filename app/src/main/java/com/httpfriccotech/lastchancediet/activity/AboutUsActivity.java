package com.httpfriccotech.lastchancediet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AboutUsActivity extends AppCompatActivity implements Observer<Object>,View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void getData() {
        APIClient.startQuery().getContactUs().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onClick(View view) {

    }
}
