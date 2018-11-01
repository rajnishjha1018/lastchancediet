package com.httpfriccotech.lastchancediet.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.base.BaseFragment;
import com.httpfriccotech.lastchancediet.model.GenericRequestModel;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserDashbordFragment extends BaseFragment implements Observer<Object> {
    private View rootView;
    private Intent intent;
    Context context;
    Bundle bundle;
    String UserId, UserName, profileImage, currentDate;
    HorizontalBarChart mChart;
    ProgressDialog progressDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList dataSets = new ArrayList<>();
    ArrayList<BarEntry> entries;
    private String[] labels = {"Fiber", "Fat", "Carb", "Protein"};
    // colors for different sections in pieChart
    public static final int[] MY_COLORS = {Color.rgb(214, 68, 11), Color.rgb(255, 178, 88), Color.rgb(31, 212, 148), Color.rgb(38, 40, 53), Color.rgb(215, 60, 55)};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.fragment_dashbord_user, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        UserId = SharedPref.getUserId(context);
        UserName = SharedPref.getUserName(context);
        final Calendar cd = Calendar.getInstance();
        mYear = cd.get(Calendar.YEAR);
        mMonth = cd.get(Calendar.MONTH);
        mMonth = mMonth + 1;
        mDay = cd.get(Calendar.DAY_OF_MONTH);
        currentDate = mYear + "-" + mMonth + "-" + mDay;

        TextView FullName = (TextView) rootView.findViewById(R.id.UserName);
        FullName.setText("Good Job, " + this.UserName + "!");
        getData();
    }
    private void getData() {

        progressDialog = ProgressDialog.show(getActivity(), "Loading...", "please wait...", false, false);
        getDashData(UserId, currentDate, System.currentTimeMillis() + "");
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
        BarEntry v1e1 = new BarEntry(Float.parseFloat(dailyObject.get("Protein").getAsString()), 3);
        valueSet1.add(v1e1);
        BarEntry v1e2;
        String foodType =SharedPref.getfoodType(context);
        if (foodType.equals("1")){
            v1e2 = new BarEntry(Float.parseFloat(dailyObject.get("Carb").getAsString()), 2);
        }
        else {
            v1e2 = new BarEntry(Float.parseFloat(dailyObject.get("Carb2").getAsString()), 2);
        }
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(Float.parseFloat(dailyObject.get("Fat").getAsString()), 1);
        valueSet1.add(v1e3);
        BarEntry v1e4;
        if (foodType.equals("1")){
            v1e4 = new BarEntry(Float.parseFloat(dailyObject.get("Fiber").getAsString()), 0);
        }
        else {
            v1e4 = new BarEntry(Float.parseFloat(dailyObject.get("Fiber2").getAsString()), 0);
        }
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
        data.setGroupSpace(0.1f);
        data.setValueFormatter(new UserDashbordFragment.MyValueFormatter());
        mChart.setData(data);

        mChart.animateXY(2000, 2000);
        //mChart.getAxisLeft().setInverted(true);
        mChart.setAutoScaleMinMaxEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);

        XAxis x = mChart.getXAxis();
        x.setEnabled(true);
        x.setTextColor(intColor);
        x.setTextSize(12f);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        Legend l = mChart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(55f); // set the space between the legend entries on the x-axis
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
                TextView TodayDesc = (TextView) rootView.findViewById(R.id.TodayDescription);
                TodayDesc.setText("You haven missed everything today!");
                entries = new ArrayList<BarEntry>();
                entries.add(new BarEntry(0, 0));
                entries.add(new BarEntry(0, 1));
                entries.add(new BarEntry(0, 2));
                entries.add(new BarEntry(0, 3));
            } else {

                entries = new ArrayList<BarEntry>();
                entries.add(new BarEntry(Float.valueOf(todayObject.get("Protein").getAsString()).floatValue(), 3));
                entries.add(new BarEntry(Float.valueOf(todayObject.get("Carb").getAsString()).floatValue(), 2));
                entries.add(new BarEntry(Float.valueOf(todayObject.get("Fat").getAsString()).floatValue(), 1));
                entries.add(new BarEntry(Float.valueOf(todayObject.get("Fiber").getAsString()).floatValue(), 0));
                TextView TodayDesc = (TextView) rootView.findViewById(R.id.TodayDescription);
                String str="You have missed";
                if (Float.parseFloat(todayObject.get("Protein").getAsString())==0){
                    str=str+" Protein,";
                }if (Float.parseFloat(todayObject.get("Carb").getAsString())==0){
                    str=str+" Carb,";
                }if (Float.parseFloat(todayObject.get("Fat").getAsString())==0){
                    str=str+" Fat,";
                }if (Float.parseFloat(todayObject.get("Fiber").getAsString())==0){
                    str=str+" Fiber";
                }
                if (str.equalsIgnoreCase("You have missed")) {
                    TodayDesc.setText("You haven’t missed anything today");
                } else {
                    TodayDesc.setText(str);
                }

            }


            mChart = (HorizontalBarChart) rootView.findViewById(R.id.chart);
            mChart.setDescription("");
            mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    if (e == null) return;
                    Toast.makeText(getContext(), labels[e.getXIndex()] + " is " + e.getVal() + "", Toast.LENGTH_SHORT).show();
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
            mFormat = new DecimalFormat("#.##"); // use one decimal if needed
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + ""; // e.g. append a dollar-sign
        }
    }

}
