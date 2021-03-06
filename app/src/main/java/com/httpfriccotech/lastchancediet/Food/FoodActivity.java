package com.httpfriccotech.lastchancediet.Food;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.Blog.BlogActivity;
import com.httpfriccotech.lastchancediet.activity.DashboardNewActivity;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.Recepies.RecepieActivity;
import com.httpfriccotech.lastchancediet.Workout.WorkoutActivity;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer<Object> ,View.OnClickListener{
    RecyclerView recyclerView;
    FoodAdapter myAdapter;
    Context context;
    TextView dDate;
    ArrayList<FoodData> myDatas = new ArrayList<>();
    ArrayList ClassNames = new ArrayList();
    String UserId, UserName;
    String profileId, role, currentDate, profileImage, ClassName;
    Bundle bundle;
    private int mYear, mMonth, mDay, mHour, mMinute;
    FoodDetailResponseModel foodDetailResponseModel;
    private boolean isTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        UserId = SharedPref.getUserId(this);
        UserName = SharedPref.getDisplayName(this);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setupRecycler();

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
        name.setText(SharedPref.getDisplayName(this));
        email.setText(SharedPref.getUserEmail(this));


        getSupportActionBar().setTitle("");
        ((TextView) findViewById(R.id.toolbar_title)).setText("Your Food Diary");
        View content = this.findViewById(android.R.id.content);
        dDate = (TextView) content.findViewById(R.id.selectedDate);
        dDate.setText(currentDate);

        content.findViewById(R.id.selectedDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(FoodActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int nmonth = monthOfYear + 1;
                                currentDate = year + "-" + nmonth + "-" + dayOfMonth;
                                getData();//date select
                                dDate.setText(nmonth + "-" + dayOfMonth + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

            }
        });
        content.findViewById(R.id.btnAddFood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FoodActivity.this, SelectFoodActivity.class);
                //intent.putExtra("foodType", "Snacks");
                startActivityForResult(intent, 1214);
            }
        });
        header.findViewById(R.id.navClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        ((RadioGroup) findViewById(R.id.radio_cardio)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbTraining) {
                    isTraining = true;
                    Toast.makeText(context, SharedPref.getfoodType(context), Toast.LENGTH_SHORT).show();
                    SharedPref.setfoodType(context,"2");
                    Toast.makeText(context, SharedPref.getfoodType(context), Toast.LENGTH_SHORT).show();
                } else {
                    isTraining = false;
                    Toast.makeText(context, SharedPref.getfoodType(context), Toast.LENGTH_SHORT).show();
                    SharedPref.setfoodType(context,"1");
                    Toast.makeText(context, SharedPref.getfoodType(context), Toast.LENGTH_SHORT).show();
                }
                if (foodDetailResponseModel != null)
                    setUpDetailData(foodDetailResponseModel.data.dailyLimit);
            }
        });
        getData();//oncreate
    }

    private void getData() {
         APIClient.startQuery().doGetFoodDetails(SharedPref.getUserId(this), currentDate,System.currentTimeMillis()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .subscribe(FoodActivity.this);
    }

    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void setupRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        myAdapter = new FoodAdapter(context, myDatas, new FoodAdapter.AddFoodListener() {
            @Override
            public void addItem(String foodType) {
                Intent intent = new Intent(FoodActivity.this, SelectFoodActivity.class);
                intent.putExtra("foodType", foodType);
                startActivityForResult(intent, 1214);
            }
        },this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
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
            Intent intent = new Intent(context, DashboardNewActivity.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1214 && resultCode == RESULT_OK) {
            if (data != null) {
                String type = data.getStringExtra("foodType");
                SelectFoodData foodData = (SelectFoodData) data.getSerializableExtra("data");

                APIClient.startQuery().doAddFoodData(SharedPref.getUserId(this),"175000", "is_" + type.toLowerCase(), SharedPref.getfoodType(this), foodData.fat, foodData.protein, foodData.carb, "FOOD-006", foodData.title, foodData.fiber, currentDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(FoodActivity.this);


            }
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull Object data) {
        if (data != null) {
            if (data instanceof AddFoodDataResponse) {
                Toast.makeText(this, ((AddFoodDataResponse) data).getSuccess(), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(context, FoodActivity.class);
//                startActivity(intent);
                getData();//After added data
            } else if (data instanceof FoodDetailResponseModel) {
                foodDetailResponseModel = (FoodDetailResponseModel) data;
                setUpData((FoodDetailResponseModel) data);
            }else {
                getData();
            }
        }
    }

    private void setUpData(FoodDetailResponseModel data) {
        myDatas.clear();
        myDatas.addAll(data.data.breakfast);
        myDatas.addAll(data.data.lunch);
        myDatas.addAll(data.data.dinner);
        myDatas.addAll(data.data.snacks);
        if (myDatas.size() == 0)
            showMessage("No data found");
        myAdapter.updateData(myDatas);
        setUpDetailData(data.data.dailyLimit);
    }

    private void setUpDetailData(List<DailyLimitData> dailyLimit) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.conclusion_container);
        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (DailyLimitData myData : dailyLimit) {
            View view = inflater.inflate(R.layout.row_daily, linearLayout, false);
            View view_divider = view.findViewById(R.id.view_divider);
            TextView txtFoodType = (TextView) view.findViewById(R.id.textFoodType);
            TextView txtProtein = (TextView) view.findViewById(R.id.textProtein);
            TextView txtCarbs = (TextView) view.findViewById(R.id.textCarbs);
            TextView txtFat = (TextView) view.findViewById(R.id.textFat);
            TextView txtFiber = (TextView) view.findViewById(R.id.textFiber);
            ImageView textAddFood = (ImageView) view.findViewById(R.id.textAddFood);
            txtFoodType.setGravity(Gravity.LEFT);
            txtFoodType.setText(myData.FoodType);
            view_divider.setVisibility(View.GONE);
            txtProtein.setText("" + myData.Protein);
            txtCarbs.setText("" + (isTraining ? myData.Carb2 : myData.Carb));
            txtFat.setText("" + myData.Fat);
            txtFiber.setText("" + (isTraining ? myData.Fiber2 : myData.Fiber));
            textAddFood.setTag("" + myData.FoodType);
            linearLayout.addView(view);
            //linearLayout.setBackgroundColor(Color.parseColor("#64CBD8"));
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textAddFood:{
                int pos=(int)v.getTag();
                deleteFoodData(pos);
                break;
            }
        }
    }

    private void deleteFoodData(int pos) {
        APIClient.startQuery().doDeleteFoodItem(myDatas.get(pos).PostId,SharedPref.getUserId(this),currentDate,System.currentTimeMillis()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(FoodActivity.this);
    }
}
