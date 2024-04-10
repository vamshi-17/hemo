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
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.R;
import edu.communication.hemo.adapter.DoctorListAdapter;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;

public class PatientDoctorsFragment extends Fragment {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    ArrayList<DoctorDetails> doctorDetailsArrayList = new ArrayList<>();
    DoctorListAdapter doctorListAdapter;
    RecyclerView doctorsList;
    DatabaseReference myRef;
    PatientDashBoardActivity patientDashBoardActivity;
    PatientDetails patientDetails;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_doctors_frgament, container, false);
        this.doctorsList = (RecyclerView) view.findViewById(R.id.doctors_list);
        fetchDoctorDetails();
        this.patientDashBoardActivity = (PatientDashBoardActivity) getActivity();
        final ChipGroup filterChipGroupSingleSelection = (ChipGroup) view.findViewById(R.id.choice_chip_group);
        try {
            filterChipGroupSingleSelection.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() { // from class: edu.communication.hemo.patient.PatientDoctorsFragment.1
                @Override // com.google.android.material.chip.ChipGroup.OnCheckedChangeListener
                public void onCheckedChanged(ChipGroup group, int checkedId) {
                    switch (filterChipGroupSingleSelection.getCheckedChipId()) {
                        case R.id.choice_anesthesiologist /* 2131296414 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Anesthesiologist");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_b_negative /* 2131296415 */:
                        case R.id.choice_b_positive /* 2131296416 */:
                        case R.id.choice_chip_group /* 2131296418 */:
                        case R.id.choice_o_negative /* 2131296424 */:
                        case R.id.choice_o_positive /* 2131296425 */:
                        default:
                            PatientDoctorsFragment patientDoctorsFragment = PatientDoctorsFragment.this;
                            patientDoctorsFragment.doctorListAdapter = new DoctorListAdapter(patientDoctorsFragment.getContext(), PatientDoctorsFragment.this.doctorDetailsArrayList, PatientDoctorsFragment.this.patientDashBoardActivity.patientDetails);
                            PatientDoctorsFragment.this.doctorsList.setAdapter(PatientDoctorsFragment.this.doctorListAdapter);
                            PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                            return;
                        case R.id.choice_cardiologist /* 2131296417 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Cardiologist");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_dermatologist /* 2131296419 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Dermatologist");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_gastroenterologist /* 2131296420 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Gastroenterologist");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_general_physician /* 2131296421 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("General physician");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_gynecologist /* 2131296422 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Gynecologist");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_neurologist /* 2131296423 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Neurologist");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_ophthalmologist /* 2131296426 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Ophthalmologist");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_orthopedic_surgeon /* 2131296427 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Orthopedic surgeon");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_pediatrician /* 2131296428 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Pediatrician");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        case R.id.choice_radiologist /* 2131296429 */:
                            if (PatientDoctorsFragment.this.doctorListAdapter != null) {
                                PatientDoctorsFragment.this.doctorListAdapter.filter("Radiologist");
                                PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void fetchDoctorDetails() {
        this.myRef = fireBaseInitialSetup();
        this.awesomeProgressDialog = new AwesomeProgressDialog(getActivity()).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.DOCTORS).addValueEventListener(new ValueEventListener() { // from class: edu.communication.hemo.patient.PatientDoctorsFragment.2
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PatientDoctorsFragment.this.awesomeProgressDialog.dismiss();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            DoctorDetails doctorDetails = (DoctorDetails) dataSnapshot1.getValue(DoctorDetails.class);
                            PatientDoctorsFragment.this.doctorDetailsArrayList.add(doctorDetails);
                        }
                        PatientDoctorsFragment patientDoctorsFragment = PatientDoctorsFragment.this;
                        patientDoctorsFragment.doctorListAdapter = new DoctorListAdapter(patientDoctorsFragment.getContext(), PatientDoctorsFragment.this.doctorDetailsArrayList, PatientDoctorsFragment.this.patientDashBoardActivity.patientDetails);
                        PatientDoctorsFragment.this.awesomeProgressDialog.dismiss();
                        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(PatientDoctorsFragment.this.getContext());
                        PatientDoctorsFragment.this.doctorsList.setLayoutManager(linearLayoutManager);
                        PatientDoctorsFragment.this.doctorsList.setItemAnimator(new DefaultItemAnimator());
                        PatientDoctorsFragment.this.doctorsList.setAdapter(PatientDoctorsFragment.this.doctorListAdapter);
                        PatientDoctorsFragment.this.doctorListAdapter.notifyDataSetChanged();
                        return;
                    }
                    PatientDoctorsFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientDoctorsFragment.this.getActivity(), "Preferred Doctor details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    PatientDoctorsFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientDoctorsFragment.this.getActivity(), "No Results Found", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(getActivity(), "Unable to process request. Please try again..!!", 0).show();
        }
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }
}
