package edu.communication.hemo.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import edu.communication.hemo.MainActivity;
import edu.communication.hemo.R;

public class AdminRolesActivity extends AppCompatActivity implements View.OnClickListener {
    Button add_doctor;
    Button add_symptoms;
    ImageView logout;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_roles);
        this.add_doctor = (Button) findViewById(R.id.add_doctor);
        this.add_symptoms = (Button) findViewById(R.id.add_symptoms);
        this.logout = (ImageView) findViewById(R.id.logout);
        this.add_doctor.setOnClickListener(this);
        this.add_symptoms.setOnClickListener(this);
        this.logout.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_doctor) {
            startActivity(new Intent(this, AddDoctorActivity.class));
        } else if (id == R.id.add_symptoms) {
            startActivity(new Intent(this, AddSymptomsActivity.class));
        } else if (id == R.id.logout) {
            showLogoutAlert();
        }
    }

    private void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.are_you_sure_logout));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.admin.AdminRolesActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(AdminRolesActivity.this.getApplicationContext(), AdminRolesActivity.this.getResources().getString(R.string.logout_success), 0).show();
                AdminRolesActivity adminRolesActivity = AdminRolesActivity.this;
                adminRolesActivity.startActivity(new Intent(adminRolesActivity, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                AdminRolesActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.admin.AdminRolesActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        showLogoutAlert();
    }
}
