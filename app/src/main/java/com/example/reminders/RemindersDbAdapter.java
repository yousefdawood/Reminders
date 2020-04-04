package com.example.reminders;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class RemindersDbAdapter {

    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMPORTANT = "important";
    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;
    public static final int INDEX_IMPORTANT = INDEX_ID + 2;
    //used for logging
    private static final String TAG = "RemindersDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "dba_remdrs";
    private static final String TABLE_NAME = "tbl_remdrs";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_CONTENT + " TEXT, " +
                    COL_IMPORTANT + " INTEGER );";


    public RemindersDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }
    //close
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }


    //TODO implement the function createReminder() which take the name as the content of the
    // reminder and boolean important...note that the id will be created for you automatically
    public void createReminder(String name, boolean important) {
        int imp = (important) ? 1:0;
        String query =  "Insert Into "+TABLE_NAME+" ("+COL_CONTENT+","+COL_IMPORTANT+")\n" +
                "Values ('"+name+"', "+imp+" );";
        mDb.execSQL(query);
    }
    //TODO overloaded to take a reminder
    public long createReminder(Reminder reminder) {

        String query ="Insert Into "+TABLE_NAME+" ("+","+COL_CONTENT+","+COL_IMPORTANT+")\n"+
                "Values ("+reminder.getId()+",'"+reminder.getContent()+"', "+reminder.getImportant()+" );";
        mDb.execSQL(query);
        //This is a dummy value
        long value = new Long(4);
        return value;
    }

    //TODO implement the function fetchReminderById() to get a certain reminder given its id
    public Reminder fetchReminderById(int id) {
        String query = "Select * from "+TABLE_NAME+" Where "+COL_ID+" = "+id;
        Cursor resultSet = mDb.rawQuery(query, null);
        resultSet.moveToFirst();
        int mId = Integer.parseInt(resultSet.getString(INDEX_ID));
        String mContent = resultSet.getString(INDEX_CONTENT);
        int mImportant = Integer.parseInt(resultSet.getString(INDEX_IMPORTANT));
        return new Reminder(mId,mContent,mImportant);
    }


    //TODO implement the function fetchAllReminders() which get all reminders
    public Cursor fetchAllReminders() {
        String query = "Select * from "+TABLE_NAME;
        Cursor resultSet = mDb.rawQuery(query,null);
        return resultSet;
    }

    //TODO implement the function updateReminder() to update a certain reminder
    public void updateReminder(Reminder reminder) {
        String query =  "UPDATE "+TABLE_NAME +
                " SET "+COL_CONTENT+ " = '" +reminder.getContent()+ "', " +COL_IMPORTANT+ " = " +reminder.getImportant() +
                " WHERE "+COL_ID+" = "+reminder.getId()+";";
        mDb.execSQL(query);
    }
    //TODO implement the function deleteReminderById() to delete a certain reminder given its id
    public void deleteReminderById(int nId) {
        String query = "Delete from "+TABLE_NAME+" Where "+COL_ID+" ="+nId;
        mDb.execSQL(query);
    }

    //TODO implement the function deleteAllReminders() to delete all reminders
    public void deleteAllReminders() {
        String query = "Delete from "+TABLE_NAME;
        mDb.execSQL(query);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


}