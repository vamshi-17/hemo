package edu.communication.hemo.patient;

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
import edu.communication.hemo.adapter.NotificationListAdapter;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.patient.model.AppointmentDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;

public class PatientNotificationsFragment extends Fragment {
    ArrayList<AppointmentDetails> appointmentDetailsArrayList = new ArrayList<>();
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    DatabaseReference myRef;
    RecyclerView notificationList;
    PatientDashBoardActivity patientDashBoardActivity;
    PatientDetails patientDetails;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_notifications, container, false);
        this.notificationList = (RecyclerView) view.findViewById(R.id.notifications_list);
        this.patientDashBoardActivity = (PatientDashBoardActivity) getActivity();
        this.patientDetails = this.patientDashBoardActivity.patientDetails;
        fetchNotificationDetails();
        return view;
    }

    private void fetchNotificationDetails() {
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(getActivity()).setMessage(R.string.load_details).show();
        final String email = this.patientDetails.getmEmail();
        try {
            this.myRef.child(CommonData.APPOINTMENTS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.patient.PatientNotificationsFragment.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PatientNotificationsFragment.this.awesomeProgressDialog.dismiss();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            AppointmentDetails appointmentDetails = (AppointmentDetails) dataSnapshot1.getValue(AppointmentDetails.class);
                            if (appointmentDetails.getPatientEmail().equals(email)) {
                                PatientNotificationsFragment.this.appointmentDetailsArrayList.add(appointmentDetails);
                            }
                        }
                        NotificationListAdapter notificationListAdapter = new NotificationListAdapter(PatientNotificationsFragment.this.getContext(), PatientNotificationsFragment.this.appointmentDetailsArrayList, PatientNotificationsFragment.this.patientDetails);
                        PatientNotificationsFragment.this.awesomeProgressDialog.dismiss();
                        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(PatientNotificationsFragment.this.getContext());
                        PatientNotificationsFragment.this.notificationList.setLayoutManager(linearLayoutManager);
                        PatientNotificationsFragment.this.notificationList.setItemAnimator(new DefaultItemAnimator());
                        PatientNotificationsFragment.this.notificationList.setAdapter(notificationListAdapter);
                        notificationListAdapter.notifyDataSetChanged();
                        return;
                    }
                    PatientNotificationsFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientNotificationsFragment.this.getActivity(), "Preferred Doctor details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    PatientNotificationsFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientNotificationsFragment.this.getActivity(), "No Results Found", 0).show();
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
