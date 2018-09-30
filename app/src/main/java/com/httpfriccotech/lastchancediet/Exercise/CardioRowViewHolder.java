package com.httpfriccotech.lastchancediet.Exercise;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;

class CardioRowViewHolder extends RecyclerView.ViewHolder {
    TextView nameTV;
    EditText valueET;
    LinearLayout titleLayout,contentLayout;
    public CardioRowViewHolder(View view) {
        super(view);
        nameTV=(TextView) view.findViewById(R.id.tv_name);
        valueET=(EditText)view.findViewById(R.id.et_value);
        titleLayout=(LinearLayout)view.findViewById(R.id.cardio_header);
        contentLayout=(LinearLayout)view.findViewById(R.id.cardio_content);
    }
}
