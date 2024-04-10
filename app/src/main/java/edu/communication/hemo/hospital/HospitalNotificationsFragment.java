package edu.communication.hemo.hospital;

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
import edu.communication.hemo.hospital.model.HospitalDetails;
import edu.communication.hemo.hospital.model.RequestBloodDetails;
import java.util.ArrayList;

public class HospitalNotificationsFragment extends Fragment {
    private Dialog awesomeProgressDialog;
    RecyclerView bloodDonorNotificationsList;
    FirebaseDatabase database;
    HospitalDashboardActivity hospitalDashboardActivity;
    HospitalDetails hospitalDetails;
    DatabaseReference myRef;
    ArrayList<RequestBloodDetails> requestBloodDetailsArrayList = new ArrayList<>();

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_notifications, container, false);
        this.bloodDonorNotificationsList = (RecyclerView) view.findViewById(R.id.blood_donor_notifications_list);
        this.hospitalDashboardActivity = (HospitalDashboardActivity) getActivity();
        this.hospitalDetails = this.hospitalDashboardActivity.hospitalDetails;
        fetchNotificationDetails();
        return view;
    }

    private void fetchNotificationDetails() {
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(getActivity()).setMessage(R.string.load_details).show();
        final String email = this.hospitalDetails.getmEmail();
        try {
            this.myRef.child(CommonData.HOSP_BLOOD_REQUESTS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.hospital.HospitalNotificationsFragment.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        HospitalNotificationsFragment.this.awesomeProgressDialog.dismiss();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            RequestBloodDetails requestBloodDetails = (RequestBloodDetails) dataSnapshot1.getValue(RequestBloodDetails.class);
                            if (requestBloodDetails.getHospEmail().equals(email) && requestBloodDetails.getIsDonationAccepted()) {
                                HospitalNotificationsFragment.this.requestBloodDetailsArrayList.add(requestBloodDetails);
                            }
                        }
                        BloodDonorNotificationListAdapter bloodDonorNotificationListAdapter = new BloodDonorNotificationListAdapter(HospitalNotificationsFragment.this.getContext(), HospitalNotificationsFragment.this.requestBloodDetailsArrayList, HospitalNotificationsFragment.this.hospitalDetails);
                        HospitalNotificationsFragment.this.awesomeProgressDialog.dismiss();
                        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(HospitalNotificationsFragment.this.getContext());
                        HospitalNotificationsFragment.this.bloodDonorNotificationsList.setLayoutManager(linearLayoutManager);
                        HospitalNotificationsFragment.this.bloodDonorNotificationsList.setItemAnimator(new DefaultItemAnimator());
                        HospitalNotificationsFragment.this.bloodDonorNotificationsList.setAdapter(bloodDonorNotificationListAdapter);
                        bloodDonorNotificationListAdapter.notifyDataSetChanged();
                        return;
                    }
                    HospitalNotificationsFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(HospitalNotificationsFragment.this.getActivity(), "Notification details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    HospitalNotificationsFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(HospitalNotificationsFragment.this.getActivity(), "No Results Found", 0).show();
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
