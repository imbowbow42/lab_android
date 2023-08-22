package com.example.exe63;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EventDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Lab07Exercise03DB";
    private static final String TABLE_EVENTS = "events";

    private static final String COLUMN_EVENT_ID = "ID";
    private static final String COLUMN_EVENT_NAME = "NAME";
    private static final String COLUMN_PLACE = "PLACE";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_TIME = "TIME";
    private static final String COLUMN_DONE = "DONE";

    public EventDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(TAG, "EventDatabaseHelper.onCreate ... ");
        // Create Event tables.
        String script = "CREATE TABLE " + TABLE_EVENTS + "("
                + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EVENT_NAME + " TEXT NOT NULL,"
                + COLUMN_PLACE + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_DONE + " INTEGER DEFAULT 0)";

        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "EventDatabaseHelper.onUpgrade ... ");

        // Drop table if exist.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

        // create db.
        onCreate(db);
    }

    public void createDefaultEvents() {
        int count = this.getEventsCount();
        if (count == 0) {
            Event event1 = new Event("Sinh hoat chu nhiem db", "c120", "09/03/2020", "04:43");
            Event event2 = new Event("Huong dan luan van", "c120", "09/03/2020", "04:43");
            this.addEvent(event1);
            this.addEvent(event2);
        }
    }


    public void addEvent(Event event) {
        Log.i(TAG, "EventDatabaseHelper.addEvent ... " + event.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, event.getName());
        values.put(COLUMN_PLACE, event.getPlace());
        values.put(COLUMN_DATE, event.getDate());
        values.put(COLUMN_TIME, event.getTime());
        values.put(COLUMN_DONE, event.getCompleted());

        // Insert a new row to table
        db.insert(TABLE_EVENTS, null, values);

        // Close database connection.
        db.close();
    }


    public Event getEvent(int id) {
        Log.i(TAG, "EventDatabaseHelper.getEvent ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[]{
                        COLUMN_EVENT_ID,
                        COLUMN_EVENT_NAME,
                        COLUMN_PLACE,
                        COLUMN_DATE,
                        COLUMN_TIME,
                        COLUMN_DONE
                }, COLUMN_EVENT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Event event = new Event();
        event.setId(cursor.getInt(0));
        event.setName(cursor.getString(1));
        event.setPlace(cursor.getString(2));
        event.setDate(cursor.getString(3));
        event.setTime(cursor.getString(4));
        event.setCompleted(cursor.getInt(5) == 1);
        return event;
    }

    public List<Event> getAllEvents(boolean complete) {
        Log.i(TAG, "EventDatabaseHelper.getAllNotes ... ");

        int completeNumber = complete ? 1 : 0;

        List<Event> eventList = new ArrayList<Event>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;
        selectQuery += " WHERE " + COLUMN_DONE + " = " + completeNumber;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setName(cursor.getString(1));
                event.setPlace(cursor.getString(2));
                event.setDate(cursor.getString(3));
                event.setTime(cursor.getString(4));
                event.setCompleted(cursor.getInt(5) == 1);

                eventList.add(event);

            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        // return note list
        return eventList;
    }

    public int getEventsCount() {
        Log.i(TAG, "EventDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateEvent(Event updateNote) {
        Log.i(TAG, "EventDatabaseHelper.updateEvent ... " + updateNote.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, updateNote.getName());
        values.put(COLUMN_DATE, updateNote.getDate());
        values.put(COLUMN_DONE, updateNote.getCompleted());

        // updating row
        return db.update(TABLE_EVENTS, values, COLUMN_EVENT_ID + " = ?",
                new String[]{String.valueOf(updateNote.getId())});
    }

    public void deleteEvent(Event event) {
        Log.i(TAG, "EventDatabaseHelper.updateNote ... " + event.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, COLUMN_EVENT_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();
    }

    public void deleteAllEvents() {
        Log.i(TAG, "EventDatabaseHelper.deleteAllEvents ... ");

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_EVENTS);
        db.close();
    }
}
