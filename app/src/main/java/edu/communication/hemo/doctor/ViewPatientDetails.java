package edu.communication.hemo.doctor;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import edu.communication.hemo.R;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.patient.SendPatientMessageActivity;
import edu.communication.hemo.patient.model.PatientDetails;

public class ViewPatientDetails extends AppCompatActivity {
    private String TAG = ViewPatientDetails.class.getSimpleName() + " : --> ";
    private Dialog awesomeProgressDialog;
    Button btUploadPrescribe;
    DoctorDetails doctorDetails;
    private ImageView iv_preview;
    private DatabaseReference mDatabase;
    TextView patientAge;
    TextView patientBloodGroup;
    Button patientCall;
    Button patientChat;
    PatientDetails patientDetails;
    TextView patientGender;
    TextView patientMail;
    TextView patientMobile;
    TextView patientName;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_details);
        this.iv_preview = (ImageView) findViewById(R.id.iv_preview);
        this.patientName = (TextView) findViewById(R.id.patient_name_et);
        this.patientAge = (TextView) findViewById(R.id.patient_age_et);
        this.patientGender = (TextView) findViewById(R.id.patient_gender_et);
        this.patientBloodGroup = (TextView) findViewById(R.id.patient_blood_group_et);
        this.patientMobile = (TextView) findViewById(R.id.patient_mobile_et);
        this.patientMail = (TextView) findViewById(R.id.patient_mail_et);
        this.patientCall = (Button) findViewById(R.id.patient_call);
        this.patientChat = (Button) findViewById(R.id.patient_chat);
        this.btUploadPrescribe = (Button) findViewById(R.id.upload_prescription);
        try {
            this.doctorDetails = (DoctorDetails) getIntent().getExtras().getParcelable("doctorDetails");
            this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
            if (this.doctorDetails == null) {
                Toast.makeText(this, "Doctor details were not found ", 0).show();
            } else {
                this.patientName.setText(this.patientDetails.getName());
                this.patientAge.setText(this.patientDetails.getmAge());
                this.patientGender.setText(this.patientDetails.getmGender());
                this.patientMail.setText(this.patientDetails.getmEmail());
                this.patientMobile.setText(this.patientDetails.getmMobileNumber());
                this.patientCall.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.doctor.ViewPatientDetails.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        Dexter.withActivity(ViewPatientDetails.this).withPermission("android.permission.CALL_PHONE").withListener(new PermissionListener() { // from class: edu.communication.hemo.doctor.ViewPatientDetails.1.1
                            @Override // com.karumi.dexter.listener.single.PermissionListener
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                if (ViewPatientDetails.this.patientDetails.getmMobileNumber() != null) {
                                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + ViewPatientDetails.this.patientDetails.getmMobileNumber()));
                                    ViewPatientDetails.this.startActivity(intent);
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
        this.btUploadPrescribe.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.doctor.ViewPatientDetails.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(ViewPatientDetails.this, UploadPrescriptionActivity.class);
                intent.putExtra("doctorDetails", ViewPatientDetails.this.doctorDetails);
                intent.putExtra("patientDetails", ViewPatientDetails.this.patientDetails);
                ViewPatientDetails.this.startActivity(intent);
            }
        });
        this.patientChat.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.doctor.ViewPatientDetails.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(ViewPatientDetails.this, SendPatientMessageActivity.class);
                intent.putExtra("doctorDetails", ViewPatientDetails.this.doctorDetails);
                intent.putExtra("patientDetails", ViewPatientDetails.this.patientDetails);
                intent.putExtra("isDoctor", true);
                ViewPatientDetails.this.startActivity(intent);
            }
        });
    }
}
