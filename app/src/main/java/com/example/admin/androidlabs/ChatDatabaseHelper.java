package com.example.admin.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Admin on 2017-10-11.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static String TAG = ChatDatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "Messages1.db";
    private static final int DATABASE_VERSION = 1;
    public static final String CHAT_TABLE = "CHAT_TABLE";
    private final Context mctx;
    SQLiteDatabase mdb;

    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "Message";

    // string array which will return the chat table fields
    public static final String[] CHAT_FIELDS = new String[]{
            KEY_ID,
            KEY_MESSAGE
    };

    // creating the table chat_table
    private static final String CREATE_TABLE_CHAT =
            "create table " + CHAT_TABLE + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_MESSAGE + " text" + "); ";


    public ChatDatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mctx = context;
    }



    // only creating the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CHAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version "+ oldVersion + " to " + newVersion + ", which " +
                "will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CHAT_TABLE);
        onCreate(sqLiteDatabase);
    }


    // open database
    public ChatDatabaseHelper open() {
        if(mdb == null){
            mdb = getWritableDatabase();
        }

        return this;
    }

    public void close(){
        if(mdb != null){
            mdb.close();
        }
    }

    // retrieving data
    public Cursor getChatMessages(){
        return mdb.query(CHAT_TABLE, CHAT_FIELDS, null, null, null, null, null);
    }

    public String getMessageFromCursor(Cursor cursor){
        String msg = cursor.getString(cursor.getColumnIndex(KEY_MESSAGE));
        return msg;

    }

    public int getIdFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        return id;

    }

    public void insert(ContentValues content){
        mdb.insert(CHAT_TABLE, null, content);

    }

    public void remove(long id){


        int deletedRecrod =  mdb.delete(CHAT_TABLE, "_id" + "=" + id, null);
        Log.i("Deleted ",Integer.toString(deletedRecrod));
    }





}