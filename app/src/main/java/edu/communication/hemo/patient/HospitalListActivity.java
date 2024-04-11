package edu.communication.hemo.patient;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.R;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.hospital.model.HospitalDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;

public class HospitalListActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    ArrayList<HospitalDetails> hospitalDetailsArrayList = new ArrayList<>();
    ImageView ivPatientLogout;
    DatabaseReference myRef;
    TextView noResults;
    PatientDetails patientDetails;
    RecyclerView recvHospList;
    ReqBloodHospitalListAdapter reqBloodHospitalListAdapter;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        this.recvHospList = (RecyclerView) findViewById(R.id.hosp_list);
        this.ivPatientLogout = (ImageView) findViewById(R.id.iv_patient_logout);
        this.noResults = (TextView) findViewById(R.id.no_results);
        try {
            this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.ivPatientLogout.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.HospitalListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HospitalListActivity.this.showLogoutAlert();
            }
        });
        fetchHospitalDetails();
    }

    private void fetchHospitalDetails() {
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.HOSPITALS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.patient.HospitalListActivity.2
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        HospitalListActivity.this.awesomeProgressDialog.dismiss();
                        HospitalListActivity.this.noResults.setVisibility(0);
                        HospitalListActivity.this.recvHospList.setVisibility(8);
                        return;
                    }
                    HospitalListActivity.this.noResults.setVisibility(8);
                    HospitalListActivity.this.recvHospList.setVisibility(0);
                    for (DataSnapshot receiptsSnapShot : dataSnapshot.getChildren()) {
                        HospitalDetails hospitalDetails = (HospitalDetails) receiptsSnapShot.getValue(HospitalDetails.class);
                        HospitalListActivity.this.hospitalDetailsArrayList.add(hospitalDetails);
                    }
                    HospitalListActivity hospitalListActivity = HospitalListActivity.this;
                    hospitalListActivity.reqBloodHospitalListAdapter = new ReqBloodHospitalListAdapter(hospitalListActivity, hospitalListActivity.hospitalDetailsArrayList, HospitalListActivity.this.patientDetails);
                    HospitalListActivity.this.awesomeProgressDialog.dismiss();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(HospitalListActivity.this);
                    HospitalListActivity.this.recvHospList.setLayoutManager(linearLayoutManager);
                    HospitalListActivity.this.recvHospList.setItemAnimator(new DefaultItemAnimator());
                    HospitalListActivity.this.recvHospList.setAdapter(HospitalListActivity.this.reqBloodHospitalListAdapter);
                    HospitalListActivity.this.reqBloodHospitalListAdapter.notifyDataSetChanged();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    HospitalListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(HospitalListActivity.this, "No Results Found", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(this, "Unable to process request. Please try again..!!", 0).show();
        }
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    public void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.are_you_sure_logout));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.patient.HospitalListActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(HospitalListActivity.this.getApplicationContext(), HospitalListActivity.this.getResources().getString(R.string.logout_success), 0).show();
                HospitalListActivity hospitalListActivity = HospitalListActivity.this;
                hospitalListActivity.startActivity(new Intent(hospitalListActivity, PatientLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                HospitalListActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.patient.HospitalListActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
