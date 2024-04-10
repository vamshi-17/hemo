package edu.communication.hemo.admin;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
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
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import java.util.Arrays;
import java.util.List;

public class AddDoctorActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    List<String> genderList;
    TextInputEditText mAge;
    TextInputEditText mCertification;
    Button mDoctorRegister;
    TextInputEditText mEmail;
    TextInputEditText mGender;
    TextInputEditText mHospital;
    TextInputEditText mMobileNumber;
    TextInputEditText mName;
    TextInputEditText mPassword;
    PickerUI mPickerUI;
    PickerUI mSpecialitiesPickerUi;
    TextInputEditText mSpecialization;
    DatabaseReference myRef;
    List<String> specialitiesList;
    private String mobileRegexp = "^[6-9][0-9]{9}$";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        this.mName = (TextInputEditText) findViewById(R.id.name_et);
        this.mAge = (TextInputEditText) findViewById(R.id.age_et);
        this.mSpecialization = (TextInputEditText) findViewById(R.id.specification_et);
        this.mCertification = (TextInputEditText) findViewById(R.id.certification_et);
        this.mHospital = (TextInputEditText) findViewById(R.id.hospital_et);
        this.mGender = (TextInputEditText) findViewById(R.id.gender_et);
        this.mEmail = (TextInputEditText) findViewById(R.id.mail_id_et);
        this.mPassword = (TextInputEditText) findViewById(R.id.password_et);
        this.mMobileNumber = (TextInputEditText) findViewById(R.id.mobile_et);
        this.mDoctorRegister = (Button) findViewById(R.id.add_doctor);
        this.mPickerUI = (PickerUI) findViewById(R.id.picker_ui_view);
        this.mSpecialitiesPickerUi = (PickerUI) findViewById(R.id.specialities_picker_ui_view);
        this.genderList = Arrays.asList(getResources().getStringArray(R.array.gender_list));
        this.specialitiesList = Arrays.asList(getResources().getStringArray(R.array.doctor_specialities_list));
        this.mGender.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.admin.AddDoctorActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddDoctorActivity.this.mPickerUI.setVisibility(View.VISIBLE);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(AddDoctorActivity.this.genderList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                AddDoctorActivity.this.mPickerUI.setSettings(pickerUISettings);
                AddDoctorActivity.this.mPickerUI.slide();
            }
        });
        this.mSpecialization.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.admin.AddDoctorActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddDoctorActivity.this.mSpecialitiesPickerUi.setVisibility(View.VISIBLE);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(AddDoctorActivity.this.specialitiesList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                AddDoctorActivity.this.mSpecialitiesPickerUi.setSettings(pickerUISettings);
                AddDoctorActivity.this.mSpecialitiesPickerUi.slide();
            }
        });
        this.mPickerUI.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.admin.AddDoctorActivity.3
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(AddDoctorActivity.this, valueResult, Toast.LENGTH_SHORT).show();
                AddDoctorActivity.this.mGender.setText(valueResult);
                AddDoctorActivity.this.mGender.setError(null);
            }
        });
        this.mSpecialitiesPickerUi.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.admin.AddDoctorActivity.4
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(AddDoctorActivity.this, valueResult, Toast.LENGTH_SHORT).show();
                AddDoctorActivity.this.mSpecialization.setText(valueResult);
                AddDoctorActivity.this.mSpecialization.setError(null);
            }
        });
        this.mDoctorRegister.setOnClickListener(new AnonymousClass5());
    }


    class AnonymousClass5 implements View.OnClickListener {
        AnonymousClass5() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (AddDoctorActivity.this.validateFields()) {
                final String encodedEmail = Utils.EncodeString(AddDoctorActivity.this.mEmail.getText().toString());
                AddDoctorActivity addDoctorActivity = AddDoctorActivity.this;
                addDoctorActivity.myRef = addDoctorActivity.fireBaseInitialSetup();
                final DoctorDetails doctorDetails = new DoctorDetails();
                doctorDetails.setmName(AddDoctorActivity.this.mName.getText().toString());
                doctorDetails.setmAge(AddDoctorActivity.this.mAge.getText().toString());
                doctorDetails.setmSpecialization(AddDoctorActivity.this.mSpecialization.getText().toString());
                doctorDetails.setmCertification(AddDoctorActivity.this.mCertification.getText().toString());
                doctorDetails.setmHospital(AddDoctorActivity.this.mHospital.getText().toString());
                doctorDetails.setmEmail(encodedEmail);
                doctorDetails.setmGender(AddDoctorActivity.this.mGender.getText().toString());
                doctorDetails.setmMobileNumber(AddDoctorActivity.this.mMobileNumber.getText().toString());
                doctorDetails.setmPassword(AddDoctorActivity.this.mPassword.getText().toString());
                try {
                    AddDoctorActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(AddDoctorActivity.this).setMessage(R.string.registering_doctor).show();
                    AddDoctorActivity.this.myRef.child(CommonData.DOCTORS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.admin.AddDoctorActivity.5.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                AddDoctorActivity.this.myRef.child(CommonData.DOCTORS).child(encodedEmail).setValue(doctorDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.admin.AddDoctorActivity.5.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            AddDoctorActivity.this.awesomeProgressDialog.dismiss();
                                            AddDoctorActivity addDoctorActivity2 = AddDoctorActivity.this;
                                            Utils.showSuccessDialogue(addDoctorActivity2, "Doctor " + AddDoctorActivity.this.mName.getText().toString() + " is added successfully", AdminRolesActivity.class, true);
                                        }
                                    }
                                });
                            } else {
                                AddDoctorActivity.this.myRef.child(CommonData.DOCTORS).child(encodedEmail).setValue(doctorDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.admin.AddDoctorActivity.5.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            AddDoctorActivity.this.awesomeProgressDialog.dismiss();
                                            AddDoctorActivity addDoctorActivity2 = AddDoctorActivity.this;
                                            Utils.showSuccessDialogue(addDoctorActivity2, "Doctor " + AddDoctorActivity.this.mName.getText().toString() + " is added successfully", AdminRolesActivity.class, true);
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            AddDoctorActivity.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(AddDoctorActivity.this, "Something went wrong. Please try again..!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    AddDoctorActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(AddDoctorActivity.this, "Unable to process request. Please try again..!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public boolean validateFields() {
        if (this.mName.getText().toString().trim().length() == 0) {
            setFocus(this.mName, getResources().getString(R.string.enter_name));
            return false;
        } else if (this.mAge.getText().toString().trim().length() == 0) {
            setFocus(this.mAge, getResources().getString(R.string.enter_age));
            return false;
        } else if (this.mSpecialization.getText().toString().trim().length() == 0) {
            setFocus(this.mSpecialization, getResources().getString(R.string.enter_specialization));
            return false;
        } else if (this.mCertification.getText().toString().trim().length() == 0) {
            setFocus(this.mCertification, getResources().getString(R.string.enter_certifications));
            return false;
        } else if (this.mHospital.getText().toString().trim().length() == 0) {
            setFocus(this.mHospital, getResources().getString(R.string.enter_hospital));
            return false;
        } else if (this.mGender.getText().toString().trim().length() == 0) {
            setFocus(this.mGender, getResources().getString(R.string.enter_gender));
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
        } else if (this.mPassword.getText().toString().trim().length() == 0) {
            setFocus(this.mPassword, getResources().getString(R.string.enter_password));
            return false;
        } else {
            return true;
        }
    }


    public DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }

    private void setFocus(TextInputEditText editText, String string) {
        editText.requestFocus();
        editText.setError(Html.fromHtml("<font color='red'>" + string + "</font>"));
    }
}
