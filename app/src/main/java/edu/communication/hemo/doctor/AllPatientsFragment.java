package edu.communication.hemo.doctor;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
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

public class AllPatientsFragment extends Fragment {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    DoctorDashBoardActivity doctorDashBoardActivity;
    DoctorDetails doctorDetails;
    DatabaseReference myRef;
    TextView noResults;
    ArrayList<PatientDetails> patientDetailsArrayList = new ArrayList<>();
    PatientListAdapter patientListAdapter;
    RecyclerView recyclerViewPatients;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_patients, container, false);
        this.recyclerViewPatients = (RecyclerView) view.findViewById(R.id.patients);
        this.noResults = (TextView) view.findViewById(R.id.no_results);
        this.doctorDashBoardActivity = (DoctorDashBoardActivity) getActivity();
        this.doctorDetails = this.doctorDashBoardActivity.doctorDetails;
        fetchPatientsDetails();
        return view;
    }

    private void fetchPatientsDetails() {
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(getActivity()).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.PATIENTS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.doctor.AllPatientsFragment.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        AllPatientsFragment.this.awesomeProgressDialog.dismiss();
                        AllPatientsFragment.this.noResults.setVisibility(0);
                        AllPatientsFragment.this.recyclerViewPatients.setVisibility(8);
                        return;
                    }
                    AllPatientsFragment.this.noResults.setVisibility(8);
                    AllPatientsFragment.this.recyclerViewPatients.setVisibility(0);
                    for (DataSnapshot receiptsSnapShot : dataSnapshot.getChildren()) {
                        PatientDetails patientDetails = (PatientDetails) receiptsSnapShot.getValue(PatientDetails.class);
                        AllPatientsFragment.this.patientDetailsArrayList.add(patientDetails);
                    }
                    AllPatientsFragment allPatientsFragment = AllPatientsFragment.this;
                    allPatientsFragment.patientListAdapter = new PatientListAdapter(allPatientsFragment.getActivity(), AllPatientsFragment.this.patientDetailsArrayList, AllPatientsFragment.this.doctorDetails);
                    AllPatientsFragment.this.awesomeProgressDialog.dismiss();
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(AllPatientsFragment.this.getActivity());
                    AllPatientsFragment.this.recyclerViewPatients.setLayoutManager(linearLayoutManager);
                    AllPatientsFragment.this.recyclerViewPatients.setItemAnimator(new DefaultItemAnimator());
                    AllPatientsFragment.this.recyclerViewPatients.setAdapter(AllPatientsFragment.this.patientListAdapter);
                    AllPatientsFragment.this.patientListAdapter.notifyDataSetChanged();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    AllPatientsFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(AllPatientsFragment.this.getActivity(), "No Results Found", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(getActivity(), "Unable to process request. Please try again..!!", 0).show();
        }
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }
}
