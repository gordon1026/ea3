package com.example.ea3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StadiumAdapter extends RecyclerView.Adapter<StadiumAdapter.StadiumViewHolder> {

    private List<Stadium> stadiumList;

    public StadiumAdapter(List<Stadium> stadiumList) {
        this.stadiumList = stadiumList;
    }

    @NonNull
    @Override
    public StadiumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stadium, parent, false);
        return new StadiumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StadiumViewHolder holder, int position) {
        Stadium stadium = stadiumList.get(position);
        holder.bind(stadium);
    }

    @Override
    public int getItemCount() {
        return stadiumList.size();
    }

    public static class StadiumViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private ImageView imageView;
        private View itemView;

        public StadiumViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            nameTextView = itemView.findViewById(R.id.stadium_name);
            imageView = itemView.findViewById(R.id.stadium_image);
        }

        public void bind(Stadium stadium) {
            nameTextView.setText(stadium.getName());

            // Use Glide to load the image
            Glide.with(itemView.getContext())
                    .load(stadium.getImageResource())
                    .into(imageView);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stadium.getMapUrl()));
                itemView.getContext().startActivity(intent);
            });
        }
    }
}