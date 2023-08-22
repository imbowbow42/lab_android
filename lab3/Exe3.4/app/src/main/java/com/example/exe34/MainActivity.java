package com.example.exe34;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gvComputers;
    private List<Computer> computers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvComputers = findViewById(R.id.gvComputers);

        generateRandomItem();
        handleEvents();


    }

    private void handleEvents() {

        ComputerArrayAdapter adapter = new ComputerArrayAdapter(this, R.layout.grid_item, computers);
        gvComputers.setAdapter(adapter);
    }

    private void generateRandomItem() {
        for (int i=0; i<20 ; i++){
            String name = "PC" + i;
            Computer computer = new Computer(name, true);
            computers.add(computer);
        }

    }



}

