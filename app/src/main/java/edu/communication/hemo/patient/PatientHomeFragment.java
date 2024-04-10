package edu.communication.hemo.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import edu.communication.hemo.R;
import edu.communication.hemo.patient.model.PatientDetails;

public class PatientHomeFragment extends Fragment implements View.OnClickListener {
    Button mBookAppointment;
    Button mRequestBlood;
    PatientDashBoardActivity patientDashBoardActivity;
    PatientDetails patientDetails;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_home, container, false);
        this.mBookAppointment = (Button) view.findViewById(R.id.patient_book_appointment);
        this.mRequestBlood = (Button) view.findViewById(R.id.patient_request_blood);
        this.mBookAppointment.setOnClickListener(this);
        this.mRequestBlood.setOnClickListener(this);
        this.patientDashBoardActivity = (PatientDashBoardActivity) getActivity();
        this.patientDetails = this.patientDashBoardActivity.patientDetails;
        return view;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.patient_book_appointment) {
            Intent intent = new Intent(getActivity(), PatientDoctorsListActivity.class);
            intent.putExtra("patientDetails", this.patientDetails);
            getContext().startActivity(intent);
        } else if (id == R.id.patient_request_blood) {
            Intent hospita_intent = new Intent(getActivity(), HospitalListActivity.class);
            hospita_intent.putExtra("patientDetails", this.patientDetails);
            getContext().startActivity(hospita_intent);
        }
    }
}
