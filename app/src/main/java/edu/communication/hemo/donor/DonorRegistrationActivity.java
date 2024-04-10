package edu.communication.hemo.donor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.dpizarro.uipicker.library.picker.PickerUI;
import com.dpizarro.uipicker.library.picker.PickerUISettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DonorRegistrationActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    List<String> bloodGroupList;
    PickerUI bloodGroupPicker;
    FirebaseDatabase database;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog datePickerDialog;
    List<String> genderList;
    PickerUI genderPicker;
    TextInputEditText mAge;
    TextInputEditText mBloodGroup;
    TextInputEditText mEmail;
    TextInputEditText mGender;
    TextInputEditText mLastDonated;
    TextInputEditText mMobileNumber;
    TextInputEditText mName;
    TextInputEditText mPassword;
    Button mRegisterBtn;
    Calendar myCalendar;
    DatabaseReference myRef;
    private String mobileRegexp = "^[6-9][0-9]{9}$";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);
        this.myCalendar = Calendar.getInstance();
        this.mName = (TextInputEditText) findViewById(R.id.name_et);
        this.mAge = (TextInputEditText) findViewById(R.id.age_et);
        this.mBloodGroup = (TextInputEditText) findViewById(R.id.blodgroup_et);
        this.mLastDonated = (TextInputEditText) findViewById(R.id.date_et);
        this.mEmail = (TextInputEditText) findViewById(R.id.email_et);
        this.mMobileNumber = (TextInputEditText) findViewById(R.id.mobile_number_et);
        this.mGender = (TextInputEditText) findViewById(R.id.gender_et);
        this.mPassword = (TextInputEditText) findViewById(R.id.password_et);
        this.mRegisterBtn = (Button) findViewById(R.id.register_btn);
        this.genderPicker = (PickerUI) findViewById(R.id.gender_picker);
        this.bloodGroupPicker = (PickerUI) findViewById(R.id.blood_group_picker);
        this.genderList = Arrays.asList(getResources().getStringArray(R.array.gender_list));
        this.bloodGroupList = Arrays.asList(getResources().getStringArray(R.array.blood_group_list));
        this.mLastDonated.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.DonorRegistrationActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DonorRegistrationActivity donorRegistrationActivity = DonorRegistrationActivity.this;
                donorRegistrationActivity.datePickerDialog = donorRegistrationActivity.createDatePickerDialog();
                DonorRegistrationActivity.this.datePickerDialog.show();
            }
        });
        this.mGender.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.DonorRegistrationActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DonorRegistrationActivity.this.genderPicker.setVisibility(0);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(DonorRegistrationActivity.this.genderList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                DonorRegistrationActivity.this.genderPicker.setSettings(pickerUISettings);
                DonorRegistrationActivity.this.genderPicker.slide();
            }
        });
        this.mBloodGroup.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.DonorRegistrationActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DonorRegistrationActivity.this.bloodGroupPicker.setVisibility(0);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(DonorRegistrationActivity.this.bloodGroupList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                DonorRegistrationActivity.this.bloodGroupPicker.setSettings(pickerUISettings);
                DonorRegistrationActivity.this.bloodGroupPicker.slide();
            }
        });
        this.genderPicker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.donor.DonorRegistrationActivity.4
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(DonorRegistrationActivity.this, valueResult, 0).show();
                DonorRegistrationActivity.this.mGender.setText(valueResult);
                DonorRegistrationActivity.this.mGender.setError(null);
            }
        });
        this.bloodGroupPicker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.donor.DonorRegistrationActivity.5
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(DonorRegistrationActivity.this, valueResult, 0).show();
                DonorRegistrationActivity.this.mBloodGroup.setText(valueResult);
                DonorRegistrationActivity.this.mBloodGroup.setError(null);
            }
        });
        this.date = new DatePickerDialog.OnDateSetListener() { // from class: edu.communication.hemo.donor.DonorRegistrationActivity.6
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("onDateSet()", "arg0 = [" + view + "], year = [" + year + "], monthOfYear = [" + monthOfYear + "], dayOfMonth = [" + dayOfMonth + "]");
                DonorRegistrationActivity.this.myCalendar.set(1, year);
                DonorRegistrationActivity.this.myCalendar.set(2, monthOfYear);
                DonorRegistrationActivity.this.myCalendar.set(5, dayOfMonth);
                DonorRegistrationActivity.this.updateLabel();
            }
        };
        this.mRegisterBtn.setOnClickListener(new AnonymousClass7());
    }


    class AnonymousClass7 implements View.OnClickListener {
        AnonymousClass7() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (DonorRegistrationActivity.this.validateFields()) {
                final String encodedEmail = Utils.EncodeString(DonorRegistrationActivity.this.mEmail.getText().toString());
                DonorRegistrationActivity donorRegistrationActivity = DonorRegistrationActivity.this;
                donorRegistrationActivity.myRef = donorRegistrationActivity.fireBaseInitialSetup();
                final DonorDetails donorDetails = new DonorDetails();
                donorDetails.setName(DonorRegistrationActivity.this.mName.getText().toString());
                donorDetails.setmAge(DonorRegistrationActivity.this.mAge.getText().toString());
                donorDetails.setmBloodGroup(DonorRegistrationActivity.this.mBloodGroup.getText().toString());
                donorDetails.setmLastDonated(DonorRegistrationActivity.this.mLastDonated.getText().toString());
                donorDetails.setmEmail(encodedEmail);
                donorDetails.setmGender(DonorRegistrationActivity.this.mGender.getText().toString());
                donorDetails.setmMobileNumber(DonorRegistrationActivity.this.mMobileNumber.getText().toString());
                donorDetails.setmPassword(DonorRegistrationActivity.this.mPassword.getText().toString());
                try {
                    DonorRegistrationActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(DonorRegistrationActivity.this).setMessage(R.string.registering_donor).show();
                    DonorRegistrationActivity.this.myRef.child(CommonData.DONORS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.donor.DonorRegistrationActivity.7.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                DonorRegistrationActivity.this.myRef.child(CommonData.DONORS).child(encodedEmail).setValue(donorDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.donor.DonorRegistrationActivity.7.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            DonorRegistrationActivity.this.awesomeProgressDialog.dismiss();
                                            DonorRegistrationActivity donorRegistrationActivity2 = DonorRegistrationActivity.this;
                                            Utils.showSuccessDialogue(donorRegistrationActivity2, "Donor " + DonorRegistrationActivity.this.mName.getText().toString() + " is registered successfully", DonorLoginActivity.class, true);
                                        }
                                    }
                                });
                            } else {
                                DonorRegistrationActivity.this.myRef.child(CommonData.DONORS).child(encodedEmail).setValue(donorDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.donor.DonorRegistrationActivity.7.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            DonorRegistrationActivity.this.awesomeProgressDialog.dismiss();
                                            DonorRegistrationActivity donorRegistrationActivity2 = DonorRegistrationActivity.this;
                                            Utils.showSuccessDialogue(donorRegistrationActivity2, "Donor " + DonorRegistrationActivity.this.mName.getText().toString() + " is registered successfully", DonorLoginActivity.class, true);
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            DonorRegistrationActivity.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(DonorRegistrationActivity.this, "Something went wrong. Please try again..!!", 0).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    DonorRegistrationActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DonorRegistrationActivity.this, "Unable to process request. Please try again..!!", 0).show();
                }
            }
        }
    }


    public DatePickerDialog createDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, -9);
        this.datePickerDialog = new DatePickerDialog(this, this.date, this.myCalendar.get(1), this.myCalendar.get(2), this.myCalendar.get(5));
        this.datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        this.datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return this.datePickerDialog;
    }


    public void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        this.mLastDonated.setText(sdf.format(this.myCalendar.getTime()));
    }


    public DatabaseReference fireBaseInitialSetup() {
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
        } else if (this.mEmail.getText().toString().trim().length() == 0) {
            setFocus(this.mEmail, getResources().getString(R.string.enter_email));
            return false;
        } else if (!this.mEmail.getText().toString().trim().matches(this.emailPattern)) {
            setFocus(this.mEmail, getResources().getString(R.string.enter_valid_email));
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
        } else if (this.mPassword.getText().toString().trim().length() == 0) {
            setFocus(this.mPassword, getResources().getString(R.string.enter_password));
            return false;
        } else {
            return true;
        }
    }

    private void setFocus(TextInputEditText editText, String string) {
        editText.requestFocus();
        editText.setError(Html.fromHtml("<font color='red'>" + string + "</font>"));
    }
}
