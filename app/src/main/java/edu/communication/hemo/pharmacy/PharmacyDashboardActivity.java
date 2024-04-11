package edu.communication.hemo.pharmacy;

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
import edu.communication.hemo.adapter.PharmaReceivedPrescriptionListAdapter;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.patient.model.SharePrescriptionToPharmacyDetails;
import edu.communication.hemo.pharmacy.model.PharmacyDetails;
import java.util.ArrayList;

public class PharmacyDashboardActivity extends AppCompatActivity implements PharmaReceivedPrescriptionListAdapter.MyInterface, PopupMenu.OnMenuItemClickListener {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    ImageView expandedMenu;
    DatabaseReference myRef;
    TextView noResults;
    PharmacyDetails pharmacyDetails;
    PharmaReceivedPrescriptionListAdapter prescriptionListAdapter;
    RecyclerView recyclerViewReceivedPrescriptions;
    ArrayList<SharePrescriptionToPharmacyDetails> sharePrescriptionToPharmacyDetailsArrayList = new ArrayList<>();


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_dashboard);
        this.pharmacyDetails = (PharmacyDetails) getIntent().getExtras().getParcelable("pharmacyDetails");
        this.recyclerViewReceivedPrescriptions = (RecyclerView) findViewById(R.id.received_prescriptions);
        this.noResults = (TextView) findViewById(R.id.no_results);
        this.expandedMenu = (ImageView) findViewById(R.id.expanded_menu);
        fetchPrescriptionDetails(this.pharmacyDetails.getmEmail());
        this.expandedMenu.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.pharmacy.PharmacyDashboardActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PharmacyDashboardActivity.this.showMenu(v);
            }
        });
    }

    private void fetchPrescriptionDetails(String mEmail) {
        this.myRef = fireBaseInitialSetup();
        final String encodedEmail = Utils.EncodeString(mEmail);
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.PHARMACYPRESCRIPTIONS).child(this.pharmacyDetails.getmEmail()).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.pharmacy.PharmacyDashboardActivity.2
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        PharmacyDashboardActivity.this.awesomeProgressDialog.dismiss();
                        PharmacyDashboardActivity.this.noResults.setVisibility(0);
                        PharmacyDashboardActivity.this.recyclerViewReceivedPrescriptions.setVisibility(8);
                        return;
                    }
                    PharmacyDashboardActivity.this.noResults.setVisibility(8);
                    PharmacyDashboardActivity.this.recyclerViewReceivedPrescriptions.setVisibility(0);
                    for (DataSnapshot receiptsSnapShot : dataSnapshot.getChildren()) {
                        SharePrescriptionToPharmacyDetails sharePrescriptionToPharmacyDetails = (SharePrescriptionToPharmacyDetails) receiptsSnapShot.getValue(SharePrescriptionToPharmacyDetails.class);
                        PharmacyDashboardActivity.this.sharePrescriptionToPharmacyDetailsArrayList.add(sharePrescriptionToPharmacyDetails);
                    }
                    PharmacyDashboardActivity pharmacyDashboardActivity = PharmacyDashboardActivity.this;
                    pharmacyDashboardActivity.prescriptionListAdapter = new PharmaReceivedPrescriptionListAdapter(pharmacyDashboardActivity, pharmacyDashboardActivity.sharePrescriptionToPharmacyDetailsArrayList, PharmacyDashboardActivity.this);
                    PharmacyDashboardActivity.this.awesomeProgressDialog.dismiss();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(PharmacyDashboardActivity.this);
                    PharmacyDashboardActivity.this.recyclerViewReceivedPrescriptions.setLayoutManager(linearLayoutManager);
                    PharmacyDashboardActivity.this.recyclerViewReceivedPrescriptions.setItemAnimator(new DefaultItemAnimator());
                    PharmacyDashboardActivity.this.recyclerViewReceivedPrescriptions.setAdapter(PharmacyDashboardActivity.this.prescriptionListAdapter);
                    PharmacyDashboardActivity.this.prescriptionListAdapter.filter(encodedEmail);
                    PharmacyDashboardActivity.this.prescriptionListAdapter.notifyDataSetChanged();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    PharmacyDashboardActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PharmacyDashboardActivity.this, "No Results Found", 0).show();
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

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_pharma);
        popup.show();
    }

    @Override // edu.communication.hemo.adapter.PharmaReceivedPrescriptionListAdapter.MyInterface
    public void sendCountToActivity(int size) {
        if (size == 0) {
            this.noResults.setVisibility(0);
            this.recyclerViewReceivedPrescriptions.setVisibility(8);
            return;
        }
        this.noResults.setVisibility(8);
        this.recyclerViewReceivedPrescriptions.setVisibility(0);
    }

    @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            showLogoutAlert();
            return true;
        }
        return false;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        showLogoutAlert();
    }

    private void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.are_you_sure_logout));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.pharmacy.PharmacyDashboardActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(PharmacyDashboardActivity.this.getApplicationContext(), PharmacyDashboardActivity.this.getResources().getString(R.string.logout_success), 0).show();
                PharmacyDashboardActivity pharmacyDashboardActivity = PharmacyDashboardActivity.this;
                pharmacyDashboardActivity.startActivity(new Intent(pharmacyDashboardActivity, PharmacyLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                PharmacyDashboardActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.pharmacy.PharmacyDashboardActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
