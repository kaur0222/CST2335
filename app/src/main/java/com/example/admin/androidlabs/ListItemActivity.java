package com.example.admin.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";
    private ImageButton iButton;
    Switch Switch;
    boolean isChecked = false;
    CheckBox cbox;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        iButton = (ImageButton) findViewById(R.id.Image);
        iButton.setOnClickListener((v) -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iButton.setImageBitmap(imageBitmap);
        }


        Switch = (Switch) findViewById(R.id.Switch1);
        Switch.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (!isChecked) {
                CharSequence text = "Switch is off";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            } else {
                CharSequence text = "Switch is on";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }

        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(ListItemActivity.this);
        cbox = (CheckBox) findViewById(R.id.Checkbox);
        cbox.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            if (isChecked) {
                builder.setMessage(R.string.dialog_message);
                builder.setTitle(R.string.dialog_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("response","This is my response");
                        setResult(Activity.RESULT_OK,resultIntent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel",(dialog, id)->{

                })
                        .show();
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
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }
}






