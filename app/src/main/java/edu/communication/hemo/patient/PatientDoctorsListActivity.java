package edu.communication.hemo.patient;

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
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;

public class PatientDoctorsListActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    ArrayList<DoctorDetails> doctorDetailsArrayList = new ArrayList<>();
    ImageView logout;
    DatabaseReference myRef;
    PatientDetails patientDetails;
    PatientDoctorListAdapter patientDoctorListAdapter;
    RecyclerView patientDoctorsList;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_doctors_list);
        this.patientDoctorsList = (RecyclerView) findViewById(R.id.patient_doctors_list);
        this.logout = (ImageView) findViewById(R.id.ic_logout);
        this.logout.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.PatientDoctorsListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PatientDoctorsListActivity.this.showLogoutAlert();
            }
        });
        try {
            this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.patientDoctorsList.setLayoutManager(linearLayoutManager);
        this.patientDoctorsList.setItemAnimator(new DefaultItemAnimator());
        final ChipGroup filterChipGroupSingleSelection = (ChipGroup) findViewById(R.id.choice_chip_group);
        try {
            filterChipGroupSingleSelection.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() { // from class: edu.communication.hemo.patient.PatientDoctorsListActivity.2
                @Override // com.google.android.material.chip.ChipGroup.OnCheckedChangeListener
                public void onCheckedChanged(ChipGroup group, int checkedId) {
                    switch (filterChipGroupSingleSelection.getCheckedChipId()) {
                        case R.id.choice_all /* 2131296412 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_all_doctors /* 2131296413 */:
                        case R.id.choice_b_negative /* 2131296415 */:
                        case R.id.choice_b_positive /* 2131296416 */:
                        case R.id.choice_chip_group /* 2131296418 */:
                        case R.id.choice_o_negative /* 2131296424 */:
                        case R.id.choice_o_positive /* 2131296425 */:
                        default:
                            PatientDoctorsListActivity patientDoctorsListActivity = PatientDoctorsListActivity.this;
                            patientDoctorsListActivity.patientDoctorListAdapter = new PatientDoctorListAdapter(patientDoctorsListActivity, patientDoctorsListActivity.doctorDetailsArrayList, PatientDoctorsListActivity.this.patientDetails);
                            PatientDoctorsListActivity.this.patientDoctorsList.setAdapter(PatientDoctorsListActivity.this.patientDoctorListAdapter);
                            PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                            return;
                        case R.id.choice_anesthesiologist /* 2131296414 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Anesthesiologist");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_cardiologist /* 2131296417 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Cardiologist");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_dermatologist /* 2131296419 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Dermatologist");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_gastroenterologist /* 2131296420 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Gastroenterologist");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_general_physician /* 2131296421 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("General physician");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_gynecologist /* 2131296422 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Gynecologist");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_neurologist /* 2131296423 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Neurologist");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_ophthalmologist /* 2131296426 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Ophthalmologist");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_orthopedic_surgeon /* 2131296427 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Orthopedic surgeon");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_pediatrician /* 2131296428 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Pediatrician");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_radiologist /* 2131296429 */:
                            if (PatientDoctorsListActivity.this.patientDoctorListAdapter != null) {
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.filter("Radiologist");
                                PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }


    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        fetchDoctorDetails();
    }

    private void fetchDoctorDetails() {
        this.doctorDetailsArrayList.clear();
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.DOCTORS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.patient.PatientDoctorsListActivity.3
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PatientDoctorsListActivity.this.awesomeProgressDialog.dismiss();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            DoctorDetails doctorDetails = (DoctorDetails) dataSnapshot1.getValue(DoctorDetails.class);
                            if (doctorDetails != null) {
                                PatientDoctorsListActivity.this.doctorDetailsArrayList.add(doctorDetails);
                            }
                            PatientDoctorsListActivity patientDoctorsListActivity = PatientDoctorsListActivity.this;
                            patientDoctorsListActivity.patientDoctorListAdapter = new PatientDoctorListAdapter(patientDoctorsListActivity, patientDoctorsListActivity.doctorDetailsArrayList, PatientDoctorsListActivity.this.patientDetails);
                            PatientDoctorsListActivity.this.awesomeProgressDialog.dismiss();
                            PatientDoctorsListActivity.this.patientDoctorsList.setAdapter(PatientDoctorsListActivity.this.patientDoctorListAdapter);
                            PatientDoctorsListActivity.this.patientDoctorListAdapter.notifyDataSetChanged();
                        }
                        return;
                    }
                    PatientDoctorsListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientDoctorsListActivity.this, "Doctor details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    PatientDoctorsListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientDoctorsListActivity.this, "No Results Found", 0).show();
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
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.patient.PatientDoctorsListActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(PatientDoctorsListActivity.this.getApplicationContext(), PatientDoctorsListActivity.this.getResources().getString(R.string.logout_success), 0).show();
                PatientDoctorsListActivity patientDoctorsListActivity = PatientDoctorsListActivity.this;
                patientDoctorsListActivity.startActivity(new Intent(patientDoctorsListActivity, PatientLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                PatientDoctorsListActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.patient.PatientDoctorsListActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
