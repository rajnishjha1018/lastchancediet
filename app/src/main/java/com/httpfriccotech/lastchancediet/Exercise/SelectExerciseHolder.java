package com.httpfriccotech.lastchancediet.Exercise;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;

/**
 * Created by John on 9/7/2016.
 */
public class SelectExerciseHolder extends RecyclerView.ViewHolder {


    View view;
    TextView txtExerciseName, txtMinutes;

    public SelectExerciseHolder(View itemView) {
        super(itemView);
        view = itemView;
        txtExerciseName = (TextView) view.findViewById(R.id.textExerciseName);
        txtMinutes = (TextView) view.findViewById(R.id.textMinutes);

    }
}
