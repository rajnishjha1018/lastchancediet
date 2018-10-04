package com.httpfriccotech.lastchancediet.Exercise;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.Exercise.dialog.AddExerciseFragmentDialog;
import com.httpfriccotech.lastchancediet.Exercise.interfaces.AddExerciseListener;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.global.GlobalManage;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectExerciseActivity extends AppCompatActivity implements Observer<Object>, View.OnClickListener, AddExerciseListener {

    private AddExerciseAdapterForCardio cardioAdapter;
    private AddExerciseAdapterForStrength strenghAdapter;
    private String type;
    private RecyclerView recyclerView;
    private RelativeLayout progressLayout;
    private String UserId;
    private String UserName;
    private ExcerciseMainResponseModel data;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }
        setContentView(R.layout.activity_select_exercise);
        UserId = GlobalManage.getInstance().getUserId();
        UserName = GlobalManage.getInstance().getUserName();
        final Calendar cd = Calendar.getInstance();
        mYear = cd.get(Calendar.YEAR);
        mMonth = cd.get(Calendar.MONTH);
        mMonth = mMonth + 1;
        mDay = cd.get(Calendar.DAY_OF_MONTH);
        currentDate = mYear + "-" + mMonth + "-" + mDay;

        recyclerView = (RecyclerView) findViewById(R.id.search_recycle);
        progressLayout = (RelativeLayout) findViewById(R.id.progressLayout);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Exercise");// + getIntent().getStringExtra("foodType"));
        }
        SearchView simpleSearchView = (SearchView) findViewById(R.id.search_view); // inititate a search view
        simpleSearchView.setIconifiedByDefault(false);
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                getData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                return false;
            }
        });
        getData("");
        showProgress();
        findViewById(R.id.addNewExercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                AddExerciseFragmentDialog addExerciseFragmentDialog = null;
                if (type.equalsIgnoreCase("cardio")) {
                    addExerciseFragmentDialog = AddExerciseFragmentDialog.newInstance("c", "Add New Calories Exercise");
                } else if (type.equalsIgnoreCase("strength")) {
                    addExerciseFragmentDialog = AddExerciseFragmentDialog.newInstance("s", "Add New Strength Exercise");
                } else {
                    return;
                }
                addExerciseFragmentDialog.addExercise(SelectExerciseActivity.this);
                addExerciseFragmentDialog.show(fm, "");

            }
        });
    }

    private void getData(String query) {

        APIClient.startQuery().doGetExercisrSearchList(query, System.currentTimeMillis()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(SelectExerciseActivity.this);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(Object ressponse) {
        hideProgress();
        if (ressponse instanceof ExcerciseMainResponseModel) {
            this.data = (ExcerciseMainResponseModel) ressponse;
            if (data != null) {
                if (data.getSuccess()) {
                    if (type.equalsIgnoreCase("cardio")) {
                        //Add cardio cardioAdapter
                        setCardioAdapter(data);
                    } else if (type.equalsIgnoreCase("strength")) {
                        //add Strength cardioAdapter
                        setStrengthAdapter(data);
                    }
                } else {
                    showToast(data.message);
                }
            }
        } else if (ressponse instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) ressponse;
//            if (jsonObject.get("success")) {
            setResult(Activity.RESULT_OK);
            finish();
//            }
        }
    }

    private void setStrengthAdapter(ExcerciseMainResponseModel data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        strenghAdapter = new AddExerciseAdapterForStrength(this, data.getExcersise().getStrength(), new AddExerciseAdapterForStrength.ExcerciseSelectedListener<SelectExerciseData>() {
            @Override
            public void onExcerciseSelected(SelectExerciseData excercise) {
                addExercise1(excercise, "strength_training");
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(strenghAdapter);
    }

    private void showToast(String data) {
        if (data != null) Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();
    }

    private void setCardioAdapter(ExcerciseMainResponseModel data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cardioAdapter = new AddExerciseAdapterForCardio(this, data.getExcersise().getCardio(), new AddExerciseAdapterForStrength.ExcerciseSelectedListener<SelectExerciseData>() {
            @Override
            public void onExcerciseSelected(SelectExerciseData excercise) {
                addExercise1(excercise, "cardiovascular");
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cardioAdapter);
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
        switch (v.getId()) {
            case R.id.cardio_content:
//                showToast("Clicked on Cardio item");
                int pos = (int) v.getTag();
                addExercise1(data.getExcersise().getCardio().get(pos), "Cardiovascular");
                break;
            case R.id.s_content:
//                showToast("Clicked on Strenght item");

                break;
        }
    }

    private void addExercise1(SelectExerciseData excercise, String type) {
        APIClient.startQuery().doAddExercise(GlobalManage.getInstance().getUserName(), GlobalManage.getInstance().getPassword(),System.currentTimeMillis()+"", excercise.getExerciseID(), excercise.getTitle(), type, excercise.getHowlong(), excercise.getCalories(), excercise.getStrength_training_set(), excercise.getStrength_training_reps_set(), excercise.getStrength_training_weight_set(), currentDate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(SelectExerciseActivity.this);

    }

    private void showProgress() {
        if (progressLayout != null) progressLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        if (progressLayout != null) progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void addExercise(SelectExerciseData excercise, String type) {
        APIClient.startQuery().doAddExercise(GlobalManage.getInstance().getUserName(), GlobalManage.getInstance().getPassword(), System.currentTimeMillis()+"", excercise.getExerciseID(), excercise.getTitle(), type, excercise.getHowlong(), excercise.getCalories(), excercise.getStrength_training_set(), excercise.getStrength_training_reps_set(), excercise.getStrength_training_weight_set(), currentDate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(SelectExerciseActivity.this);
    }
}
