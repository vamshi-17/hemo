package edu.communication.hemo.patient;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.R;
import edu.communication.hemo.adapter.PharmaListAdapter;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.doctor.model.PrescriptionDetails;
import edu.communication.hemo.patient.model.SharePrescriptionToPharmacyDetails;
import edu.communication.hemo.pharmacy.model.PharmacyDetails;
import java.util.ArrayList;

public class PharmacyListActivity extends AppCompatActivity implements PharmaListAdapter.MyInterface {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView noResults;
    PharmaListAdapter pharmaListAdapter;
    ArrayList<PharmacyDetails> pharmacyDetailsArrayList = new ArrayList<>();
    PrescriptionDetails prescriptionDetails;
    RecyclerView recyclerViewPharmacies;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_list);
        this.recyclerViewPharmacies = (RecyclerView) findViewById(R.id.pharmacies);
        this.noResults = (TextView) findViewById(R.id.no_results);
        this.prescriptionDetails = (PrescriptionDetails) getIntent().getExtras().getParcelable("prescriptionDetails");
        fetchPharmacyDetails();
    }

    private void fetchPharmacyDetails() {
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.PHARMACY).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.patient.PharmacyListActivity.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        PharmacyListActivity.this.awesomeProgressDialog.dismiss();
                        PharmacyListActivity.this.noResults.setVisibility(0);
                        PharmacyListActivity.this.recyclerViewPharmacies.setVisibility(8);
                        return;
                    }
                    PharmacyListActivity.this.noResults.setVisibility(8);
                    PharmacyListActivity.this.recyclerViewPharmacies.setVisibility(0);
                    for (DataSnapshot receiptsSnapShot : dataSnapshot.getChildren()) {
                        PharmacyDetails pharmacyDetails = (PharmacyDetails) receiptsSnapShot.getValue(PharmacyDetails.class);
                        PharmacyListActivity.this.pharmacyDetailsArrayList.add(pharmacyDetails);
                    }
                    PharmacyListActivity pharmacyListActivity = PharmacyListActivity.this;
                    pharmacyListActivity.pharmaListAdapter = new PharmaListAdapter(pharmacyListActivity, pharmacyListActivity.pharmacyDetailsArrayList, PharmacyListActivity.this.prescriptionDetails, PharmacyListActivity.this);
                    PharmacyListActivity.this.awesomeProgressDialog.dismiss();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(PharmacyListActivity.this);
                    PharmacyListActivity.this.recyclerViewPharmacies.setLayoutManager(linearLayoutManager);
                    PharmacyListActivity.this.recyclerViewPharmacies.setItemAnimator(new DefaultItemAnimator());
                    PharmacyListActivity.this.recyclerViewPharmacies.setAdapter(PharmacyListActivity.this.pharmaListAdapter);
                    PharmacyListActivity.this.pharmaListAdapter.notifyDataSetChanged();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    PharmacyListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PharmacyListActivity.this, "No Results Found", 0).show();
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

    @Override // edu.communication.hemo.adapter.PharmaListAdapter.MyInterface
    public void sendPrescriptionToActivity(PrescriptionDetails prescriptionDetails, PharmacyDetails pharmacyDetails) {
        sharePrescriptionToPharmacy(prescriptionDetails, pharmacyDetails);
    }

    private void sharePrescriptionToPharmacy(PrescriptionDetails prescriptionDetails, PharmacyDetails pharmacyDetails) {
        this.myRef = fireBaseInitialSetup();
        final String encodedEmail = Utils.EncodeString(pharmacyDetails.getmEmail());
        final SharePrescriptionToPharmacyDetails sharePrescriptionToPharmacyDetails = new SharePrescriptionToPharmacyDetails();
        sharePrescriptionToPharmacyDetails.setPatientName(prescriptionDetails.getPatientName());
        sharePrescriptionToPharmacyDetails.setPatientMobileNumber(prescriptionDetails.getPatientMobileNumber());
        sharePrescriptionToPharmacyDetails.setPatientEmail(Utils.EncodeString(prescriptionDetails.getPatientEmail()));
        sharePrescriptionToPharmacyDetails.setDoctorEmail(Utils.EncodeString(prescriptionDetails.getDoctorEmail()));
        sharePrescriptionToPharmacyDetails.setDoctorName(prescriptionDetails.getDoctorName());
        sharePrescriptionToPharmacyDetails.setConsultType(prescriptionDetails.getConsultType());
        sharePrescriptionToPharmacyDetails.setConsultDate(prescriptionDetails.getConsultDate());
        sharePrescriptionToPharmacyDetails.setSuggestions(prescriptionDetails.getSuggestions());
        sharePrescriptionToPharmacyDetails.setPrescriptionImageURL(prescriptionDetails.getPrescriptionImageURL());
        sharePrescriptionToPharmacyDetails.setPharmacyEmail(encodedEmail);
        try {
            this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.uploading_prescription_details).show();
            this.myRef.child(CommonData.PHARMACYPRESCRIPTIONS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.patient.PharmacyListActivity.2
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PharmacyListActivity.this.myRef.child(CommonData.PHARMACYPRESCRIPTIONS).child(encodedEmail).push().setValue(sharePrescriptionToPharmacyDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.patient.PharmacyListActivity.2.1
                            @Override // com.google.android.gms.tasks.OnCompleteListener
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    PharmacyListActivity.this.awesomeProgressDialog.dismiss();
                                    Utils.showSuccessDialogue(PharmacyListActivity.this, "Patient prescription shared successfully ", true);
                                }
                            }
                        });
                    } else {
                        PharmacyListActivity.this.myRef.child(CommonData.PHARMACYPRESCRIPTIONS).child(encodedEmail).push().setValue(sharePrescriptionToPharmacyDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.patient.PharmacyListActivity.2.2
                            @Override // com.google.android.gms.tasks.OnCompleteListener
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    PharmacyListActivity.this.awesomeProgressDialog.dismiss();
                                    Utils.showSuccessDialogue(PharmacyListActivity.this, "Patient  prescription shared successfully", true);
                                }
                            }
                        });
                    }
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    PharmacyListActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PharmacyListActivity.this, "Something went wrong. Please try again..!!", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(this, "Unable to process request. Please try again..!!", 0).show();
        }
    }
}
