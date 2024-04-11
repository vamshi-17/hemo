package edu.communication.hemo.donor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.R;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.donor.model.DonorDetails;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DonorProfileFragment extends Fragment {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog datePickerDialog;
    DonorDashboardActivity donorDashboardActivity;
    DonorDetails donorDetails;
    TextInputEditText mAge;
    TextInputEditText mBloodGroup;
    TextInputEditText mEmail;
    TextInputEditText mGender;
    TextInputEditText mLastDonated;
    TextInputEditText mMobileNumber;
    TextInputEditText mName;
    TextInputEditText mPassword;
    Button mUpdateBtn;
    Calendar myCalendar;
    DatabaseReference myRef;
    private String mobileRegexp = "^[6-9][0-9]{9}$";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donor_profile, container, false);
        this.myCalendar = Calendar.getInstance();
        this.mName = (TextInputEditText) view.findViewById(R.id.name_et);
        this.mAge = (TextInputEditText) view.findViewById(R.id.age_et);
        this.mBloodGroup = (TextInputEditText) view.findViewById(R.id.blodgroup_et);
        this.mLastDonated = (TextInputEditText) view.findViewById(R.id.date_et);
        this.mEmail = (TextInputEditText) view.findViewById(R.id.email_et);
        this.mMobileNumber = (TextInputEditText) view.findViewById(R.id.mobile_number_et);
        this.mGender = (TextInputEditText) view.findViewById(R.id.gender_et);
        this.mUpdateBtn = (Button) view.findViewById(R.id.update_btn);
        this.donorDashboardActivity = (DonorDashboardActivity) getActivity();
        this.donorDetails = this.donorDashboardActivity.donorDetails;
        this.myRef = fireBaseInitialSetup();
        fetchDonorDetails();
        this.mLastDonated.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.DonorProfileFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DonorProfileFragment donorProfileFragment = DonorProfileFragment.this;
                donorProfileFragment.datePickerDialog = donorProfileFragment.createDatePickerDialog();
                DonorProfileFragment.this.datePickerDialog.show();
            }
        });
        this.date = new DatePickerDialog.OnDateSetListener() { // from class: edu.communication.hemo.donor.DonorProfileFragment.2
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker view2, int year, int monthOfYear, int dayOfMonth) {
                Log.e("onDateSet()", "arg0 = [" + view2 + "], year = [" + year + "], monthOfYear = [" + monthOfYear + "], dayOfMonth = [" + dayOfMonth + "]");
                DonorProfileFragment.this.myCalendar.set(1, year);
                DonorProfileFragment.this.myCalendar.set(2, monthOfYear);
                DonorProfileFragment.this.myCalendar.set(5, dayOfMonth);
                DonorProfileFragment.this.updateLabel();
            }
        };
        this.mUpdateBtn.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.DonorProfileFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (DonorProfileFragment.this.validateFields()) {
                    String encodedEmail = Utils.EncodeString(DonorProfileFragment.this.mEmail.getText().toString());
                    DatabaseReference ref = DonorProfileFragment.this.myRef.child(CommonData.DONORS).child(encodedEmail);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put(AppMeasurementSdk.ConditionalUserProperty.NAME, DonorProfileFragment.this.mName.getText().toString());
                    updates.put("mAge", DonorProfileFragment.this.mAge.getText().toString());
                    updates.put("mBloodGroup", DonorProfileFragment.this.mBloodGroup.getText().toString());
                    updates.put("mMobileNumber", DonorProfileFragment.this.mMobileNumber.getText().toString());
                    updates.put("mGender", DonorProfileFragment.this.mGender.getText().toString());
                    updates.put("mLastDonated", DonorProfileFragment.this.mLastDonated.getText().toString());
                    try {
                        DonorProfileFragment.this.awesomeProgressDialog = new AwesomeProgressDialog(DonorProfileFragment.this.getActivity()).setMessage(R.string.updating_donor).show();
                        ref.updateChildren(updates);
                        new Handler().postDelayed(new Runnable() { // from class: edu.communication.hemo.donor.DonorProfileFragment.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                DonorProfileFragment.this.awesomeProgressDialog.dismiss();
                                DonorProfileFragment.this.fetchDonorDetails();
                            }
                        }, 2000L);
                    } catch (Exception e) {
                        e.printStackTrace();
                        DonorProfileFragment.this.awesomeProgressDialog.dismiss();
                        Toast.makeText(DonorProfileFragment.this.getActivity(), "Unable to process request. Please try again..!!", 0).show();
                    }
                }
            }
        });
        return view;
    }


    public DatePickerDialog createDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, -9);
        this.datePickerDialog = new DatePickerDialog(getActivity(), this.date, this.myCalendar.get(1), this.myCalendar.get(2), this.myCalendar.get(5));
        this.datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        this.datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return this.datePickerDialog;
    }


    public void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        this.mLastDonated.setText(sdf.format(this.myCalendar.getTime()));
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    public boolean validateFields() {
        if (this.mName.getText().toString().trim().length() == 0) {
            setFocus(this.mName, getResources().getString(R.string.enter_name));
            return false;
        } else if (this.mAge.getText().toString().trim().length() == 0) {
            setFocus(this.mAge, getResources().getString(R.string.enter_age));
            return false;
        } else if (this.mBloodGroup.getText().toString().trim().length() == 0) {
            setFocus(this.mBloodGroup, getResources().getString(R.string.enter_bloodgroup));
            return false;
        } else if (this.mMobileNumber.getText().toString().trim().length() == 0) {
            setFocus(this.mMobileNumber, getResources().getString(R.string.enter_mobileNumber));
            return false;
        } else if (this.mMobileNumber.getText().toString().trim().length() < 10 || this.mMobileNumber.getText().toString().trim().length() > 11) {
            setFocus(this.mMobileNumber, getResources().getString(R.string.enter_length_mobile));
            return false;
        } else if (!this.mMobileNumber.getText().toString().trim().matches(this.mobileRegexp)) {
            setFocus(this.mMobileNumber, getResources().getString(R.string.enter_valid_mobile));
            return false;
        } else if (this.mGender.getText().toString().trim().length() == 0) {
            setFocus(this.mGender, getResources().getString(R.string.enter_gender));
            return false;
        } else {
            return true;
        }
    }

    private void setFocus(TextInputEditText editText, String string) {
        editText.requestFocus();
        editText.setError(Html.fromHtml("<font color='red'>" + string + "</font>"));
    }


    public void fetchDonorDetails() {
        String encodedEmail = Utils.EncodeString(this.donorDetails.getmEmail());
        this.awesomeProgressDialog = new AwesomeProgressDialog(getActivity()).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.DONORS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.donor.DonorProfileFragment.4
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        DonorProfileFragment.this.awesomeProgressDialog.dismiss();
                        DonorDetails donorDetails = (DonorDetails) dataSnapshot.getValue(DonorDetails.class);
                        DonorProfileFragment.this.mName.setText(donorDetails.getName());
                        DonorProfileFragment.this.mEmail.setText(donorDetails.getmEmail());
                        DonorProfileFragment.this.mBloodGroup.setText(donorDetails.getmBloodGroup());
                        DonorProfileFragment.this.mAge.setText(donorDetails.getmAge());
                        DonorProfileFragment.this.mGender.setText(donorDetails.getmGender());
                        DonorProfileFragment.this.mMobileNumber.setText(donorDetails.getmMobileNumber());
                        if (donorDetails.getmLastDonated() != null) {
                            DonorProfileFragment.this.mLastDonated.setText(donorDetails.getmLastDonated());
                            return;
                        }
                        return;
                    }
                    DonorProfileFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DonorProfileFragment.this.getActivity(), "Donor details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    DonorProfileFragment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DonorProfileFragment.this.getActivity(), "No Results Found", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(getActivity(), "Unable to process request. Please try again..!!", 0).show();
        }
    }
}
