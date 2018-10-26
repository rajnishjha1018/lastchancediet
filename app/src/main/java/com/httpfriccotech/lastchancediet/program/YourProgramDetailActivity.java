package com.httpfriccotech.lastchancediet.program;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class YourProgramDetailActivity extends AppCompatActivity implements Observer<Object> {


    private RelativeLayout progressLayout;
    private int postId;
    private TextView titleTv;
    private TextView contentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postId=getIntent().getIntExtra("postId",-1);
        if (postId==-1)
            return;
        setContentView(R.layout.activity_your_program_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        titleTv=(TextView)findViewById(R.id.tv_title_detail);
        contentTv=(TextView)findViewById(R.id.tv_content_detail);
        progressLayout=(RelativeLayout)findViewById(R.id.progressLayout);
        getProgramDetail(postId);
    }

    private void getProgramDetail(int blogId) {
        showProgress();
        APIClient.startQuery().getProgramDetail(blogId+"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object data) {
        hideProgress();
        if (data != null) {
            JsonArray jsonElements=(JsonArray)data;
            if (jsonElements.size()>0){
                JsonObject jsonObject=jsonElements.get(0).getAsJsonObject();
                titleTv.setText(jsonObject.get("title").getAsString());
                contentTv.setText(Html.fromHtml(jsonObject.get("content").getAsString()));
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        hideProgress();
    }

    @Override
    public void onComplete() {
    hideProgress();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void showProgress(){
        if (progressLayout!=null)
            progressLayout.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        if (progressLayout!=null)
            progressLayout.setVisibility(View.GONE);
    }
}
