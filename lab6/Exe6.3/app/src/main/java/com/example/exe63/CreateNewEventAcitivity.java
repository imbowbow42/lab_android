package com.example.exe63;

import static com.example.exe63.MainActivity.INTENT_EDIT_EVENT_ACTION;
import static com.example.exe63.MainActivity.INTENT_EVENT_ACTION;
import static com.example.exe63.MainActivity.INTENT_EVENT_ID;
import static com.example.exe63.MainActivity.INTENT_NEW_EVENT_ACTION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateNewEventAcitivity extends AppCompatActivity {

    private EditText etName;
    private TextView etNameError;
    private EditText etPlace;
    private EditText etDate;
    private EditText etTime;
    private EventDatabaseHelper mDbHelper;
    boolean isNew = false;
    private Event selectedEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_event);

        mDbHelper = new EventDatabaseHelper(this);

        // bind ui controls
        etName = findViewById(R.id.name);
        etNameError = findViewById(R.id.name_error);
        etPlace = findViewById(R.id.place);
        etDate = findViewById(R.id.date);
        etTime = findViewById(R.id.time);

        // invisibility error messages
        etNameError.setVisibility(View.INVISIBLE);

        // get intent that started this activity
        Intent callerIntent = getIntent();

        // retrieve map of extended data from the intent
        Bundle callerBundle = callerIntent.getExtras();

        // get given key-value map
        String action = callerBundle.getString(INTENT_EVENT_ACTION, INTENT_NEW_EVENT_ACTION);

        if (action.equals(INTENT_EDIT_EVENT_ACTION)) {
            isNew = false;
            int eventId = callerBundle.getInt(INTENT_EVENT_ID);
            selectedEvent = mDbHelper.getEvent(eventId);
            populateUI(selectedEvent);
        } else {
            isNew = true;
        }

        etPlace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    showPlaceSelectionDialog();
                }

                return true;
            }
        });

        etDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    showDateSelectionDialog();
                }

                return true;
            }
        });

        etTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    showTimeSelectionDialog();
                }

                return true;
            }
        });
    }

    private void populateUI(Event selectedEvent) {
        etName.setText(selectedEvent.getName());
        etPlace.setText(selectedEvent.getPlace());
        etDate.setText(selectedEvent.getDate());
        etTime.setText(selectedEvent.getTime());
    }

    private void showTimeSelectionDialog() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                etTime.setText(hourOfDay + ":" + minute);
            }
        }, mHourOfDay, mMinute, false);
        timePickerDialog.show();
    }

    private void showDateSelectionDialog() {
        // get current date
        Calendar c = Calendar.getInstance();
        int startYear = c.get(Calendar.YEAR);
        int startMonth = c.get(Calendar.MONTH);
        int startDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                StringBuilder stringBuilder = new StringBuilder(dayOfMonth).append("/").append(monthOfYear)
                        .append("/").append(year);
                etDate.setText(stringBuilder.toString());
            }
        }, startYear, startMonth, startDay);
        datePickerDialog.show();
    }

    private void showPlaceSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select place");
        builder.setSingleChoiceItems(PlaceConstant.places, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etPlace.setText(PlaceConstant.places[i]);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSave) {
            Intent exercise02ActivityIntent = new Intent();
            String eventName = etName.getText().toString();
            String eventPlace = etPlace.getText().toString();
            String eventDate = etDate.getText().toString();
            String eventTime = etTime.getText().toString();

            if (eventName.trim().isEmpty()) {
                etNameError.setText("Event name cannot be empty");
                etNameError.setVisibility(View.VISIBLE);
            } else {
                if (isNew) {
                    Event newEvent = new Event(eventName, eventPlace, eventDate, eventTime);
                    mDbHelper.addEvent(newEvent);
                } else {
                    selectedEvent.setName(eventName);
                    selectedEvent.setPlace(eventPlace);
                    selectedEvent.setDate(eventDate);
                    selectedEvent.setTime(eventTime);

                    mDbHelper.updateEvent(selectedEvent);
                }
                setResult(Activity.RESULT_OK, exercise02ActivityIntent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}