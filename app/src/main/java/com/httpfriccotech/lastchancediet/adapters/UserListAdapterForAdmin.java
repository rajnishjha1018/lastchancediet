package com.httpfriccotech.lastchancediet.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.model.UserListModel;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapterForAdmin extends RecyclerView.Adapter<UserListAdapterForAdmin.ViewHolder>{
    private Context context;
    private List<UserListModel> itemList;
    public UserListAdapterForAdmin(Context context, List<UserListModel> itemList){
        this.context=context;
        this.itemList =itemList;
    }
    @NonNull
    @Override
    public UserListAdapterForAdmin.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_user_list, null);
        return new UserListAdapterForAdmin.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapterForAdmin.ViewHolder holder, final int position) {

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
    public void updateData(ArrayList<UserListModel> itemList){
        this.itemList=itemList;
        notifyDataSetChanged();
    }
}
