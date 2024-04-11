package edu.communication.hemo.pharmacy;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
import edu.communication.hemo.pharmacy.model.PharmacyDetails;

public class PharmacyRegisterActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    TextInputEditText mAddress;
    TextInputEditText mEmail;
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
        setContentView(R.layout.activity_pharmacy_register);
        this.mName = (TextInputEditText) findViewById(R.id.name_et);
        this.mEmail = (TextInputEditText) findViewById(R.id.email_et);
        this.mMobileNumber = (TextInputEditText) findViewById(R.id.mobile_number_et);
        this.mAddress = (TextInputEditText) findViewById(R.id.address_et);
        this.mPassword = (TextInputEditText) findViewById(R.id.password_et);
        this.mRegisterBtn = (Button) findViewById(R.id.register_btn);
        this.mRegisterBtn.setOnClickListener(new AnonymousClass1());
    }


    class AnonymousClass1 implements View.OnClickListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (PharmacyRegisterActivity.this.validateFields()) {
                final String encodedEmail = Utils.EncodeString(PharmacyRegisterActivity.this.mEmail.getText().toString());
                String encodedAddress = Utils.EncodeString(PharmacyRegisterActivity.this.mAddress.getText().toString());
                PharmacyRegisterActivity pharmacyRegisterActivity = PharmacyRegisterActivity.this;
                pharmacyRegisterActivity.myRef = pharmacyRegisterActivity.fireBaseInitialSetup();
                final PharmacyDetails pharmacyDetails = new PharmacyDetails();
                pharmacyDetails.setName(PharmacyRegisterActivity.this.mName.getText().toString());
                pharmacyDetails.setmEmail(encodedEmail);
                pharmacyDetails.setmAddress(encodedAddress);
                pharmacyDetails.setmMobileNumber(PharmacyRegisterActivity.this.mMobileNumber.getText().toString());
                pharmacyDetails.setmPassword(PharmacyRegisterActivity.this.mPassword.getText().toString());
                try {
                    PharmacyRegisterActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(PharmacyRegisterActivity.this).setMessage(R.string.registering_patient).show();
                    PharmacyRegisterActivity.this.myRef.child(CommonData.PHARMACY).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.pharmacy.PharmacyRegisterActivity.1.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                PharmacyRegisterActivity.this.myRef.child(CommonData.PHARMACY).child(encodedEmail).setValue(pharmacyDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.pharmacy.PharmacyRegisterActivity.1.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            PharmacyRegisterActivity.this.awesomeProgressDialog.dismiss();
                                            PharmacyRegisterActivity pharmacyRegisterActivity2 = PharmacyRegisterActivity.this;
                                            Utils.showSuccessDialogue(pharmacyRegisterActivity2, "Pharmacy " + PharmacyRegisterActivity.this.mName.getText().toString() + " is registered successfully", PharmacyLoginActivity.class, true);
                                        }
                                    }
                                });
                            } else {
                                PharmacyRegisterActivity.this.myRef.child(CommonData.PHARMACY).child(encodedEmail).setValue(pharmacyDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.pharmacy.PharmacyRegisterActivity.1.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            PharmacyRegisterActivity.this.awesomeProgressDialog.dismiss();
                                            PharmacyRegisterActivity pharmacyRegisterActivity2 = PharmacyRegisterActivity.this;
                                            Utils.showSuccessDialogue(pharmacyRegisterActivity2, "Pharmacy " + PharmacyRegisterActivity.this.mName.getText().toString() + " is registered successfully", PharmacyLoginActivity.class, true);
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            PharmacyRegisterActivity.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(PharmacyRegisterActivity.this, "Something went wrong. Please try again..!!", 0).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    PharmacyRegisterActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PharmacyRegisterActivity.this, "Unable to process request. Please try again..!!", 0).show();
                }
            }
        }
    }


    public DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    public boolean validateFields() {
        if (this.mName.getText().toString().trim().length() == 0) {
            setFocus(this.mName, getResources().getString(R.string.enter_name));
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
        } else if (this.mAddress.getText().toString().trim().length() == 0) {
            setFocus(this.mAddress, getResources().getString(R.string.enter_gender));
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
