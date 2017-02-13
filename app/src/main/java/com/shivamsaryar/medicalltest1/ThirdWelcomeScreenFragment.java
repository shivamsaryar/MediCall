package com.shivamsaryar.medicalltest1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdWelcomeScreenFragment extends Fragment {


    public ThirdWelcomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_welcome_screen2, container, false);
    }

    public void proceedToLogin(View view) {
        Intent toLoginIntent = new Intent(getContext(),LoginActivity.class);
        startActivity(toLoginIntent);
    }
}
