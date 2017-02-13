package com.shivamsaryar.medicalltest1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.Map;

public class AmbulanceHome extends AppCompatActivity {

    Firebase myRootRef;
    Button acceptButton;
    Button clearButton;
    Bundle myBundle1;
    EditText pNameText;
    EditText pPhoneText;
    EditText pAddressText;
    String pName;
    String pPhone;
    String pAddress;
    String pLocation;
    Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("User Requests");
        setContentView(R.layout.activity_ambulance_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("User Requests");
        Firebase.setAndroidContext(this);
        pName = null;
        pPhone = null;
        pAddress = null;
        pLocation = null;
        myRootRef = new Firebase("https://medicall.firebaseio.com/user_request");
        acceptButton = (Button)findViewById(R.id.buttonAcceptReq);
        clearButton = (Button)findViewById(R.id.buttonClearReq);
        callButton = (Button)findViewById(R.id.buttonPCall);
        pNameText = (EditText)findViewById(R.id.editTextPatName);
        pPhoneText = (EditText)findViewById(R.id.editTextPatPhone);
        pAddressText = (EditText)findViewById(R.id.editTextPatAddress);
        myBundle1 = getIntent().getExtras();
        pName = myBundle1.getString("pName");
        pPhone = myBundle1.getString("pPhone");
        pAddress = myBundle1.getString("pAddress");
        pLocation = myBundle1.getString("pLocation");
                                                                //Set edit text as the requesting user's data
        pNameText.setText(pName);
        pPhoneText.setText(pPhone);
        pAddressText.setText(pAddress);
                                                                //Clear requests
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pNameText.setText(null);
                pPhoneText.setText(null);
                pAddressText.setText(null);
                pLocation = null;
                myRootRef.child("name").setValue("null");
                myRootRef.child("phone").setValue("null");
                myRootRef.child("address").setValue("null");
                myRootRef.child("location").setValue("null");
                myRootRef.child("driver_status").setValue("Idle");
                Intent nIntent = new Intent(getApplicationContext(),DriverHome.class);
                startActivity(nIntent);
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRootRef.child("driver_status").setValue("Busy");
                Intent mapIntent2 = new Intent(getApplicationContext(),DoctorMapsActivity.class);
                mapIntent2.putExtra("AddressTitle", pAddress);
                mapIntent2.putExtra("AddressLocation", pLocation);
                startActivity(mapIntent2);
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pPhoneText.getText().toString().equals("")){
                    Intent dialerIntent = new Intent(Intent.ACTION_DIAL);
                    dialerIntent.setData(Uri.parse("tel:" + pPhone));
                    startActivity(dialerIntent);
                }

            }
        });
    }
}
