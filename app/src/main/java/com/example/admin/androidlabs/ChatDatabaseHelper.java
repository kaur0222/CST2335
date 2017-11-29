package com.example.admin.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Admin on 11/22/2017.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static String TAG = ChatDatabaseHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "Messages.db";
    public static final int VERSION_NUM = 1;
    public static final String CHAT_TABLE ="CHAT_TABLE";
    SQLiteDatabase mdb;
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "Message";

    private final Context mctx;
    public static final String[] CHAT_FIELDS = new String[]{
            KEY_ID,
            KEY_MESSAGE
    };

    public ChatDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
        this.mctx = context;
    }

    private static final String CREATE_TABLE_CHAT =
            "create table " + CHAT_TABLE + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_MESSAGE + " text" + "); ";



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CHAT);
    }


       public void onUpgrade(SQLiteDatabase db, int i , int i1){
       db.execSQL(" DROP TABLE IF EXISTS " + CHAT_TABLE);
       onCreate(db);


}
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
    public Cursor getChatMssages(){
        return mdb.query(CHAT_TABLE, CHAT_FIELDS, null, null, null, null, null);
    }

    public String getMeessageFromCursor(Cursor cursor){
        String msg = cursor.getString(cursor.getColumnIndex(KEY_MESSAGE));
        return msg;

    }

    public void insert(ContentValues content){
        mdb.insert(CHAT_TABLE, null, content);

    }



}
