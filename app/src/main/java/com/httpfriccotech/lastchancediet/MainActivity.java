package com.httpfriccotech.lastchancediet;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.model.LoginModel;
import com.httpfriccotech.lastchancediet.model.LoginResponseModel;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Observer<Object> {
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        context = this;
        if (SharedPref.getToken(context) != null && !SharedPref.getToken(context).equalsIgnoreCase("")) {
            launchDashBoard();
            finish();
            return;
        }
        initView();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if (saveLogin == true) {
            editTextUser.setText(loginPreferences.getString("username", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
        }

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

        progressDialog = ProgressDialog.show(MainActivity.this, "Loading...", "please wait...", false, false);
        doLogin(user, password);

    }

    private void doLogin(String user, String password) {
        LoginModel jsonObject = new LoginModel();
        jsonObject.setPassword(password);
        jsonObject.setUsername(user);
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
                    SharedPref.setToken(context, "Bearer "+token);
                    SharedPref.setUserEmail(context, email);
                    SharedPref.setNickName(context, nickName);
                    SharedPref.setDisplayName(context, displayName);
                    launchDashBoard();
                } else {
                    textViewInvalid.setVisibility(View.VISIBLE);
                    textViewInvalid.setText("Authentication fail ...");
                }
            }
            if (progressDialog != null) progressDialog.dismiss();
        }
    }

    private void launchDashBoard() {
        Intent intent = new Intent(context, DashboardnewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onError(Throwable e) {
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    public void onComplete() {
        if (progressDialog != null) progressDialog.dismiss();
    }
}
