package com.httpfriccotech.lastchancediet.Workout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.global.GlobalManage;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WorkoutActivity extends AppCompatActivity
        implements Observer<Object> {
    GridView gridView;
    TextView textView;
    MyAdapter myAdapter;
    Context context;
    String UserId, UserName,profileImage;
    ArrayList ClassNames = new ArrayList();
    Bundle bundle;
    ArrayList<WorkoutItem> workoutList = new ArrayList<WorkoutItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        context = this;
        UserId = GlobalManage.getInstance().getUserId();
        UserName = GlobalManage.getInstance().getUserName();
        ((TextView) findViewById(R.id.toolbar_title)).setText("Workouts");

        getData();

    }
    private void getData() {
        APIClient.startQuery().doGetWorkoutList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

//        String url = context.getString(R.string.ServiceURL)+"/wp-json/users/v1/getWorkouts";
//        Log.i("url", url);
//        Ion.with(context)
//                .load(url)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        if(result!=null) {
//                            JsonArray GetWorkoutResult = result.getAsJsonArray();
//                            setData(GetWorkoutResult);
//                        }
//                    }
//                });
    }

    private void setData(JsonArray GetGetRecipesResult) {
        int size = GetGetRecipesResult.size();

        if (size <= 0) {
            showMessage("No data found");
            workoutList.clear();
            myAdapter.notifyDataSetChanged();
            return;
        }
        for (int i = 0; i < size; i++) {
            JsonObject jsonObject = GetGetRecipesResult.get(i).getAsJsonObject();
            String WorkoutName=jsonObject.get("title").getAsString();
            String WorkoutDescription=jsonObject.get("content").getAsString();
            String WorkoutImage=jsonObject.get("url").getAsString();
            Integer WorkoutPostId=Integer.parseInt(jsonObject.get("postId").getAsString());

            WorkoutItem recepieItem = new WorkoutItem(WorkoutName, WorkoutDescription, WorkoutImage, WorkoutPostId);
            if (!workoutList.contains(recepieItem)) {
                workoutList.add(recepieItem);
            }
        }
        myAdapter = new MyAdapter(WorkoutActivity.this, workoutList);
        RecyclerView recycle=((RecyclerView)findViewById(R.id.recycleview));
        recycle.setLayoutManager(new GridLayoutManager(this,2));
        recycle.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
            super.onBackPressed();
//        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        if (o instanceof JsonArray) {
            JsonArray result =(JsonArray)o;
            JsonArray GetRecipesResult = result.getAsJsonArray();
            setData(GetRecipesResult);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
