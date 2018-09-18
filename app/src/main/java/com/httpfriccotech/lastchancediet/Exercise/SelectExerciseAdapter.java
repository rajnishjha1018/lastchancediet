package com.httpfriccotech.lastchancediet.Exercise;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.httpfriccotech.lastchancediet.Exercise.SelectExerciseHolder;
import com.httpfriccotech.lastchancediet.Exercise.ExerciseData;
import com.httpfriccotech.lastchancediet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAJNISH on 09/03/2018.
 */

public class SelectExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YOU = 0;
    private static final int THEM = 1;
    private final LayoutInflater inflater;
    private final OnItemClicked listener;
    private List<ExerciseData> itemList;
    private Context context;
    ArrayList<Boolean> booleanGames = new ArrayList<>();
    SelectExerciseHolder viewHolder;
    Button closePopupBtn;
    PopupWindow popupWindow;

    public SelectExerciseAdapter(Context context, List<ExerciseData> itemList, OnItemClicked listener) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.select_row_exercise, parent, false);
        viewHolder = new SelectExerciseHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        SelectExerciseHolder viewHolder = (SelectExerciseHolder) holder;
        ExerciseData myData = itemList.get(position);
        viewHolder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        viewHolder.txtExerciseName.setText("test 123");
        viewHolder.txtMinutes.setText("Hello 123");
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateData(List<ExerciseData> data) {
        itemList = data;
        notifyDataSetChanged();
    }

    public interface OnItemClicked {
        public void onItemClick(ExerciseData data);
    }
}
