package edu.communication.hemo.hospital;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
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
import edu.communication.hemo.hospital.model.HospitalDetails;
import java.util.ArrayList;

public class DoctorsListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    ArrayList<DoctorDetails> doctorDetailsArrayList = new ArrayList<>();
    DoctorListAdapter doctorListAdapter;
    ImageView expandedMenu;
    RecyclerView hospDoctorsList;
    HospitalDetails hospitalDetails;
    DatabaseReference myRef;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        this.hospDoctorsList = (RecyclerView) findViewById(R.id.hosp_doctors_list);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.hospDoctorsList.setLayoutManager(linearLayoutManager);
        this.hospDoctorsList.setItemAnimator(new DefaultItemAnimator());
        final ChipGroup filterChipGroupSingleSelection = (ChipGroup) findViewById(R.id.hosp_choice_chip_group);
        try {
            this.hospitalDetails = (HospitalDetails) getIntent().getExtras().getParcelable("hospitalDetails");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
        try {
            filterChipGroupSingleSelection.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() { // from class: edu.communication.hemo.hospital.DoctorsListActivity.1
                @Override // com.google.android.material.chip.ChipGroup.OnCheckedChangeListener
                public void onCheckedChanged(ChipGroup group, int checkedId) {
                    switch (filterChipGroupSingleSelection.getCheckedChipId()) {
                        case R.id.choice_all /* 2131296412 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
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
                            DoctorsListActivity doctorsListActivity = DoctorsListActivity.this;
                            doctorsListActivity.doctorListAdapter = new DoctorListAdapter(doctorsListActivity, doctorsListActivity.doctorDetailsArrayList);
                            DoctorsListActivity.this.hospDoctorsList.setAdapter(DoctorsListActivity.this.doctorListAdapter);
                            DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                            return;
                        case R.id.choice_anesthesiologist /* 2131296414 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Anesthesiologist");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_cardiologist /* 2131296417 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Cardiologist");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_dermatologist /* 2131296419 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Dermatologist");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_gastroenterologist /* 2131296420 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Gastroenterologist");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_general_physician /* 2131296421 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("General physician");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_gynecologist /* 2131296422 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Gynecologist");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_neurologist /* 2131296423 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Neurologist");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_ophthalmologist /* 2131296426 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Ophthalmologist");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_orthopedic_surgeon /* 2131296427 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Orthopedic surgeon");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_pediatrician /* 2131296428 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Pediatrician");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_radiologist /* 2131296429 */:
                            if (DoctorsListActivity.this.doctorListAdapter != null) {
                                DoctorsListActivity.this.doctorListAdapter.filter("Radiologist");
                                DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.expandedMenu = (ImageView) findViewById(R.id.add_doctor_menu);
        this.expandedMenu.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.hospital.DoctorsListActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DoctorsListActivity.this.showMenu(v);
            }
        });
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.add_doctor_menu);
        popup.show();
    }

    @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId != R.id.add_doctor_item) {
            if (itemId == R.id.logout) {
                showLogoutAlert();
                return true;
            }
            return false;
        }
        try {
            Intent intent = new Intent(this, AddDoctorActivity.class);
            intent.putExtra("hospitalDetails", this.hospitalDetails);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void fetchDoctorDetails() {
        this.doctorDetailsArrayList.clear();
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.DOCTORS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.hospital.DoctorsListActivity.3
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        DoctorsListActivity.this.awesomeProgressDialog.dismiss();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            DoctorDetails doctorDetails = (DoctorDetails) dataSnapshot1.getValue(DoctorDetails.class);
                            if (DoctorsListActivity.this.hospitalDetails.getmHosCode().equals(doctorDetails.getmHospital())) {
                                DoctorsListActivity.this.doctorDetailsArrayList.add(doctorDetails);
                            }
                            DoctorsListActivity doctorsListActivity = DoctorsListActivity.this;
                            doctorsListActivity.doctorListAdapter = new DoctorListAdapter(doctorsListActivity, doctorsListActivity.doctorDetailsArrayList);
                            DoctorsListActivity.this.awesomeProgressDialog.dismiss();
                            DoctorsListActivity.this.hospDoctorsList.setAdapter(DoctorsListActivity.this.doctorListAdapter);
                            DoctorsListActivity.this.doctorListAdapter.notifyDataSetChanged();
                        }
                        return;
                    }
                    DoctorsListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DoctorsListActivity.this, "Doctor details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    DoctorsListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DoctorsListActivity.this, "No Results Found", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(this, "Unable to process request. Please try again..!!", 0).show();
        }
    }

    private void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.are_you_sure_logout));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.hospital.DoctorsListActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(DoctorsListActivity.this.getApplicationContext(), DoctorsListActivity.this.getResources().getString(R.string.logout_success), 0).show();
                DoctorsListActivity doctorsListActivity = DoctorsListActivity.this;
                doctorsListActivity.startActivity(new Intent(doctorsListActivity, HospitalLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                DoctorsListActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.hospital.DoctorsListActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        fetchDoctorDetails();
    }
}
