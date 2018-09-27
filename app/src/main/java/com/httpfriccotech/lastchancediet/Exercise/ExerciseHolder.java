package com.httpfriccotech.lastchancediet.Exercise;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;

/**
 * Created by John on 9/7/2016.
 */
public class ExerciseHolder extends RecyclerView.ViewHolder {


    View view;
    TextView txtExerciseType, txtMinutes, txtCaloriesBurned, valueThree,titleTV;
    ImageButton addExerciseIB1,addExerciseIB2;
     LinearLayout caloriesLayout,strengthLayout;

    public ExerciseHolder(View itemView, boolean isstrength) {
        super(itemView);
        view = itemView;
        txtExerciseType = (TextView) view.findViewById(isstrength ? R.id.txtSetsType : R.id.textExercise);
        txtMinutes = (TextView) view.findViewById(isstrength ? R.id.txtSets : R.id.textMinutes);
        txtCaloriesBurned = (TextView) view.findViewById(isstrength ? R.id.textReps : R.id.textCaloriesBurned);
        valueThree = (TextView) view.findViewById(R.id.textWeight);
        addExerciseIB1 = (ImageButton) view.findViewById(R.id.ib_add_ex1);
        addExerciseIB2= (ImageButton) view.findViewById(R.id.ib_add_ex2);
        titleTV=(TextView)view.findViewById(R.id.title);
        caloriesLayout=(LinearLayout) view.findViewById(R.id.caloriesLayout);
        strengthLayout=(LinearLayout) view.findViewById(R.id.caloriesLayout);

    }
}
