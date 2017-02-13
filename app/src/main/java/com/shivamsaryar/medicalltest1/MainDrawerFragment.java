package com.shivamsaryar.medicalltest1;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainDrawerFragment extends Fragment implements View.OnClickListener {

    Button callAmbulanceButton;
    private View myInflaterView2;
    String userEmail;

    public MainDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myInflaterView2 = inflater.inflate(R.layout.fragment_main_drawer,container,false);
        userEmail = getArguments().getString("myEmail");
        callAmbulanceButton = (Button)myInflaterView2.findViewById(R.id.buttonCallAmb);
        callAmbulanceButton.setOnClickListener(this);
        return myInflaterView2;
    }

    @Override
    public void onClick(View v) {
        Intent callIntent = new Intent(getActivity(),AmbulanceCall.class);
        callIntent.putExtra("userEmail",userEmail);
        startActivity(callIntent);
    }
}
