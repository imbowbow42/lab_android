package com.example.exe22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    EditText edLink;
    Button btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();


    }

    private void initView() {
        edLink = this.findViewById(R.id.edLink);
        btnOpen = this.findViewById(R.id.btnOpen);

        btnOpen.setOnClickListener(view -> {
            try{
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + edLink.getText().toString()));
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                String title = "Vui lòng chọn trình duyệt";

                Intent chooser = Intent.createChooser(intent, title);
                startActivity(chooser);

                /*if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }*/

            }
            catch (ActivityNotFoundException ex){
                Log.e(ex.getClass().getSimpleName(),ex.getMessage());
            }
        });
    }
}