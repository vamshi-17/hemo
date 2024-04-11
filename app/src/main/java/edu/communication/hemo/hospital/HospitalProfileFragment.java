package edu.communication.hemo.hospital;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import edu.communication.hemo.R;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.hospital.model.HospitalDetails;

public class HospitalProfileFragment extends Fragment {
    HospitalDashboardActivity hospitalDashboardActivity;
    HospitalDetails hospitalDetails;
    TextInputEditText mAddress;
    TextInputEditText mCodeHosp;
    TextInputEditText mEmail;
    TextInputEditText mHosName;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_profile, container, false);
        this.mHosName = (TextInputEditText) view.findViewById(R.id.prof_hos_name_et);
        this.mEmail = (TextInputEditText) view.findViewById(R.id.prof_email_et);
        this.mAddress = (TextInputEditText) view.findViewById(R.id.prof_address_et);
        this.mCodeHosp = (TextInputEditText) view.findViewById(R.id.prof_code_hos_et);
        this.hospitalDashboardActivity = (HospitalDashboardActivity) getActivity();
        this.hospitalDetails = this.hospitalDashboardActivity.hospitalDetails;
        this.mHosName.setText(this.hospitalDetails.getmHosName());
        this.mAddress.setText(this.hospitalDetails.getmAddress());
        this.mEmail.setText(Utils.DecodeString(this.hospitalDetails.getmEmail()));
        this.mCodeHosp.setText(this.hospitalDetails.getmHosCode());
        return view;
    }
}
