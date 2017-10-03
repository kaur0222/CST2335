package com.example.admin.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String StartActivity = "StartActivity";
   protected final int requestCode=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button b2 = (Button) findViewById(R.id.button);

     b2.setOnClickListener((v) -> {
         Intent i = new Intent (StartActivity.this,ListItemActivity.class);
         startActivityForResult(i,10);
        }) ;



    }
    protected void onActivityResult(int requestCode, int responseCode, Intent data){

        if(requestCode == 10){
            Log.i(StartActivity,"Retuned to StartActivity.onActivityResult");
            if ( requestCode == Activity.RESULT_OK){
               String messagePassed = data.getStringExtra("Response");
               String text ="ListItemsActivity passed: My information to share" + messagePassed;
               Toast toast = Toast.makeText(StartActivity.this,text,Toast.LENGTH_SHORT);
               toast.show();
            }
        }
    }

    protected void onStart() {
        super.onStart();
    }


    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }
    protected void onDestroy() {
        super.onDestroy();
    }
}



