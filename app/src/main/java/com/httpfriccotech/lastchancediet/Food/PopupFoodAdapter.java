package com.httpfriccotech.lastchancediet.Food;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAJNISH on 09/03/2018.
 */

public class PopupFoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YOU = 0;
    private static final int THEM = 1;
    private final LayoutInflater inflater;
    private final OnItemClicked listener;
    private List<SelectFoodData> itemList;
    private Context context;
    ArrayList<Boolean> booleanGames = new ArrayList<>();
    FoodHolder viewHolder;
    Button closePopupBtn;
    PopupWindow popupWindow;

    public PopupFoodAdapter(Context context, List<SelectFoodData> itemList, OnItemClicked listener) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_food, parent, false);
        viewHolder = new FoodHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        FoodHolder viewHolder = (FoodHolder) holder;
        SelectFoodData myData = itemList.get(position);
        viewHolder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        viewHolder.txtFoodType.setText(myData.getTitle());
        viewHolder.txtProtein.setText(myData.getProtein());
        viewHolder.txtCarbs.setText(myData.getCarb());
        viewHolder.txtFat.setText(myData.getFat());
        viewHolder.txtFiber.setText(myData.getFiber());
        viewHolder.textAddFood.setTag(myData.getTitle());
        viewHolder.textAddFood.setVisibility(myData.getProtein().equalsIgnoreCase("0") ? View.VISIBLE : View.INVISIBLE);
        viewHolder.titleTV.setVisibility(View.GONE);
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(itemList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateData(List<SelectFoodData> data) {
        itemList = data;
        notifyDataSetChanged();
    }

    public interface OnItemClicked {
        public void onItemClick(SelectFoodData data);
    }
}
