package com.httpfriccotech.lastchancediet.Food;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.httpfriccotech.lastchancediet.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public class AddFoodPopupFragment extends DialogFragment {
    private View rootView,parentView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.food_popup, container, false);
        parentView = inflater.inflate(R.layout.activity_select_food, container, false);
        rootView.findViewById(R.id.closePopupBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RadioGroup rg = (RadioGroup)  parentView.findViewById(R.id.rFoodType);
                final String value =
                        ((RadioButton)parentView.findViewById(rg.getCheckedRadioButtonId()))
                                .getText().toString();
                SelectFoodData foodData = new SelectFoodData();
                foodData.setAddedFood(true);
                foodData.setTitle(((EditText) rootView.findViewById(R.id.textFoodTitle)).getText().toString());
                foodData.setProtein(((EditText) rootView.findViewById(R.id.textProtein)).getText().toString());
                foodData.setCarb(((EditText) rootView.findViewById(R.id.textCarbs)).getText().toString());
                foodData.setFat(((EditText) rootView.findViewById(R.id.textFat)).getText().toString());
                foodData.setFiber(((EditText) rootView.findViewById(R.id.textFiber)).getText().toString());
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
