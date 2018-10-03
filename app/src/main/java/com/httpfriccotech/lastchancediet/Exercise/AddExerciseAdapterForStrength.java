package com.httpfriccotech.lastchancediet.Exercise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.httpfriccotech.lastchancediet.R;

import java.util.List;

class AddExerciseAdapterForStrength extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final LayoutInflater inflater;
    private List<SelectExerciseData> selectExerciseData;
    private Context context;
    private ExcerciseSelectedListener<SelectExerciseData> onClickListener;
    public AddExerciseAdapterForStrength(Context context, List<SelectExerciseData> cardio, ExcerciseSelectedListener<SelectExerciseData> onClickListener) {
        selectExerciseData=cardio;
        this.context=context;
        this.onClickListener=onClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.select_row_strength, parent, false);
        return new SelectStrengthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SelectStrengthViewHolder viewHolder = (SelectStrengthViewHolder) holder;
        SelectExerciseData myData = selectExerciseData.get(position);
        if (position==0){
            viewHolder.titleLayout.setVisibility(View.VISIBLE);
        }else{
            viewHolder.titleLayout.setVisibility(View.GONE);
        }
        viewHolder.nameTV.setText(myData.getTitle());
        viewHolder.setsET.setText(myData.getStrength_training_set());
        viewHolder.repsET.setText(myData.strength_training_reps_set);
        viewHolder.weightET.setText(myData.getStrength_training_weight_set());
        viewHolder.contentLayout.setTag(position);
        viewHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener!=null){
                    onClickListener.onExcerciseSelected(selectExerciseData.get(position));
                }
            }
        });
        viewHolder.setsET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null){
                    Log.d("Edit",s.toString());
                    selectExerciseData.get(position).setStrength_training_set(s.toString());
                }

            }
        });
        viewHolder.repsET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null){
                    Log.d("Edit",s.toString());
                    selectExerciseData.get(position).setStrength_training_reps_set(s.toString());
                }
            }
        });
        viewHolder.weightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s!=null){
                    Log.d("Edit",s.toString());
                    selectExerciseData.get(position).setStrength_training_weight_set(s.toString());
                }            }
        });
    }

    @Override
    public int getItemCount() {
        return selectExerciseData==null?0:selectExerciseData.size();
    }

    public interface ExcerciseSelectedListener<T>{
        public void onExcerciseSelected(T excercise);
    }
}
