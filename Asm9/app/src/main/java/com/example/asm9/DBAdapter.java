package com.example.asm9;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    public static final String KEY_URL = "url";
    public static final String KEY_CONTENT = "content";

    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "webURLDB.db";
    private static final String TABLE_NAME = "Contacts";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CREATE = "create table " +
            TABLE_NAME +
            " (" + KEY_URL + " text not null, "
            + KEY_CONTENT + " text not null)";

    private static final String TABLE_DELETE = "drop table if exists " + TABLE_NAME;

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqlLiteDb;

    public DBAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);

    }

    // Here we define the DatabaseHelper class
    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(TABLE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ". All old data will be deleted! ");

// Here we remove the table
            db.execSQL(TABLE_DELETE);

// Here we create the table again
            onCreate(db);

        }
    }

    // This method will open the database
    public DBAdapter open() {
        sqlLiteDb = dbHelper.getWritableDatabase();
        return this;
    }

    // This method will close the database
    public void close() {
        dbHelper.close();
    }

    // Here we add a customer to the database
    public long addWeb(String url, String content) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_URL, url);
        initialValues.put(KEY_CONTENT, content);

        return sqlLiteDb.insert(TABLE_NAME, null, initialValues);
    }

    public void deleteDB() {
        sqlLiteDb.execSQL(TABLE_DELETE);
    }
    // This method will retrieve all customers
    public Cursor getAllWebs() {

        return sqlLiteDb.query(TABLE_NAME, new String[] {
                KEY_URL,
                KEY_CONTENT}, null, null, null, null, null);
    }

/*// This method will retrieve a particular customer

    public Cursor getContact(long rowID) {

        Cursor cursor = sqlLiteDb.query(true, TABLE_NAME, null*//*new String[] {
                        KEY_ROW_ID, KEY_LAST_NAME, KEY_FIRST_NAME, KEY_PHONE, KEY_EDUCATION, KEY_HOBBIES, KEY_DATE }*//*, KEY_ROW_ID + "=" + rowID,
                null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }*/

    public Cursor getWebByUrl(String url) {
        String[] query = new String[1];
        query[0] = url;
        Cursor cursor = sqlLiteDb.query(true, TABLE_NAME, new String[] {
                        KEY_URL,
                        KEY_CONTENT,
                         }, KEY_URL+"=?",
                query, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

}
