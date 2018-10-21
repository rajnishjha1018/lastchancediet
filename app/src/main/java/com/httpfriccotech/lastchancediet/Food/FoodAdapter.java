package com.httpfriccotech.lastchancediet.Food;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YOU = 0;
    private static final int THEM = 1;
    private LayoutInflater inflater;
    private List<FoodData> itemList;
    private Context context;
    private AddFoodListener addFoodListener;
    ArrayList<Boolean> booleanGames = new ArrayList<>();
    FoodHolder viewHolder;
    Button closePopupBtn;
    PopupWindow popupWindow;
    PopupFoodAdapter myAdapter;
    TextView txtmsg;
    private View.OnClickListener onClickListener;
    private boolean isB,isL,isD,isS;

    public FoodAdapter(Context context, List<FoodData> itemList, AddFoodListener addFoodListener, View.OnClickListener onClickListener) {
        this.itemList = itemList;
        this.context = context;
        this.addFoodListener = addFoodListener;
        this.onClickListener=onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_food, parent, false);
        viewHolder = new FoodHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        FoodHolder viewHolder = (FoodHolder) holder;
        final FoodData myData = itemList.get(position);
        viewHolder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        boolean isTitle = (position == 0 || isTitle(position));
        viewHolder.titleTV.setText(myData.Title);
        viewHolder.txtFoodType.setText(myData.FoodType);
        viewHolder.view_divider.setVisibility(isNextTitle(position) ? View.VISIBLE : View.GONE);
        viewHolder.txtProtein.setText(myData.Protein);
        viewHolder.txtCarbs.setText(myData.Carb);
        viewHolder.txtFat.setText(myData.Fat);
        viewHolder.txtFiber.setText(myData.Fiber);
        viewHolder.textAddFood.setOnClickListener(onClickListener);
        viewHolder.textAddFood.setTag(position);
        if (myData.FoodType.equalsIgnoreCase("Breakfast")){
            if (!isB) {
                viewHolder.foodTypeTV.setText(myData.FoodType);
                viewHolder.foodTypeTV.setText(myData.FoodType);
                viewHolder.foodTypeTV.setVisibility(View.VISIBLE);
                isB=true;
            }else{
                viewHolder.foodTypeTV.setVisibility(View.GONE);
            }

        }else if(myData.FoodType.equalsIgnoreCase("Lunch")&& !isL){
            if (!isL) {
                viewHolder.foodTypeTV.setText(myData.FoodType);
                viewHolder.foodTypeTV.setText(myData.FoodType);
                viewHolder.foodTypeTV.setVisibility(View.VISIBLE);
                isL=true;
            }else{
                viewHolder.foodTypeTV.setVisibility(View.GONE);
            }
        }else if(myData.FoodType.equalsIgnoreCase("Dinner") && !isD){
            if (!isD) {
                viewHolder.foodTypeTV.setText(myData.FoodType);
                viewHolder.foodTypeTV.setText(myData.FoodType);
                viewHolder.foodTypeTV.setVisibility(View.VISIBLE);

                isD=true;
            }else{
                viewHolder.foodTypeTV.setVisibility(View.GONE);
            }
        }else if(myData.FoodType.equalsIgnoreCase("Snacks") && !isS){
            if (!isS) {
                viewHolder.foodTypeTV.setText(myData.FoodType);
                viewHolder.foodTypeTV.setText(myData.FoodType);
                viewHolder.foodTypeTV.setVisibility(View.VISIBLE);

                isS=true;
            }else{
                viewHolder.foodTypeTV.setVisibility(View.GONE);
            }
        }
    }

    private boolean isTitle(int position) {
        if (itemList.get(position - 1).FoodType.equalsIgnoreCase(itemList.get(position).FoodType))
            return false;
        return true;
    }

    private boolean isNextTitle(int position) {
//        if (itemList.size() < position + 2)
//            return false;
//        if (!itemList.get(position).FoodType.equalsIgnoreCase(itemList.get(position + 1).FoodType))
//            return true;
//        return false;
        if (itemList.size()>position+1)
            return true;
        return false;
    }

    public void updateData(List<FoodData> itemList) {
        isB=false;
        isL=false;
        isD=false;
        isS=false;
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AddFoodListener {
        public void addItem(String foodType);
    }

}