package com.httpfriccotech.lastchancediet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.Blog.BlogActivity;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.Recepies.RecepieActivity;
import com.httpfriccotech.lastchancediet.Workout.WorkoutActivity;
import com.httpfriccotech.lastchancediet.base.BaseActivity;
import com.httpfriccotech.lastchancediet.fragment.AdminDashbordFragment;
import com.httpfriccotech.lastchancediet.fragment.ExcerciseMainFragment;
import com.httpfriccotech.lastchancediet.fragment.FoodMainFragment;
import com.httpfriccotech.lastchancediet.fragment.PartnerDashBord;
import com.httpfriccotech.lastchancediet.fragment.ProgramMainFragment;
import com.httpfriccotech.lastchancediet.fragment.UserDashbordFragment;
import com.httpfriccotech.lastchancediet.util.Constants;
import com.httpfriccotech.lastchancediet.util.Global;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.Calendar;
import java.util.Objects;

public class MainBaseActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,DrawerLayout.DrawerListener {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    adminDashbord();
                    //setTitle("Dashboard");
                    return true;
                case R.id.navigation_Exercise:
                    ExcerciseMainFragment excerciseMainFragment = new ExcerciseMainFragment();
                    getSupportFragmentManager();
                    replaceFragment(excerciseMainFragment);
                    setTitle("Your Exercise Diary");
                    return true;
                case R.id.navigation_food:
                    FoodMainFragment foodMainFragment = new FoodMainFragment();
                    getSupportFragmentManager();
                    replaceFragment(foodMainFragment);
                    setTitle("Your Food Diary");
                    return true;
                case R.id.navigation_yourProgram:
                    ProgramMainFragment programMainFragment = new ProgramMainFragment();
                    getSupportFragmentManager();
                    replaceFragment(programMainFragment);
                    setTitle("Your Program");
                    return true;
            }
            return false;
        }
    };
    private String userId;
    private Context context;
    private String userName;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String currentDate;
    private DrawerLayout drawer;
    private TextView toolbarTitle;
    private ActionBarDrawerToggle toggle;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPref.getUserType(this).equalsIgnoreCase(Constants.USER)){
            setContentView(R.layout.activity_dash_bord_user);

        }else if (SharedPref.getUserType(this).equalsIgnoreCase(Constants.ADMIN)){

            setContentView(R.layout.activity_dash_bord_admin);
        }else if(SharedPref.getUserType(this).equalsIgnoreCase(Constants.PARTNER)){
            setContentView(R.layout.activity_dash_bord_admin);
        }else{
            setContentView(R.layout.activity_dash_bord_user);
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        context =this;
        userId = SharedPref.getUserId(context);
        userName = SharedPref.getUserName(context);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=(TextView)findViewById(R.id.title);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.currentuser);
        TextView email = (TextView) header.findViewById(R.id.useremail);
        name.setText(userName);
        email.setText(SharedPref.getUserEmail(this));
        header.findViewById(R.id.navClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        getDate();

        adminDashbord();

    }

    private void getDate() {
        final Calendar cd = Calendar.getInstance();
        mYear = cd.get(Calendar.YEAR);
        mMonth = cd.get(Calendar.MONTH);
        mMonth = mMonth + 1;
        mDay = cd.get(Calendar.DAY_OF_MONTH);
        currentDate = mYear + "-" + mMonth + "-" + mDay;
    }
private void setTitle(String s){
       if (TextUtils.isEmpty(s)){
           if (toolbarTitle!=null)
               toolbarTitle.setText(getString(R.string.app_name));
       }else {
           if (toolbarTitle!=null)
               toolbarTitle.setText(s);
       }
}
    private void adminDashbord() {

        if (SharedPref.getUserType(this).equalsIgnoreCase(Constants.USER)){

            UserDashbordFragment userDashbordFragment = new UserDashbordFragment();
            getSupportFragmentManager();
            replaceFragment(userDashbordFragment);
            setTitle("Dashboard");
            if (bottomNavigationView!=null){
                bottomNavigationView.setVisibility(View.VISIBLE);
            }

        }else if (SharedPref.getUserType(this).equalsIgnoreCase(Constants.ADMIN)){
            AdminDashbordFragment adminDashbordFragment = new AdminDashbordFragment();
            getSupportFragmentManager();
            replaceFragment(adminDashbordFragment);
            setTitle("Admin Dashboard");
            if (bottomNavigationView!=null){
                bottomNavigationView.setVisibility(View.GONE);
            }

        }else if(SharedPref.getUserType(this).equalsIgnoreCase(Constants.PARTNER)){
            PartnerDashBord partnerDashBord = new PartnerDashBord();
            getSupportFragmentManager();
            replaceFragment(partnerDashBord);
            setTitle("Partner Dashboard");
            if (bottomNavigationView!=null){
                bottomNavigationView.setVisibility(View.GONE);
            }
        }
        }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle bottomNavigationView view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_DASHBOARD) {
           adminDashbord();
        } else if (id == R.id.nav_RECIPES) {
            Intent intent = new Intent(context, RecepieActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_WORKOUTS) {
            Intent intent = new Intent(context, WorkoutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_BLOG) {
            Intent intent = new Intent(context, BlogActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_PROFILE) {
            Intent intent = new Intent(context, YourProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_CONTACTUS) {
            Intent intent = new Intent(context, ContactUs.class);
            startActivity(intent);
        }else if (id == R.id.nav_ABOUTUS) {
            Intent intent = new Intent(context, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_SCIENCEBEHINDUS) {

        } else if (id == R.id.nav_LOGOUT) {
            SharedPref.setUserId(this,"");
//            SharedPref.setToken(this,"");
            SharedPref.setPassword(this,"");
            SharedPref.setUserName(this,"");
            reDirectToLogin();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void reDirectToLogin() {
        Toast.makeText(Global.getMyApplicationContext(),"Logout Successfully..",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (toggle!=null)
        toggle.syncState();
        invalidateOptionsMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);  // OPEN DRAWER
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }

    @Override
    public void onDrawerOpened(@NonNull View view) {

    }

    @Override
    public void onDrawerClosed(@NonNull View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {
        if (drawer.isDrawerVisible(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }
}
