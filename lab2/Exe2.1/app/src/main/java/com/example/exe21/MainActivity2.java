package com.example.exe21;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    public static String KEY_FULL_NAME_RESULT = "KEY_FULL_NAME_RESULT";
    EditText txtView2;
    TextView txtShow;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtView2 = findViewById(R.id.txtView2);
        txtShow = findViewById(R.id.txtShow);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        String Email = intent.getStringExtra("Email");
        txtShow.setText(new StringBuilder().append("Xin chao").append(Email.toString()).append("vui long nhap ten").toString());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity2.this, MainActivity.class);
                intent1.putExtra("Name",txtView2.getText().toString());
                setResult(2,intent1);
                finish();
            }
        });
    }
}