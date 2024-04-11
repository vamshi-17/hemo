package edu.communication.hemo.donor;

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
import edu.communication.hemo.MainActivity;
import edu.communication.hemo.R;
import edu.communication.hemo.donor.model.DonorDetails;

public class DonorDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    public DonorDetails donorDetails;
    ImageView logout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() { // from class: edu.communication.hemo.donor.DonorDashboardActivity.1
        @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home /* 2131296732 */:
                    DonorDashboardActivity.this.toolbarTitle.setText("Home");
                    Fragment fragment = new DonorHomeFragment();
                    DonorDashboardActivity.this.loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications /* 2131296733 */:
                    DonorDashboardActivity.this.toolbarTitle.setText("Notifications");
                    Fragment fragment2 = new DonorNotificationFragment();
                    DonorDashboardActivity.this.loadFragment(fragment2);
                    return true;
                case R.id.navigation_profile /* 2131296734 */:
                    DonorDashboardActivity.this.toolbarTitle.setText("Profile");
                    Fragment fragment3 = new DonorProfileFragment();
                    DonorDashboardActivity.this.loadFragment(fragment3);
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
        setContentView(R.layout.activity_donor_dashboard);
        try {
            this.donorDetails = (DonorDetails) getIntent().getExtras().getParcelable("donorDetails");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
        }
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.logout = (ImageView) findViewById(R.id.donor_logout);
        this.navigation = (BottomNavigationView) findViewById(R.id.donor_navigation);
        this.navigation.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);
        this.logout.setOnClickListener(this);
        loadFragment(new DonorHomeFragment());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.getId() == R.id.donor_logout) {
            showLogoutAlert();
        }
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
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.donor.DonorDashboardActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(DonorDashboardActivity.this.getApplicationContext(), DonorDashboardActivity.this.getResources().getString(R.string.logout_success), 0).show();
                DonorDashboardActivity donorDashboardActivity = DonorDashboardActivity.this;
                donorDashboardActivity.startActivity(new Intent(donorDashboardActivity, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                DonorDashboardActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.donor.DonorDashboardActivity.3
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
