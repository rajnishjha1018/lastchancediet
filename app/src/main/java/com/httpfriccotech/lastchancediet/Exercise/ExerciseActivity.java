package com.httpfriccotech.lastchancediet.Exercise;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.Blog.BlogActivity;
import com.httpfriccotech.lastchancediet.DashboardnewActivity;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.Recepies.RecepieActivity;
import com.httpfriccotech.lastchancediet.Workout.WorkoutActivity;
import com.httpfriccotech.lastchancediet.global.GlobalManage;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExerciseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer<ExcerciseResponseModel> {
    RecyclerView recyclerView;
    ExerciseAdapter cardioAdapter;
    ExerciseAdapter strengthAdapter;
    Context context;
    TextView dDate;

    String UserId, UserName;
    String currentDate;
    Bundle bundle;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        UserId = GlobalManage.getInstance().getUserId();
        UserName = GlobalManage.getInstance().getUserId();
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final Calendar cd = Calendar.getInstance();
        mYear = cd.get(Calendar.YEAR);
        mMonth = cd.get(Calendar.MONTH);
        mMonth = mMonth + 1;
        mDay = cd.get(Calendar.DAY_OF_MONTH);
        currentDate = mYear + "-" + mMonth + "-" + mDay;


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.currentuser);
        TextView email = (TextView) header.findViewById(R.id.useremail);
        name.setText(this.UserName);
        email.setText("rajnish1018@gmail.com");

        getSupportActionBar().setTitle("");
        ((TextView) findViewById(R.id.toolbar_title)).setText("Your Exercise Diary");



        View content = this.findViewById(android.R.id.content);
        dDate = (TextView) content.findViewById(R.id.selectedDate);
        dDate.setText(currentDate);

        header.findViewById(R.id.navClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        content.findViewById(R.id.selectedDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(ExerciseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int nmonth = monthOfYear + 1;
                                currentDate = year + "-" + nmonth + "-" + dayOfMonth;
                                getData();
                                dDate.setText(nmonth + "-" + dayOfMonth + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

            }
        });

        content.findViewById(R.id.btnAddExercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Add Exercise for btnAddExercise",Toast.LENGTH_SHORT).show();

            }
        });
        content.findViewById(R.id.textAddExercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Add Exercise for textAddExercise",Toast.LENGTH_SHORT).show();
            }
        });

        getData();
    }

    private void getData() {
        APIClient.startQuery().doGetExcercises(UserId, currentDate).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ExerciseActivity.this);
    }


    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void setupRecycler(ExcerciseResponseModel model) {
        if (cardioAdapter == null) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            cardioAdapter = new ExerciseAdapter(context, model.getCardio(),false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(cardioAdapter);
        } else {
            cardioAdapter.updateData(model.getCardio());
        }

        if (strengthAdapter == null) {
            RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler1);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            strengthAdapter = new ExerciseAdapter(context, model.getStrength(),true);
            recyclerView1.setLayoutManager(linearLayoutManager);
            recyclerView1.setAdapter(strengthAdapter);
        } else {
            strengthAdapter.updateData(model.getStrength());
        }
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
        getMenuInflater().inflate(R.menu.food, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_DASHBOARD) {
            Intent intent = new Intent(context, DashboardnewActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_RECIPES) {
            Intent intent = new Intent(context, RecepieActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_WORKOUTS) {
            Intent intent = new Intent(context, WorkoutActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_BLOG) {
            Intent intent = new Intent(context, BlogActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_PROFILE) {

        } else if (id == R.id.nav_SCIENCEBEHINDUS) {

        } else if (id == R.id.nav_LOGOUT) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull ExcerciseResponseModel excerciseResponseModel) {
        setupRecycler(excerciseResponseModel);
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
