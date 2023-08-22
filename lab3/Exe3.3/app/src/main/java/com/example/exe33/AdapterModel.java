package com.example.exe33;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterModel extends ArrayAdapter<Item> {

    private List<Item> arrayList;

    public AdapterModel(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
    }

    public static class ViewHolder{
        ImageView imgAvatar;
        TextView textView;
        CheckBox checkBox;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);
        ViewHolder v;


        convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item_row, parent, false);

        v = new ViewHolder();
        v.imgAvatar = convertView.findViewById(R.id.imgAvatar);
        v.textView = convertView.findViewById(R.id.textView);
        v.checkBox = convertView.findViewById(R.id.checkBox);

        v.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    item.setCheckBox(true);
                }
                else {
                    item.setCheckBox(false);
                }
            }
        });
        convertView.setTag(v);

        v.imgAvatar.setImageResource(item.getImgAvatar());
        v.textView.setText(item.getText());



        return convertView;

    }
}
