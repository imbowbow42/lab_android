package com.example.exe102;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context mContext;
    List<Statistic> statisticList;

    public Adapter(Context mContext, List<Statistic> statisticList) {
        this.mContext = mContext;
        this.statisticList = statisticList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statis, parent, false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Statistic statistic = statisticList.get(position);
        holder.itemLabel.setText(statistic.getLabel().getValue());
        holder.itemData.setText(statistic.getData());
    }

    @Override
    public int getItemCount() {
        return statisticList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView itemLabel, itemData;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLabel = itemView.findViewById(R.id.item_label);
            itemData = itemView.findViewById(R.id.item_data);
        }
    }
}
