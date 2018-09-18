package com.httpfriccotech.lastchancediet.Food;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by RAJNISH on 08/20/2018.
 */

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YOU = 0;
    private static final int THEM = 1;
    private final LayoutInflater inflater;
    private List<FoodData> itemList;
    private Context context;
    private AddFoodListener addFoodListener;
    ArrayList<Boolean> booleanGames = new ArrayList<>();
    FoodHolder viewHolder;
    Button closePopupBtn;
    PopupWindow popupWindow;
    PopupFoodAdapter myAdapter;
    TextView txtmsg;

    public FoodAdapter(Context context, List<FoodData> itemList, AddFoodListener addFoodListener) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.addFoodListener = addFoodListener;
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
        FoodData myData = itemList.get(position);
        viewHolder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        viewHolder.txtFoodType.setText(myData.FoodType);
        viewHolder.txtProtein.setText(myData.Protein);
        viewHolder.txtCarbs.setText(myData.Carbs);
        viewHolder.txtFat.setText(myData.Fat);
        viewHolder.txtFiber.setText(myData.Fiber);
        viewHolder.textAddFood.setTag(myData.FoodType);

       // viewHolder.textAddFood.setVisibility(myData.Protein.equalsIgnoreCase("0") ? View.VISIBLE : View.INVISIBLE);
       // viewHolder.textAddFood.setOnClickListener(new View.OnClickListener() {
        //    @Override
         //   public void onClick(View v) {
           //     String type = (String) v.getTag();
                // Toast.makeText(context,"Add food for "+type,Toast.LENGTH_SHORT).show();
                /*LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                //LayoutInflater layoutInflater = (LayoutInflater) ExerciseActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.food_popup,null);
                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

                txtmsg = (TextView) customView.findViewById(R.id.txtmsg);
                txtmsg.setText("List of food for "+type +" will comming soon..");
                //instantiate popup window
                popupWindow = new PopupWindow(customView, RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                View viewa = inflater.inflate(R.layout.row_exercise, null);
                //display the popup window
                popupWindow.showAtLocation(viewa, Gravity.CENTER, 0, 0);
                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });*/
              //  if (addFoodListener != null) {
              //      addFoodListener.addItem(type);
               // }
            //}
       // });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AddFoodListener {
        public void addItem(String foodType);
    }

}