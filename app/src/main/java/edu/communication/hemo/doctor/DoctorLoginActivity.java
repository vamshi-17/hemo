package edu.communication.hemo.doctor;

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
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;

public class DoctorLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    Button doctorLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextInputEditText mEmail;
    TextView mForgotPassword;
    TextInputEditText mPassword;
    DatabaseReference myRef;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        this.mEmail = (TextInputEditText) findViewById(R.id.user_id_et);
        this.mPassword = (TextInputEditText) findViewById(R.id.password_et);
        this.doctorLogin = (Button) findViewById(R.id.doctor_login_btn);
        this.mForgotPassword = (TextView) findViewById(R.id.forgot_password);
        this.doctorLogin.setOnClickListener(this);
        this.mForgotPassword.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.doctor_login_btn) {
            if (validateFields()) {
                processLogin(this.mEmail.getText().toString(), this.mPassword.getText().toString());
            }
        } else if (id == R.id.forgot_password) {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            intent.putExtra("userType", "doctor");
            startActivity(intent);
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

    private DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }

    private void processLogin(final String email, final String password) {
        this.myRef = fireBaseInitialSetup();
        String encodedEmail = Utils.EncodeString(email);
        this.awesomeProgressDialog = new AwesomeProgressDialog(this).setMessage(R.string.load_details).show();
        try {
            this.myRef.child(CommonData.DOCTORS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.doctor.DoctorLoginActivity.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        DoctorLoginActivity.this.awesomeProgressDialog.dismiss();
                        DoctorDetails doctorDetails = (DoctorDetails) dataSnapshot.getValue(DoctorDetails.class);
                        String decodedEmail = Utils.DecodeString(doctorDetails.getmEmail());
                        String databasePassword = doctorDetails.getmPassword();
                        if (email.equals(decodedEmail) && password.equals(databasePassword)) {
                            Intent intent = new Intent(DoctorLoginActivity.this, DoctorDashBoardActivity.class);
                            intent.putExtra("doctorDetails", doctorDetails);
                            DoctorLoginActivity.this.startActivity(intent);
                            return;
                        }
                        Toast.makeText(DoctorLoginActivity.this, "Invalid Credentials", 0).show();
                        return;
                    }
                    DoctorLoginActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DoctorLoginActivity.this, "Doctor details were not found", 0).show();
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError databaseError) {
                    DoctorLoginActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(DoctorLoginActivity.this, "No Results Found", 0).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.awesomeProgressDialog.dismiss();
            Toast.makeText(this, "Unable to process request. Please try again..!!", 0).show();
        }
    }
}
