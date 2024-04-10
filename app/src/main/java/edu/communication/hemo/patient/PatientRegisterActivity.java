package edu.communication.hemo.patient;

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
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.Arrays;
import java.util.List;

public class PatientRegisterActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    List<String> bloodGroupList;
    PickerUI bloodGroupPicker;
    FirebaseDatabase database;
    List<String> genderList;
    PickerUI genderPicker;
    TextInputEditText mAge;
    TextInputEditText mBloodGroup;
    TextInputEditText mEmail;
    TextInputEditText mGender;
    TextInputEditText mMobileNumber;
    TextInputEditText mName;
    TextInputEditText mPassword;
    Button mRegisterBtn;
    DatabaseReference myRef;
    private String mobileRegexp = "^[6-9][0-9]{9}$";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        this.mName = (TextInputEditText) findViewById(R.id.name_et);
        this.mAge = (TextInputEditText) findViewById(R.id.age_et);
        this.mBloodGroup = (TextInputEditText) findViewById(R.id.blodgroup_et);
        this.mEmail = (TextInputEditText) findViewById(R.id.email_et);
        this.mMobileNumber = (TextInputEditText) findViewById(R.id.mobile_number_et);
        this.mGender = (TextInputEditText) findViewById(R.id.gender_et);
        this.mPassword = (TextInputEditText) findViewById(R.id.password_et);
        this.mRegisterBtn = (Button) findViewById(R.id.register_btn);
        this.genderPicker = (PickerUI) findViewById(R.id.gender_picker);
        this.bloodGroupPicker = (PickerUI) findViewById(R.id.blood_group_picker);
        this.genderList = Arrays.asList(getResources().getStringArray(R.array.gender_list));
        this.bloodGroupList = Arrays.asList(getResources().getStringArray(R.array.blood_group_list));
        this.mGender.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.PatientRegisterActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PatientRegisterActivity.this.genderPicker.setVisibility(0);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(PatientRegisterActivity.this.genderList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                PatientRegisterActivity.this.genderPicker.setSettings(pickerUISettings);
                PatientRegisterActivity.this.genderPicker.slide();
            }
        });
        this.mBloodGroup.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.PatientRegisterActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PatientRegisterActivity.this.bloodGroupPicker.setVisibility(0);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(PatientRegisterActivity.this.bloodGroupList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                PatientRegisterActivity.this.bloodGroupPicker.setSettings(pickerUISettings);
                PatientRegisterActivity.this.bloodGroupPicker.slide();
            }
        });
        this.genderPicker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.patient.PatientRegisterActivity.3
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(PatientRegisterActivity.this, valueResult, 0).show();
                PatientRegisterActivity.this.mGender.setText(valueResult);
                PatientRegisterActivity.this.mGender.setError(null);
            }
        });
        this.bloodGroupPicker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.patient.PatientRegisterActivity.4
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(PatientRegisterActivity.this, valueResult, 0).show();
                PatientRegisterActivity.this.mBloodGroup.setText(valueResult);
                PatientRegisterActivity.this.mBloodGroup.setError(null);
            }
        });
        this.mRegisterBtn.setOnClickListener(new AnonymousClass5());
    }


    class AnonymousClass5 implements View.OnClickListener {
        AnonymousClass5() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (PatientRegisterActivity.this.validateFields()) {
                final String encodedEmail = Utils.EncodeString(PatientRegisterActivity.this.mEmail.getText().toString());
                PatientRegisterActivity patientRegisterActivity = PatientRegisterActivity.this;
                patientRegisterActivity.myRef = patientRegisterActivity.fireBaseInitialSetup();
                final PatientDetails patientDetails = new PatientDetails();
                patientDetails.setName(PatientRegisterActivity.this.mName.getText().toString());
                patientDetails.setmAge(PatientRegisterActivity.this.mAge.getText().toString());
                patientDetails.setmBloodGroup(PatientRegisterActivity.this.mBloodGroup.getText().toString());
                patientDetails.setmEmail(encodedEmail);
                patientDetails.setmGender(PatientRegisterActivity.this.mGender.getText().toString());
                patientDetails.setmMobileNumber(PatientRegisterActivity.this.mMobileNumber.getText().toString());
                patientDetails.setmPassword(PatientRegisterActivity.this.mPassword.getText().toString());
                try {
                    PatientRegisterActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(PatientRegisterActivity.this).setMessage(R.string.registering_patient).show();
                    PatientRegisterActivity.this.myRef.child(CommonData.PATIENTS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.patient.PatientRegisterActivity.5.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                PatientRegisterActivity.this.myRef.child(CommonData.PATIENTS).child(encodedEmail).setValue(patientDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.patient.PatientRegisterActivity.5.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            PatientRegisterActivity.this.awesomeProgressDialog.dismiss();
                                            PatientRegisterActivity patientRegisterActivity2 = PatientRegisterActivity.this;
                                            Utils.showSuccessDialogue(patientRegisterActivity2, "Patient " + PatientRegisterActivity.this.mName.getText().toString() + " is registered successfully", PatientLoginActivity.class, true);
                                        }
                                    }
                                });
                            } else {
                                PatientRegisterActivity.this.myRef.child(CommonData.PATIENTS).child(encodedEmail).setValue(patientDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.patient.PatientRegisterActivity.5.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            PatientRegisterActivity.this.awesomeProgressDialog.dismiss();
                                            PatientRegisterActivity patientRegisterActivity2 = PatientRegisterActivity.this;
                                            Utils.showSuccessDialogue(patientRegisterActivity2, "Patient " + PatientRegisterActivity.this.mName.getText().toString() + " is registered successfully", PatientLoginActivity.class, true);
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            PatientRegisterActivity.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(PatientRegisterActivity.this, "Something went wrong. Please try again..!!", 0).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    PatientRegisterActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientRegisterActivity.this, "Unable to process request. Please try again..!!", 0).show();
                }
            }
        }
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
