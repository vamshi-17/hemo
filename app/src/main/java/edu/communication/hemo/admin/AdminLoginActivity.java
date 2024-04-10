package edu.communication.hemo.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import edu.communication.hemo.R;

public class AdminLoginActivity extends AppCompatActivity {
    Button mAdminLogin;
    TextInputEditText mPassword;
    TextInputEditText mUserId;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        this.mUserId = (TextInputEditText) findViewById(R.id.user_id_et);
        this.mPassword = (TextInputEditText) findViewById(R.id.password_et);
        this.mAdminLogin = (Button) findViewById(R.id.admin_login_btn);
        this.mAdminLogin.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.admin.AdminLoginActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (AdminLoginActivity.this.validateFields()) {
                    if (AdminLoginActivity.this.mUserId.getText().toString().equals("admin") && AdminLoginActivity.this.mPassword.getText().toString().equals("admin")) {
                        AdminLoginActivity adminLoginActivity = AdminLoginActivity.this;
                        adminLoginActivity.startActivity(new Intent(adminLoginActivity, AdminRolesActivity.class));
                        AdminLoginActivity.this.finish();
                        return;
                    }
                    AdminLoginActivity adminLoginActivity2 = AdminLoginActivity.this;
                    Toast.makeText(adminLoginActivity2, adminLoginActivity2.getResources().getString(R.string.correct_details), 0).show();
                }
            }
        });
    }


    public boolean validateFields() {
        if (this.mUserId.getText().toString().trim().length() == 0) {
            setFocus(this.mUserId, "Please enter username");
            return false;
        } else if (this.mUserId.getText().toString().trim().length() < 5) {
            setFocus(this.mUserId, "Please enter valid username");
            return false;
        } else if (this.mPassword.getText().toString().trim().length() == 0) {
            setFocus(this.mPassword, "Please enter password");
            return false;
        } else if (this.mPassword.getText().toString().trim().length() >= 5) {
            return true;
        } else {
            setFocus(this.mPassword, "Please enter valid password");
            return false;
        }
    }

    private void setFocus(TextInputEditText editText, String string) {
        editText.requestFocus();
        editText.setError(Html.fromHtml("<font color='red'>" + string + "</font>"));
    }
}
