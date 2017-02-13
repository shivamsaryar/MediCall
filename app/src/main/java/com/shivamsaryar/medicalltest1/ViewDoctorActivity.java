package com.shivamsaryar.medicalltest1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDoctorActivity extends AppCompatActivity {

    Button callButton;
    Button getDirectionButton;
    Button doneButton;
    TextView nameDisplay;
    TextView phoneDisplay;
    TextView emailDisplay;
    TextView addressDisplay;
    SQLiteDatabase displayDB;
    Bundle intentValues;
    String nameSelected;
    String d_phone;
    String d_email;
    String d_address;
    String d_location;
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Doctor Details");
        setContentView(R.layout.activity_view_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Doctor Details");

        nameDisplay = (TextView) findViewById(R.id.textViewNameDisplay);
        phoneDisplay = (TextView)findViewById(R.id.textViewPhoneDisplay);
        emailDisplay = (TextView)findViewById(R.id.textViewEmailDisplay);
        addressDisplay = (TextView)findViewById(R.id.textViewAddressDisplay);
        nameDisplay.setText(null);
        phoneDisplay.setText(null);
        emailDisplay.setText(null);
        addressDisplay.setText(null);
        doneButton = (Button)findViewById(R.id.doneButton);
        callButton = (Button)findViewById(R.id.buttonCallDoctor);
        getDirectionButton = (Button)findViewById(R.id.buttonGetDir);

        intentValues = getIntent().getExtras();
        nameSelected = intentValues.getString("SelectedName");
        mail = intentValues.getString("userEmail",mail);
        nameDisplay.setText(nameSelected);

        try{
            displayDB = this.openOrCreateDatabase("MedicallApp",MODE_PRIVATE,null);
            Cursor newCursor;
            newCursor = displayDB.rawQuery("Select * from Doctor where name = '"+nameSelected+"'",null);
            if(newCursor.moveToFirst()){
                //String d_name = newCursor.getString(newCursor.getColumnIndex("Name"));
                d_phone = newCursor.getString(newCursor.getColumnIndex("Phone"));
                d_email = newCursor.getString(newCursor.getColumnIndex("Email"));
                d_address = newCursor.getString(newCursor.getColumnIndex("Address"));
                d_location = newCursor.getString(newCursor.getColumnIndex("Location"));
                phoneDisplay.setText(d_phone);
                emailDisplay.setText(d_email);
                addressDisplay.setText(d_address);
            }

        }
        catch(SQLiteException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        getDirectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getApplicationContext(),DoctorMapsActivity.class);
                mapIntent.putExtra("AddressTitle",d_address);
                mapIntent.putExtra("AddressLocation",d_location);
                startActivity(mapIntent);
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialerIntent = new Intent(Intent.ACTION_DIAL);
                dialerIntent.setData(Uri.parse("tel:"+d_phone));
                startActivity(dialerIntent);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent doneIntent = new Intent(getApplicationContext(),MainDrawerActivity.class);
                doneIntent.putExtra("myEmail", mail);
                startActivity(doneIntent);
            }
        });
    }
}
