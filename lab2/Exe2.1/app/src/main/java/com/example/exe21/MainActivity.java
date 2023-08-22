package com.example.exe21;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    EditText txtView1;
    Button btnLogin;
    TextView tvXinChao;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView1 = findViewById(R.id.txtView1);
        btnLogin = findViewById(R.id.btnLogin);
        tvXinChao = findViewById(R.id.tvXinChao);
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmailInput = txtView1.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Email",EmailInput);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1 && resultCode == 2){
            txtView1.setText(data.getStringExtra("Name").toString());
        }
        tvXinChao.setText("Hen gap lai");
        btnLogin.setVisibility(View.INVISIBLE);
        txtView1.setEnabled(false);

    }

}