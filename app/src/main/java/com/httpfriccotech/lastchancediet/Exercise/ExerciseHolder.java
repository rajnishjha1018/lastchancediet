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
    ImageButton deleteEx,deleteStr;
     LinearLayout caloriesLayout,strengthLayout;

    public ExerciseHolder(View itemView, boolean isstrength) {
        super(itemView);
        view = itemView;
        txtExerciseType = (TextView) view.findViewById(isstrength ? R.id.txtSetsType : R.id.textExercise);
        txtMinutes = (TextView) view.findViewById(isstrength ? R.id.txtSets : R.id.textMinutes);
        txtCaloriesBurned = (TextView) view.findViewById(R.id.textReps);
        valueThree = (TextView) view.findViewById(R.id.textWeight);
        deleteEx=(ImageButton)view.findViewById(R.id.ib_delete_cal);
        deleteStr=(ImageButton)view.findViewById(R.id.ib_delete_str);

    }
}
