package com.example.exe42;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exe42.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<String> listitems;
    private Context context;

    public ItemAdapter(ArrayList<String> items, Context context) {
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

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        Button btnRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            tv_name = itemView.findViewById(R.id.tv_name);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }

        public void bindView(int position, View itemView, String item){
            tv_name.setText(item);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listitems.remove(position);
                    notifyDataSetChanged();
                    Log.e("TAG", "Button clicked: " + item);
                }
            });

        }
    }
}
