package com.httpfriccotech.lastchancediet.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.Food.AddFoodDataResponse;
import com.httpfriccotech.lastchancediet.Food.DailyLimitData;
import com.httpfriccotech.lastchancediet.Food.FoodAdapter;
import com.httpfriccotech.lastchancediet.Food.FoodData;
import com.httpfriccotech.lastchancediet.Food.FoodDetailResponseModel;
import com.httpfriccotech.lastchancediet.Food.SelectFoodActivity;
import com.httpfriccotech.lastchancediet.Food.SelectFoodData;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.base.BaseFragment;
import com.httpfriccotech.lastchancediet.model.UpdateRadio;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by manish on 27-06-2017.
 */

public class FoodMainFragment extends BaseFragment implements Observer<Object>, View.OnClickListener {

    private View rootView;
    RecyclerView recyclerView;
    FoodAdapter myAdapter;
    Context context;
    TextView dDate;
    ArrayList<FoodData> myDatas;
    ArrayList ClassNames = new ArrayList();
    String UserId, UserName;
    String profileId, role, profileImage, ClassName;
    Bundle bundle;
    private int mYear, mMonth, mDay, mHour, mMinute;
    FoodDetailResponseModel foodDetailResponseModel;
    private RadioButton cardioRB;
    private RadioButton trainingRB;
    private RadioGroup rrg;
    private AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.fragment_main_food, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        UserId = SharedPref.getUserId(getActivity());
        UserName = SharedPref.getDisplayName(getActivity());
        myDatas = new ArrayList<>();
        setupRecycler();
        final Calendar cd = Calendar.getInstance();
        mYear = cd.get(Calendar.YEAR);
        mMonth = cd.get(Calendar.MONTH);
        mMonth = mMonth + 1;
        mDay = cd.get(Calendar.DAY_OF_MONTH);
        String currentDate = mYear + "-" + mMonth + "-" + mDay;
        if (TextUtils.isEmpty(SharedPref.getSelectedDate(getActivity()))){
            SharedPref.setSelectedDate(getActivity(),currentDate);
        }
        NavigationView navigationView = (NavigationView) rootView.findViewById(R.id.nav_view);
        View content = getActivity().findViewById(android.R.id.content);
        dDate = (TextView) content.findViewById(R.id.selectedDate);
        dDate.setText(SharedPref.getSelectedDate(getActivity()));

        content.findViewById(R.id.selectedDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int nmonth = monthOfYear + 1;
                        String currentDate1 = year + "-" + nmonth + "-" + dayOfMonth;
                        SharedPref.setSelectedDate(getActivity(),currentDate1);
                        getData();//date select
                        dDate.setText(SharedPref.getSelectedDate(getActivity()));
                    }
                }, mYear, mMonth, mDay);
                dpd.show();

            }
        });
        cardioRB = (RadioButton) rootView.findViewById(R.id.rbCardio);
        trainingRB = (RadioButton) rootView.findViewById(R.id.rbTraining);
        rrg = (RadioGroup) rootView.findViewById(R.id.radio_cardio);
        content.findViewById(R.id.btnAddFood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SelectFoodActivity.class);
                //intent.putExtra("foodType", "Snacks");
                startActivityForResult(intent, 1214);
            }
        });
        rrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbTraining) {

                    if (TextUtils.isEmpty(SharedPref.getfoodType(getContext()))
                            ||SharedPref.getfoodType(getContext()).equalsIgnoreCase("1") )
                        setRadio("2");
                    SharedPref.setfoodType(context, "2");
                } else {

                    if (TextUtils.isEmpty(SharedPref.getfoodType(getContext()))
                            ||SharedPref.getfoodType(getContext()).equalsIgnoreCase("2") )
                        setRadio("1");
                    SharedPref.setfoodType(context, "1");
                }
                if (foodDetailResponseModel != null)
                    setUpDetailData(foodDetailResponseModel.getData().getDailyLimit());
            }
        });
        setRadioButton();//oncreate
        getData();//oncreate
    }

    private void setRadioButton() {
        if (SharedPref.getfoodType(getActivity()).equals("1")) {
            cardioRB.setChecked(true);
        } else if (SharedPref.getfoodType(getActivity()).equals("2")) {
            trainingRB.setChecked(true);
        } else {
            cardioRB.setChecked(true);
        }
    }

    private void getData() {
        APIClient.startQuery().doGetFoodDetails(SharedPref.getUserId(getActivity()), SharedPref.getSelectedDate(getActivity()), System.currentTimeMillis()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(FoodMainFragment.this);
    }

    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void setupRecycler() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        myAdapter = new FoodAdapter(context, myDatas, new FoodAdapter.AddFoodListener() {
            @Override
            public void addItem(String foodType) {
                Intent intent = new Intent(getActivity(), SelectFoodActivity.class);
                intent.putExtra("foodType", foodType);
                startActivityForResult(intent, 1214);
            }
        }, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
    }
    private void setRadio(String s) {
        APIClient.startQuery().doGetRadioCheck(SharedPref.getUserId(getActivity()), SharedPref.getSelectedDate(getContext()), System.currentTimeMillis(),s).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(FoodMainFragment.this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1214 && resultCode == RESULT_OK) {
            if (data != null) {
                String type = data.getStringExtra("foodType");
                SelectFoodData foodData = (SelectFoodData) data.getSerializableExtra("data");

                APIClient.startQuery().doAddFoodData(SharedPref.getUserId(getActivity()), "175000", "is_" + type.toLowerCase(), SharedPref.getfoodType(getActivity()), foodData.getFat(), foodData.getProtein(), foodData.getCarb(), "FOOD-006", foodData.getTitle(), foodData.getFiber(), SharedPref.getSelectedDate(getActivity())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(FoodMainFragment.this);
            }
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull Object data) {
        if (!isVisible()) return;
        if (data != null) {
            if (data instanceof AddFoodDataResponse) {
                Toast.makeText(getContext(), ((AddFoodDataResponse) data).getSuccess(), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(context, FoodActivity.class);
//                startActivity(intent);
                getData();//After added data
            } else if (data instanceof FoodDetailResponseModel) {
                foodDetailResponseModel = (FoodDetailResponseModel) data;
                setUpData((FoodDetailResponseModel) data);
            }else if (data instanceof UpdateRadio){
                UpdateRadio updateRadio=(UpdateRadio) data;
                if (getActivity()!=null)
                    SharedPref.setfoodType(getActivity(),updateRadio.getData());
                setRadioButton();
            } else {
                getData();
                if (data instanceof JsonObject) {
                    JsonObject jsonObject = (JsonObject) data;
                    Toast.makeText(getContext(), jsonObject.get("success").getAsString(), Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private void setUpData(FoodDetailResponseModel data) {
        myDatas.clear();
        myDatas.addAll(data.getData().getBreakfast());
        myDatas.addAll(data.getData().getLunch());
        myDatas.addAll(data.getData().getDinner());
        myDatas.addAll(data.getData().getSnacks());
        if (myDatas.size() == 0) showMessage("No data found");
        SharedPref.setfoodType(getContext(), data.getData().getFoodTypeVal());
        setRadioButton();//onresponse
        myAdapter.updateData(myDatas);
        setUpDetailData(data.getData().getDailyLimit());
    }

    private void setUpDetailData(List<DailyLimitData> dailyLimit) {
        if (!isVisible()) return;
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.conclusion_container);
        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
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
            if (SharedPref.getfoodType(getActivity()).equalsIgnoreCase("2")) {
                txtCarbs.setText(myData.Carb2);
                txtFiber.setText(myData.Fiber2);
            } else {
                txtCarbs.setText(myData.Carb);
                txtFiber.setText(myData.Fiber);
            }

            txtFat.setText("" + myData.Fat);

            textAddFood.setTag("" + myData.FoodType);
            linearLayout.addView(view);
            //linearLayout.setBackgroundColor(Color.parseColor("#64CBD8"));
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textAddFood: {
                int pos = (int) v.getTag();
                showDialogC(getActivity(), null, "Are you sure, you want to delete!", pos);
                break;
            }
        }
    }

    public void showDialogC(Activity activity, String title, CharSequence message, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteFoodData(pos);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (alertDialog != null) alertDialog.dismiss();
            }
        });
        alertDialog = builder.show();
    }

    private void deleteFoodData(int pos) {
        APIClient.startQuery().doDeleteFoodItem(myDatas.get(pos).PostId, SharedPref.getUserId(getActivity()), SharedPref.getSelectedDate(getActivity()), System.currentTimeMillis()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(FoodMainFragment.this);
    }

}
