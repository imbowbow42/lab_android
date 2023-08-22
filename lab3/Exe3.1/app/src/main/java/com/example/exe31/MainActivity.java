package com.example.exe31;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.listView);

        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=1; i <= 20; i++){
            arrayList.add("Item " + i);
        }

        ListAdapter arrayAdapter = new ListAdapter(this, R.layout.
                item,arrayList);
        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedItem=(String) listview.getItemAtPosition(i);
                Toast.makeText(MainActivity.this,clickedItem,Toast.LENGTH_LONG).show();
            }
        });


    }




}