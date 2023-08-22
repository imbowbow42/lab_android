package com.example.exe71;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int READ_CONTACT_CODE = 100;
    private static final int CALL_CONTACT_CODE = 2;
    private RecyclerView rView;
    private List<Contact> contacts  = new ArrayList<>();;
    private MyCustomAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rView = findViewById(R.id.reView);
        if(hasListContactPermission())
        {
            getContactList();
        }
        else
        {
            requestListContactPermission();
        }
    }

    private boolean hasListContactPermission() {
        boolean result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestListContactPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_CONTACTS
                },READ_CONTACT_CODE);
    }

    private void getContactList() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC ";
        Cursor cursor = getContentResolver().query(
                uri,null,null,null,sort
        );

        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactsContract.Contacts._ID
                ));

                String name = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactsContract.Contacts.DISPLAY_NAME
                ));
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";
                Cursor phoneCursor = getContentResolver().query(
                        uriPhone,null,selection
                        ,new String[]{id},null
                );

                if(phoneCursor.moveToNext()){
                    String number = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));

                    Contact contact = new Contact();
                    contact.setName(name);
                    contact.setNumber(number);

                    contacts.add(contact);
                    phoneCursor.close();
                }
            }

            cursor.close();
        }

        rView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyCustomAdapter(this,contacts);
        rView.addItemDecoration(new DividerItemDecoration(this,RecyclerView.VERTICAL));
        rView.setAdapter(adapter);
        adapter.setOnClick((MyCustomAdapter.OnItemClicked) MainActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == READ_CONTACT_CODE && grantResults.length > 0)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted.", Toast.LENGTH_SHORT).show();
                getContactList();
            }
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
            }
        }

        if(requestCode == CALL_CONTACT_CODE && grantResults.length > 0)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted.", Toast.LENGTH_SHORT).show();
            }
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_item).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Intent i = new Intent(this,AddContactActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean hasMakeContactPermission() {
        boolean result = ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestMakePhoneContactPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.CALL_PHONE
                },CALL_CONTACT_CODE);
    }


    public void onItemClick(int position) {
        if(hasMakeContactPermission())
        {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + contacts.get(position).getNumber()));
            startActivity(intent);
        }
        else
        {
            requestMakePhoneContactPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG","Resume");
        contacts.clear();
        getContactList();
    }
}