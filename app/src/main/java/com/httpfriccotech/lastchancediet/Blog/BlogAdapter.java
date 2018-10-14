package com.httpfriccotech.lastchancediet.Blog;

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

import java.util.List;

/**
 * Created by RAJNISH on 09/03/2018.
 */

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private Context mContext;
    private List<BlogData> itemList;
    public BlogAdapter(Context c, List<BlogData> items) {
        mContext = c;
        this.itemList = items;
    }
    @Override
    public BlogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.row_recepie, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BlogAdapter.ViewHolder holder, final int position) {
        holder.txtName.setText(itemList.get(position).getTitle());
        holder.textDesc.setText(itemList.get(position).getContent());
        holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BlogByIdActivity.class);
                    intent.putExtra("blogId", ""+ itemList.get(position).getPostId());
                    intent.putExtra("postType", "post");
                    mContext.startActivity(intent);
                }
            });
        new DownLoadImageTask(holder.imageView).execute(itemList.get(position).getBlogThumbUrl());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public void updateData(List<BlogData> data) {
        itemList = data;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,textDesc;
        ImageView imageView;
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.textName);
            textDesc = (TextView) itemView.findViewById(R.id.txtDescription);
            imageView = (ImageView) itemView.findViewById(R.id.imgImage);
            button=(Button)itemView.findViewById(R.id.read_more);
        }
    }
}
