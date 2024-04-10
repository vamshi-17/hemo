package edu.communication.hemo.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import edu.communication.hemo.R;
import edu.communication.hemo.hospital.model.HospitalDetails;

public class HospitalHomeFragment extends Fragment implements View.OnClickListener {
    Button btnAppointments;
    Button btnBloodPlasma;
    Button btnDoctorsList;
    Button btnDonors;
    HospitalDashboardActivity hospitalDashboardActivity;
    HospitalDetails hospitalDetails;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_home, container, false);
        this.btnDoctorsList = (Button) view.findViewById(R.id.doctors_list_btn);
        this.btnAppointments = (Button) view.findViewById(R.id.appointments_btn);
        this.btnBloodPlasma = (Button) view.findViewById(R.id.blood_plasma_btn);
        this.btnDonors = (Button) view.findViewById(R.id.request_donors_btn);
        this.hospitalDashboardActivity = (HospitalDashboardActivity) getActivity();
        this.hospitalDetails = this.hospitalDashboardActivity.hospitalDetails;
        this.btnDoctorsList.setOnClickListener(this);
        this.btnAppointments.setOnClickListener(this);
        this.btnBloodPlasma.setOnClickListener(this);
        this.btnDonors.setOnClickListener(this);
        return view;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appointments_btn /* 2131296352 */:
                Intent appointment_intent = new Intent(getActivity(), HospitalDoctorAppointmentActivity.class);
                appointment_intent.putExtra("hospitalDetails", this.hospitalDetails);
                getContext().startActivity(appointment_intent);
                return;
            case R.id.blood_plasma_btn /* 2131296373 */:
                Intent blood_plasma_intent = new Intent(getActivity(), BloodPlasmaRequestsList.class);
                blood_plasma_intent.putExtra("hospitalDetails", this.hospitalDetails);
                getContext().startActivity(blood_plasma_intent);
                return;
            case R.id.doctors_list_btn /* 2131296512 */:
                Intent intent = new Intent(getActivity(), DoctorsListActivity.class);
                intent.putExtra("hospitalDetails", this.hospitalDetails);
                getContext().startActivity(intent);
                return;
            case R.id.request_donors_btn /* 2131296826 */:
                Intent donors_intent = new Intent(getActivity(), DonorsListActivity.class);
                donors_intent.putExtra("hospitalDetails", this.hospitalDetails);
                getContext().startActivity(donors_intent);
                return;
            default:
                return;
        }
    }
}
