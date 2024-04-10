package edu.communication.hemo.donor;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
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
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.donor.DonorHospitalBloodRequestListAdapter;
import edu.communication.hemo.donor.model.DonorDetails;
import edu.communication.hemo.hospital.model.RequestBloodDetails;
import java.util.ArrayList;

public class DonorNotificationFragment extends Fragment implements DonorHospitalBloodRequestListAdapter.MyInterface {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    DonorDashboardActivity donorDashboardActivity;
    DonorDetails donorDetails;
    DonorHospitalBloodRequestListAdapter donorHospitalBloodRequestListAdapter;
    DatabaseReference myRef;
    TextView noResults;
    RecyclerView recyclerViewDonorHospitalBloodRequests;
    ArrayList<RequestBloodDetails> requestBloodDetailsArrayList = new ArrayList<>();

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donor_notification, container, false);
        this.recyclerViewDonorHospitalBloodRequests = (RecyclerView) view.findViewById(R.id.hospital_blood_requests_list);
        this.recyclerViewDonorHospitalBloodRequests.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        this.recyclerViewDonorHospitalBloodRequests.setLayoutManager(linearLayoutManager);
        this.noResults = (TextView) view.findViewById(R.id.no_results);
        this.donorDashboardActivity = (DonorDashboardActivity) getActivity();
        this.donorDetails = this.donorDashboardActivity.donorDetails;
        this.myRef = fireBaseInitialSetup();
        fetchDonorHospBloodRequets();
        return view;
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }

    @Override // edu.communication.hemo.donor.DonorHospitalBloodRequestListAdapter.MyInterface
    public void acceptAppointment(RequestBloodDetails requestBloodDetails) {
        requestBloodDetails.setIsDonationAccepted(true);
        this.myRef.child(CommonData.HOSP_BLOOD_REQUESTS).child(requestBloodDetails.getuId()).setValue(requestBloodDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.donor.DonorNotificationFragment.1
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    DonorNotificationFragment.this.fetchDonorHospBloodRequets();
                    DonorNotificationFragment.this.donorHospitalBloodRequestListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override // edu.communication.hemo.donor.DonorHospitalBloodRequestListAdapter.MyInterface
    public void rejectAppointment(String uId) {
        this.myRef.child(CommonData.HOSP_BLOOD_REQUESTS).child(uId).setValue(null);
        fetchDonorHospBloodRequets();
        this.donorHospitalBloodRequestListAdapter.notifyDataSetChanged();
    }


    public void fetchDonorHospBloodRequets() {
        this.myRef.child(CommonData.HOSP_BLOOD_REQUESTS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.donor.DonorNotificationFragment.2
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                DonorNotificationFragment.this.requestBloodDetailsArrayList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1 != null) {
                            RequestBloodDetails requestBloodDetails = (RequestBloodDetails) dataSnapshot1.getValue(RequestBloodDetails.class);
                            requestBloodDetails.setuId(dataSnapshot1.getKey());
                            if (requestBloodDetails != null && requestBloodDetails.getDonorEmail().equals(Utils.EncodeString(DonorNotificationFragment.this.donorDetails.getmEmail()))) {
                                DonorNotificationFragment.this.requestBloodDetailsArrayList.add(requestBloodDetails);
                                DonorNotificationFragment donorNotificationFragment = DonorNotificationFragment.this;
                                donorNotificationFragment.donorHospitalBloodRequestListAdapter = new DonorHospitalBloodRequestListAdapter(donorNotificationFragment.getActivity(), DonorNotificationFragment.this.requestBloodDetailsArrayList, DonorNotificationFragment.this);
                                DonorNotificationFragment.this.recyclerViewDonorHospitalBloodRequests.setAdapter(DonorNotificationFragment.this.donorHospitalBloodRequestListAdapter);
                            }
                        }
                    }
                }
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
