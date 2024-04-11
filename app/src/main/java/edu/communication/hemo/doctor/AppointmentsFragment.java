package edu.communication.hemo.doctor;

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
import edu.communication.hemo.adapter.AppointmentListAdapter;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.patient.model.AppointmentDetails;
import java.util.ArrayList;

public class AppointmentsFragment extends Fragment implements AppointmentListAdapter.MyInterface {
    ArrayList<AppointmentDetails> appointmentDetailsArrayList = new ArrayList<>();
    AppointmentListAdapter appointmentListAdapter;
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    DoctorDashBoardActivity doctorDashBoardActivity;
    DoctorDetails doctorDetails;
    DatabaseReference myRef;
    TextView noResults;
    RecyclerView recyclerViewAppointments;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointments, container, false);
        this.recyclerViewAppointments = (RecyclerView) view.findViewById(R.id.appointments);
        this.recyclerViewAppointments.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        this.recyclerViewAppointments.setLayoutManager(linearLayoutManager);
        this.noResults = (TextView) view.findViewById(R.id.no_results);
        this.doctorDashBoardActivity = (DoctorDashBoardActivity) getActivity();
        this.doctorDetails = this.doctorDashBoardActivity.doctorDetails;
        this.myRef = fireBaseInitialSetup();
        fetchAppointmentDetails();
        return view;
    }


    public void fetchAppointmentDetails() {
        this.myRef.child(CommonData.APPOINTMENTS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.doctor.AppointmentsFragment.1
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppointmentsFragment.this.appointmentDetailsArrayList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1 != null) {
                            AppointmentDetails appointmentDetails = (AppointmentDetails) dataSnapshot1.getValue(AppointmentDetails.class);
                            appointmentDetails.setuId(dataSnapshot1.getKey());
                            if (appointmentDetails != null && appointmentDetails.getDoctorEmail().equals(Utils.EncodeString(AppointmentsFragment.this.doctorDetails.getmEmail()))) {
                                AppointmentsFragment.this.appointmentDetailsArrayList.add(appointmentDetails);
                                AppointmentsFragment appointmentsFragment = AppointmentsFragment.this;
                                appointmentsFragment.appointmentListAdapter = new AppointmentListAdapter(appointmentsFragment.getActivity(), AppointmentsFragment.this.appointmentDetailsArrayList, AppointmentsFragment.this.doctorDetails, AppointmentsFragment.this);
                                AppointmentsFragment.this.recyclerViewAppointments.setAdapter(AppointmentsFragment.this.appointmentListAdapter);
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

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }

    @Override // edu.communication.hemo.adapter.AppointmentListAdapter.MyInterface
    public void acceptAppointment(AppointmentDetails appointmentDetails) {
        appointmentDetails.setAccepted(true);
        this.myRef.child(CommonData.APPOINTMENTS).child(appointmentDetails.getuId()).setValue(appointmentDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.doctor.AppointmentsFragment.2
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    AppointmentsFragment.this.fetchAppointmentDetails();
                    AppointmentsFragment.this.appointmentListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override // edu.communication.hemo.adapter.AppointmentListAdapter.MyInterface
    public void rejectAppointment(String uId) {
        this.myRef.child(CommonData.APPOINTMENTS).child(uId).setValue(null);
        fetchAppointmentDetails();
        this.appointmentListAdapter.notifyDataSetChanged();
    }
}
