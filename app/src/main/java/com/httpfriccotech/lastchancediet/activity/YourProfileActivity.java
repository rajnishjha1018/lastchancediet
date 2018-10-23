package com.httpfriccotech.lastchancediet.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.base.BaseActivity;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class YourProfileActivity extends BaseActivity implements Observer<Object>, View.OnClickListener {

    private RecyclerView recyclerView;
    private RelativeLayout progressLayout;
    private EditText userNameET, emailEt, firstNameET, lastnameET, weightET, heightET, dobET, genderET, dailyactivitiesET, wheretodoET;
    private String username;
    private String firstname;
    private String lastname;
    private String weight;
    private String height;
    private String dob;
    private String gender;
    private String dailyactivities;
    private String wheretodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        userNameET = (EditText) findViewById(R.id.et_username);
        emailEt = (EditText) findViewById(R.id.et_email);
        firstNameET = (EditText) findViewById(R.id.et_first_name);
        lastnameET = (EditText) findViewById(R.id.et_last_name);
        weightET = (EditText) findViewById(R.id.et_weight);
        heightET = (EditText) findViewById(R.id.et_height_feet);
        dobET = (EditText) findViewById(R.id.et_dob);
        genderET = (EditText) findViewById(R.id.et_gender);
        dailyactivitiesET = (EditText) findViewById(R.id.et_activity);
        wheretodoET = (EditText) findViewById(R.id.et_place);
        progressLayout = (RelativeLayout) findViewById(R.id.progressLayout);
        getData();
        showProgress();
    }

    private void getData() {
        APIClient.startQuery().getUserProfileDetail(SharedPref.getUserId(this)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object data) {
        hideProgress();
        if (data != null && data instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) data;
            if (jsonObject.get("success").getAsBoolean()) {
                if (jsonObject.has("data")) {
                    JsonObject jsonElements = jsonObject.getAsJsonObject("data");
                    if (jsonElements.get("username") != null)
                        username = jsonElements.get("username").getAsString();
                    if (jsonElements.get("firstname") != null)
                        firstname = jsonElements.get("firstname").getAsString();
                    if (jsonElements.get("lastname") != null)
                        lastname = jsonElements.get("lastname").getAsString();
                    if (jsonElements.get("weight") != null)
                        weight = jsonElements.get("weight").getAsString();
                    if (jsonElements.get("height") != null)
                        height = jsonElements.get("height").getAsString();
                    if (jsonElements.get("dob") != null)
                        dob = jsonElements.get("dob").getAsString();
                    if (jsonElements.get("gender") != null)
                        gender = jsonElements.get("gender").getAsString();
                    if (jsonElements.get("dailyactivities") != null)
                        dailyactivities = jsonElements.get("dailyactivities").getAsString();
                    if (jsonElements.get("wheretodo") != null)
                        wheretodo = jsonElements.get("wheretodo").getAsString();
                    setAllData(username, firstname, lastname, weight, height, dob, gender, dailyactivities, wheretodo);
                }
            } else {
                Toast.makeText(getApplicationContext(), jsonObject.get("message").getAsString(), Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * @param username
     * @param firstname
     * @param lastname
     * @param weight
     * @param height
     * @param dob
     * @param gender
     * @param dailyactivities
     * @param wheretodo
     */
    private void setAllData(String username, String firstname, String lastname, String weight, String height, String dob, String gender, String dailyactivities, String wheretodo) {
        if (!TextUtils.isEmpty(username) && userNameET != null) {
            userNameET.setText(username);
        }
        if (!TextUtils.isEmpty(firstname) && firstname != null) {
            firstNameET.setText(firstname);
        }
        if (!TextUtils.isEmpty(lastname) && lastnameET != null) {
            lastnameET.setText(lastname);
        }
        if (!TextUtils.isEmpty(weight) && weightET != null) {
            weightET.setText(weight);
        }
        if (!TextUtils.isEmpty(height) && heightET != null) {
            heightET.setText(height);
        }
        if (!TextUtils.isEmpty(dob) && dobET != null) {
            dobET.setText(dob);
        }
        if (!TextUtils.isEmpty(gender) && genderET != null) {
            genderET.setText(gender);
        }
        if (!TextUtils.isEmpty(dailyactivities) && dailyactivitiesET != null) {
            dailyactivitiesET.setText(dailyactivities);
        }
        if (!TextUtils.isEmpty(wheretodo) && wheretodoET != null) {
            wheretodoET.setText(wheretodo);
        }
    }

    @Override
    public void onError(Throwable e) {
        hideProgress();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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

    private void showProgress() {
        if (progressLayout != null) progressLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        if (progressLayout != null) progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

    }
}
