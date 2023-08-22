package com.example.exe52;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.appsearch.PutDocumentsRequest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ModelAdapter mAdapter;
    private List<Model> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mListView= findViewById(R.id.listView);

        initData();

        registerForContextMenu(mListView);
    }


    private void initData() {
        mData = new ArrayList<>();
        mData.add(new Model("Nhập môn Trí tuệ nhân tạo", "B305", "14/09/2022", "09:20"));
        mData.add(new Model("Phát triển ứng dụng di động", "B306-A", "21/010/2022", "07:00"));

        mAdapter = new ModelAdapter(this, R.layout.row_layout, mData);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);

        MenuItem menuItem = menu.findItem(R.id.miSwitch);
        menuItem.setActionView(R.layout.bar_action_switch);

        Switch switchEvent = menuItem.getActionView().findViewById(R.id.sw_event);

        switchEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.miDeleteAll) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                showConfirmDialog();
            }
        } else if (item.getItemId() == R.id.miAbout) {
            showAboutDialog();
        } else if (item.getItemId() == R.id.miCreate) {
            openCreateNewEventActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openCreateNewEventActivity() {
        // create intent to call NewEventActivity
        Intent newEventActivityIntent = new Intent(MainActivity.this, NewActivity.class);
        startActivityForResult(newEventActivityIntent, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to delete all phones?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mData.clear();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "The selected phone is deleted!", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });

        AlertDialog confirmDialog = builder.create();
        confirmDialog.show();
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About");
        builder.setMessage("The application about my plan");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == Activity.RESULT_OK) {
            Bundle returnedBundle = data.getExtras();
            String title = returnedBundle.getString(NewActivity.TITLE);
            String place = returnedBundle.getString(NewActivity.PLACE);
            String date = returnedBundle.getString(NewActivity.DATE);
            String time = returnedBundle.getString(NewActivity.TIME);
            Model newEvent = new Model(title, place, date, time);
            mData.add(newEvent);
            mAdapter.notifyDataSetChanged();
        }
    }
}