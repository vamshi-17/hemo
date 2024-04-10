package edu.communication.hemo.doctor;

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
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import edu.communication.hemo.R;
import edu.communication.hemo.admin.model.DoctorDetails;

public class DoctorDashBoardActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    DoctorDetails doctorDetails;
    ImageView expandedMenu;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() { // from class: edu.communication.hemo.doctor.DoctorDashBoardActivity.2
        @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_all_patients /* 2131296724 */:
                    DoctorDashBoardActivity.this.toolbarTitle.setText("All Patients");
                    Fragment fragment = new AllPatientsFragment();
                    DoctorDashBoardActivity.this.loadFragment(fragment);
                    return true;
                case R.id.navigation_appointments /* 2131296725 */:
                    DoctorDashBoardActivity.this.toolbarTitle.setText("Appointments");
                    Fragment fragment2 = new AppointmentsFragment();
                    DoctorDashBoardActivity.this.loadFragment(fragment2);
                    return true;
                case R.id.navigation_notifications /* 2131296733 */:
                    DoctorDashBoardActivity.this.toolbarTitle.setText("Notifications");
                    Fragment fragment3 = new DoctorNotificationsFragment();
                    DoctorDashBoardActivity.this.loadFragment(fragment3);
                    return true;
                default:
                    return false;
            }
        }
    };
    BottomNavigationView navigation;
    Toolbar toolbar;
    TextView toolbarTitle;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dash_board);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.navigation = (BottomNavigationView) findViewById(R.id.navigation);
        this.navigation.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);
        this.expandedMenu = (ImageView) findViewById(R.id.expanded_menu);
        this.doctorDetails = (DoctorDetails) getIntent().getExtras().getParcelable("doctorDetails");
        this.expandedMenu.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.doctor.DoctorDashBoardActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DoctorDashBoardActivity.this.showMenu(v);
            }
        });
        loadFragment(new AllPatientsFragment());
    }


    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.are_you_sure_logout));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.doctor.DoctorDashBoardActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(DoctorDashBoardActivity.this.getApplicationContext(), DoctorDashBoardActivity.this.getResources().getString(R.string.logout_success), 0).show();
                DoctorDashBoardActivity doctorDashBoardActivity = DoctorDashBoardActivity.this;
                doctorDashBoardActivity.startActivity(new Intent(doctorDashBoardActivity, DoctorLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                DoctorDashBoardActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.doctor.DoctorDashBoardActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_doctor);
        popup.show();
    }

    @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.logout) {
            showLogoutAlert();
            return true;
        } else if (itemId == R.id.profile) {
            try {
                if (this.doctorDetails != null) {
                    Intent intent = new Intent(this, DoctorProfileActivity.class);
                    intent.putExtra("doctorDetails", this.doctorDetails);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        showLogoutAlert();
    }
}
