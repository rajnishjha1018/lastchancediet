package com.httpfriccotech.lastchancediet.program;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;

import java.util.List;

public class ProgramListAdapter extends RecyclerView.Adapter<ProgramListAdapter.ViewHolder> {
    private Context context;
    private List<ProgramData> itemList;

    public ProgramListAdapter(Context context, List<ProgramData> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_program_row, null);
        return new ProgramListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(itemList.get(position).getTitle());
        holder.content.setText(itemList.get(position).getContent());
        if (itemList.get(position).getIsAllowedProgram()==1) {
            holder.relativeLayout.setVisibility(View.GONE);
            holder.viewMore.setEnabled(true);

            holder.title.setTextColor(context.getResources().getColor(R.color.black));
            holder.content.setTextColor(context.getResources().getColor(R.color.album_title));
            holder.viewMore.setBackground(context.getResources().getDrawable(R.color.colorAccent));
        } else {
            holder.title.setTextColor(context.getResources().getColor(R.color.fade_title));
            holder.content.setTextColor(context.getResources().getColor(R.color.fade_title));
            holder.viewMore.setBackground(context.getResources().getDrawable(R.color.fade_button));
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.viewMore.setEnabled(false);

        }
        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YourProgramDetailActivity.class);
                intent.putExtra("postId", itemList.get(position).getPostId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView title, viewMore;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            viewMore = (TextView) itemView.findViewById(R.id.tv_view_more);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl_disable_enable);
        }
    }
}
