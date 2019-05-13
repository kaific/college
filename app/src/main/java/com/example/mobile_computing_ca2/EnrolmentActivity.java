package com.example.mobile_computing_ca2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class EnrolmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolment);

        Intent intent = getIntent();
        int enrolmentId = intent.getIntExtra(EnrolmentFragment.ARG_ENROLMENT_ID, 0);

        // Create fragment transaction to handle the transactions between different fragments
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        EnrolmentFragment fragment = EnrolmentFragment.newInstance(enrolmentId);
        ft.add(R.id.enrolment_container, fragment);
        ft.commit();
    }
}