package edu.communication.hemo.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import edu.communication.hemo.R;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.patient.model.PatientDetails;

public class PatientProfileFragment extends Fragment {
    TextInputEditText mAge;
    TextInputEditText mBloodGroup;
    TextInputEditText mGender;
    TextInputEditText mMailId;
    TextInputEditText mMobileNumber;
    TextInputEditText mName;
    PatientDashBoardActivity patientDashBoardActivity;
    PatientDetails patientDetails;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);
        this.mName = (TextInputEditText) view.findViewById(R.id.name_et);
        this.mAge = (TextInputEditText) view.findViewById(R.id.age_et);
        this.mBloodGroup = (TextInputEditText) view.findViewById(R.id.bloodGroup_et);
        this.mGender = (TextInputEditText) view.findViewById(R.id.gender_et);
        this.mMailId = (TextInputEditText) view.findViewById(R.id.mail_id_et);
        this.mMobileNumber = (TextInputEditText) view.findViewById(R.id.mobile_et);
        try {
            this.patientDashBoardActivity = (PatientDashBoardActivity) getActivity();
            this.patientDetails = this.patientDashBoardActivity.patientDetails;
            if (this.patientDetails == null) {
                Toast.makeText(getActivity(), "Patient details were not found ", 0).show();
            } else {
                this.mName.setText(this.patientDetails.getName());
                this.mAge.setText(this.patientDetails.getmAge());
                this.mBloodGroup.setText(this.patientDetails.getmBloodGroup());
                this.mGender.setText(this.patientDetails.getmGender());
                this.mMailId.setText(Utils.DecodeString(this.patientDetails.getmEmail()));
                this.mMobileNumber.setText(this.patientDetails.getmMobileNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Something went wrong", 0).show();
        }
        return view;
    }
}
