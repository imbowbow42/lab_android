package com.example.exe45;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int TOTAL_USER_USER = 5;
    private RecyclerView rvUser;
    private List<User> users = new ArrayList<>();
    private Button btnAdd;
    private Button btnRemove;
    private TextView tvTotalUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind UI control to Java Objects
        rvUser = findViewById(R.id.exercise_05_rv_list);
        btnAdd = findViewById(R.id.exercise_05_btn_add);
        btnRemove = findViewById(R.id.exercise_05_btn_remove);
        tvTotalUser = findViewById(R.id.exercise_05_tv_total_user);

        final UserAdapter userAdapter = new UserAdapter(users);
        rvUser.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvUser.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvUser.setAdapter(userAdapter);

        setTotalUserToView();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startIndex = users.size();
                int toIndex = startIndex + TOTAL_USER_USER;
                for (int i = startIndex; i < toIndex; i++) {
                    String userName = "User " + i;
                    String userEmail = "user" + i + "@tdtu.edu.vn";
                    User newUser = new User(userName, userEmail);
                    users.add(newUser);
                }
                setTotalUserToView();
                userAdapter.notifyDataSetChanged();
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (users.size() == 0) {
                    Toast.makeText(MainActivity.this, "List of users is empty", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                for (int i = 0; i < TOTAL_USER_USER; i++) {
                    users.remove(users.size() - 1);
                }
                setTotalUserToView();
                userAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setTotalUserToView() {
        tvTotalUser.setText("Total user: " + users.size());
    }
}