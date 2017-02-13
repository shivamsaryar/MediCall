package com.shivamsaryar.medicalltest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class DriverHome extends AppCompatActivity {

    String pName;
    String pPhone;
    String pAddress;
    String pLocation;
    Firebase myRootRef2;
    Button checkButton;
    Button dLogoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Home - Medicall Staff");
        setContentView(R.layout.activity_driver_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home - Staff");

        checkButton = (Button)findViewById(R.id.btnCheckReq);
        dLogoutButton = (Button)findViewById(R.id.btnDriverLogout);

        Firebase.setAndroidContext(this);
        myRootRef2 = new Firebase("https://medicall.firebaseio.com/user_request");
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRootRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, String> map = dataSnapshot.getValue(Map.class);
                        pName = map.get("name");
                        pPhone = map.get("phone");
                        pAddress = map.get("address");
                        pLocation = map.get("location");
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                if(pName=="null" && pPhone=="null" && pAddress=="null" && pLocation=="null"){
                    Toast.makeText(getApplicationContext(),"No current requests.",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent mIntent = new Intent(getApplicationContext(),AmbulanceHome.class);
                    mIntent.putExtra("pName",pName);
                    mIntent.putExtra("pPhone",pPhone);
                    mIntent.putExtra("pAddress",pAddress);
                    mIntent.putExtra("pLocation",pLocation);
                    startActivity(mIntent);
                }
            }
        });
        dLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dLogout = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(dLogout);
            }
        });
    }

}
