package com.httpfriccotech.lastchancediet.Blog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.DownLoadImageTask;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.ReadMore.BlogByIdActivity;

import java.util.List;

/**
 * Created by RAJNISH on 09/03/2018.
 */

public class BlogAdapter extends BaseAdapter {
    private Context mContext;
    private List<BlogData> itemList;

    public BlogAdapter(Context c, List<BlogData> items) {
        mContext = c;

        this.itemList = items;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.itemList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.row_recepie, null);
            BlogData myData = itemList.get(position);
            TextView txtName = (TextView) grid.findViewById(R.id.textName);
            TextView textDesc = (TextView) grid.findViewById(R.id.txtDescription);

            txtName.setText(myData.getTitle());
            textDesc.setText(myData.getContent());
            ImageView imageView = (ImageView) grid.findViewById(R.id.imgImage);
            new DownLoadImageTask(imageView).execute(myData.getBlogThumbUrl());
            grid.findViewById(R.id.read_more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BlogByIdActivity.class);
                    intent.putExtra("blogId", ""+ itemList.get(position).getPostId());
                    intent.putExtra("postType", "post");
                    mContext.startActivity(intent);
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    public void updateData(List<BlogData> data) {
        itemList = data;
        notifyDataSetChanged();
    }
}
