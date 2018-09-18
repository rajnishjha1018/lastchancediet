package com.httpfriccotech.lastchancediet.Exercise;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;

/**
 * Created by John on 9/7/2016.
 */
public class ExerciseHolder extends RecyclerView.ViewHolder {


    View view;
    TextView txtExerciseType, txtMinutes, txtCaloriesBurned, valueThree;
    ImageView textAddExercise;

    public ExerciseHolder(View itemView, boolean isstrength) {
        super(itemView);
        view = itemView;
        txtExerciseType = (TextView) view.findViewById(isstrength ? R.id.txtSetsType : R.id.textExercise);
        txtMinutes = (TextView) view.findViewById(isstrength ? R.id.txtSets : R.id.textMinutes);
        txtCaloriesBurned = (TextView) view.findViewById(isstrength ? R.id.textReps : R.id.textCaloriesBurned);
        valueThree = (TextView) view.findViewById(R.id.textWeight);
        textAddExercise = (ImageView) view.findViewById(R.id.textAddExercise);
    }
}
