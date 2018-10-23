package com.httpfriccotech.lastchancediet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.model.UserListModel;

import java.util.ArrayList;
import java.util.List;

public class PartnerListAdapterForAdmin extends RecyclerView.Adapter<PartnerListAdapterForAdmin.ViewHolder>{
    Context context;
    List<UserListModel> itemList;
    public PartnerListAdapterForAdmin(Context context, List<UserListModel> itemList){
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
        holder.nameTV.setText(itemList.get(position).getName());
        holder.emailTV.setText(itemList.get(position).getEmail());
        holder.joinDateTV.setText(itemList.get(position).getRegdate());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV,emailTV,joinDateTV;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.tv_name);
            emailTV=itemView.findViewById(R.id.tv_email);
            joinDateTV=itemView.findViewById(R.id.tv_join_date);
        }
    }
    public void updatePartnerList(ArrayList<UserListModel> itemList){
        this.itemList=itemList;
        notifyDataSetChanged();
    }
}
