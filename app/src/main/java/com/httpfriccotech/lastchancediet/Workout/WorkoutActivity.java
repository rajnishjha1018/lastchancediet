package com.httpfriccotech.lastchancediet.Workout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.Blog.BlogActivity;
import com.httpfriccotech.lastchancediet.DashboardnewActivity;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.Recepies.RecepieItem;
import com.httpfriccotech.lastchancediet.Workout.MyAdapter;
import com.httpfriccotech.lastchancediet.Recepies.RecepieActivity;
import com.httpfriccotech.lastchancediet.global.GlobalManage;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
        context = this;
        UserId = GlobalManage.getInstance().getUserId();
        UserName = GlobalManage.getInstance().getUserName();
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
        getSupportActionBar().setTitle("");
        ((TextView) findViewById(R.id.toolbar_title)).setText("Workouts");
        header.findViewById(R.id.navClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        getData();

    }
    private void getData() {
        String url = context.getString(R.string.ServiceURL)+"/wp-json/users/v1/getWorkouts";
        Log.i("url", url);
        Ion.with(context)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(result!=null) {
                            JsonArray GetWorkoutResult = result.getAsJsonArray();
                            setData(GetWorkoutResult);
                        }
                    }
                });
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
        gridView=(GridView)findViewById(R.id.gridView);
        gridView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.reciepy_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
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
}
