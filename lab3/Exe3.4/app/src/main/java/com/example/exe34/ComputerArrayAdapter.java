package com.example.exe34;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

public class ComputerArrayAdapter extends ArrayAdapter<Computer> {

    private Context context;
    private int layoutToBeInFlated;
    private List<Computer> computers;


    public ComputerArrayAdapter(@NonNull Context context, int resource, @NonNull List<Computer> computers) {
        super(context, resource, computers);
        this.computers = computers;
        this.layoutToBeInFlated = resource;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ComputerViewHolder holder;
        View row = convertView;

        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutToBeInFlated, null);

            holder = new ComputerViewHolder();
            holder.tvComputerName = row.findViewById(R.id.tv_computer_name);
            holder.ivComputer = row.findViewById(R.id.imv_computer);

            row.setTag(holder);
        }
        else{
            holder = (ComputerViewHolder)  row.getTag();
        }

        Computer computer = computers.get(position);
        holder.tvComputerName.setText(computer.getName());

        if (computer.isSelected()){
            holder.ivComputer.setImageResource(R.drawable.img);
        }
        else {
            holder.ivComputer.setImageResource(R.drawable.ic_launcher_background);
        }


        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Computer computer = computers.get(position);
                computer.setSelected(!computer.isSelected());

                Toast.makeText(context,"COMPUTER CLICKED - " + computer.getName(), Toast.LENGTH_LONG).show();

                notifyDataSetChanged();
            }
        });

        return row;

    }

    public class ComputerViewHolder {

        ImageView ivComputer;
        TextView tvComputerName;
    }
}
