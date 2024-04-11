package edu.communication.hemo.hospital;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
import edu.communication.hemo.hospital.HospAppointmentListAdapter;
import edu.communication.hemo.hospital.model.HospitalDetails;
import edu.communication.hemo.patient.model.AppointmentDetails;
import java.util.ArrayList;

public class HospitalDoctorAppointmentActivity extends AppCompatActivity implements HospAppointmentListAdapter.MyInterface {
    ArrayList<AppointmentDetails> appointmentDetailsArrayList = new ArrayList<>();
    HospAppointmentListAdapter appointmentListAdapter;
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    HospitalDetails hospitalDetails;
    DatabaseReference myRef;
    TextView noResults;
    RecyclerView recyclerViewAppointments;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_doctor_appointment);
        this.recyclerViewAppointments = (RecyclerView) findViewById(R.id.hosp_appointments_list);
        this.recyclerViewAppointments.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        this.recyclerViewAppointments.setLayoutManager(linearLayoutManager);
        this.noResults = (TextView) findViewById(R.id.no_results);
        try {
            this.hospitalDetails = (HospitalDetails) getIntent().getExtras().getParcelable("hospitalDetails");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
        this.myRef = fireBaseInitialSetup();
        fetchAppointmentDetails();
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    public void fetchAppointmentDetails() {
        this.myRef.child(CommonData.APPOINTMENTS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.hospital.HospitalDoctorAppointmentActivity.1
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                HospitalDoctorAppointmentActivity.this.appointmentDetailsArrayList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1 != null) {
                            AppointmentDetails appointmentDetails = (AppointmentDetails) dataSnapshot1.getValue(AppointmentDetails.class);
                            appointmentDetails.setuId(dataSnapshot1.getKey());
                            if (appointmentDetails != null && appointmentDetails.getHospCode().equals(HospitalDoctorAppointmentActivity.this.hospitalDetails.getmHosCode())) {
                                HospitalDoctorAppointmentActivity.this.appointmentDetailsArrayList.add(appointmentDetails);
                                HospitalDoctorAppointmentActivity hospitalDoctorAppointmentActivity = HospitalDoctorAppointmentActivity.this;
                                hospitalDoctorAppointmentActivity.appointmentListAdapter = new HospAppointmentListAdapter(hospitalDoctorAppointmentActivity, hospitalDoctorAppointmentActivity.appointmentDetailsArrayList, HospitalDoctorAppointmentActivity.this);
                                HospitalDoctorAppointmentActivity.this.recyclerViewAppointments.setAdapter(HospitalDoctorAppointmentActivity.this.appointmentListAdapter);
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

    @Override // edu.communication.hemo.hospital.HospAppointmentListAdapter.MyInterface
    public void acceptAppointment(AppointmentDetails appointmentDetails) {
        appointmentDetails.setAccepted(true);
        this.myRef.child(CommonData.APPOINTMENTS).child(appointmentDetails.getuId()).setValue(appointmentDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.hospital.HospitalDoctorAppointmentActivity.2
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    HospitalDoctorAppointmentActivity.this.fetchAppointmentDetails();
                    HospitalDoctorAppointmentActivity.this.appointmentListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override // edu.communication.hemo.hospital.HospAppointmentListAdapter.MyInterface
    public void rejectAppointment(String uId) {
        this.myRef.child(CommonData.APPOINTMENTS).child(uId).setValue(null);
        fetchAppointmentDetails();
        this.appointmentListAdapter.notifyDataSetChanged();
    }
}
