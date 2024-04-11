package edu.communication.hemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import edu.communication.hemo.donor.DonorLoginActivity;
import edu.communication.hemo.hospital.HospitalLoginActivity;
import edu.communication.hemo.patient.PatientLoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView donor;
    ImageView hospital;
    ImageView patient;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.hospital = (ImageView) findViewById(R.id.hospital);
        this.patient = (ImageView) findViewById(R.id.patient);
        this.donor = (ImageView) findViewById(R.id.donor);
        this.hospital.setOnClickListener(this);
        this.patient.setOnClickListener(this);
        this.donor.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.donor) {
            startActivity(new Intent(this, DonorLoginActivity.class));
        } else if (id == R.id.hospital) {
            startActivity(new Intent(this, HospitalLoginActivity.class));
        } else if (id == R.id.patient) {
            startActivity(new Intent(this, PatientLoginActivity.class));
        }
    }
}
