package edu.communication.hemo.patient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import edu.communication.hemo.R;
import edu.communication.hemo.patient.model.PatientDetails;

public class PatientDashBoardActivity extends AppCompatActivity {
    ImageView expandedMenu;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() { // from class: edu.communication.hemo.patient.PatientDashBoardActivity.2
        @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home /* 2131296732 */:
                    PatientDashBoardActivity.this.toolbarTitle.setText("Home");
                    Fragment fragment = new PatientHomeFragment();
                    PatientDashBoardActivity.this.loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications /* 2131296733 */:
                    PatientDashBoardActivity.this.toolbarTitle.setText("Notifications");
                    Fragment fragment2 = new PatientNotificationsFragment();
                    PatientDashBoardActivity.this.loadFragment(fragment2);
                    return true;
                case R.id.navigation_profile /* 2131296734 */:
                    PatientDashBoardActivity.this.toolbarTitle.setText("Profile");
                    Fragment fragment3 = new PatientProfileFragment();
                    PatientDashBoardActivity.this.loadFragment(fragment3);
                    return true;
                default:
                    return false;
            }
        }
    };
    BottomNavigationView navigation;
    public PatientDetails patientDetails;
    Toolbar toolbar;
    TextView toolbarTitle;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dash_board);
        this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.expandedMenu = (ImageView) findViewById(R.id.expanded_menu);
        this.navigation = (BottomNavigationView) findViewById(R.id.navigation);
        this.navigation.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);
        this.expandedMenu.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.PatientDashBoardActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PatientDashBoardActivity.this.showLogoutAlert();
            }
        });
        loadFragment(new PatientHomeFragment());
    }


    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.are_you_sure_logout));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.patient.PatientDashBoardActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(PatientDashBoardActivity.this.getApplicationContext(), PatientDashBoardActivity.this.getResources().getString(R.string.logout_success), 0).show();
                PatientDashBoardActivity patientDashBoardActivity = PatientDashBoardActivity.this;
                patientDashBoardActivity.startActivity(new Intent(patientDashBoardActivity, PatientLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                PatientDashBoardActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.patient.PatientDashBoardActivity.4
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
