package edu.communication.hemo.doctor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.dpizarro.uipicker.library.picker.PickerUI;
import com.dpizarro.uipicker.library.picker.PickerUISettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import edu.communication.hemo.CommonData;
import edu.communication.hemo.R;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.doctor.model.PrescriptionDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class UploadPrescriptionActivity extends AppCompatActivity {
    private static final int REQUEST_CHOOSE_IMAGE = 3;
    private String TAG = UploadPrescriptionActivity.class.getSimpleName() + ": --> ";
    private Dialog awesomeProgressDialog;
    Button btUploadDetails;
    PickerUI consultTypePicker;
    List<String> consultationList;
    FirebaseDatabase database;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog datePickerDialog;
    DoctorDetails doctorDetails;
    TextInputEditText etConsultDate;
    TextInputEditText etConsultType;
    TextInputEditText etSuggestions;
    private Uri imgFilePath;
    ImageView ivChoosePrescription;
    Calendar myCalendar;
    DatabaseReference myRef;
    PatientDetails patientDetails;
    private Dialog progressDialog;
    private StorageReference storageRefer;
    String strProfileImage;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.storageRefer = FirebaseStorage.getInstance().getReference(CommonData.PRESCRIPTIONS);
        this.myCalendar = Calendar.getInstance();
        this.doctorDetails = (DoctorDetails) getIntent().getExtras().getParcelable("doctorDetails");
        this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
        setContentView(R.layout.activity_upload_prescription);
        this.etConsultDate = (TextInputEditText) findViewById(R.id.date_et);
        this.etConsultType = (TextInputEditText) findViewById(R.id.consult_type_et);
        this.etSuggestions = (TextInputEditText) findViewById(R.id.suggestions_et);
        this.ivChoosePrescription = (ImageView) findViewById(R.id.iv_choose_image);
        this.btUploadDetails = (Button) findViewById(R.id.bt_upload_details);
        this.consultTypePicker = (PickerUI) findViewById(R.id.consult_picker);
        this.consultationList = Arrays.asList(getResources().getStringArray(R.array.consult_type));
        this.ivChoosePrescription.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EasyImage.openChooserWithGallery(UploadPrescriptionActivity.this, "Choose Picture", 3);
            }
        });
        this.etConsultDate.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UploadPrescriptionActivity uploadPrescriptionActivity = UploadPrescriptionActivity.this;
                uploadPrescriptionActivity.datePickerDialog = uploadPrescriptionActivity.createDatePickerDialog();
                UploadPrescriptionActivity.this.datePickerDialog.show();
            }
        });
        this.etConsultType.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UploadPrescriptionActivity.this.consultTypePicker.setVisibility(View.VISIBLE);
                PickerUISettings pickerUISettings = new PickerUISettings.Builder().withItems(UploadPrescriptionActivity.this.consultationList).withAutoDismiss(true).withItemsClickables(false).withUseBlur(false).build();
                UploadPrescriptionActivity.this.consultTypePicker.setSettings(pickerUISettings);
                UploadPrescriptionActivity.this.consultTypePicker.slide();
            }
        });
        this.date = new DatePickerDialog.OnDateSetListener() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.4
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("onDateSet()", "arg0 = [" + view + "], year = [" + year + "], monthOfYear = [" + monthOfYear + "], dayOfMonth = [" + dayOfMonth + "]");
                UploadPrescriptionActivity.this.myCalendar.set(1, year);
                UploadPrescriptionActivity.this.myCalendar.set(2, monthOfYear);
                UploadPrescriptionActivity.this.myCalendar.set(5, dayOfMonth);
                UploadPrescriptionActivity.this.updateLabel();
            }
        };
        this.consultTypePicker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.5
            @Override // com.dpizarro.uipicker.library.picker.PickerUI.PickerUIItemClickListener
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(UploadPrescriptionActivity.this, valueResult, Toast.LENGTH_SHORT).show();
                UploadPrescriptionActivity.this.etConsultType.setText(valueResult);
                UploadPrescriptionActivity.this.etConsultType.setError(null);
            }
        });
        this.btUploadDetails.setOnClickListener(new AnonymousClass6());
    }


    class AnonymousClass6 implements View.OnClickListener {
        AnonymousClass6() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (UploadPrescriptionActivity.this.validateFields()) {
                UploadPrescriptionActivity uploadPrescriptionActivity = UploadPrescriptionActivity.this;
                uploadPrescriptionActivity.myRef = uploadPrescriptionActivity.fireBaseInitialSetup();
                final String encodedEmail = Utils.EncodeString(UploadPrescriptionActivity.this.patientDetails.getmEmail());
                final PrescriptionDetails prescriptionDetails = new PrescriptionDetails();
                prescriptionDetails.setPatientName(UploadPrescriptionActivity.this.patientDetails.getName());
                prescriptionDetails.setPatientMobileNumber(UploadPrescriptionActivity.this.patientDetails.getmMobileNumber());
                prescriptionDetails.setPatientEmail(Utils.EncodeString(UploadPrescriptionActivity.this.patientDetails.getmEmail()));
                prescriptionDetails.setDoctorEmail(Utils.EncodeString(UploadPrescriptionActivity.this.doctorDetails.getmEmail()));
                prescriptionDetails.setDoctorName(UploadPrescriptionActivity.this.doctorDetails.getmName());
                prescriptionDetails.setConsultType(UploadPrescriptionActivity.this.etConsultType.getText().toString());
                prescriptionDetails.setConsultDate(UploadPrescriptionActivity.this.etConsultDate.getText().toString());
                prescriptionDetails.setSuggestions(UploadPrescriptionActivity.this.etSuggestions.getText().toString());
                prescriptionDetails.setPrescriptionImageURL(UploadPrescriptionActivity.this.strProfileImage);
                try {
                    UploadPrescriptionActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(UploadPrescriptionActivity.this).setMessage(R.string.uploading_prescription_details).show();
                    UploadPrescriptionActivity.this.myRef.child(CommonData.PRESCRIPTIONS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.6.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                UploadPrescriptionActivity.this.myRef.child(CommonData.PRESCRIPTIONS).child(encodedEmail).push().setValue(prescriptionDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.6.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            UploadPrescriptionActivity.this.awesomeProgressDialog.dismiss();
                                            Utils.showSuccessDialogue(UploadPrescriptionActivity.this, "Patient prescription added successfully ", true);
                                        }
                                    }
                                });
                            } else {
                                UploadPrescriptionActivity.this.myRef.child(CommonData.PRESCRIPTIONS).child(encodedEmail).push().setValue(prescriptionDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.6.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            UploadPrescriptionActivity.this.awesomeProgressDialog.dismiss();
                                            Utils.showSuccessDialogue(UploadPrescriptionActivity.this, "Patient  prescription added successfully", true);
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            UploadPrescriptionActivity.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(UploadPrescriptionActivity.this, "Something went wrong. Please try again..!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    UploadPrescriptionActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(UploadPrescriptionActivity.this, "Unable to process request. Please try again..!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public boolean validateFields() {
        if (this.etConsultDate.getText().toString().trim().length() == 0) {
            Utils.showToast(this, getResources().getString(R.string.choose_date));
            return false;
        } else if (this.etConsultType.getText().toString().trim().length() == 0) {
            Utils.showToast(this, getResources().getString(R.string.choose_consult_type));
            return false;
        } else if (this.etSuggestions.getText().toString().trim().length() == 0) {
            setFocus(this.etSuggestions, getResources().getString(R.string.enter_suggestions));
            return false;
        } else if (this.imgFilePath == null) {
            Utils.showToast(this, getResources().getString(R.string.choose_prescription));
            return false;
        } else {
            return true;
        }
    }

    private void setFocus(TextInputEditText editText, String string) {
        editText.requestFocus();
        editText.setError(Html.fromHtml("<font color='red'>" + string + "</font>"));
    }


    public void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        this.etConsultDate.setText(sdf.format(this.myCalendar.getTime()));
    }


    public DatePickerDialog createDatePickerDialog() {
        this.datePickerDialog = new DatePickerDialog(this, this.date, this.myCalendar.get(1), this.myCalendar.get(2), this.myCalendar.get(5));
        this.datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        return this.datePickerDialog;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.7
            @Override // pl.aprilapps.easyphotopicker.EasyImage.Callbacks
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Intent intent = CropImage.activity(Uri.fromFile(imageFile)).getIntent(UploadPrescriptionActivity.this);
                UploadPrescriptionActivity.this.startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }

            @Override // pl.aprilapps.easyphotopicker.DefaultCallback, pl.aprilapps.easyphotopicker.EasyImage.Callbacks
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Utils.showToast(UploadPrescriptionActivity.this, e.getMessage());
            }

            @Override // pl.aprilapps.easyphotopicker.DefaultCallback, pl.aprilapps.easyphotopicker.EasyImage.Callbacks
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
            }
        });
        if (requestCode == 203) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) {
                this.imgFilePath = result.getUri();
                Uri uri = this.imgFilePath;
                if (uri != null) {
                    prescriptionUpload(uri);
                } else {
                    Utils.showToast(this, "Something Went Wrong...!");
                }
            } else if (resultCode == 204) {
                Exception error = result.getError();
                Utils.showToast(this, error.toString());
            }
        }
    }

    private void prescriptionUpload(Uri imgFilePath) {
        if (imgFilePath != null) {
            StorageReference storageReference = this.storageRefer.child(imgFilePath.getLastPathSegment());
            this.progressDialog = new AwesomeProgressDialog(this).setMessage(R.string.uploading_image).show();
            storageReference.putFile(imgFilePath).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<UploadTask.TaskSnapshot>() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.10
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.10.1
                        @Override
                        public void onSuccess(Uri downloadUrl) {
                            UploadPrescriptionActivity.this.strProfileImage = downloadUrl.toString();
                            String str = UploadPrescriptionActivity.this.TAG;
                            Log.d(str, "onSuccess: " + UploadPrescriptionActivity.this.strProfileImage);
                            Glide.with((FragmentActivity) UploadPrescriptionActivity.this).load(UploadPrescriptionActivity.this.strProfileImage).into(UploadPrescriptionActivity.this.ivChoosePrescription);
                            Log.d(UploadPrescriptionActivity.this.TAG, "onSuccess: ");
                            UploadPrescriptionActivity.this.progressDialog.dismiss();
                            Utils.showToast(UploadPrescriptionActivity.this, "Uploaded Successfully");
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.9
                @Override // com.google.android.gms.tasks.OnFailureListener
                public void onFailure(Exception e) {
                    String str = UploadPrescriptionActivity.this.TAG;
                    Log.d(str, "onFailure: " + e.getMessage());
                    UploadPrescriptionActivity.this.progressDialog.dismiss();
                }
            }).addOnProgressListener((OnProgressListener) new OnProgressListener<UploadTask.TaskSnapshot>() { // from class: edu.communication.hemo.doctor.UploadPrescriptionActivity.8
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double pr = (taskSnapshot.getBytesTransferred() * 100.0d) / taskSnapshot.getTotalByteCount();
                    String str = UploadPrescriptionActivity.this.TAG;
                    Log.d(str, "onProgress: Status" + pr);
                    int i = (int) pr;
                }
            });
        }
    }


    public DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }
}
