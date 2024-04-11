package edu.communication.hemo.hospital;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.R;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.donor.model.DonorDetails;
import edu.communication.hemo.hospital.model.HospitalDetails;
import edu.communication.hemo.hospital.model.RequestBloodDetails;

public class RequestBloodActivity extends AppCompatActivity implements View.OnClickListener {
    private Dialog awesomeProgressDialog;
    Button callDonor;
    FirebaseDatabase database;
    TextView donationStatus;
    TextView donorAge;
    TextView donorBloodGroup;
    DonorDetails donorDetails;
    TextView donorGender;
    TextView donorMail;
    TextView donorMobile;
    TextView donorName;
    HospitalDetails hospitalDetails;
    DatabaseReference myRef;
    Button requestBloodBtn;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        this.donorName = (TextView) findViewById(R.id.donor_name_et);
        this.donorAge = (TextView) findViewById(R.id.donor_age_et);
        this.donorBloodGroup = (TextView) findViewById(R.id.donor_blood_group_et);
        this.donorMail = (TextView) findViewById(R.id.donor_mail_et);
        this.donorMobile = (TextView) findViewById(R.id.donor_mobile_et);
        this.donorGender = (TextView) findViewById(R.id.donor_gender_et);
        this.donorGender = (TextView) findViewById(R.id.donor_gender_et);
        this.donationStatus = (TextView) findViewById(R.id.donation_status);
        this.requestBloodBtn = (Button) findViewById(R.id.request_blood_btn);
        this.callDonor = (Button) findViewById(R.id.donor_call);
        this.myRef = fireBaseInitialSetup();
        this.requestBloodBtn.setOnClickListener(this);
        this.callDonor.setOnClickListener(this);
        fetchDonationStatus();
        try {
            this.hospitalDetails = (HospitalDetails) getIntent().getExtras().getParcelable("hospitalDetails");
            this.donorDetails = (DonorDetails) getIntent().getExtras().getParcelable("donorDetails");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
        this.donorName.setText(this.donorDetails.getName());
        this.donorAge.setText(this.donorDetails.getmAge());
        this.donorMobile.setText(this.donorDetails.getmMobileNumber());
        this.donorGender.setText(this.donorDetails.getmGender());
        this.donorMail.setText(Utils.DecodeString(this.donorDetails.getmEmail()));
        this.donorBloodGroup.setText(this.donorDetails.getmBloodGroup());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.donor_call) {
            callDonor();
        } else if (id == R.id.request_blood_btn) {
            submitBloodRequest();
        }
    }

    private void submitBloodRequest() {
        final String encodedDonorEmail = Utils.EncodeString(this.donorDetails.getmEmail());
        String encodedHospitalEmail = Utils.EncodeString(this.hospitalDetails.getmEmail());
        final RequestBloodDetails requestBloodDetails = new RequestBloodDetails();
        requestBloodDetails.setDonorName(this.donorDetails.getName());
        requestBloodDetails.setDonorAge(this.donorDetails.getmAge());
        requestBloodDetails.setDonorGender(this.donorDetails.getmGender());
        requestBloodDetails.setDonorBloodGroup(this.donorDetails.getmBloodGroup());
        requestBloodDetails.setDonorEmail(encodedDonorEmail);
        requestBloodDetails.setDonorMobileNumber(this.donorDetails.getmMobileNumber());
        requestBloodDetails.setHospCode(this.hospitalDetails.getmHosCode());
        requestBloodDetails.setHospitalName(this.hospitalDetails.getmHosName());
        requestBloodDetails.setHospAddress(this.hospitalDetails.getmAddress());
        requestBloodDetails.setHospEmail(encodedHospitalEmail);
        requestBloodDetails.setIsDonationAccepted(false);
        try {
            this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.request_blood).show();
            this.myRef.child(CommonData.HOSP_BLOOD_REQUESTS).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.hospital.RequestBloodActivity.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        RequestBloodActivity.this.myRef.child(CommonData.HOSP_BLOOD_REQUESTS).child(RequestBloodActivity.this.myRef.push().getKey()).setValue(requestBloodDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.hospital.RequestBloodActivity.1.1
                            @Override // com.google.android.gms.tasks.OnCompleteListener
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    RequestBloodActivity.this.awesomeProgressDialog.dismiss();
                                    RequestBloodActivity requestBloodActivity = RequestBloodActivity.this;
                                    Utils.showSuccessDialogue(requestBloodActivity, "Blood Request to Donor " + encodedDonorEmail + "sent  successfully", true);
                                }
                            }
                        });
                    } else {
                        RequestBloodActivity.this.myRef.child(CommonData.HOSP_BLOOD_REQUESTS).child(RequestBloodActivity.this.myRef.push().getKey()).setValue(requestBloodDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.hospital.RequestBloodActivity.1.2
                            @Override // com.google.android.gms.tasks.OnCompleteListener
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    RequestBloodActivity.this.awesomeProgressDialog.dismiss();
                                    RequestBloodActivity requestBloodActivity = RequestBloodActivity.this;
                                    Utils.showSuccessDialogue(requestBloodActivity, "Blood Request to Donor " + encodedDonorEmail + "sent  successfully", true);
                                }
                            }
                        });
                    }
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    RequestBloodActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(RequestBloodActivity.this, "Something went wrong. Please try again..!!", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(this, "Unable to process request. Please try again..!!", 0).show();
        }
    }

    public void callDonor() {
        Dexter.withActivity(this).withPermission("android.permission.CALL_PHONE").withListener(new PermissionListener() { // from class: edu.communication.hemo.hospital.RequestBloodActivity.2
            @Override // com.karumi.dexter.listener.single.PermissionListener
            public void onPermissionGranted(PermissionGrantedResponse response) {
                if (RequestBloodActivity.this.donorDetails.getmMobileNumber() != null) {
                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + RequestBloodActivity.this.donorDetails.getmMobileNumber()));
                    RequestBloodActivity.this.startActivity(intent);
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

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }

    private void fetchDonationStatus() {
        this.myRef.child(CommonData.HOSP_BLOOD_REQUESTS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.hospital.RequestBloodActivity.3
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1 != null) {
                            RequestBloodDetails requestBloodDetails = (RequestBloodDetails) dataSnapshot1.getValue(RequestBloodDetails.class);
                            requestBloodDetails.setuId(dataSnapshot1.getKey());
                            if (requestBloodDetails != null && RequestBloodActivity.this.donorDetails.getmEmail().equals(Utils.EncodeString(requestBloodDetails.getDonorEmail()))) {
                                if (requestBloodDetails.getIsDonationAccepted()) {
                                    RequestBloodActivity.this.requestBloodBtn.setVisibility(8);
                                    RequestBloodActivity.this.donationStatus.setVisibility(0);
                                } else {
                                    RequestBloodActivity.this.requestBloodBtn.setVisibility(0);
                                    RequestBloodActivity.this.donationStatus.setVisibility(8);
                                }
                            }
                        }
                    }
                }
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
