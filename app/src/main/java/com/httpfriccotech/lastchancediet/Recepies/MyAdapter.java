package com.httpfriccotech.lastchancediet.Recepies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.DownLoadImageTask;
import com.httpfriccotech.lastchancediet.ForgotActivity;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.ReadMore.BlogByIdActivity;

import java.util.List;

/**
 * Created by John on 8/29/2016.
 */
public class MyAdapter extends BaseAdapter{
    private Context mContext;
    private List<RecepieItem> itemList;
    public MyAdapter(Context c, List<RecepieItem> items ) {
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
            RecepieItem myData= itemList.get(position);
            TextView txtName = (TextView) grid.findViewById(R.id.textName);
            TextView textDesc = (TextView) grid.findViewById(R.id.txtDescription);

            txtName.setText(myData.getRecepieName());
            textDesc.setText(myData.getRecepieDescription());
            ImageView imageView = (ImageView)grid.findViewById(R.id.imgImage);
            new DownLoadImageTask(imageView).execute(myData.getRecepieImage());
            grid.findViewById(R.id.read_more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = "read Mode button clicked";
                    Toast.makeText(mContext,"see "+msg,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, BlogByIdActivity.class);
                    intent.putExtra("blogId", ""+ itemList.get(position).getRecepiePostId());
                    mContext.startActivity(intent);
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}