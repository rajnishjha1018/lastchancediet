package com.httpfriccotech.lastchancediet.Exercise;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.Exercise.ExerciseData;
import com.httpfriccotech.lastchancediet.Exercise.ExerciseHolder;
import com.httpfriccotech.lastchancediet.Food.FoodActivity;
import com.httpfriccotech.lastchancediet.Food.SelectFoodActivity;
import com.httpfriccotech.lastchancediet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAJNISH on 08/20/2018.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YOU = 0;
    private static final int THEM = 1;
    private final LayoutInflater inflater;
    private List<ExerciseDataModel> itemList;
    private Context context;
    boolean isstrength;

    public ExerciseAdapter(Context context, List<ExerciseDataModel> itemList, boolean isstrength) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.isstrength = isstrength;
    }

    public void updateData(List<ExerciseDataModel> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(isstrength ? R.layout.strength_item_layout : R.layout.row_exercise, parent, false);
        return new ExerciseHolder(view, isstrength);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ExerciseHolder viewHolder = (ExerciseHolder) holder;
        ExerciseDataModel myData = itemList.get(position);
        viewHolder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        viewHolder.txtExerciseType.setText(myData.getEx_type());
        viewHolder.txtMinutes.setText(myData.getVal1());
        viewHolder.txtCaloriesBurned.setText(myData.getVal2());
        if (viewHolder.valueThree != null)
            viewHolder.valueThree.setText(myData.getVal3());
        viewHolder.textAddExercise.setTag(myData.getEx_type());
       // viewHolder.textAddExercise.setVisibility(myData.getVal1().equalsIgnoreCase("0") ? View.VISIBLE : View.INVISIBLE);
        viewHolder.textAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = (String) v.getTag();
                Intent intent = new Intent(context, SelectExerciseActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}