package com.httpfriccotech.lastchancediet.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.DownLoadImageTask;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.ReadMore.BlogByIdActivity;
import com.httpfriccotech.lastchancediet.Recepies.RecepieItem;

import java.util.ArrayList;

/**
 * Created by RAJNISH on 08/20/2018.
 */

public class RecepiesAdaper extends RecyclerView.Adapter<RecepiesAdaper.ViewHolder>{
    Context context;
    ArrayList<RecepieItem> recepieItemList;
    public RecepiesAdaper(Context context, ArrayList<RecepieItem> recepieItemList){
        this.context=context;
        this.recepieItemList=recepieItemList;
    }
    @Override
    public RecepiesAdaper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_recepie, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecepiesAdaper.ViewHolder holder, final int position) {

        new DownLoadImageTask(holder.imgImage).execute(recepieItemList.get(position).getRecepieImage());
        holder.textName.setText(recepieItemList.get(position).getRecepieName());
        holder.txtDescription.setText(recepieItemList.get(position).getRecepieDescription());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BlogByIdActivity.class);
                intent.putExtra("blogId", ""+ recepieItemList.get(position).getRecepiePostId());
                intent.putExtra("postType", "recipe");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recepieItemList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgImage;
        TextView textName;
        TextView txtDescription;
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            imgImage= (ImageView) itemView.findViewById(R.id.imgImage);
            textName= (TextView) itemView.findViewById(R.id.textName);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            button=(Button)itemView.findViewById(R.id.read_more);

        }
    }
}
