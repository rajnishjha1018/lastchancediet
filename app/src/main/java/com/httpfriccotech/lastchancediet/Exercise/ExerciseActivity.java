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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.Blog.BlogActivity;
import com.httpfriccotech.lastchancediet.DashboardnewActivity;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.Recepies.RecepieActivity;
import com.httpfriccotech.lastchancediet.Workout.WorkoutActivity;
import com.httpfriccotech.lastchancediet.global.GlobalManage;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExerciseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer<Object>,View.OnClickListener {
    RecyclerView recyclerView;
    ExerciseAdapter cardioAdapter;
    ExerciseAdapter strengthAdapter;
    Context context;
    TextView dDate;

    String UserId, UserName;
    String currentDate;
    Bundle bundle;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private final int CARDIO_REQ=1001;
    private final int STRENGTH_REQ=100;
    private RelativeLayout progressLayout;
    private ImageButton addExerciseIB1;
    private ImageButton addExerciseIB2;
    private TextView titleTV;
    private LinearLayout caloriesLayout;
    private LinearLayout strengthLayout;
    private RecyclerView recyclerView1;
    private ExcerciseResponseModel excerciseResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        UserId = GlobalManage.getInstance().getUserId();
        UserName = GlobalManage.getInstance().getUserName();
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        progressLayout = (RelativeLayout) findViewById(R.id.progressLayout);

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
        addExerciseIB1 = (ImageButton)findViewById(R.id.ib_add_ex1);
        addExerciseIB1.setOnClickListener(this);
        addExerciseIB2= (ImageButton)findViewById(R.id.ib_add_ex2);
        addExerciseIB2.setOnClickListener(this);
        titleTV=(TextView)findViewById(R.id.title);
        caloriesLayout=(LinearLayout)findViewById(R.id.caloriesLayout);
        strengthLayout=(LinearLayout)findViewById(R.id.strLayout);

//        content.findViewById(R.id.btnAddExercise).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context,"Add Exercise for btnAddExercise",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        content.findViewById(R.id.textAddExercise).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               /* Intent intent = new Intent(context, SelectExerciseActivity.class);
//                context.startActivity(intent);*/
//                Toast.makeText(context,"Add Exercise for textAddExercise",Toast.LENGTH_SHORT).show();
//            }
//        });
        excerciseResponseModel=new ExcerciseResponseModel();
        getData();
    }

    private void getData() {
        APIClient.startQuery().doGetExcercises("122", currentDate,System.currentTimeMillis()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ExerciseActivity.this);
        showProgress();
    }


    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void setupRecycler(ExcerciseResponseModel model) {
        if (cardioAdapter == null) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            cardioAdapter = new ExerciseAdapter(context, model.getCardio(),false,this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(cardioAdapter);
        } else {
            cardioAdapter.updateData(model.getCardio());
        }

        if (strengthAdapter == null) {
            recyclerView1 = (RecyclerView) findViewById(R.id.recycler1);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            strengthAdapter = new ExerciseAdapter(context, model.getStrength(),true,this);
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
    public void onNext(Object response) {
        if (response instanceof ExcerciseResponseModel) {
            this.excerciseResponseModel = (ExcerciseResponseModel) response;
            setupRecycler(this.excerciseResponseModel);
            hideProgress();
        }else if (response instanceof JsonObject){
            getData();
            showProgress();
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        hideProgress();
    }

    @Override
    public void onComplete() {
        hideProgress();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_delete_cal:{
                int pos=(int)v.getTag();
               deleExcercise(excerciseResponseModel.getCardio().get(pos).getExerciseID());
                break;
            } case R.id.ib_delete_str:{
                int pos=(int)v.getTag();
               deleExcercise(excerciseResponseModel.getStrength().get(pos).getExerciseID());
                break;
            }
            case R.id.ib_add_ex1:{
                Intent intent = new Intent(context, SelectExerciseActivity.class);
                intent.putExtra("type","cardio");
                startActivityForResult(intent,CARDIO_REQ);
                break;
            }
            case R.id.ib_add_ex2:{
                Intent intent = new Intent(context, SelectExerciseActivity.class);
                intent.putExtra("type","strength");
                startActivityForResult(intent,STRENGTH_REQ);
                break;
            }
        }
    }

    private void deleExcercise(String exerciseID) {
        APIClient.startQuery().doDeleteExercisItem(UserName,GlobalManage.getInstance().getPassword(),exerciseID,UserId, currentDate,System.currentTimeMillis()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
        showProgress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CARDIO_REQ)
            getData();
        else if (requestCode==STRENGTH_REQ)
            getData();

    }
    private void showProgress() {
        if (progressLayout != null) progressLayout.setVisibility(View.VISIBLE);
        if (caloriesLayout!=null)caloriesLayout.setVisibility(View.GONE);
        if (strengthLayout!=null)strengthLayout.setVisibility(View.GONE);
        if (recyclerView!=null)recyclerView.setVisibility(View.GONE);
        if (recyclerView1!=null)recyclerView1.setVisibility(View.GONE);

    }

    private void hideProgress() {
        if (progressLayout != null) progressLayout.setVisibility(View.GONE);
        if (caloriesLayout!=null)caloriesLayout.setVisibility(View.VISIBLE);
        if (strengthLayout!=null)strengthLayout.setVisibility(View.VISIBLE);
        if (recyclerView!=null)recyclerView.setVisibility(View.VISIBLE);
        if (recyclerView1!=null)recyclerView1.setVisibility(View.VISIBLE);

    }
}
