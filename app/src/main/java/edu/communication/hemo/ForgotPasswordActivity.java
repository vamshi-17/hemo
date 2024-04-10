package edu.communication.hemo;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.hospital.model.HospitalDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import edu.communication.hemo.pharmacy.model.PharmacyDetails;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextInputEditText forgotEmail;
    Button forgotPassword;
    DatabaseReference myRef;
    String userType;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        this.forgotEmail = (TextInputEditText) findViewById(R.id.forgot_email_id_et);
        this.forgotPassword = (Button) findViewById(R.id.forgot_password_btn);
        this.userType = getIntent().getStringExtra("userType");
        this.forgotPassword.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.ForgotPasswordActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ForgotPasswordActivity.this.validateFields()) {
                    String encodedEmail = Utils.EncodeString(ForgotPasswordActivity.this.forgotEmail.getText().toString());
                    ForgotPasswordActivity forgotPasswordActivity = ForgotPasswordActivity.this;
                    forgotPasswordActivity.myRef = forgotPasswordActivity.fireBaseInitialSetup();
                    try {
                        ForgotPasswordActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(ForgotPasswordActivity.this).setMessage(R.string.load_details).show();
                        if (ForgotPasswordActivity.this.userType.equals("hospital")) {
                            ForgotPasswordActivity.this.fetchHospitalPassword(encodedEmail);
                        } else if (ForgotPasswordActivity.this.userType.equals("patient")) {
                            ForgotPasswordActivity.this.fetchPatientsPassword(encodedEmail);
                        } else if (ForgotPasswordActivity.this.userType.equals("donor")) {
                            ForgotPasswordActivity.this.fetchDonorsPassword(encodedEmail);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Unable to process request. Please try again..!!", 0).show();
                    }
                }
            }
        });
    }


    public void fetchPatientsPassword(String encodedEmail) {
        this.myRef.child(CommonData.PATIENTS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.ForgotPasswordActivity.2
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                    PatientDetails patientDetails = (PatientDetails) dataSnapshot.getValue(PatientDetails.class);
                    String decodedEmail = Utils.DecodeString(patientDetails.getmEmail());
                    String databasePassword = patientDetails.getmPassword();
                    ForgotPasswordActivity forgotPasswordActivity = ForgotPasswordActivity.this;
                    Utils.showSuccessDialogue(forgotPasswordActivity, "Your Password for " + decodedEmail + " is : " + databasePassword);
                    return;
                }
                ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Patient details were not found", 0).show();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "No Results Found", 0).show();
            }
        });
    }


    public void fetchHospitalPassword(String encodedEmail) {
        this.myRef.child(CommonData.HOSPITALS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.ForgotPasswordActivity.3
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                    HospitalDetails hospitalDetails = (HospitalDetails) dataSnapshot.getValue(HospitalDetails.class);
                    String decodedEmail = Utils.DecodeString(hospitalDetails.getmEmail());
                    String databasePassword = hospitalDetails.getmPassword();
                    ForgotPasswordActivity forgotPasswordActivity = ForgotPasswordActivity.this;
                    Utils.showSuccessDialogue(forgotPasswordActivity, "Your Password for " + decodedEmail + " is : " + databasePassword);
                    return;
                }
                ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Hospital details were not found", 0).show();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "No Results Found", 0).show();
            }
        });
    }


    public void fetchDonorsPassword(String encodedEmail) {
        this.myRef.child(CommonData.DONORS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.ForgotPasswordActivity.4
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                    PharmacyDetails pharmacyDetails = (PharmacyDetails) dataSnapshot.getValue(PharmacyDetails.class);
                    String decodedEmail = Utils.DecodeString(pharmacyDetails.getmEmail());
                    String databasePassword = pharmacyDetails.getmPassword();
                    ForgotPasswordActivity forgotPasswordActivity = ForgotPasswordActivity.this;
                    Utils.showSuccessDialogue(forgotPasswordActivity, "Your Password for " + decodedEmail + " is : " + databasePassword);
                    return;
                }
                ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Donor details were not found", 0).show();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                ForgotPasswordActivity.this.awesomeProgressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "No Results Found", 0).show();
            }
        });
    }


    public DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    public boolean validateFields() {
        if (this.forgotEmail.getText().toString().trim().length() == 0) {
            setFocus(this.forgotEmail, "Please enter Email ID");
            return false;
        } else if (this.forgotEmail.getText().toString().trim().matches(this.emailPattern)) {
            return true;
        } else {
            setFocus(this.forgotEmail, getResources().getString(R.string.enter_valid_email));
            return false;
        }
    }

    private void setFocus(TextInputEditText editText, String string) {
        editText.requestFocus();
        editText.setError(Html.fromHtml("<font color='red'>" + string + "</font>"));
    }
}
