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
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAJNISH on 08/20/2018.
 */

public class FoodSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YOU = 0;
    private static final int THEM = 1;
    private final LayoutInflater inflater;
    private List<FoodData> itemList;
    private Context context;
    ArrayList<Boolean> booleanGames = new ArrayList<>();
    FoodHolder viewHolder;
    Button closePopupBtn;
    PopupWindow popupWindow;
    PopupFoodAdapter myAdapter;
    TextView txtmsg;
    public FoodSelectAdapter(Context context, List<FoodData> itemList) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_food, parent, false);
        viewHolder = new FoodHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        FoodHolder viewHolder = (FoodHolder) holder;
        FoodData myData=itemList.get(position);
        viewHolder.view.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        viewHolder.txtFoodType.setText(myData.FoodType);
        viewHolder.txtProtein.setText(myData.Protein);
        viewHolder.txtCarbs.setText(myData.Carb);
        viewHolder.txtFat.setText(myData.Fat);
        viewHolder.txtFiber.setText(myData.Fiber);
        viewHolder.textAddFood.setTag(myData.FoodType);
        viewHolder.textAddFood.setVisibility(myData.Protein.equalsIgnoreCase("0")?View.VISIBLE:View.INVISIBLE);


    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }




}