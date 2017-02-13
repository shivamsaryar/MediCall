package com.shivamsaryar.medicalltest1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FindDoctorActivity extends AppCompatActivity {

    String doctorField;
    Bundle extras;
    SQLiteDatabase dbFindDoctor;
    String[] doc_names;
    ListView docNamesList;
    String selectedName;
    Button viewButton;
    Button bkButton;
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Doctors around you");
        setContentView(R.layout.activity_find_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Doctors around you");
        extras = getIntent().getExtras();
        mail = extras.getString("userEmail");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String dialog_title = "No doctors available!";
        String dialog_message = "There are currently no doctors around you. Please try again later.";
        builder.setMessage(dialog_message);
        builder.setTitle(dialog_title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Intent backIntent = new Intent(getApplicationContext(), MainDrawerActivity.class);
                startActivity(backIntent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        try{
            doctorField = extras.getString("DoctorFieldSelected");
        }
        catch(NullPointerException e){
            Toast.makeText(getApplicationContext(),e.getMessage()+" Bundle reception exception",Toast.LENGTH_LONG).show();
        }

        try{
            dbFindDoctor = this.openOrCreateDatabase("MedicallApp",MODE_PRIVATE,null);
            dbFindDoctor.execSQL("Drop table if exists Doctor");
            dbFindDoctor.execSQL("Create table if not exists Doctor(Name text not null, Email text not null, Field text, Phone text not null, Address text, Location text, primary key(Email));");
            dbFindDoctor.execSQL("Insert into Doctor values('Dr. R.N. Prasad','rnprasad@gmail.com','Eye','9898989898','iCare Eye Clinic, Tiger Circle, Manipal','13.3537,74.7848');");
            dbFindDoctor.execSQL("Insert into Doctor values('Dr. S.K. Jha','sk.jha@yahoo.com','Ear','7295432456','Ear Care Clinic, MD Road, Udupi','13.3537,74.7848');");
            dbFindDoctor.execSQL("Insert into Doctor values('Dr. Sanya Mehta','mehta.sanya@rediffmail.com','Dentist','9167342797','Dental Clinic, Sector-21, Mangalore','12.8704,74.8445');");
            dbFindDoctor.execSQL("Insert into Doctor values('Dr. P.K. Mishra','pramod.mishra@gmail.com','Child','7673332456','Happy Clinic, New Town Market, Udupi','13.3537,74.7848');");
            dbFindDoctor.execSQL("Insert into Doctor values('Dr. Anjali Saxena','anjali.saxena@hotmail.com','Women','9762345697','MCare Clinic, Eshwara Nagar, Manipal','13.3543,74.7860');");
            dbFindDoctor.execSQL("Insert into Doctor values('Dr. Poonam Sinha','poonam.sinha@gmail.com','Pregnancy','9776542320','Mother''s Care Clinic, Model Town, Manipal','13.3540,74.7890');");
            Cursor myCursor;
            myCursor = dbFindDoctor.rawQuery("Select * from Doctor where Field = '"+doctorField+"'",null);

            doc_names = new String[myCursor.getCount()];
            int i=0;
            while(myCursor.moveToNext()){
                String d_name = myCursor.getString(myCursor.getColumnIndex("Name"));
                doc_names[i] = d_name;
                i++;
            }
            if(myCursor.getCount()==0){
                Toast.makeText(getApplicationContext(),"There are currently no doctors around you. Please try again later.",Toast.LENGTH_LONG).show();
                dialog.show();
            }
            myCursor.moveToFirst();
        }
        catch(SQLiteException e){
            Toast.makeText(getApplicationContext(),e.getMessage()+" inside onCreate.",Toast.LENGTH_LONG).show();
        }

        try{
            ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,doc_names);
            docNamesList = (ListView)findViewById(R.id.listViewDocName);
            docNamesList.setAdapter(mArrayAdapter);
            docNamesList.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectedName = String.valueOf(parent.getItemAtPosition(position));
                        }
                    }
            );
        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage()+" List view exception",Toast.LENGTH_LONG).show();
        }

        viewButton = (Button)findViewById(R.id.buttonViewDoctor);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedName==null){
                    Toast.makeText(getApplicationContext(),"Please select a doctor",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent viewDoctorIntent = new Intent(getApplicationContext(),ViewDoctorActivity.class);
                    viewDoctorIntent.putExtra("SelectedName",selectedName);
                    viewDoctorIntent.putExtra("userEmail",mail);
                    startActivity(viewDoctorIntent);
                }
            }
        });
        bkButton = (Button)findViewById(R.id.buttondocBack);
        bkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bkIntent = new Intent(getApplicationContext(),MainDrawerActivity.class);
                bkIntent.putExtra("myEmail", mail);
                startActivity(bkIntent);
            }
        });

    }


}
