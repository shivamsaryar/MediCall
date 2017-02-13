package com.shivamsaryar.medicalltest1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

public class RegisterActivity extends AppCompatActivity {

    EditText inputFName;
    EditText inputLName;
    EditText inputEmail;
    EditText inputPhone;
    EditText inputPassword;
    EditText inputConfPassword;
    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;
    RadioButton otherGenderRadioButton;
    String sex = null;
    TextView fnameError;
    TextView lnameError;
    TextView emailError;
    TextView phoneError;
    TextView passError;
    TextView confPassError;
    ProgressDialog mProgressDialog;
    CheckBox agreementCheckBox;

    SQLiteDatabase myDB;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Boolean fNameCorrect = false;
            Boolean lNameCorrect = false;
            Boolean emailCorrect = false;
            Boolean phoneCorrect = false;
            Boolean passCheck = false;
            Boolean passCorrect = false;
            Boolean agreementOK = false;
            Boolean allCredsOk = false;
            Boolean userExists = false;
            String fname = inputFName.getText().toString();
            String lname = inputLName.getText().toString();
            String email = inputEmail.getText().toString();
            String phone = inputPhone.getText().toString();
            String pass = inputPassword.getText().toString();
            String confpass = inputConfPassword.getText().toString();
            String gender = sex;
            //Toast.makeText(getApplicationContext(), "Gender Selected: " + sex, Toast.LENGTH_SHORT).show();

            //Name Validation
            if(fname.length()==0){
                //This field cannot be left blank
                fnameError.setText("Compulsory Field");
            }
            else if(lname.contains(" ")){
                //First Name cannot contain spaces
                fnameError.setText("Compulsory Field");
            }
            else{
                fNameCorrect = true;
            }

            if(lname.length()==0){
                //This field cannot be left blank
                lnameError.setText("Compulsory field");
            }
            else if(lname.contains(" ")){
                //Last Name cannot contain spaces
                lnameError.setText("This field cannot contain spaces");

            }
            else{
                lNameCorrect = true;
            }

            //Email Validation
            if(email.length()==0){
                //This field cannot be left blank
                emailError.setText("This field cannot be left blank");
            }
            else if(!(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@hotmail.com")|| email.endsWith("medicall.com") || email.endsWith("@yahoo.co.in")|| email.endsWith("@rediffmail.com"))){
                //Enter a valid email id
                emailError.setText("Please enter a valid email id");

            }
            else{
                emailCorrect = true;
            }

            //Phone Validation
            if(phone.length()==0){
                //Cannot be left blank
                phoneError.setText("This field cannot be left blank");
            }
            else if(phone.length()!=10){
                //Enter a valid 10-digit phone number
                phoneError.setText("Please enter a valid 10-digit phone number");
            }
            else{
                phoneCorrect = true;
            }

            //Password Validation
            if(pass.length()==0 && confpass.length()==0){
                //This field cannot be left blank
                passError.setText("This field cannot be left blank");
            }
            else if(pass.length()==0){
                passError.setText("This field cannot be left blank");
            }
            else if(pass.length()>0 && pass.length()<8){
                //Password should be minimum 8 characters long
                passError.setText("Password  should be at least 8 characters long");
                inputPassword.setText(null);
            }
            else{
                passCheck = true;
            }

            if(!confpass.equals(pass)){
                //Passwords do not match
                confPassError.setText("Passwords do not match");
            }
            else if(pass.length()==0 && confpass.length()==0){
                //This field cannot be left blank
                confPassError.setText("This field cannot be left blank");
            }

            else{
                passCorrect = true;
            }

            if(agreementCheckBox.isChecked()){
                agreementOK = true;

            }
            else{
                Toast.makeText(getApplicationContext(),"You are required to agree with our Agreement Services",Toast.LENGTH_SHORT).show();
            }

            if(fNameCorrect==true && lNameCorrect==true && emailCorrect==true && phoneCorrect==true && passCheck==true && passCorrect == true && agreementOK ==true){

                try{

                    Cursor userExistCursor;
                    userExistCursor = myDB.rawQuery("Select Email from User where Email = '"+ email +"' ",null);
                    if(userExistCursor.moveToFirst()){
                        userExists = true;
                        Toast.makeText(getApplicationContext(), "The username already exists.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        userExists = false;
                        String name = fname + " " + lname;
                        myDB.execSQL("Insert into User values('" + name + "', '"+email+"', '"+phone+"', '"+gender+"', '"+pass+"');");
                        Toast.makeText(getApplicationContext(),"Account successfully created.",Toast.LENGTH_LONG).show();
                        Intent finishIntent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(finishIntent);
                    }

                }
                catch(SQLException e){
                    Toast.makeText(getApplicationContext(), "Could not retrieve info about user existence "+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Register");
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Register");
        //Initialize Text Fields
        inputFName = (EditText)findViewById(R.id.editTextFirstName);
        inputLName = (EditText)findViewById(R.id.editTextLastName);
        inputEmail = (EditText)findViewById(R.id.editTextEmail);
        inputPhone = (EditText)findViewById(R.id.editTextPhone);
        inputPassword = (EditText)findViewById(R.id.editTextPassword);
        inputConfPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
        maleRadioButton = (RadioButton)findViewById(R.id.radioButtonMale);
        femaleRadioButton = (RadioButton)findViewById(R.id.radioButtonFemale);
        otherGenderRadioButton = (RadioButton)findViewById(R.id.radioButtonOther);
        fnameError = (TextView)findViewById(R.id.textViewfnameError);
        lnameError = (TextView)findViewById(R.id.textViewlnameError);
        emailError = (TextView)findViewById(R.id.tvEmailError);
        phoneError = (TextView)findViewById(R.id.textViewphoneError);
        passError = (TextView)findViewById(R.id.tvPassError);
        confPassError = (TextView)findViewById(R.id.tvConfPassError);
        agreementCheckBox = (CheckBox)findViewById(R.id.checkBox);

        fnameError.setText(null);
        lnameError.setText(null);
        emailError.setText(null);
        phoneError.setText(null);
        passError.setText(null);
        confPassError.setText(null);
        try{
            myDB = this.openOrCreateDatabase("MedicallApp", MODE_PRIVATE, null);
            myDB.execSQL("Create table if not exists User(Name text not null, Email text primary key not null, Phone text not null, Gender text, Password text);");
        }
        catch(SQLiteException e){
            Toast.makeText(getApplicationContext(),e.getMessage() + ". This is inside onCreate.",Toast.LENGTH_LONG).show();
        }

    }

    public void registerCancel(View view) {

        Intent cancelledIntent = new Intent(this,LoginActivity.class);
        startActivity(cancelledIntent);
    }

    public void registerFinish(View view) {

        fnameError.setText(null);
        lnameError.setText(null);
        emailError.setText(null);
        phoneError.setText(null);
        passError.setText(null);
        confPassError.setText(null);
        mProgressDialog = new ProgressDialog(getApplicationContext());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setMax(1000);
        //mProgressDialog.setProgress(0);
        //mProgressDialog.show();

        Runnable runnableThread = new Runnable() {
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis()+5000;
                while(System.currentTimeMillis()<futureTime){
                    synchronized (this){
                        try{
                            wait(futureTime - System.currentTimeMillis());
                            //mProgressDialog.setProgress(mProgressDialog.getMax());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                handler.sendEmptyMessage(0);
            }
        };

        Thread finishWaitThread = new Thread(runnableThread);
        finishWaitThread.start();
    }

    public void maleButtonSelected(View view) {
        maleRadioButton.setChecked(true);
        sex = "Male";
        femaleRadioButton.setChecked(false);
        otherGenderRadioButton.setChecked(false);
    }

    public void femaleButtonSelected(View view) {
        femaleRadioButton.setChecked(true);
        sex = "Female";
        maleRadioButton.setChecked(false);
        otherGenderRadioButton.setChecked(false);
    }

    public void otherGenderButtonSelected(View view) {
        otherGenderRadioButton.setChecked(true);
        sex = "Other";
        femaleRadioButton.setChecked(false);
        maleRadioButton.setChecked(false);
    }
}
