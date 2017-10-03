package com.example.admin.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
     Button b1;
     String demail= "email@domain.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       final SharedPreferences email = getPreferences(Context.MODE_PRIVATE);
        final EditText editT = (EditText) findViewById(R.id.EdiTextView);
        String address = email.getString("DefaultEmail", demail);
        editT.setText(address);

        b1 = (Button) findViewById(R.id.Button);
        email.getString("DefaultEmail", demail);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputemail = editT.getEditableText().toString();
                SharedPreferences.Editor write = email.edit();
                write.putString("DefaultEmail",inputemail);
                write.commit();

                Intent startIntent = new Intent(LoginActivity.this,StartActivity.class);
                startActivity(startIntent);
            }
        });

    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }



    protected void onResume() {

        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }




}




