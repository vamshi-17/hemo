package edu.communication.hemo.patient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
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
import edu.communication.hemo.hospital.model.HospitalDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SendRequestBloodPlasmaActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    List<String> bloodGroupList;
    PickerUI bloodGroupPicker;
    FirebaseDatabase database;
    TextInputEditText date;
    DatePickerDialog.OnDateSetListener datePicker;
    DatePickerDialog datePickerDialog;
    TextView hospAddress;
    TextView hospCode;
    TextView hospMail;
    TextView hospName;
    HospitalDetails hospitalDetails;
    Calendar myCalendar;
    DatabaseReference myRef;
    TextInputEditText noOfUnits;
    TextInputEditText patientAge;
    TextInputEditText patientBloodGroup;
    PatientDetails patientDetails;
    TextInputEditText patientName;
    TextInputEditText purpose;
    TextInputEditText requestFor;
    List<String> requestForList;
    PickerUI requestForPicker;
    TextInputEditText requestType;
    List<String> requestTypeList;
    PickerUI requestTypePicker;
    Button sendRequest;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request_blood_plasma);
        try {
            this.hospitalDetails = (HospitalDetails) getIntent().getExtras().getParcelable("hospitalDetails");
            this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.hospName = (TextView) findViewById(R.id.hosp_name_et);
        this.hospAddress = (TextView) findViewById(R.id.hosp_add_et);
        this.hospMail = (TextView) findViewById(R.id.hosp_mail_et);
        this.hospCode = (TextView) findViewById(R.id.hosp_code_et);
        this.patientName = (TextInputEditText) findViewById(R.id.patient_name_et);
        this.patientAge = (TextInputEditText) findViewById(R.id.age_et);
        this.patientBloodGroup = (TextInputEditText) findViewById(R.id.blood_group_et);
        this.requestFor = (TextInputEditText) findViewById(R.id.request_for_et);
        this.requestType = (TextInputEditText) findViewById(R.id.request_type_et);
        this.date = (TextInputEditText) findViewById(R.id.date_et);
        this.noOfUnits = (TextInputEditText) findViewById(R.id.no_of_units_et);
        this.purpose = (TextInputEditText) findViewById(R.id.purpose_et);
        this.sendRequest = (Button) findViewById(R.id.request_blood_plasma_btn);
        this.bloodGroupPicker = (PickerUI) findViewById(R.id.blood_group_picker);
        this.requestForPicker = (PickerUI) findViewById(R.id.request_for_picker);
        this.requestTypePicker = (PickerUI) findViewById(R.id.request_type_picker);
        this.bloodGroupList = Arrays.asList(getResources().getStringArray(R.array.blood_group_list));
        this.requestForList = Arrays.asList(getResources().getStringArray(R.array.request_for_list));
        this.requestTypeList = Arrays.asList(getResources().getStringArray(R.array.request_type_list));
        this.hospCode.setText(this.hospitalDetails.getmHosCode());
        this.hospName.setText(this.hospitalDetails.getmHosName());
        this.hospAddress.setText(this.hospitalDetails.getmAddress());
        this.hospAddress.setText(this.hospitalDetails.getmAddress());
        this.myCalendar = Calendar.getInstance();
        this.date.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SendRequestBloodPlasmaActivity sendRequestBloodPlasmaActivity = SendRequestBloodPlasmaActivity.this;
                sendRequestBloodPlasmaActivity.datePickerDialog = sendRequestBloodPlasmaActivity.createDatePickerDialog();
                SendRequestBloodPlasmaActivity.this.datePickerDialog.show();
            }
        });
        this.datePicker = new DatePickerDialog.OnDateSetListener() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.2
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("onDateSet()", "arg0 = [" + view + "], year = [" + year + "], monthOfYear = [" + monthOfYear + "], dayOfMonth = [" + dayOfMonth + "]");
                SendRequestBloodPlasmaActivity.this.myCalendar.set(1, year);
                SendRequestBloodPlasmaActivity.this.myCalendar.set(2, monthOfYear);
                SendRequestBloodPlasmaActivity.this.myCalendar.set(5, dayOfMonth);
                SendRequestBloodPlasmaActivity.this.updateLabel();
            }
        };
        this.patientBloodGroup.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SendRequestBloodPlasmaActivity.this.bloodGroupPicker.setVisibility(0);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(SendRequestBloodPlasmaActivity.this.bloodGroupList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                SendRequestBloodPlasmaActivity.this.bloodGroupPicker.setSettings(pickerUISettings);
                SendRequestBloodPlasmaActivity.this.bloodGroupPicker.slide();
            }
        });
        this.requestFor.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SendRequestBloodPlasmaActivity.this.requestForPicker.setVisibility(0);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(SendRequestBloodPlasmaActivity.this.requestForList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                SendRequestBloodPlasmaActivity.this.requestForPicker.setSettings(pickerUISettings);
                SendRequestBloodPlasmaActivity.this.requestForPicker.slide();
            }
        });
        this.requestType.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SendRequestBloodPlasmaActivity.this.requestTypePicker.setVisibility(0);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(SendRequestBloodPlasmaActivity.this.requestTypeList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                SendRequestBloodPlasmaActivity.this.requestTypePicker.setSettings(pickerUISettings);
                SendRequestBloodPlasmaActivity.this.requestTypePicker.slide();
            }
        });
        this.bloodGroupPicker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.6
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(SendRequestBloodPlasmaActivity.this, valueResult, 0).show();
                SendRequestBloodPlasmaActivity.this.patientBloodGroup.setText(valueResult);
                SendRequestBloodPlasmaActivity.this.patientBloodGroup.setError(null);
            }
        });
        this.requestForPicker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.7
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(SendRequestBloodPlasmaActivity.this, valueResult, 0).show();
                SendRequestBloodPlasmaActivity.this.requestFor.setText(valueResult);
                SendRequestBloodPlasmaActivity.this.requestFor.setError(null);
            }
        });
        this.requestTypePicker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.8
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(SendRequestBloodPlasmaActivity.this, valueResult, 0).show();
                SendRequestBloodPlasmaActivity.this.requestType.setText(valueResult);
                SendRequestBloodPlasmaActivity.this.requestType.setError(null);
            }
        });
        this.sendRequest.setOnClickListener(new AnonymousClass9());
    }


    class AnonymousClass9 implements View.OnClickListener {
        AnonymousClass9() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (SendRequestBloodPlasmaActivity.this.validateFields()) {
                Utils.EncodeString(SendRequestBloodPlasmaActivity.this.patientDetails.getmEmail());
                SendRequestBloodPlasmaActivity sendRequestBloodPlasmaActivity = SendRequestBloodPlasmaActivity.this;
                sendRequestBloodPlasmaActivity.myRef = sendRequestBloodPlasmaActivity.fireBaseInitialSetup();
                final BloodPlasmaRequestDetails bloodPlasmaRequestDetails = new BloodPlasmaRequestDetails();
                bloodPlasmaRequestDetails.setHospName(SendRequestBloodPlasmaActivity.this.hospitalDetails.getmHosName());
                bloodPlasmaRequestDetails.setHospEmail(SendRequestBloodPlasmaActivity.this.hospitalDetails.getmEmail());
                bloodPlasmaRequestDetails.setHospAddress(SendRequestBloodPlasmaActivity.this.hospitalDetails.getmAddress());
                bloodPlasmaRequestDetails.setHospCode(SendRequestBloodPlasmaActivity.this.hospitalDetails.getmHosCode());
                bloodPlasmaRequestDetails.setPatientName(SendRequestBloodPlasmaActivity.this.patientName.getText().toString());
                bloodPlasmaRequestDetails.setPatientEmail(SendRequestBloodPlasmaActivity.this.patientDetails.getmEmail());
                bloodPlasmaRequestDetails.setPatientMobile(SendRequestBloodPlasmaActivity.this.patientDetails.getmMobileNumber());
                bloodPlasmaRequestDetails.setPatientBloodGroup(SendRequestBloodPlasmaActivity.this.patientBloodGroup.getText().toString());
                bloodPlasmaRequestDetails.setRequestFor(SendRequestBloodPlasmaActivity.this.requestFor.getText().toString());
                bloodPlasmaRequestDetails.setRequestType(SendRequestBloodPlasmaActivity.this.requestType.getText().toString());
                bloodPlasmaRequestDetails.setRequiredDate(SendRequestBloodPlasmaActivity.this.date.getText().toString());
                bloodPlasmaRequestDetails.setNoOfUnits(SendRequestBloodPlasmaActivity.this.noOfUnits.getText().toString());
                bloodPlasmaRequestDetails.setPurpose(SendRequestBloodPlasmaActivity.this.purpose.getText().toString());
                bloodPlasmaRequestDetails.setPatientAge(SendRequestBloodPlasmaActivity.this.patientAge.getText().toString());
                bloodPlasmaRequestDetails.setPatientGender(SendRequestBloodPlasmaActivity.this.patientDetails.getmGender());
                bloodPlasmaRequestDetails.setIsRequestAccepted("created");
                try {
                    SendRequestBloodPlasmaActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(SendRequestBloodPlasmaActivity.this).setMessage(R.string.registering_patient).show();
                    SendRequestBloodPlasmaActivity.this.myRef.child(CommonData.BLOOD_PLASMA_REQUESTS).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.9.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                SendRequestBloodPlasmaActivity.this.myRef.child(CommonData.BLOOD_PLASMA_REQUESTS).child(SendRequestBloodPlasmaActivity.this.myRef.push().getKey()).setValue(bloodPlasmaRequestDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.9.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SendRequestBloodPlasmaActivity.this.awesomeProgressDialog.dismiss();
                                            Utils.showSuccessDialogue(SendRequestBloodPlasmaActivity.this, "Request for Blood/Plasma is registered successfully", true);
                                        }
                                    }
                                });
                            } else {
                                SendRequestBloodPlasmaActivity.this.myRef.child(CommonData.BLOOD_PLASMA_REQUESTS).child(SendRequestBloodPlasmaActivity.this.myRef.push().getKey()).setValue(bloodPlasmaRequestDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.patient.SendRequestBloodPlasmaActivity.9.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SendRequestBloodPlasmaActivity.this.awesomeProgressDialog.dismiss();
                                            Utils.showSuccessDialogue(SendRequestBloodPlasmaActivity.this, "Request for Blood/Plasma is registered successfully", true);
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            SendRequestBloodPlasmaActivity.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(SendRequestBloodPlasmaActivity.this, "Something went wrong. Please try again..!!", 0).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    SendRequestBloodPlasmaActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(SendRequestBloodPlasmaActivity.this, "Unable to process request. Please try again..!!", 0).show();
                }
            }
        }
    }


    public void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        this.date.setText(sdf.format(this.myCalendar.getTime()));
    }


    public DatePickerDialog createDatePickerDialog() {
        this.datePickerDialog = new DatePickerDialog(this, this.datePicker, this.myCalendar.get(1), this.myCalendar.get(2), this.myCalendar.get(5));
        this.datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        return this.datePickerDialog;
    }


    public DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    public boolean validateFields() {
        if (this.patientName.getText().toString().trim().length() == 0) {
            setFocus(this.patientName, getResources().getString(R.string.enter_name));
            return false;
        } else if (this.patientAge.getText().toString().trim().length() == 0) {
            setFocus(this.patientAge, getResources().getString(R.string.enter_age));
            return false;
        } else if (this.patientBloodGroup.getText().toString().trim().length() == 0) {
            setFocus(this.patientBloodGroup, getResources().getString(R.string.enter_bloodgroup));
            return false;
        } else if (this.requestFor.getText().toString().trim().length() == 0) {
            setFocus(this.requestFor, getResources().getString(R.string.choose_for));
            return false;
        } else if (this.requestType.getText().toString().trim().length() == 0) {
            setFocus(this.requestType, getResources().getString(R.string.choose_request_type));
            return false;
        } else if (this.date.getText().toString().trim().length() == 0) {
            setFocus(this.date, getResources().getString(R.string.choose_date));
            return false;
        } else if (this.noOfUnits.getText().toString().trim().length() == 0) {
            setFocus(this.noOfUnits, getResources().getString(R.string.enter_no_units));
            return false;
        } else if (this.purpose.getText().toString().trim().length() == 0) {
            setFocus(this.purpose, getResources().getString(R.string.enter_purpose));
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
