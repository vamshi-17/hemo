package edu.communication.hemo.doctor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
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
import edu.communication.hemo.adapter.PatientListAdapter;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;

public class PatientsListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    DoctorDetails doctorDetails;
    ImageView expandedMenu;
    DatabaseReference myRef;
    TextView noResults;
    ArrayList<PatientDetails> patientDetailsArrayList = new ArrayList<>();
    PatientListAdapter patientListAdapter;
    RecyclerView recyclerViewPatients;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);
        this.recyclerViewPatients = (RecyclerView) findViewById(R.id.patients);
        this.noResults = (TextView) findViewById(R.id.no_results);
        this.expandedMenu = (ImageView) findViewById(R.id.expanded_menu);
        this.doctorDetails = (DoctorDetails) getIntent().getExtras().getParcelable("doctorDetails");
        this.expandedMenu.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.doctor.PatientsListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PatientsListActivity.this.showMenu(v);
            }
        });
        fetchPatientsDetails();
    }

    private void fetchPatientsDetails() {
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.PATIENTS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.doctor.PatientsListActivity.2
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        PatientsListActivity.this.awesomeProgressDialog.dismiss();
                        PatientsListActivity.this.noResults.setVisibility(0);
                        PatientsListActivity.this.recyclerViewPatients.setVisibility(8);
                        return;
                    }
                    PatientsListActivity.this.noResults.setVisibility(8);
                    PatientsListActivity.this.recyclerViewPatients.setVisibility(0);
                    for (DataSnapshot receiptsSnapShot : dataSnapshot.getChildren()) {
                        PatientDetails patientDetails = (PatientDetails) receiptsSnapShot.getValue(PatientDetails.class);
                        PatientsListActivity.this.patientDetailsArrayList.add(patientDetails);
                    }
                    PatientsListActivity patientsListActivity = PatientsListActivity.this;
                    patientsListActivity.patientListAdapter = new PatientListAdapter(patientsListActivity, patientsListActivity.patientDetailsArrayList, PatientsListActivity.this.doctorDetails);
                    PatientsListActivity.this.awesomeProgressDialog.dismiss();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(PatientsListActivity.this);
                    PatientsListActivity.this.recyclerViewPatients.setLayoutManager(linearLayoutManager);
                    PatientsListActivity.this.recyclerViewPatients.setItemAnimator(new DefaultItemAnimator());
                    PatientsListActivity.this.recyclerViewPatients.setAdapter(PatientsListActivity.this.patientListAdapter);
                    PatientsListActivity.this.patientListAdapter.notifyDataSetChanged();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    PatientsListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientsListActivity.this, "No Results Found", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(this, "Unable to process request. Please try again..!!", 0).show();
        }
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }

    private void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.are_you_sure_logout));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.doctor.PatientsListActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(PatientsListActivity.this.getApplicationContext(), PatientsListActivity.this.getResources().getString(R.string.logout_success), 0).show();
                PatientsListActivity patientsListActivity = PatientsListActivity.this;
                patientsListActivity.startActivity(new Intent(patientsListActivity, DoctorLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                PatientsListActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.doctor.PatientsListActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        showLogoutAlert();
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_doctor);
        popup.show();
    }

    @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.logout) {
            showLogoutAlert();
            return true;
        } else if (itemId == R.id.profile) {
            try {
                if (this.doctorDetails != null) {
                    Intent intent = new Intent(this, DoctorProfileActivity.class);
                    intent.putExtra("doctorDetails", this.doctorDetails);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}
