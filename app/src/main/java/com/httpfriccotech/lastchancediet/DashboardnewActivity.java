package com.httpfriccotech.lastchancediet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.Blog.BlogActivity;
import com.httpfriccotech.lastchancediet.Exercise.ExerciseActivity;
import com.httpfriccotech.lastchancediet.Food.FoodActivity;
import com.httpfriccotech.lastchancediet.Recepies.RecepieActivity;
import com.httpfriccotech.lastchancediet.Workout.WorkoutActivity;
import com.httpfriccotech.lastchancediet.global.GlobalManage;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DashboardnewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Intent intent;
    Context context;
    Bundle bundle;
    String UserId, UserName, profileImage,currentDate;
    HorizontalBarChart mChart;
    ProgressDialog progressDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList dataSets = new ArrayList<>();
    ArrayList<BarEntry> entries;
    private String[] labels = {"Fat", "Carb", "Fiber", "Protein"};
    // colors for different sections in pieChart
    public static final int[] MY_COLORS = {
            Color.rgb(214, 68, 11), Color.rgb(255, 178, 88), Color.rgb(31, 212, 148),
            Color.rgb(38, 40, 53), Color.rgb(215, 60, 55)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardnew);
        context = this;
        UserId = GlobalManage.getInstance().getUserId();
        UserName = GlobalManage.getInstance().getUserName();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.currentuser);
        TextView email = (TextView) header.findViewById(R.id.useremail);
        name.setText(this.UserName);
        email.setText("rajnish1018@gmail.com");

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
        }
        else if (id == R.id.nav_PROFILE) {

        } else if (id == R.id.nav_SCIENCEBEHINDUS) {

        } else if (id == R.id.nav_LOGOUT) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getData() {

        progressDialog = ProgressDialog.show(DashboardnewActivity.this, "Loading...", "please wait...", false, false);
        String url = context.getString(R.string.ServiceURL) + "wp-json/users/v1/UserFoodDetail?userId=" + UserId +"&CurrentDate="+currentDate;
        //String url = context.getString(R.string.ServiceURL)+"/lastchance/wp-json/users/v1/UserFoodDetail?userId=" + UserId;
        Log.i("url", url);
        Ion.with(context)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result == null || result.size() > 0 ) {
                            TextView TodayDesc = (TextView) findViewById(R.id.TodayDescription);
                            TodayDesc.setText("You haven missed everything today!");
                            entries = new ArrayList<BarEntry>();
                            entries.add(new BarEntry(0, 0));
                            entries.add(new BarEntry(0, 1));
                            entries.add(new BarEntry(0, 2));
                            entries.add(new BarEntry(0, 3));
                        } else {

                            entries = new ArrayList<BarEntry>();
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            entries.add(new BarEntry(Integer.parseInt(jsonObject.get("Protein").getAsString()), 3));
                            entries.add(new BarEntry(Integer.parseInt(jsonObject.get("Fiber").getAsString()), 2));
                            entries.add(new BarEntry(Integer.parseInt(jsonObject.get("Carb").getAsString()), 1));
                            entries.add(new BarEntry(Integer.parseInt(jsonObject.get("Fat").getAsString()), 0));
                            TextView TodayDesc = (TextView) findViewById(R.id.TodayDescription);
                            if (Boolean.parseBoolean(jsonObject.get("IsMissed").getAsString())) {
                                TodayDesc.setText("You haven missed something today");
                            } else {
                                TodayDesc.setText("You haven’t missed anything today");
                            }
                        }


                        mChart = (HorizontalBarChart) findViewById(R.id.chart);
                        mChart.setDescription("");
                        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                                if (e == null)
                                    return;
                                Toast.makeText(DashboardnewActivity.this, labels[e.getXIndex()] + " is " + e.getVal() + "", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected() {
                            }
                        });
                        setDataForChart();
                        progressDialog.dismiss();
                    }
                });
    }

    public void setDataForChart() {
        int intColor = Color.BLACK;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(75f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(75f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(75f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(75f, 3); // Apr
        valueSet1.add(v1e4);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "DAILY GOAL");
        barDataSet1.setColor(Color.rgb(216, 216, 216));
        BarDataSet barDataSet2 = new BarDataSet(entries, "ACTUAL INTAKE");
        barDataSet2.setColors(MY_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        BarData data = new BarData(labels, dataSets);
        data.setValueFormatter(new MyValueFormatter());
        mChart.setData(data);
        mChart.animateXY(2000, 2000);
        //mChart.getAxisLeft().setInverted(true);
        mChart.setAutoScaleMinMaxEnabled(true);
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
