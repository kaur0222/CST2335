package com.example.admin.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    protected ChatDatabaseHelper chatHelper ;
    protected SQLiteDatabase dbd;
    EditText editTxt;
    Button sendBtn;
    ArrayList<String> storeChat = new ArrayList<String> ();
    protected static final String ACTIVITY_NAME = "ChatWindow";
    ChatAdapter messageAdapter;
    ContentValues newValues = new ContentValues();
    TextView message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        //setContentView(R.layout.);
        Resources resources = getResources();

        sendBtn = (Button)findViewById(R.id.buttonSend);
        editTxt = (EditText)findViewById(R.id.editTextChat);
        Context context = getApplicationContext();

         final ListView list = (ListView)findViewById(R.id.showListView);
        messageAdapter =new ChatAdapter( this );
        list.setAdapter (messageAdapter);
        chatHelper = new ChatDatabaseHelper(context);

        newValues = new ContentValues();



        chatHelper.open();

        Cursor cursor = chatHelper.getChatMssages();
        if(cursor.moveToFirst()){
            do{
                String msg = chatHelper.getMeessageFromCursor(cursor);
                Log.i(ACTIVITY_NAME, "SQL Message: " +  msg);
                Log.i(ACTIVITY_NAME, "Cursor's column count=" + cursor.getColumnCount());
                storeChat.add(msg);
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
            messageAdapter.notifyDataSetChanged();

        }

        for (int i=0; i<cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, cursor.getColumnName(i));

        }

        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Log.v("EditText", editTxt.getText().toString());
                String chatString = editTxt.getText().toString();
                storeChat.add(chatString);
                messageAdapter.notifyDataSetChanged();
                editTxt.setText("");
                newValues.put("message" , chatString);
                chatHelper.insert(newValues);
            }
        });

    }
    protected void onDestroy(){
        super.onDestroy();
        if(chatHelper != null ){
            chatHelper.close();
        }
    }




    private class ChatAdapter extends ArrayAdapter<String>
    {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return storeChat.size();
        }

        public String getItem(int position){
            return storeChat.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            message = (TextView)result.findViewById(R.id.messageText);
            message.setText(   getItem(position)  ); // get the string at position
            //position++;
            return result;

        }

    }

}

