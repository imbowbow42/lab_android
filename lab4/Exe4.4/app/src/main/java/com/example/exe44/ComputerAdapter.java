package com.example.exe44;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ComputerAdapter extends RecyclerView.Adapter<ComputerAdapter.ViewHolder>{

    private ArrayList<String> listitems;
    private Context context;

    public ComputerAdapter(ArrayList<String> items, Context context) {
        this.listitems = items;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = listitems.get(position);
        holder.bindView(position,holder.itemView,item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
                holder.clickView(position,holder.itemView,item);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_computer_name;
        ImageView imv_computer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_computer_name = itemView.findViewById(R.id.tv_computer_name);
            imv_computer = itemView.findViewById(R.id.imv_computer);
        }

        public void bindView(int position, View itemView, String item){
            tv_computer_name.setText(item);

        }
        public void clickView(int position, View itemView, String item){
            imv_computer.setImageResource(R.drawable.img);
        }

    }
}
