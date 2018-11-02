package com.httpfriccotech.lastchancediet.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.BuildConfig;
import com.httpfriccotech.lastchancediet.NetworkStattus;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.model.LoginModel;
import com.httpfriccotech.lastchancediet.model.LoginResponseModel;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.network.CommunicationConstants;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Observer<Object> ,View.OnClickListener{
    EditText editTextUser, editTextPassword;
    Spinner spinnerRole;
    String role;
    Context context;
    Bundle bundle;
    private Boolean saveLogin;
    TextView textViewInvalid;
    LinearLayout LinearViewInvalid;
    private SharedPreferences loginPreferences;
    ProgressDialog progressDialog;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SharedPreferences.Editor loginPrefsEditor;
    private TextView txtRegId, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().hide();
        context = this;
        if ((SharedPref.getToken(context) != null && !SharedPref.getToken(context).equalsIgnoreCase("")) && !TextUtils.isEmpty(SharedPref.getUserId(this))) {
            launchAdminDashbord();
            return;
        }
        initView();

    }

    private void launchAdminDashbord() {
        Intent intent = new Intent(context, MainBaseActivity.class);
        startActivity(intent);
        finish();
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

    private void initView() {
        textViewInvalid = (TextView) findViewById(R.id.textViewInvalid);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        LinearViewInvalid = (LinearLayout) findViewById(R.id.LinearViewInvalid);
        editTextUser = (EditText) findViewById(R.id.et_username);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
//        if (saveLogin == true) {
//            editTextUser.setText(loginPreferences.getString("username", ""));
//            editTextPassword.setText(loginPreferences.getString("password", ""));
//        }

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPassword.length() <= 0 || editTextUser.length() <= 0) {
                    Toast.makeText(MainActivity.this, "input field", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", editTextUser.getText().toString());
                loginPrefsEditor.putString("password", editTextPassword.getText().toString());
                loginPrefsEditor.commit();
                login(editTextUser.getText().toString(), editTextPassword.getText().toString());
            }
        });
        findViewById(R.id.ForgotPass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ForgotActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login(String user, final String password) {
        if (!NetworkStattus.getInstance(this).isOnline()) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Closing the App").setMessage("No Internet Connection,check your network settings").setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        }
        showProgress();
        doLogin(user, password);
    }

    private void showProgress() {
        progressDialog = ProgressDialog.show(MainActivity.this, "Loading...", "please wait...", false, false);
    }

    private void doLogin(String user, String password) {
        LoginModel jsonObject = new LoginModel();
        jsonObject.setPassword(password);
        jsonObject.setUsername(user);
        SharedPref.setPassword(this, password);
        SharedPref.setUserName(this, user);
        APIClient.startQuery().goLogin(jsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        if (o instanceof LoginResponseModel) {
            LoginResponseModel result = (LoginResponseModel) o;
            Bundle bundle = new Bundle();
            if (result == null) {
                textViewInvalid.setVisibility(View.VISIBLE);
                textViewInvalid.setText("Something went wrong. Please try again!");
            } else {
                String email = result.getUser_email();
                String nickName = result.getUser_nicename();
                String displayName = result.getUser_display_name();
                String token = result.getToken();
                if (!(token.equalsIgnoreCase(""))) {
                    SharedPref.setToken(context, "Bearer " + token);
                    if (BuildConfig.DEBUG) Log.d("token",token);
                    SharedPref.setUserEmail(context, email);
                    SharedPref.setUserName(context, nickName);
                    SharedPref.setDisplayName(context, displayName);
                } else {
                    textViewInvalid.setVisibility(View.VISIBLE);
                    textViewInvalid.setText("Invalid username or password");
                }
                goGetLoginCallBack();
            }

        }else if (o instanceof JsonObject){
            hideProgress();
            // JsonArray jsonArray =(JsonArray)o;
            JsonObject result=(JsonObject) o;
            if(result.get("success").getAsBoolean()){
                JsonArray UserArray = result.get("data").getAsJsonArray();
                JsonObject jsonObject= UserArray.get(0).getAsJsonObject();

                if (jsonObject!=null && jsonObject.size()>0) {
                    String userId= jsonObject.get("userId").getAsString();
                    String userType=jsonObject.get("userType").getAsString();
                    String payStatus=jsonObject.get("payStatus").getAsString();
                    String foodType=jsonObject.get("foodTypeVal").getAsString();
                    if (TextUtils.isEmpty(userId)) {
                        textViewInvalid.setVisibility(View.VISIBLE);
                        textViewInvalid.setText("Invalid username or password");
                        return;
                    }

                    SharedPref.setPayStatus(this,payStatus);
                    if (!payStatus.equalsIgnoreCase("Success")){
                        openSignup();
                        return;
                    }
                    SharedPref.setUserId(this,userId);
                    SharedPref.setfoodType(this,foodType);
                    SharedPref.setUserType(this,userType);
                    if (!TextUtils.isEmpty(userType))
                        launchAdminDashbord();

                }else{
                    textViewInvalid.setVisibility(View.VISIBLE);
                    textViewInvalid.setText("Invalid username or password");
                }
            }
            else {
                textViewInvalid.setVisibility(View.VISIBLE);
                textViewInvalid.setText("Invalid username or password");
            }
        }
    }


    private void goGetLoginCallBack() {
        final Calendar cd = Calendar.getInstance();
        int mYear = cd.get(Calendar.YEAR);
        int mMonth = cd.get(Calendar.MONTH);
        mMonth = mMonth + 1;
        int mDay = cd.get(Calendar.DAY_OF_MONTH);
        String currentDate = mYear + "-" + mMonth + "-" + mDay;
        LoginModel jsonObject = new LoginModel();
        jsonObject.setPassword(SharedPref.getPassword(this));
        jsonObject.setUsername(SharedPref.getUserName(this));
        APIClient.startQuery().doLoginCallBack(SharedPref.getUserName(this),SharedPref.getPassword(this),currentDate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

    }

    private void launchDashBoard() {
        Intent intent = new Intent(context, DashboardNewActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(Throwable e) {
        hideProgress();
        textViewInvalid.setVisibility(View.VISIBLE);
        textViewInvalid.setText("Invalid username or password");
    }

    @Override
    public void onComplete() {
//        hideProgress();
    }

    private void hideProgress() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.SignUP){
            openSignup();
        }
    }
    private void openSignup() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(CommunicationConstants.SIGNUP_RL));
        startActivity(browserIntent);
    }
}
