package com.shivamsaryar.medicalltest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.app.Activity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindDoctorFragment extends Fragment implements View.OnClickListener {

    String doctorFields[] = {"Eye", "Ear", "Dentist", "Child", "Women", "Pregnancy"};
    Spinner doctorSpinner;
    Button findDoctorButton;
    private View myInflaterView;
    String userEmail;

    public FindDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myInflaterView = inflater.inflate(R.layout.fragment_find_doctor2,container,false);
        userEmail = getArguments().getString("myEmail");
        doctorSpinner = (Spinner)myInflaterView.findViewById(R.id.spinner);
        findDoctorButton = (Button) myInflaterView.findViewById(R.id.buttonSearchDoctor);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,doctorFields);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorSpinner.setAdapter(spinnerArrayAdapter);
        findDoctorButton.setOnClickListener(this);
        doctorSpinner.setSelection(0);
        return myInflaterView;
    }

    @Override
    public void onClick(View v) {
        String doctorFieldSelected = doctorSpinner.getSelectedItem().toString();
        Intent doctorIntent = new Intent(getActivity(),FindDoctorActivity.class);
        doctorIntent.putExtra("DoctorFieldSelected", doctorFieldSelected);
        doctorIntent.putExtra("userEmail",userEmail);
        startActivity(doctorIntent);
    }
}
