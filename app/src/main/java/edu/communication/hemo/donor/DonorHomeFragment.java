package edu.communication.hemo.donor;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.donor.model.DonorDetails;
import edu.communication.hemo.hospital.model.HospitalDetails;
import java.util.ArrayList;

public class DonorHomeFragment extends Fragment {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    DonorDashboardActivity donorDashboardActivity;
    DonorDetails donorDetails;
    RecyclerView donorHospitalsList;
    ArrayList<HospitalDetails> hospitalDetailsArrayList = new ArrayList<>();
    DatabaseReference myRef;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donor_home, container, false);
        this.donorHospitalsList = (RecyclerView) view.findViewById(R.id.hospital_list);
        this.donorDashboardActivity = (DonorDashboardActivity) getActivity();
        this.donorDetails = this.donorDashboardActivity.donorDetails;
        fetchHospitalDetails();
        return view;
    }

    private void fetchHospitalDetails() {
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(getActivity()).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.HOSPITALS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.donor.DonorHomeFragment.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        DonorHomeFragment.this.awesomeProgressDialog.dismiss();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            HospitalDetails hospitalDetails = (HospitalDetails) dataSnapshot1.getValue(HospitalDetails.class);
                            DonorHomeFragment.this.hospitalDetailsArrayList.add(hospitalDetails);
                        }
                        HospitalListAdapter hospitalListAdapter = new HospitalListAdapter(DonorHomeFragment.this.getContext(), DonorHomeFragment.this.hospitalDetailsArrayList, DonorHomeFragment.this.donorDetails);
                        DonorHomeFragment.this.awesomeProgressDialog.dismiss();
                        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(DonorHomeFragment.this.getContext());
                        DonorHomeFragment.this.donorHospitalsList.setLayoutManager(linearLayoutManager);
                        DonorHomeFragment.this.donorHospitalsList.setItemAnimator(new DefaultItemAnimator());
                        DonorHomeFragment.this.donorHospitalsList.setAdapter(hospitalListAdapter);
                        hospitalListAdapter.notifyDataSetChanged();
                        return;
                    }
                    DonorHomeFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DonorHomeFragment.this.getActivity(), "Notification details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    DonorHomeFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DonorHomeFragment.this.getActivity(), "No Results Found", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(getActivity(), "Unable to process request. Please try again..!!", 0).show();
        }
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }
}
