package com.example.admin.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ChatWindow extends Activity {

    //private static SQLiteDatabase chatDB;
    Button sendBtn;
    EditText editTxt;
    ArrayList<String> chatList = new ArrayList<String> ();
    ChatAdapter chatAdapter;
    TextView message;
    boolean isTab;
    FrameLayout frameLayout;
    int deleteId;
    long deleteBDid;
    ChatWindow ownActivity;
    Cursor cursor;

    protected static final String ACTIVITY_NAME = "ChatWindow";
    ChatDatabaseHelper chatHelper;
    ContentValues newValues = new ContentValues();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        Resources resources = getResources();
        Context context = getApplicationContext();
        frameLayout = (FrameLayout)findViewById(R.id.entryType);


        //phone
        if(frameLayout == null){
            Log.i(ACTIVITY_NAME, "frame is not loaded");
            isTab = false;
        }
        //tab
        else{
            Log.i(ACTIVITY_NAME, "frame is loaded");
            isTab = true;
        }

        final ListView list = (ListView)findViewById(R.id.listViewChat);
        chatAdapter = new ChatAdapter( this );
        list.setAdapter (chatAdapter);
        editTxt = (EditText)findViewById(R.id.editTextChat);
        sendBtn = (Button)findViewById(R.id.buttonSend);

        chatHelper = new ChatDatabaseHelper(context);
        newValues = new ContentValues();
        chatHelper.open();
        cursor = chatHelper.getChatMessages();

        //if (result != null && result.moveToFirst());
        if(cursor.moveToFirst()){
            do{
                String msg = chatHelper.getMessageFromCursor(cursor);
                Log.i(ACTIVITY_NAME, "SQL Message: " +  msg);
                Log.i(ACTIVITY_NAME, "Cursor's column count=" + cursor.getColumnCount());
                chatList.add(msg);
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
            chatAdapter.notifyDataSetChanged();

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
                chatList.add(chatString);
                chatAdapter.notifyDataSetChanged();
                editTxt.setText("");
                newValues.put("message" , chatString);
                chatHelper.insert(newValues);
                cursor = chatHelper.getChatMessages();
            }
        });

        ownActivity = this;

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = chatAdapter.getItem(position);
                String  str = (String)o; //Default String Adapter

                Toast.makeText(getBaseContext(),str,Toast.LENGTH_SHORT).show();



                if(isTab){

                    MessageFragment fragment = new MessageFragment();
                    fragment.setChatWindow(ownActivity);
                    Bundle bundle = new Bundle();
                    bundle.putString("chatMsg", str);
                    bundle.putInt("Id",position);
                    fragment.setArguments(bundle);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.entryType, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                /* sending the activity to the newly created MessageDetails class */
                else{
                    Intent intent = new Intent(getApplicationContext(), MessageDetails.class);
                    intent.putExtra("chatMsg",str);
                    intent.putExtra("Id", position);
                    startActivityForResult(intent, 10);
                }
            }
        });


    }



    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode == 10  && responseCode == 10) {
            // received data from fragment to delete the message
            Bundle bundle = data.getExtras();
            deleteId = bundle.getInt("deleteMsgId");
            //deleteBDid = bundle.getLong("deleteDBMsgId");
            deleteBDid = chatAdapter.getItemId(deleteId);
            chatHelper.remove(deleteBDid);
            chatList.remove(deleteId);
            cursor = chatHelper.getChatMessages();
            chatAdapter.notifyDataSetChanged();
            //deleteMessage(deleteId);
            //Log.i(String.valueOf(ChatWindow.this), String.valueOf(chatList.size()));
        }
    }

    public void deleteMessage(int id){
        long deleteDBIdTab = chatAdapter.getItemId(id);
        chatHelper.remove(deleteDBIdTab);
        chatList.remove(id);
        chatAdapter.notifyDataSetChanged();

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
            return chatList.size();
        }

        public String getItem(int position){
            return chatList.get(position);
        }

        public long getItemId(int position){
            cursor.moveToPosition(position);
            int  id = chatHelper.getIdFromCursor(cursor);
            return id;
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
            return result;

        }
    }

}