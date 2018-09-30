package com.httpfriccotech.lastchancediet.Exercise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.httpfriccotech.lastchancediet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAJNISH on 09/03/2018.
 */

public class AddExerciseAdapterForCardio extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YOU = 0;
    private static final int THEM = 1;
    private final LayoutInflater inflater;
    //    private final AddExerciseAdapterForCardio.OnItemClicked listener;
    private List<SelectExerciseData> itemList;
    private Context context;
    ArrayList<Boolean> booleanGames = new ArrayList<>();
    SelectExerciseHolder viewHolder;
    Button closePopupBtn;
    PopupWindow popupWindow;
    private AddExerciseAdapterForStrength.ExcerciseSelectedListener<SelectExerciseData> onClickListener;


    public AddExerciseAdapterForCardio(Context context, List<SelectExerciseData> itemList, AddExerciseAdapterForStrength.ExcerciseSelectedListener<SelectExerciseData> onClickListener) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.select_row_cardio, parent, false);
        return new CardioRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        CardioRowViewHolder viewHolder = (CardioRowViewHolder) holder;
        SelectExerciseData myData = itemList.get(position);
        if (position == 0) {
            viewHolder.titleLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.titleLayout.setVisibility(View.INVISIBLE);
        }
        viewHolder.nameTV.setText(myData.getTitle());
        viewHolder.valueET.setText(myData.howlong);
        viewHolder.valueET.addTextChangedListener(new TextWatcher() {
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
                    itemList.get(position).setHowlong(s.toString());
                }
            }
        });
        viewHolder.contentLayout.setTag(position);
        viewHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onExcerciseSelected(itemList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateData(List<SelectExerciseData> data) {
        itemList = data;
        notifyDataSetChanged();
    }
}
