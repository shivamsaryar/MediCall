package com.shivamsaryar.medicalltest1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView registerText;
    SQLiteDatabase myDB;
    Button loginButton;
    EditText emailInput;
    EditText passwordInput;
    String dbEmail;
    String dbPassword;
    Boolean userValid;
    Boolean passCorrect;
    Boolean userIsAdmin;
    String emailEntered;
    String passwordEntered;
    String dbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Sign In");
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sign In");
        userValid = false;
        passCorrect = false;
        userIsAdmin = false;
        loginButton = (Button)findViewById(R.id.buttonLogin);
        emailInput = (EditText)findViewById(R.id.editTextLoginEmail);
        passwordInput = (EditText)findViewById(R.id.editTextLoginPassword);
        registerText = (TextView)findViewById(R.id.textViewRegisterClick);
        registerText.setPaintFlags(registerText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emailInput.setText(null);
        passwordInput.setText(null);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    emailEntered = emailInput.getText().toString();
                    passwordEntered = passwordInput.getText().toString();
                    myDB = getApplicationContext().openOrCreateDatabase("MedicallApp", MODE_PRIVATE, null);
                    myDB.execSQL("Create table if not exists User(Name text not null, Email text primary key not null, Phone text not null, Gender text, Password text);");
                    Cursor loginCursor;
                    loginCursor = myDB.rawQuery("Select Name, Email, Password from User where email = '"+emailEntered+"'",null);
                    if(!loginCursor.moveToFirst()){
                        Toast.makeText(getApplicationContext(),"User does not exist",Toast.LENGTH_LONG).show();
                    }
                    else if(loginCursor.moveToFirst()){
                        {
                            userValid = true;
                            dbName = loginCursor.getString(0);
                            dbEmail = loginCursor.getString(1);
                            dbPassword = loginCursor.getString(2);
                        }
                    }
                }
                catch(SQLiteException e){
                    Toast.makeText(getApplicationContext(),e.getMessage() + " Verification failed",Toast.LENGTH_LONG).show();
                }

                if(userValid == true && !passwordEntered.equals(dbPassword)){
                    //Incorrect password
                    Toast.makeText(getApplicationContext(),"Incorrect password",Toast.LENGTH_LONG).show();
                }
                else if (userValid == true && passwordEntered.equals(dbPassword)){
                    passCorrect =true;
                }
                else{
                    passCorrect = false;
                }

                if(userValid == true && passCorrect == true){
                    if(emailEntered.endsWith("@medicall.com")){
                        userIsAdmin = true;
                        Toast.makeText(getApplicationContext(),"MediCall Staff Login",Toast.LENGTH_LONG).show();
                        Intent staffIntent = new Intent(getApplicationContext(),DriverHome.class);
                        startActivity(staffIntent);
                    }
                    else{
                        userIsAdmin = false;
                        Toast.makeText(getApplicationContext(), "Login Successful. Welcome " + dbName, Toast.LENGTH_SHORT).show();
                        Intent newIntent = new Intent(getApplicationContext(),MainDrawerActivity.class);
                        newIntent.putExtra("myEmail",emailEntered);
                        startActivity(newIntent);
                    }
                }
            }
        });
    }

    public void registerNewUser(View view) {
        Intent toRegisterIntent = new Intent(this,RegisterActivity.class);
        startActivity(toRegisterIntent);
    }
}
