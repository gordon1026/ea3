package com.example.ea3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedalAdapter extends RecyclerView.Adapter<MedalAdapter.MedalViewHolder> {
    private List<Medal> medals;

    public MedalAdapter(List<Medal> medals) {
        this.medals = medals;
    }

    @Override
    public MedalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medal_item, parent, false);
        return new MedalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedalViewHolder holder, int position) {
        holder.bind(medals.get(position));
    }

    @Override
    public int getItemCount() {
        return medals.size();
    }

    public static class MedalViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMedalIcon;
        TextView tvMedalTitle;
        TextView tvMedalDescription;

        public MedalViewHolder(View itemView) {
            super(itemView);
            ivMedalIcon = itemView.findViewById(R.id.iv_medal_icon);
            tvMedalTitle = itemView.findViewById(R.id.tv_medal_title);
            tvMedalDescription = itemView.findViewById(R.id.tv_medal_description);
        }

        public void bind(Medal medal) {
            // 由于 Medal 类没有图标资源，暂时不处理 ivMedalIcon
            tvMedalTitle.setText(medal.getTitle());
            tvMedalDescription.setText(medal.getDescription());
        }
    }
}