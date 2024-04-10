package edu.communication.hemo.patient;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import edu.communication.hemo.R;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.patient.model.PatientDetails;

public class PatientProfileActivity extends AppCompatActivity {
    TextInputEditText mAge;
    TextInputEditText mBloodGroup;
    TextInputEditText mGender;
    TextInputEditText mMailId;
    TextInputEditText mMobileNumber;
    TextInputEditText mName;
    PatientDetails patientDetails;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        this.mName = (TextInputEditText) findViewById(R.id.name_et);
        this.mAge = (TextInputEditText) findViewById(R.id.age_et);
        this.mBloodGroup = (TextInputEditText) findViewById(R.id.bloodGroup_et);
        this.mGender = (TextInputEditText) findViewById(R.id.gender_et);
        this.mMailId = (TextInputEditText) findViewById(R.id.mail_id_et);
        this.mMobileNumber = (TextInputEditText) findViewById(R.id.mobile_et);
        try {
            this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
            if (this.patientDetails == null) {
                Toast.makeText(this, "Doctor details were not found ", 0).show();
            } else {
                this.mName.setText(this.patientDetails.getName());
                this.mAge.setText(this.patientDetails.getmAge());
                this.mBloodGroup.setText(this.patientDetails.getmBloodGroup());
                this.mGender.setText(this.patientDetails.getmGender());
                this.mMailId.setText(Utils.DecodeString(this.patientDetails.getmEmail()));
                this.mMobileNumber.setText(this.patientDetails.getmMobileNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
    }
}
