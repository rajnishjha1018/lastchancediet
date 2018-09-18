package com.httpfriccotech.lastchancediet.Workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.httpfriccotech.lastchancediet.DownLoadImageTask;
import com.httpfriccotech.lastchancediet.R;
import java.util.List;

/**
 * Created by John on 8/29/2016.
 */
public class MyAdapter extends BaseAdapter{
    private Context mContext;
    private List<WorkoutItem> itemList;
    public MyAdapter(Context c, List<WorkoutItem> items ) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.row_recepie, null);
            WorkoutItem myData= itemList.get(position);
            TextView txtName = (TextView) grid.findViewById(R.id.textName);
            TextView textDesc = (TextView) grid.findViewById(R.id.txtDescription);

            txtName.setText(myData.getWorkoutName());
            textDesc.setText(myData.getWorkoutDescription());
            ImageView imageView = (ImageView)grid.findViewById(R.id.imgImage);
            new DownLoadImageTask(imageView).execute(myData.getWorkoutImage());
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}