package com.example.exe31;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<String> {
    private Context context;
    private int layout;
    private List<String> arrayList;
    public ListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.arrayList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        TextView tvItem = view.findViewById(R.id.tvItem);
        Button BtnRemove = view.findViewById((R.id.BtnRemove));
        final String item = arrayList.get(position);
        tvItem.setText(item);

        BtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(position);
                notifyDataSetChanged();
                Log.e("TAG", "Button clicked: " + item);
            }
        });
        return view;


    }
}
