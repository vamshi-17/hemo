package edu.communication.hemo.hospital;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import edu.communication.hemo.hospital.BloodPlasmaListAdapter;
import edu.communication.hemo.hospital.model.HospitalDetails;
import edu.communication.hemo.patient.BloodPlasmaRequestDetails;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BloodPlasmaRequestsList extends AppCompatActivity implements BloodPlasmaListAdapter.MyInterface {
    private Dialog awesomeProgressDialog;
    BloodPlasmaListAdapter bloodPlasmaListAdapter;
    ArrayList<BloodPlasmaRequestDetails> bloodPlasmaRequestDetailsArrayList = new ArrayList<>();
    FirebaseDatabase database;
    RecyclerView hospBloodPlasmaRequestList;
    HospitalDetails hospitalDetails;
    ImageView ivHospLogout;
    DatabaseReference myRef;
    TextView noResults;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_plasma_requests_list);
        try {
            this.hospitalDetails = (HospitalDetails) getIntent().getExtras().getParcelable("hospitalDetails");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
        this.hospBloodPlasmaRequestList = (RecyclerView) findViewById(R.id.hosp_blood_plasma_request_list);
        this.noResults = (TextView) findViewById(R.id.no_results);
        this.ivHospLogout = (ImageView) findViewById(R.id.iv_hosp_logout);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.hospBloodPlasmaRequestList.setLayoutManager(linearLayoutManager);
        this.hospBloodPlasmaRequestList.setItemAnimator(new DefaultItemAnimator());
        this.ivHospLogout.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.hospital.BloodPlasmaRequestsList.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BloodPlasmaRequestsList.this.showLogoutAlert();
            }
        });
    }


    public void fetchBloodPlasmaRequestDetails() {
        this.bloodPlasmaRequestDetailsArrayList.clear();
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.BLOOD_PLASMA_REQUESTS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.hospital.BloodPlasmaRequestsList.2
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        BloodPlasmaRequestsList.this.awesomeProgressDialog.dismiss();
                        BloodPlasmaRequestsList.this.noResults.setVisibility(8);
                        BloodPlasmaRequestsList.this.hospBloodPlasmaRequestList.setVisibility(0);
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            BloodPlasmaRequestDetails bloodPlasmaRequestDetails = (BloodPlasmaRequestDetails) dataSnapshot1.getValue(BloodPlasmaRequestDetails.class);
                            bloodPlasmaRequestDetails.setuId(dataSnapshot1.getKey());
                            if (bloodPlasmaRequestDetails.getIsRequestAccepted().equals("created") || bloodPlasmaRequestDetails.getIsRequestAccepted().equals("rejected")) {
                                BloodPlasmaRequestsList.this.bloodPlasmaRequestDetailsArrayList.add(bloodPlasmaRequestDetails);
                            }
                            BloodPlasmaRequestsList bloodPlasmaRequestsList = BloodPlasmaRequestsList.this;
                            bloodPlasmaRequestsList.bloodPlasmaListAdapter = new BloodPlasmaListAdapter(bloodPlasmaRequestsList, bloodPlasmaRequestsList.bloodPlasmaRequestDetailsArrayList, BloodPlasmaRequestsList.this);
                            BloodPlasmaRequestsList.this.awesomeProgressDialog.dismiss();
                            BloodPlasmaRequestsList.this.hospBloodPlasmaRequestList.setAdapter(BloodPlasmaRequestsList.this.bloodPlasmaListAdapter);
                            BloodPlasmaRequestsList.this.bloodPlasmaListAdapter.notifyDataSetChanged();
                        }
                        return;
                    }
                    BloodPlasmaRequestsList.this.noResults.setVisibility(0);
                    BloodPlasmaRequestsList.this.hospBloodPlasmaRequestList.setVisibility(8);
                    BloodPlasmaRequestsList.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(BloodPlasmaRequestsList.this, "Blood/Plasma details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    BloodPlasmaRequestsList.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(BloodPlasmaRequestsList.this, "No Results Found", 0).show();
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
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.hospital.BloodPlasmaRequestsList.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(BloodPlasmaRequestsList.this.getApplicationContext(), BloodPlasmaRequestsList.this.getResources().getString(R.string.logout_success), 0).show();
                BloodPlasmaRequestsList bloodPlasmaRequestsList = BloodPlasmaRequestsList.this;
                bloodPlasmaRequestsList.startActivity(new Intent(bloodPlasmaRequestsList, HospitalLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                BloodPlasmaRequestsList.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.hospital.BloodPlasmaRequestsList.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override // edu.communication.hemo.hospital.BloodPlasmaListAdapter.MyInterface
    public void acceptRequest(BloodPlasmaRequestDetails bloodPlasmaRequestDetails) {
        DatabaseReference ref = this.myRef.child(CommonData.BLOOD_PLASMA_REQUESTS).child(bloodPlasmaRequestDetails.getuId());
        Map<String, Object> updates = new HashMap<>();
        updates.put("isRequestAccepted", "accepted");
        try {
            this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.updating_acceptance).show();
            ref.updateChildren(updates);
            new Handler().postDelayed(new Runnable() { // from class: edu.communication.hemo.hospital.BloodPlasmaRequestsList.5
                @Override // java.lang.Runnable
                public void run() {
                    BloodPlasmaRequestsList.this.awesomeProgressDialog.dismiss();
                    BloodPlasmaRequestsList.this.fetchBloodPlasmaRequestDetails();
                }
            }, 2000L);
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(this, "Unable to process request. Please try again..!!", 0).show();
        }
    }

    @Override // edu.communication.hemo.hospital.BloodPlasmaListAdapter.MyInterface
    public void rejectRequest(BloodPlasmaRequestDetails bloodPlasmaRequestDetails) {
        DatabaseReference ref = this.myRef.child(CommonData.BLOOD_PLASMA_REQUESTS).child(bloodPlasmaRequestDetails.getuId());
        Map<String, Object> updates = new HashMap<>();
        updates.put("isRequestAccepted", "rejected");
        try {
            this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.updating_reject).show();
            ref.updateChildren(updates);
            new Handler().postDelayed(new Runnable() { // from class: edu.communication.hemo.hospital.BloodPlasmaRequestsList.6
                @Override // java.lang.Runnable
                public void run() {
                    BloodPlasmaRequestsList.this.awesomeProgressDialog.dismiss();
                    BloodPlasmaRequestsList.this.fetchBloodPlasmaRequestDetails();
                }
            }, 2000L);
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(this, "Unable to process request. Please try again..!!", 0).show();
        }
    }


    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        fetchBloodPlasmaRequestDetails();
    }
}
