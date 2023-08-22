package com.example.exe33;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    Button btnAll, btnSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAll = findViewById(R.id.btnAll);
        btnSelected = findViewById(R.id.btnSelected);

        listView = findViewById(R.id.listView);

        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(new Item(R.drawable.img,"OPPO"));
        arrayList.add(new Item(R.drawable.img,"Samsung"));
        arrayList.add(new Item(R.drawable.img,"iPhone"));
        arrayList.add(new Item(R.drawable.img,"Xiaomi"));
        arrayList.add(new Item(R.drawable.img,"VIVO"));
        arrayList.add(new Item(R.drawable.img,"Realmi"));
        arrayList.add(new Item(R.drawable.img,"Nokia"));
        arrayList.add(new Item(R.drawable.img,"Masstel"));
        arrayList.add(new Item(R.drawable.img,"Itel"));
        arrayList.add(new Item(R.drawable.img,"Mobell"));

        AdapterModel arrayAdapter = new AdapterModel(MainActivity.this, R.layout.activity_list_item_row,arrayList);
        listView.setAdapter(arrayAdapter);

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        });

        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i< arrayList.size(); i++){
                    if(arrayList.get(i).getCheckBox()){
                        arrayList.remove(i);
                        arrayAdapter.notifyDataSetChanged();

                    }
                }
            }

        });

    }
}
