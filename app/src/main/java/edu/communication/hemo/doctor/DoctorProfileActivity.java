package edu.communication.hemo.doctor;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import edu.communication.hemo.R;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.Utils;

public class DoctorProfileActivity extends AppCompatActivity {
    DoctorDetails doctorDetails;
    TextInputEditText mAge;
    TextInputEditText mCertifications;
    TextInputEditText mGender;
    TextInputEditText mHospital;
    TextInputEditText mMailId;
    TextInputEditText mMobileNumber;
    TextInputEditText mName;
    TextInputEditText mSpecialization;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        this.mName = (TextInputEditText) findViewById(R.id.name_et);
        this.mAge = (TextInputEditText) findViewById(R.id.age_et);
        this.mSpecialization = (TextInputEditText) findViewById(R.id.specification_et);
        this.mCertifications = (TextInputEditText) findViewById(R.id.certification_et);
        this.mHospital = (TextInputEditText) findViewById(R.id.hospital_et);
        this.mGender = (TextInputEditText) findViewById(R.id.gender_et);
        this.mMailId = (TextInputEditText) findViewById(R.id.mail_id_et);
        this.mMobileNumber = (TextInputEditText) findViewById(R.id.mobile_et);
        try {
            this.doctorDetails = (DoctorDetails) getIntent().getExtras().getParcelable("doctorDetails");
            if (this.doctorDetails == null) {
                Toast.makeText(this, "Doctor details were not found ", 0).show();
            } else {
                this.mName.setText(this.doctorDetails.getmName());
                this.mAge.setText(this.doctorDetails.getmAge());
                this.mSpecialization.setText(this.doctorDetails.getmSpecialization());
                this.mCertifications.setText(this.doctorDetails.getmCertification());
                this.mHospital.setText(this.doctorDetails.getmHospital());
                this.mGender.setText(this.doctorDetails.getmGender());
                this.mMailId.setText(Utils.DecodeString(this.doctorDetails.getmEmail()));
                this.mMobileNumber.setText(this.doctorDetails.getmMobileNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
    }
}
