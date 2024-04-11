package edu.communication.hemo.admin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.dpizarro.uipicker.library.picker.PickerUI;
import com.dpizarro.uipicker.library.picker.PickerUISettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.MainActivity;
import edu.communication.hemo.R;
import edu.communication.hemo.admin.model.SymptomsDetails;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import java.util.Arrays;
import java.util.List;

public class AddSymptomsActivity extends AppCompatActivity {
    Button add_symptoms_btn;
    private Dialog awesomeProgressDialog;
    FirebaseDatabase database;
    List<String> doctorTypeList;
    ImageView logout;
    TextInputEditText mAddSymptoms;
    TextInputEditText mPreferredDoctor;
    TextInputEditText mSolutions;
    DatabaseReference myRef;
    PickerUI preferred_doctor_picker;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptoms);
        this.logout = (ImageView) findViewById(R.id.logout);
        this.mAddSymptoms = (TextInputEditText) findViewById(R.id.admin_symptoms_et);
        this.mPreferredDoctor = (TextInputEditText) findViewById(R.id.preferred_doctor_et);
        this.mSolutions = (TextInputEditText) findViewById(R.id.solution_et);
        this.add_symptoms_btn = (Button) findViewById(R.id.add_symptoms_btn);
        this.preferred_doctor_picker = (PickerUI) findViewById(R.id.preferred_doctor_picker);
        this.doctorTypeList = Arrays.asList(getResources().getStringArray(R.array.doctor_specialities_list));
        this.mPreferredDoctor.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.admin.AddSymptomsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddSymptomsActivity.this.preferred_doctor_picker.setVisibility(View.VISIBLE);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(AddSymptomsActivity.this.doctorTypeList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                AddSymptomsActivity.this.preferred_doctor_picker.setSettings(pickerUISettings);
                AddSymptomsActivity.this.preferred_doctor_picker.slide();
            }
        });
        this.add_symptoms_btn.setOnClickListener(new AnonymousClass2());
        this.logout.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.admin.AddSymptomsActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddSymptomsActivity.this.showLogoutAlert();
            }
        });
        this.preferred_doctor_picker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.admin.AddSymptomsActivity.4
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(AddSymptomsActivity.this, valueResult, Toast.LENGTH_SHORT).show();
                AddSymptomsActivity.this.mPreferredDoctor.setText(valueResult);
                AddSymptomsActivity.this.mPreferredDoctor.setError(null);
            }
        });
    }


    class AnonymousClass2 implements View.OnClickListener {
        AnonymousClass2() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (AddSymptomsActivity.this.validateFields()) {
                AddSymptomsActivity addSymptomsActivity = AddSymptomsActivity.this;
                addSymptomsActivity.myRef = addSymptomsActivity.fireBaseInitialSetup();
                final String encodedAddSymptoms = Utils.EncodeString(AddSymptomsActivity.this.mAddSymptoms.getText().toString().toLowerCase());
                String encodedSolution = Utils.EncodeString(AddSymptomsActivity.this.mSolutions.getText().toString().toLowerCase());
                final SymptomsDetails symptomsDetails = new SymptomsDetails();
                symptomsDetails.setmSymptom(encodedAddSymptoms);
                symptomsDetails.setmSolution(encodedSolution);
                symptomsDetails.setmPreferredDoctorType(AddSymptomsActivity.this.mPreferredDoctor.getText().toString());
                try {
                    AddSymptomsActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(AddSymptomsActivity.this).setMessage(R.string.adding_symptoms).show();
                    AddSymptomsActivity.this.myRef.child(CommonData.SYMPTOMS).child(encodedAddSymptoms).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.admin.AddSymptomsActivity.2.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                AddSymptomsActivity.this.myRef.child(CommonData.SYMPTOMS).child(encodedAddSymptoms).setValue(symptomsDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.admin.AddSymptomsActivity.2.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            AddSymptomsActivity.this.awesomeProgressDialog.dismiss();
                                            AddSymptomsActivity addSymptomsActivity2 = AddSymptomsActivity.this;
                                            Utils.showSuccessDialogue(addSymptomsActivity2, "Symptom " + AddSymptomsActivity.this.mAddSymptoms.getText().toString() + " is added successfully");
                                            AddSymptomsActivity.this.clearFields();
                                        }
                                    }
                                });
                            } else {
                                AddSymptomsActivity.this.myRef.child(CommonData.SYMPTOMS).child(encodedAddSymptoms).setValue(symptomsDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.admin.AddSymptomsActivity.2.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            AddSymptomsActivity.this.awesomeProgressDialog.dismiss();
                                            AddSymptomsActivity addSymptomsActivity2 = AddSymptomsActivity.this;
                                            Utils.showSuccessDialogue(addSymptomsActivity2, "Symptom " + AddSymptomsActivity.this.mAddSymptoms.getText().toString() + " is added successfully");
                                            AddSymptomsActivity.this.clearFields();
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            AddSymptomsActivity.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(AddSymptomsActivity.this, "Something went wrong. Please try again..!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    AddSymptomsActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(AddSymptomsActivity.this, "Unable to process request. Please try again..!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void clearFields() {
        this.mAddSymptoms.setText("");
        this.mSolutions.setText("");
        this.mPreferredDoctor.setText("");
    }


    public DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    public boolean validateFields() {
        if (this.mAddSymptoms.getText().toString().trim().length() == 0) {
            setFocus(this.mAddSymptoms, getResources().getString(R.string.enter_symptoms));
            return false;
        } else if (this.mSolutions.getText().toString().trim().length() == 0) {
            setFocus(this.mSolutions, getResources().getString(R.string.enter_solutions));
            return false;
        } else if (this.mPreferredDoctor.getText().toString().trim().length() == 0) {
            setFocus(this.mPreferredDoctor, getResources().getString(R.string.enter_preferred_doctor));
            return false;
        } else {
            return true;
        }
    }

    private void setFocus(TextInputEditText editText, String string) {
        editText.requestFocus();
        editText.setError(Html.fromHtml("<font color='red'>" + string + "</font>"));
    }


    public void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.are_you_sure_logout));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.admin.AddSymptomsActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(AddSymptomsActivity.this.getApplicationContext(), AddSymptomsActivity.this.getResources().getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
                AddSymptomsActivity addSymptomsActivity = AddSymptomsActivity.this;
                addSymptomsActivity.startActivity(new Intent(addSymptomsActivity, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                AddSymptomsActivity.this.finish();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: edu.communication.hemo.admin.AddSymptomsActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
