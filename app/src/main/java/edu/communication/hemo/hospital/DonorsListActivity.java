package edu.communication.hemo.hospital;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.R;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.donor.model.DonorDetails;
import edu.communication.hemo.hospital.model.HospitalDetails;
import java.util.ArrayList;

public class DonorsListActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    ArrayList<DonorDetails> donorDetailsArrayList = new ArrayList<>();
    DonorListAdapter donorListAdapter;
    ImageView donorLogout;
    RecyclerView donorsList;
    HospitalDetails hospitalDetails;
    DatabaseReference myRef;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors_list);
        this.donorsList = (RecyclerView) findViewById(R.id.donors_list);
        this.donorLogout = (ImageView) findViewById(R.id.donor_logout);
        try {
            this.hospitalDetails = (HospitalDetails) getIntent().getExtras().getParcelable("hospitalDetails");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
        this.donorLogout.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.hospital.DonorsListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DonorsListActivity.this.showLogoutAlert();
            }
        });
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.donorsList.setLayoutManager(linearLayoutManager);
        this.donorsList.setItemAnimator(new DefaultItemAnimator());
        final ChipGroup filterChipGroupSingleSelection = (ChipGroup) findViewById(R.id.blood_choice_chip_group);
        try {
            filterChipGroupSingleSelection.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() { // from class: edu.communication.hemo.hospital.DonorsListActivity.2
                @Override // com.google.android.material.chip.ChipGroup.OnCheckedChangeListener
                public void onCheckedChanged(ChipGroup group, int checkedId) {
                    switch (filterChipGroupSingleSelection.getCheckedChipId()) {
                        case R.id.choice_a_negative /* 2131296408 */:
                            if (DonorsListActivity.this.donorListAdapter != null) {
                                DonorsListActivity.this.donorListAdapter.filter("A-");
                                DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_a_positive /* 2131296409 */:
                            if (DonorsListActivity.this.donorListAdapter != null) {
                                DonorsListActivity.this.donorListAdapter.filter("A+");
                                DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_ab_negative /* 2131296410 */:
                            if (DonorsListActivity.this.donorListAdapter != null) {
                                DonorsListActivity.this.donorListAdapter.filter("AB-");
                                DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_ab_positive /* 2131296411 */:
                            if (DonorsListActivity.this.donorListAdapter != null) {
                                DonorsListActivity.this.donorListAdapter.filter("AB+");
                                DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_b_negative /* 2131296415 */:
                            if (DonorsListActivity.this.donorListAdapter != null) {
                                DonorsListActivity.this.donorListAdapter.filter("B-");
                                DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_b_positive /* 2131296416 */:
                            if (DonorsListActivity.this.donorListAdapter != null) {
                                DonorsListActivity.this.donorListAdapter.filter("B+");
                                DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_o_negative /* 2131296424 */:
                            if (DonorsListActivity.this.donorListAdapter != null) {
                                DonorsListActivity.this.donorListAdapter.filter("O-");
                                DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_o_positive /* 2131296425 */:
                            if (DonorsListActivity.this.donorListAdapter != null) {
                                DonorsListActivity.this.donorListAdapter.filter("O+");
                                DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        default:
                            DonorsListActivity donorsListActivity = DonorsListActivity.this;
                            donorsListActivity.donorListAdapter = new DonorListAdapter(donorsListActivity, donorsListActivity.donorDetailsArrayList, DonorsListActivity.this.hospitalDetails);
                            DonorsListActivity.this.donorsList.setAdapter(DonorsListActivity.this.donorListAdapter);
                            DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                            return;
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void fetchDonorsDetails() {
        this.donorDetailsArrayList.clear();
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.DONORS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.hospital.DonorsListActivity.3
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        DonorsListActivity.this.awesomeProgressDialog.dismiss();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            DonorDetails donorDetails = (DonorDetails) dataSnapshot1.getValue(DonorDetails.class);
                            if (donorDetails != null) {
                                DonorsListActivity.this.donorDetailsArrayList.add(donorDetails);
                            }
                            DonorsListActivity donorsListActivity = DonorsListActivity.this;
                            donorsListActivity.donorListAdapter = new DonorListAdapter(donorsListActivity, donorsListActivity.donorDetailsArrayList, DonorsListActivity.this.hospitalDetails);
                            DonorsListActivity.this.awesomeProgressDialog.dismiss();
                            DonorsListActivity.this.donorsList.setAdapter(DonorsListActivity.this.donorListAdapter);
                            DonorsListActivity.this.donorListAdapter.notifyDataSetChanged();
                        }
                        return;
                    }
                    DonorsListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DonorsListActivity.this, "Doctor details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    DonorsListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DonorsListActivity.this, "No Results Found", 0).show();
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


    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        fetchDonorsDetails();
    }


    public void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.are_you_sure_logout));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.hospital.DonorsListActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(DonorsListActivity.this.getApplicationContext(), DonorsListActivity.this.getResources().getString(R.string.logout_success), 0).show();
                DonorsListActivity donorsListActivity = DonorsListActivity.this;
                donorsListActivity.startActivity(new Intent(donorsListActivity, HospitalLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                DonorsListActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.hospital.DonorsListActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
