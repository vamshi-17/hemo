package edu.communication.hemo.patient;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.ForgotPasswordActivity;
import edu.communication.hemo.R;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.patient.model.PatientDetails;

public class PatientLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView forgotPassword;
    Button login;
    TextInputEditText mEmail;
    TextInputEditText mPassword;
    DatabaseReference myRef;
    TextView patientRegister;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        this.mEmail = (TextInputEditText) findViewById(R.id.email_et);
        this.mPassword = (TextInputEditText) findViewById(R.id.password_et);
        this.forgotPassword = (TextView) findViewById(R.id.forgot_password);
        this.patientRegister = (TextView) findViewById(R.id.patient_register);
        this.login = (Button) findViewById(R.id.login);
        this.forgotPassword.setOnClickListener(this);
        this.login.setOnClickListener(this);
        this.patientRegister.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.forgot_password) {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            intent.putExtra("userType", "patient");
            startActivity(intent);
        } else if (id != R.id.login) {
            if (id == R.id.patient_register) {
                startActivity(new Intent(this, PatientRegisterActivity.class));
            }
        } else if (validateFields()) {
            processLogin(this.mEmail.getText().toString(), this.mPassword.getText().toString());
        }
    }

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }

    private void processLogin(final String email, final String password) {
        this.myRef = fireBaseInitialSetup();
        String encodedEmail = Utils.EncodeString(email);
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.PATIENTS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.patient.PatientLoginActivity.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PatientLoginActivity.this.awesomeProgressDialog.dismiss();
                        PatientDetails patientDetails = (PatientDetails) dataSnapshot.getValue(PatientDetails.class);
                        String decodedEmail = Utils.DecodeString(patientDetails.getmEmail());
                        String databasePassword = patientDetails.getmPassword();
                        if (email.equals(decodedEmail) && password.equals(databasePassword)) {
                            Intent intent = new Intent(PatientLoginActivity.this, PatientDashBoardActivity.class);
                            intent.putExtra("patientDetails", patientDetails);
                            PatientLoginActivity.this.startActivity(intent);
                            return;
                        }
                        Toast.makeText(PatientLoginActivity.this, "Invalid Credentials", 0).show();
                        return;
                    }
                    PatientLoginActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientLoginActivity.this, "Patient details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    PatientLoginActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(PatientLoginActivity.this, "No Results Found", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(this, "Unable to process request. Please try again..!!", 0).show();
        }
    }

    private boolean validateFields() {
        if (this.mEmail.getText().toString().trim().length() == 0) {
            setFocus(this.mEmail, getResources().getString(R.string.enter_email));
            return false;
        } else if (!this.mEmail.getText().toString().trim().matches(this.emailPattern)) {
            setFocus(this.mEmail, getResources().getString(R.string.enter_valid_email));
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
