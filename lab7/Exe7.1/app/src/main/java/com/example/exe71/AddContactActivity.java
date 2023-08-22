package com.example.exe71;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class AddContactActivity extends AppCompatActivity {

    static final int PICK_CONTACT = 1;
    private static int WRITE_CONTACT_CODE = 200;
    private Button save;
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);

        save = findViewById(R.id.saveContact);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstName.getText().toString().isEmpty())
                {
                    Toast.makeText(AddContactActivity.this,"Enter First name", Toast.LENGTH_SHORT).show();
                }
                else if(lastName.getText().toString().isEmpty())
                {
                    Toast.makeText(AddContactActivity.this,"Enter Last name", Toast.LENGTH_SHORT).show();
                }
                else if(phoneNumber.getText().toString().isEmpty())
                {
                    Toast.makeText(AddContactActivity.this,"Enter Phone number", Toast.LENGTH_SHORT).show();
                }
                else if(!phoneNumber.getText().toString().matches("^[0-9]{10}$"))
                {
                    Toast.makeText(AddContactActivity.this,"Enter Valid Phone number", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(hasWriteContactPermission())
                    {
                        saveContact();
                    }
                    else
                    {
                        requestWriteContactPermission();
                    }
                }
            }
        });
    }

    private void requestWriteContactPermission() {
        ActivityCompat.requestPermissions(AddContactActivity.this,
                new String[]{
                        Manifest.permission.WRITE_CONTACTS
                },WRITE_CONTACT_CODE);
    }

    private boolean hasWriteContactPermission() {
        boolean result = ContextCompat.checkSelfPermission(AddContactActivity.this,Manifest.permission.WRITE_CONTACTS) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }



    private void saveContact() {

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);

        String DisplayName = firstName.getText().toString() + " " + lastName.getText().toString();
        String MobileNumber = PhoneNumberUtils.formatNumber(phoneNumber.getText().toString(), Locale.getDefault().getCountry());

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        //------------------------------------------------------ Names
        if (DisplayName != null) {
            ops.add(ContentProviderOperation.newInsert(
                            ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        //------------------------------------------------------ Mobile Number
        if (MobileNumber != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }


        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            Log.d("TAG","saveContact: Saved...");
            Toast.makeText(this, "Contact is saved success! ", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG","saveContact: " + e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == WRITE_CONTACT_CODE && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            saveContact();
        }
        else
        {
            Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
        }
    }
}