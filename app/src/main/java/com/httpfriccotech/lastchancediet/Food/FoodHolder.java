package com.httpfriccotech.lastchancediet.Food;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;

/**
 * Created by John on 9/7/2016.
 */
public class FoodHolder extends RecyclerView.ViewHolder {

    View view;
    View view_divider;
    TextView txtFoodType, txtProtein,txtCarbs,txtFat,txtFiber,titleTV,foodTypeTV;
    ImageView textAddFood;

    View container;
    public FoodHolder(View itemView) {
        super(itemView);
        view = itemView;
        container = itemView.findViewById(R.id.ll_container);
        view_divider = itemView.findViewById(R.id.view_divider);
        txtFoodType= (TextView) view.findViewById(R.id.textFoodType);
        txtProtein= (TextView) view.findViewById(R.id.textProtein);
        txtCarbs= (TextView) view.findViewById(R.id.textCarbs);
        txtFat= (TextView) view.findViewById(R.id.textFat);
        txtFiber = (TextView) view.findViewById(R.id.textFiber);
        foodTypeTV = (TextView) view.findViewById(R.id.foodType);
        textAddFood = (ImageView) view.findViewById(R.id.textAddFood);
        titleTV = (TextView) view.findViewById(R.id.textTitle);

    }
}
