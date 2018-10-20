package com.httpfriccotech.lastchancediet.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.Exercise.ExcerciseResponseModel;
import com.httpfriccotech.lastchancediet.Exercise.ExerciseAdapter;
import com.httpfriccotech.lastchancediet.Exercise.SelectExerciseActivity;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.base.BaseFragment;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by manish on 27-06-2017.
 */

public class ExcerciseMainFragment extends BaseFragment implements Observer<Object>,View.OnClickListener {
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
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(
                R.layout.fragment_main_exercise, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        UserId = SharedPref.getUserId(getActivity());
        UserName = SharedPref.getUserName(getActivity());
        progressLayout = (RelativeLayout) rootView.findViewById(R.id.progressLayout);
        final Calendar cd = Calendar.getInstance();
        mYear = cd.get(Calendar.YEAR);
        mMonth = cd.get(Calendar.MONTH);
        mMonth = mMonth + 1;
        mDay = cd.get(Calendar.DAY_OF_MONTH);
        currentDate = mYear + "-" + mMonth + "-" + mDay;
        View content = getActivity().findViewById(android.R.id.content);
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
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
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
        addExerciseIB1 = (ImageButton)rootView.findViewById(R.id.ib_add_ex1);
        addExerciseIB1.setOnClickListener(this);
        addExerciseIB2= (ImageButton)rootView.findViewById(R.id.ib_add_ex2);
        addExerciseIB2.setOnClickListener(this);
        titleTV=(TextView)rootView.findViewById(R.id.title);
        caloriesLayout=(LinearLayout)rootView.findViewById(R.id.caloriesLayout);
        strengthLayout=(LinearLayout)rootView.findViewById(R.id.strLayout);
        excerciseResponseModel=new ExcerciseResponseModel();
        getData();
    }
    private void getData() {
        APIClient.startQuery().doGetExcercises(SharedPref.getUserId(getActivity()), currentDate,System.currentTimeMillis()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ExcerciseMainFragment.this);
        showProgress();
    }


    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void setupRecycler(ExcerciseResponseModel model) {
        if (cardioAdapter == null) {
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            cardioAdapter = new ExerciseAdapter(context, model.getCardio(),false,this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(cardioAdapter);
        } else {
            cardioAdapter.updateData(model.getCardio());
        }

        if (strengthAdapter == null) {
            recyclerView1 = (RecyclerView) rootView.findViewById(R.id.recycler1);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            strengthAdapter = new ExerciseAdapter(context, model.getStrength(),true,this);
            recyclerView1.setLayoutManager(linearLayoutManager);
            recyclerView1.setAdapter(strengthAdapter);
        } else {
            strengthAdapter.updateData(model.getStrength());
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

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
                deleteExcercise(excerciseResponseModel.getCardio().get(pos).getExerciseID());
                break;
            } case R.id.ib_delete_str:{
                int pos=(int)v.getTag();
                deleteExcercise(excerciseResponseModel.getStrength().get(pos).getExerciseID());
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

    private void deleteExcercise(String exerciseID) {
        APIClient.startQuery().doDeleteExercisItem(SharedPref.getUserName(getActivity()), SharedPref.getPassword(getActivity()),exerciseID,SharedPref.getUserId(getActivity()), currentDate,System.currentTimeMillis()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
        showProgress();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
