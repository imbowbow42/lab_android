package com.example.exe62;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String INTERNAL_FILE_NAME = "internal_file.txt";

    private static final String EXTERNAL_FILE_NAME = "external_file.txt";
    private String TAG = MainActivity.class.getName();

    private EditText message;
    private Button btnReadInternal;
    private Button btnReadExternal;
    private Button btnWriteInternal;
    private Button btnWriteExternal;
    private TextView txtInternalFilePath;
    private TextView txtExternalFilePath;

    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = findViewById(R.id.message);
        btnReadInternal = findViewById(R.id.btnReadInternal);
        btnReadExternal = findViewById(R.id.btnReadExternal);
        btnWriteInternal = findViewById(R.id.btnWriteInternal);
        btnWriteExternal = findViewById(R.id.btnWriteExternal);
        txtInternalFilePath = findViewById(R.id.txtInternalFilePath);
        txtExternalFilePath =findViewById(R.id.txtExternalFilePath);

        btnReadInternal.setOnClickListener(this);
        btnReadExternal.setOnClickListener(this);
        btnWriteInternal.setOnClickListener(this);
        btnWriteExternal.setOnClickListener(this);

        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        txtInternalFilePath.setText("/data/data/" + getPackageName() + "/" + INTERNAL_FILE_NAME);
        txtExternalFilePath.setText(path + EXTERNAL_FILE_NAME);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnWriteInternal:
                try {
                    writeInternalFileContent();
                    Toast.makeText(getBaseContext(), "Done writing internal file" + INTERNAL_FILE_NAME, Toast.LENGTH_LONG).show();
                    clearMessenger();
                }
                catch (IOException e){
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnReadInternal:
                clearMessenger();
                try {
                    String internalFileContent = readInternalFileContent();
                    message.setText(internalFileContent);
                    Toast.makeText(getApplicationContext(), "Done reading internal file", Toast.LENGTH_LONG).show();
                }
                catch (IOException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnWriteExternal:
                try{
                    writeExternalFile();
                    Toast.makeText(getBaseContext(), "DOne writing external file", Toast.LENGTH_LONG).show();
                    clearMessenger();
                }
                catch (IOException e){
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnReadExternal:
                clearMessenger();
                try {
                    String externalFileContent = readExternalFileContent();
                    message.setText(externalFileContent);
                    Toast.makeText(getApplicationContext(), "Done reading external file", Toast.LENGTH_LONG).show();
                }
                catch (IOException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    private String readExternalFileContent() throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(new File(path + "/"+ EXTERNAL_FILE_NAME))));

        String dataRow = "";
        while ((dataRow = bufferedReader.readLine()) != null){
            stringBuilder.append(dataRow).append("\n");

        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    private void writeExternalFile() throws IOException{
        File myFile = new File(path +"/" + EXTERNAL_FILE_NAME);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(myFile));

        outputStreamWriter.append(message.getText().toString());
        outputStreamWriter.close();
    }

    private String readInternalFileContent() throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = openFileInput(INTERNAL_FILE_NAME);
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String dataRow = "";

            while ((dataRow = bufferedReader.readLine()) != null) {
                stringBuilder.append(dataRow).append("\n");
            }
            inputStream.close();
        }
        return stringBuilder.toString();
    }

    private void clearMessenger() {
        message.getText().clear();
    }

    private void writeInternalFileContent() throws IOException{
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                openFileOutput(INTERNAL_FILE_NAME, 0));
        outputStreamWriter.write(message.getText().toString());
        outputStreamWriter.close();
    }
}
