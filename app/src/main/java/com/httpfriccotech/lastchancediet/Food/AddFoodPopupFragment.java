package com.httpfriccotech.lastchancediet.Food;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class AddFoodPopupFragment extends DialogFragment {
    private View rootView, parentView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.food_popup, container, false);
        parentView = inflater.inflate(R.layout.activity_select_food, container, false);
        rootView.findViewById(R.id.closePopupBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RadioGroup rg = (RadioGroup) parentView.findViewById(R.id.rFoodType);
                final String value = ((RadioButton) parentView.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                String protin = ((EditText) rootView.findViewById(R.id.textProtein)).getText().toString();
                String carbs = ((EditText) rootView.findViewById(R.id.textCarbs)).getText().toString();
                String fat = ((EditText) rootView.findViewById(R.id.textFat)).getText().toString();
                String fiber = ((EditText) rootView.findViewById(R.id.textFiber)).getText().toString();
                if (TextUtils.isEmpty(protin) || TextUtils.isEmpty(carbs) || TextUtils.isEmpty(fat) || TextUtils.isEmpty(fiber)) {
                    Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_LONG).show();
                    return;
                }
                float pr = Float.parseFloat(protin) / 20;
                float cr = Float.parseFloat(carbs) / 50;
                float ft = Float.parseFloat(fat) / 8;
                float fbr = (float) (Float.parseFloat(fiber) / 2.5);
                SelectFoodData foodData = new SelectFoodData();
                foodData.setAddedFood(true);
                foodData.setTitle(((EditText) rootView.findViewById(R.id.textFoodTitle)).getText().toString());
                foodData.setProtein(pr + "");
                foodData.setCarb(cr + "");
                foodData.setFat(ft + "");
                foodData.setFiber(fbr + "");
                Intent intent = new Intent();
                intent.putExtra("data", foodData);
                intent.putExtra("foodType", value);
                getActivity().setResult(RESULT_OK, intent);
                dismiss();
                getActivity().finish();
            }
        });
        return rootView;
    }
}
