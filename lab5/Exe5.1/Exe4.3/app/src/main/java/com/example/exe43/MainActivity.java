package com.example.exe43;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView rcView;
    private RecyclerAdapter adapter;
    private List<ItemList> items;
    private Boolean isSelectAll = false;
    Button btnAll, btnSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Are you sure?");


        rcView = findViewById(R.id.rcView);
        btnAll = findViewById(R.id.btnAll);
        btnSelected = findViewById(R.id.btnSelected);

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.clear();
                adapter.notifyDataSetChanged();
            }
        });
        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i< items.size(); i++){
                    if(items.get(i).isCheckBox()){
                        items.remove(i);
                        adapter.notifyDataSetChanged();

                    }
                }
            }

        });

        initViews();
        initValues();

    }

    private void initValues() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcView.setLayoutManager(manager);

        items = getItems();
        adapter = new RecyclerAdapter(items, this);
        rcView.setAdapter(adapter);
    }

    private List<ItemList> getItems() {
        List<ItemList> itemLists = new ArrayList<>();
        itemLists.add(new ItemList( R.drawable.img, "OPPO"));
        itemLists.add(new ItemList(R.drawable.img,"Samsung"));
        itemLists.add(new ItemList(R.drawable.img,"iPhone"));
        itemLists.add(new ItemList(R.drawable.img,"Xiaomi"));
        itemLists.add(new ItemList(R.drawable.img,"VIVO"));
        itemLists.add(new ItemList(R.drawable.img,"Realmi"));
        itemLists.add(new ItemList(R.drawable.img,"Nokia"));
        itemLists.add(new ItemList(R.drawable.img,"Masstel"));
        itemLists.add(new ItemList(R.drawable.img,"Itel"));
        itemLists.add(new ItemList(R.drawable.img,"Mobell"));

        return itemLists;
    }

    private void initViews() {
        rcView = findViewById(R.id.rcView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.deleteall:
                DeleteDialog();
                return true;
            case R.id.check:
                // do your code
                isSelectAll = !isSelectAll;
                CheckOrUncheckAllDialog();
                return true;
            case R.id.deleteselect:
                // do your code
                DeleteSelectDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void DeleteSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                for(int i = 0; i< items.size(); i++){
                    if(items.get(i).isCheckBox()){
                        items.remove(i);
                        adapter.notifyDataSetChanged();

                    }
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void CheckOrUncheckAllDialog() {
        for(int i = 0; i< items.size(); i++){
            items.get(i).setCheckBox(isSelectAll);
        }
        adapter.notifyDataSetChanged();
    }

    private void DeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                items.clear();
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }


}