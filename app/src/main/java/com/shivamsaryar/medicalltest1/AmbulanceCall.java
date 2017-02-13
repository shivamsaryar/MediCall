package com.shivamsaryar.medicalltest1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AmbulanceCall extends AppCompatActivity {

    Firebase mRootRef;
    Firebase statusRef;
    Button callAmbButton;
    AlertDialog dialog2;
    AlertDialog dialog3;
    SQLiteDatabase ambDb;
    Bundle mailBundle;
    String userMail;
    String curr_status;
    String d_name;
    String d_phone;
    String d_email;
    String d_address;
    String d_location;
    EditText addUser;
    Boolean addrOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Call Ambulance");
        setContentView(R.layout.activity_ambulance_call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Call Ambulance");
        addrOk = false;
        mailBundle = getIntent().getExtras();
        userMail = mailBundle.getString("userEmail");
        callAmbButton = (Button)findViewById(R.id.buttonCallAmbulance);
        addUser = (EditText)findViewById(R.id.editTextpAddressEnter);
        Firebase.setAndroidContext(this);
        mRootRef = new Firebase("https://medicall.firebaseio.com/user_request");


        try{
            ambDb = this.openOrCreateDatabase("MedicallApp",MODE_PRIVATE,null);
            Cursor newCursor;
            newCursor = ambDb.rawQuery("Select * from User where email = '"+ userMail +"'",null);
            if(newCursor.moveToFirst()){
                d_name = newCursor.getString(newCursor.getColumnIndex("Name"));
                d_phone = newCursor.getString(newCursor.getColumnIndex("Phone"));
                d_email = newCursor.getString(newCursor.getColumnIndex("Email"));
                //d_address = newCursor.getString(newCursor.getColumnIndex("Address"));
                //d_location = newCursor.getString(newCursor.getColumnIndex("Location"));
                //Toast.makeText(getApplicationContext(),"db ok",Toast.LENGTH_LONG).show();
            }
        }
        catch(SQLiteException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
                                                                    //First alert dialog
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        String dialog_title = "Driver busy";
        String dialog_message = "All drivers are currently busy. Please try again after some time.";
        builder2.setMessage(dialog_message);
        builder2.setTitle(dialog_title);
        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Intent backIntent = new Intent(getApplicationContext(), MainDrawerActivity.class);
                backIntent.putExtra("myEmail",userMail);
                startActivity(backIntent);
            }
        });
        dialog2 = builder2.create();
        dialog2.setCanceledOnTouchOutside(false);
                                                                    //Second alert dialog
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setTitle("Request sent");
        builder3.setMessage("A request has been sent to the driver. The driver will contact you soon.");
        builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Intent backIntent = new Intent(getApplicationContext(), MainDrawerActivity.class);
                backIntent.putExtra("myEmail", userMail);
                startActivity(backIntent);
            }
        });
        dialog3 = builder3.create();

                               //Check driver status from Firebase
        statusRef = mRootRef.child("driver_status");
        callAmbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_address = addUser.getText().toString();
                d_location = "13.3525,74.7928";
                if(d_address=="" || addUser.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter an address",Toast.LENGTH_LONG).show();
                    addrOk = false;
                }
                else{
                    addrOk = true;
                }
                statusRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        curr_status = dataSnapshot.getValue().toString();
                        if(addrOk==true){
                            if(curr_status.equals("Busy")){
                                dialog2.show();
                            }
                            else{
                                mRootRef.child("name").setValue(d_name);
                                mRootRef.child("phone").setValue(d_phone);
                                mRootRef.child("address").setValue(d_address);
                                mRootRef.child("location").setValue(d_location);
                                dialog3.show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

    }
}
