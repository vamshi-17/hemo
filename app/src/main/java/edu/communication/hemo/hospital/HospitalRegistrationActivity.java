package edu.communication.hemo.hospital;

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
import edu.communication.hemo.hospital.model.HospitalDetails;

public class HospitalRegistrationActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextInputEditText mAddress;
    TextInputEditText mCodeHosp;
    TextInputEditText mEmail;
    TextInputEditText mHosName;
    TextInputEditText mPassword;
    Button mRegisterBtn;
    DatabaseReference myRef;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_registration);
        this.mHosName = (TextInputEditText) findViewById(R.id.hos_name_et);
        this.mAddress = (TextInputEditText) findViewById(R.id.address_et);
        this.mEmail = (TextInputEditText) findViewById(R.id.email_et);
        this.mPassword = (TextInputEditText) findViewById(R.id.password_et);
        this.mCodeHosp = (TextInputEditText) findViewById(R.id.code_hos_et);
        this.mRegisterBtn = (Button) findViewById(R.id.register_btn);
        this.mRegisterBtn.setOnClickListener(new AnonymousClass1());
    }


    class AnonymousClass1 implements View.OnClickListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (HospitalRegistrationActivity.this.validateFields()) {
                final String encodedEmail = Utils.EncodeString(HospitalRegistrationActivity.this.mEmail.getText().toString());
                HospitalRegistrationActivity hospitalRegistrationActivity = HospitalRegistrationActivity.this;
                hospitalRegistrationActivity.myRef = hospitalRegistrationActivity.fireBaseInitialSetup();
                final HospitalDetails hospitalDetails = new HospitalDetails();
                hospitalDetails.setmHosName(HospitalRegistrationActivity.this.mHosName.getText().toString());
                hospitalDetails.setmAddress(HospitalRegistrationActivity.this.mAddress.getText().toString());
                hospitalDetails.setmEmail(encodedEmail);
                hospitalDetails.setmPassword(HospitalRegistrationActivity.this.mPassword.getText().toString());
                hospitalDetails.setmHosCode(HospitalRegistrationActivity.this.mCodeHosp.getText().toString());
                try {
                    HospitalRegistrationActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(HospitalRegistrationActivity.this).setMessage(R.string.registering_hospital).show();
                    HospitalRegistrationActivity.this.myRef.child(CommonData.HOSPITALS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.hospital.HospitalRegistrationActivity.1.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                HospitalRegistrationActivity.this.myRef.child(CommonData.HOSPITALS).child(encodedEmail).setValue(hospitalDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.hospital.HospitalRegistrationActivity.1.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            HospitalRegistrationActivity.this.awesomeProgressDialog.dismiss();
                                            HospitalRegistrationActivity hospitalRegistrationActivity2 = HospitalRegistrationActivity.this;
                                            Utils.showSuccessDialogue(hospitalRegistrationActivity2, "Your Hospital with " + HospitalRegistrationActivity.this.mHosName.getText().toString() + " Name is registered successfully", HospitalLoginActivity.class, true);
                                        }
                                    }
                                });
                            } else {
                                HospitalRegistrationActivity.this.myRef.child(CommonData.HOSPITALS).child(encodedEmail).setValue(hospitalDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.hospital.HospitalRegistrationActivity.1.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            HospitalRegistrationActivity.this.awesomeProgressDialog.dismiss();
                                            HospitalRegistrationActivity hospitalRegistrationActivity2 = HospitalRegistrationActivity.this;
                                            Utils.showSuccessDialogue(hospitalRegistrationActivity2, "Your Hospital with  " + HospitalRegistrationActivity.this.mHosName.getText().toString() + " Name is registered successfully", HospitalLoginActivity.class, true);
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            HospitalRegistrationActivity.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(HospitalRegistrationActivity.this, "Something went wrong. Please try again..!!", 0).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    HospitalRegistrationActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(HospitalRegistrationActivity.this, "Unable to process request. Please try again..!!", 0).show();
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
        if (this.mHosName.getText().toString().trim().length() == 0) {
            setFocus(this.mHosName, getResources().getString(R.string.enter_hos_name));
            return false;
        } else if (this.mAddress.getText().toString().trim().length() == 0) {
            setFocus(this.mAddress, getResources().getString(R.string.enter_address));
            return false;
        } else if (this.mEmail.getText().toString().trim().length() == 0) {
            setFocus(this.mEmail, getResources().getString(R.string.enter_email));
            return false;
        } else if (!this.mEmail.getText().toString().trim().matches(this.emailPattern)) {
            setFocus(this.mEmail, getResources().getString(R.string.enter_valid_email));
            return false;
        } else if (this.mPassword.getText().toString().trim().length() == 0) {
            setFocus(this.mPassword, getResources().getString(R.string.enter_password));
            return false;
        } else if (this.mCodeHosp.getText().toString().trim().length() == 0) {
            setFocus(this.mCodeHosp, getResources().getString(R.string.enter_hos_code));
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
