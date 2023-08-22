package com.example.exe44;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvComputers;

    private ArrayList<String> listitem;
    private ComputerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvComputers = findViewById(R.id.rvComputers);

        listitem = new ArrayList<String>();

        for (int i = 1; i <= 20; i++) {
            listitem.add("PC "+i);
        }
        adapter = new ComputerAdapter(listitem,this);
        rvComputers.setAdapter(adapter);

        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        rvComputers.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView





    }
}