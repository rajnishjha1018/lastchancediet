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
import com.httpfriccotech.lastchancediet.fragment.ProgramMainFragment;
import com.httpfriccotech.lastchancediet.util.Global;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.Calendar;

public class DashBordAdminActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    adminDashbord();
                    return true;
                case R.id.navigation_Exercise:
                    ExcerciseMainFragment excerciseMainFragment = new ExcerciseMainFragment();
                    getSupportFragmentManager();
                    replaceFragment(excerciseMainFragment);
                    return true;
                case R.id.navigation_food:
                    FoodMainFragment foodMainFragment = new FoodMainFragment();
                    getSupportFragmentManager();
                    replaceFragment(foodMainFragment);
                    return true;
                case R.id.navigation_yourProgram:
                    ProgramMainFragment programMainFragment = new ProgramMainFragment();
                    getSupportFragmentManager();
                    replaceFragment(programMainFragment);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_bord_admin);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        context =this;
        userId = SharedPref.getUserId(context);
        userName = SharedPref.getUserName(context);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
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

    private void adminDashbord() {
        AdminDashbordFragment adminDashbordFragment = new AdminDashbordFragment();
        getSupportFragmentManager();
        replaceFragment(adminDashbordFragment);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_DASHBOARD) {
            Intent intent = new Intent(context, DashboardNewActivity.class);
            startActivity(intent);

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
        } else if (id == R.id.nav_SCIENCEBEHINDUS) {

        } else if (id == R.id.nav_LOGOUT) {
            SharedPref.setUserId(this,"");
            SharedPref.setToken(this,"");
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
}
