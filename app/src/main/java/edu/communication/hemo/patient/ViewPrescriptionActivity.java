package edu.communication.hemo.patient;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
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
import edu.communication.hemo.adapter.PrescriptionListAdapter;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.doctor.model.PrescriptionDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;

public class ViewPrescriptionActivity extends AppCompatActivity implements PrescriptionListAdapter.MyInterface {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView noResults;
    PatientDetails patientDetails;
    PrescriptionListAdapter prescriptionListAdapter;
    RecyclerView recyclerViewPrescriptions;
    ArrayList<PrescriptionDetails> prescriptionDetailsArrayLists = new ArrayList<>();
    int prescriptionsCount = 0;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        this.recyclerViewPrescriptions = (RecyclerView) findViewById(R.id.prescriptions_list);
        this.noResults = (TextView) findViewById(R.id.no_results);
        this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
        fetchPrescriptionDetails(this.patientDetails.getmEmail());
    }

    private void fetchPrescriptionDetails(String email) {
        this.myRef = fireBaseInitialSetup();
        final String encodedEmail = Utils.EncodeString(email);
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.PRESCRIPTIONS).child(this.patientDetails.getmEmail()).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.patient.ViewPrescriptionActivity.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        ViewPrescriptionActivity.this.awesomeProgressDialog.dismiss();
                        ViewPrescriptionActivity.this.noResults.setVisibility(0);
                        ViewPrescriptionActivity.this.recyclerViewPrescriptions.setVisibility(8);
                        return;
                    }
                    ViewPrescriptionActivity.this.noResults.setVisibility(8);
                    ViewPrescriptionActivity.this.recyclerViewPrescriptions.setVisibility(0);
                    for (DataSnapshot receiptsSnapShot : dataSnapshot.getChildren()) {
                        PrescriptionDetails prescriptionDetails = (PrescriptionDetails) receiptsSnapShot.getValue(PrescriptionDetails.class);
                        ViewPrescriptionActivity.this.prescriptionDetailsArrayLists.add(prescriptionDetails);
                    }
                    ViewPrescriptionActivity viewPrescriptionActivity = ViewPrescriptionActivity.this;
                    viewPrescriptionActivity.prescriptionListAdapter = new PrescriptionListAdapter(viewPrescriptionActivity, viewPrescriptionActivity.prescriptionDetailsArrayLists, ViewPrescriptionActivity.this);
                    ViewPrescriptionActivity.this.awesomeProgressDialog.dismiss();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(ViewPrescriptionActivity.this);
                    ViewPrescriptionActivity.this.recyclerViewPrescriptions.setLayoutManager(linearLayoutManager);
                    ViewPrescriptionActivity.this.recyclerViewPrescriptions.setItemAnimator(new DefaultItemAnimator());
                    ViewPrescriptionActivity.this.recyclerViewPrescriptions.setAdapter(ViewPrescriptionActivity.this.prescriptionListAdapter);
                    ViewPrescriptionActivity.this.prescriptionListAdapter.filter(encodedEmail);
                    ViewPrescriptionActivity.this.prescriptionListAdapter.notifyDataSetChanged();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    ViewPrescriptionActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(ViewPrescriptionActivity.this, "No Results Found", 0).show();
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

    @Override // edu.communication.hemo.adapter.PrescriptionListAdapter.MyInterface
    public void sendCountToActivity(int size) {
        if (size == 0) {
            this.noResults.setVisibility(0);
            this.recyclerViewPrescriptions.setVisibility(8);
            return;
        }
        this.noResults.setVisibility(8);
        this.recyclerViewPrescriptions.setVisibility(0);
    }
}
