package edu.communication.hemo.patient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import edu.communication.hemo.R;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.patient.model.PatientDetails;

public class PreferredDoctorDetails extends AppCompatActivity {
    TextView docAge;
    Button docBookAppointment;
    Button docCall;
    TextView docCertificate;
    TextView docGender;
    TextView docHospital;
    TextView docMail;
    TextView docMobile;
    TextView docName;
    TextView docSpecialize;
    DoctorDetails doctorDetails;
    PatientDetails patientDetails;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_doctor_details);
        this.docName = (TextView) findViewById(R.id.doc_name_et);
        this.docAge = (TextView) findViewById(R.id.doc_age_et);
        this.docSpecialize = (TextView) findViewById(R.id.doc_specialize_et);
        this.docCertificate = (TextView) findViewById(R.id.doc_certificate_et);
        this.docHospital = (TextView) findViewById(R.id.doc_hospital_et);
        this.docGender = (TextView) findViewById(R.id.doc_gender_et);
        this.docMail = (TextView) findViewById(R.id.doc_mail_et);
        this.docMobile = (TextView) findViewById(R.id.doc_mobile_et);
        this.docCall = (Button) findViewById(R.id.doc_call);
        this.docBookAppointment = (Button) findViewById(R.id.book_appointment);
        try {
            this.doctorDetails = (DoctorDetails) getIntent().getExtras().getParcelable("doctorDetails");
            this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
            if (this.doctorDetails == null) {
                Toast.makeText(this, "Doctor details were not found ", 0).show();
            } else {
                this.docName.setText(this.doctorDetails.getmName());
                this.docAge.setText(this.doctorDetails.getmAge());
                this.docSpecialize.setText(this.doctorDetails.getmSpecialization());
                this.docCertificate.setText(this.doctorDetails.getmCertification());
                this.docHospital.setText(this.doctorDetails.getmHospital());
                this.docGender.setText(this.doctorDetails.getmGender());
                this.docMail.setText(this.doctorDetails.getmEmail());
                this.docMobile.setText(this.doctorDetails.getmMobileNumber());
                this.docCall.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.PreferredDoctorDetails.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        Dexter.withActivity(PreferredDoctorDetails.this).withPermission("android.permission.CALL_PHONE").withListener(new PermissionListener() { // from class: edu.communication.hemo.patient.PreferredDoctorDetails.1.1
                            @Override // com.karumi.dexter.listener.single.PermissionListener
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                if (PreferredDoctorDetails.this.doctorDetails.getmMobileNumber() != null) {
                                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + PreferredDoctorDetails.this.doctorDetails.getmMobileNumber()));
                                    PreferredDoctorDetails.this.startActivity(intent);
                                }
                            }

                            @Override // com.karumi.dexter.listener.single.PermissionListener
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                            }

                            @Override // com.karumi.dexter.listener.single.PermissionListener
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            }
                        }).check();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
        this.docBookAppointment.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.PreferredDoctorDetails.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(PreferredDoctorDetails.this, BookAnAppointment.class);
                intent.putExtra("doctorDetails", PreferredDoctorDetails.this.doctorDetails);
                intent.putExtra("patientDetails", PreferredDoctorDetails.this.patientDetails);
                PreferredDoctorDetails.this.startActivity(intent);
            }
        });
    }
}
