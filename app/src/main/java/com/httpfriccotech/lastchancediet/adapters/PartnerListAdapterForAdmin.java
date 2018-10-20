package com.httpfriccotech.lastchancediet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.model.UserList;

import java.util.List;

public class PartnerListAdapterForAdmin extends RecyclerView.Adapter<PartnerListAdapterForAdmin.ViewHolder>{
    Context context;
    List<UserList> itemList;
    public PartnerListAdapterForAdmin(Context context, List<UserList> itemList){
        this.context=context;
        this.itemList=itemList;
    }
    @Override
    public PartnerListAdapterForAdmin.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_user_list, null);
        return new PartnerListAdapterForAdmin.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartnerListAdapterForAdmin.ViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 5;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgImage;
        TextView textName;
        TextView txtDescription;
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
