package com.httpfriccotech.lastchancediet.Exercise;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.httpfriccotech.lastchancediet.R;

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
    private View.OnClickListener onClickListener;

    public ExerciseAdapter(Context context, List<ExerciseDataModel> itemList, boolean isstrength, View.OnClickListener onClickListener) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.isstrength = isstrength;
        this.onClickListener=onClickListener;
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

        if (position==0 && viewHolder.titleTV!=null){
            viewHolder.titleTV.setVisibility(View.VISIBLE);
            viewHolder.caloriesLayout.setVisibility(View.VISIBLE);
            viewHolder.titleTV.setText(myData.getEx_type());
        }else{
            if (viewHolder.titleTV!=null)
                viewHolder.titleTV.setVisibility(View.GONE);
            if (viewHolder.caloriesLayout!=null)
                viewHolder.caloriesLayout.setVisibility(View.GONE);
        }
        viewHolder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        viewHolder.txtExerciseType.setText(myData.getTitle());
        viewHolder.txtMinutes.setText(myData.getVal1());
        viewHolder.txtCaloriesBurned.setText(myData.getVal2());
        if (viewHolder.valueThree != null)
            viewHolder.valueThree.setText(myData.getVal3());
        if (viewHolder.addExerciseIB1!=null) {
            viewHolder.addExerciseIB1.setTag(position);
            viewHolder.addExerciseIB1.setOnClickListener(onClickListener);
        } if (viewHolder.addExerciseIB2!=null) {
            viewHolder.addExerciseIB2.setTag(position);
            viewHolder.addExerciseIB2.setOnClickListener(onClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}