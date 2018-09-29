package com.httpfriccotech.lastchancediet.Exercise;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;

class SelectStrengthViewHolder extends RecyclerView.ViewHolder {
    TextView nameTV;
    EditText setsET,repsET,weightET;
    LinearLayout titleLayout,contentLayout;
    public SelectStrengthViewHolder(View view) {
        super(view);
        nameTV=(TextView) view.findViewById(R.id.tv_name_s);
        setsET=(EditText)view.findViewById(R.id.et_sets_s);
        repsET=(EditText)view.findViewById(R.id.et_reps_sets_s);
        weightET=(EditText)view.findViewById(R.id.et_weight_sets_s);
        titleLayout=(LinearLayout)view.findViewById(R.id.s_header);
        contentLayout=(LinearLayout)view.findViewById(R.id.s_content);
    }
}
