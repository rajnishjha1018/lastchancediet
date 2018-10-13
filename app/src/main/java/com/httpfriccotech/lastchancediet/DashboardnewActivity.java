package com.httpfriccotech.lastchancediet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.Blog.BlogActivity;
import com.httpfriccotech.lastchancediet.Exercise.ExerciseActivity;
import com.httpfriccotech.lastchancediet.Food.FoodActivity;
import com.httpfriccotech.lastchancediet.Recepies.RecepieActivity;
import com.httpfriccotech.lastchancediet.Workout.WorkoutActivity;
import com.httpfriccotech.lastchancediet.model.GenericRequestModel;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.Global;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DashboardnewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,Observer<Object> {
    private Intent intent;
    Context context;
    Bundle bundle;
    String UserId, UserName, profileImage, currentDate;
    HorizontalBarChart mChart;
    ProgressDialog progressDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList dataSets = new ArrayList<>();
    ArrayList<BarEntry> entries;
    private String[] labels = {"Fat", "Carb", "Fiber", "Protein"};
    // colors for different sections in pieChart
    public static final int[] MY_COLORS = {Color.rgb(214, 68, 11), Color.rgb(255, 178, 88), Color.rgb(31, 212, 148), Color.rgb(38, 40, 53), Color.rgb(215, 60, 55)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardnew);
        context = this;
        UserId = SharedPref.getUserId(context);
        UserName = SharedPref.getUserName(context);
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
        name.setText(this.UserName);
        email.setText(SharedPref.getUserEmail(this));

        final Calendar cd = Calendar.getInstance();
        mYear = cd.get(Calendar.YEAR);
        mMonth = cd.get(Calendar.MONTH);
        mMonth = mMonth + 1;
        mDay = cd.get(Calendar.DAY_OF_MONTH);
        currentDate = mYear + "-" + mMonth + "-" + mDay;

        getSupportActionBar().setTitle("");
        ((TextView) findViewById(R.id.toolbar_title)).setText("Dashboard");
        TextView FullName = (TextView) findViewById(R.id.UserName);
        FullName.setText("Good Job, " + this.UserName + "!");
        getData();

        header.findViewById(R.id.navClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        findViewById(R.id.layoutfood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.layoutexercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseActivity.class);
                startActivity(intent);
            }
        });
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
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

        } else if (id == R.id.nav_BLOG) {
            Intent intent = new Intent(context, BlogActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_PROFILE) {

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

    private void getData() {

        progressDialog = ProgressDialog.show(DashboardnewActivity.this, "Loading...", "please wait...", false, false);
        getDashData(UserId, currentDate, System.currentTimeMillis() + "");
        //String url = context.getString(R.string.ServiceURL)+"/lastchance/wp-json/users/v1/UserFoodDetail?userId=" + UserId;
//        Log.i("url", url);
//        Ion.with(context)
//                .load(url)
//                .addHeader("Bearer",SharedPref.getToken(context))
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//
//                });
    }

    private void getDashData(String userId, String currentDate, String s) {
        GenericRequestModel genericRequestModel = new GenericRequestModel();
        genericRequestModel.setUserId(userId);
        genericRequestModel.setCurrentDate(currentDate);
        genericRequestModel.setRefno(s);
        APIClient.startQuery().doGetDashBoard(genericRequestModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

    }

    public void setDataForChart(JsonObject dailyObject) {
        int intColor = Color.BLACK;
//        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(Float.parseFloat(dailyObject.get("Protein").getAsString()), 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(Float.parseFloat(dailyObject.get("Fiber").getAsString()), 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(Float.parseFloat(dailyObject.get("Carb").getAsString()), 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(Float.parseFloat(dailyObject.get("Fat").getAsString()), 3); // Apr
        valueSet1.add(v1e4);
//        valueSet1.add(new BarEntry(Float.parseFloat(dailyObject.get("Protein").getAsString()), 3));
//        valueSet1.add(new BarEntry(Float.parseFloat(dailyObject.get("Fiber").getAsString()), 2));
//        valueSet1.add(new BarEntry(Float.parseFloat(dailyObject.get("Carb").getAsString()), 1));
//        valueSet1.add(new BarEntry(Float.parseFloat(dailyObject.get("Fat").getAsString()), 0));
        BarDataSet barDataSet2 = new BarDataSet(entries, "ACTUAL INTAKE");
        barDataSet2.setColors(MY_COLORS);
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "DAILY GOAL");
        barDataSet1.setColor(Color.rgb(216, 216, 216));


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        BarData data = new BarData(labels, dataSets);
        data.setValueFormatter(new MyValueFormatter());
        mChart.setData(data);
        mChart.animateXY(2000, 2000);
        //mChart.getAxisLeft().setInverted(true);
        mChart.setAutoScaleMinMaxEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);

        XAxis x = mChart.getXAxis();
        x.setEnabled(true);
        x.setTextColor(intColor);
        x.setTextSize(12);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        Legend l = mChart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(15f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(15f); // set t
        //.setAlign(LegendRenderer.LegendAlign.TOP);
        mChart.invalidate();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        if (o instanceof JsonObject) {
            JsonObject result=(JsonObject) o;
            JsonObject dailyObject = result.get("data").getAsJsonObject().get("DailyLimit").getAsJsonObject();
            JsonObject todayObject = result.get("data").getAsJsonObject().get("Today").getAsJsonObject();
            if (result == null || result.size() <= 0) {
                TextView TodayDesc = (TextView) findViewById(R.id.TodayDescription);
                TodayDesc.setText("You haven missed everything today!");
                entries = new ArrayList<BarEntry>();
                entries.add(new BarEntry(0, 0));
                entries.add(new BarEntry(0, 1));
                entries.add(new BarEntry(0, 2));
                entries.add(new BarEntry(0, 3));
            } else {

                entries = new ArrayList<BarEntry>();
                entries.add(new BarEntry(Float.parseFloat(todayObject.get("Protein").getAsString()), 3));
                entries.add(new BarEntry(Float.parseFloat(todayObject.get("Fiber").getAsString()), 2));
                entries.add(new BarEntry(Float.parseFloat(todayObject.get("Carb").getAsString()), 1));
                entries.add(new BarEntry(Float.parseFloat(todayObject.get("Fat").getAsString()), 0));
                TextView TodayDesc = (TextView) findViewById(R.id.TodayDescription);
                String str="You have missed";
                if (Float.parseFloat(todayObject.get("Protein").getAsString())==0){
                    str=str+" Protein,";
                }if (Float.parseFloat(todayObject.get("Fiber").getAsString())==0){
                    str=str+" Fiber,";
                }if (Float.parseFloat(todayObject.get("Carb").getAsString())==0){
                    str=str+" Carb,";
                }if (Float.parseFloat(todayObject.get("Fat").getAsString())==0){
                    str=str+" Fat";
                }
                if (str.equalsIgnoreCase("You have missed")) {
                    TodayDesc.setText("You haven’t missed anything today");
                } else {
                    TodayDesc.setText(str);
                }

            }


            mChart = (HorizontalBarChart) findViewById(R.id.chart);
            mChart.setDescription("");
            mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    if (e == null) return;
                    Toast.makeText(DashboardnewActivity.this, labels[e.getXIndex()] + " is " + e.getVal() + "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected() {
                }
            });
            setDataForChart(dailyObject);
            if (progressDialog!=null)
                progressDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {

        if (progressDialog!=null)
            progressDialog.dismiss();
    }

    @Override
    public void onComplete() {

        if (progressDialog!=null)
            progressDialog.dismiss();
    }

    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0"); // use one decimal if needed
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + ""; // e.g. append a dollar-sign
        }
    }

}
