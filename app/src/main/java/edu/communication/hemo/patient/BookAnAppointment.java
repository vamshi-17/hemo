package edu.communication.hemo.patient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
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
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.patient.model.AppointmentDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookAnAppointment extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    Button bookAppointment;
    FirebaseDatabase database;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog datePickerDialog;
    DoctorDetails doctorDetails;
    TextInputEditText mDate;
    TextInputEditText mTime;
    Calendar myCalendar;
    DatabaseReference myRef;
    PatientDetails patientDetails;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_an_appointment);
        this.doctorDetails = (DoctorDetails) getIntent().getExtras().getParcelable("doctorDetails");
        this.patientDetails = (PatientDetails) getIntent().getExtras().getParcelable("patientDetails");
        this.mDate = (TextInputEditText) findViewById(R.id.date_et);
        this.mTime = (TextInputEditText) findViewById(R.id.time_et);
        this.bookAppointment = (Button) findViewById(R.id.book_appointment_btn);
        this.myCalendar = Calendar.getInstance();
        this.mDate.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.BookAnAppointment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BookAnAppointment bookAnAppointment = BookAnAppointment.this;
                bookAnAppointment.datePickerDialog = bookAnAppointment.createDatePickerDialog();
                BookAnAppointment.this.datePickerDialog.show();
            }
        });
        this.mTime.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.BookAnAppointment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(11);
                int minute = mcurrentTime.get(12);
                TimePickerDialog mTimePicker = new TimePickerDialog(BookAnAppointment.this, new TimePickerDialog.OnTimeSetListener() { // from class: edu.communication.hemo.patient.BookAnAppointment.2.1
                    @Override // android.app.TimePickerDialog.OnTimeSetListener
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        TextInputEditText textInputEditText = BookAnAppointment.this.mTime;
                        textInputEditText.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        this.date = new DatePickerDialog.OnDateSetListener() { // from class: edu.communication.hemo.patient.BookAnAppointment.3
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("onDateSet()", "arg0 = [" + view + "], year = [" + year + "], monthOfYear = [" + monthOfYear + "], dayOfMonth = [" + dayOfMonth + "]");
                BookAnAppointment.this.myCalendar.set(1, year);
                BookAnAppointment.this.myCalendar.set(2, monthOfYear);
                BookAnAppointment.this.myCalendar.set(5, dayOfMonth);
                BookAnAppointment.this.updateLabel();
            }
        };
        this.bookAppointment.setOnClickListener(new AnonymousClass4());
    }


    class AnonymousClass4 implements View.OnClickListener {
        AnonymousClass4() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (BookAnAppointment.this.validateFields()) {
                BookAnAppointment bookAnAppointment = BookAnAppointment.this;
                bookAnAppointment.myRef = bookAnAppointment.fireBaseInitialSetup();
                final String encodedDoctorEmail = Utils.EncodeString(BookAnAppointment.this.doctorDetails.getmEmail());
                String encodedPatientEmail = Utils.EncodeString(BookAnAppointment.this.patientDetails.getmEmail());
                final AppointmentDetails appointmentDetails = new AppointmentDetails();
                appointmentDetails.setDate(BookAnAppointment.this.mDate.getText().toString());
                appointmentDetails.setTime(BookAnAppointment.this.mTime.getText().toString());
                appointmentDetails.setDoctorEmail(encodedDoctorEmail);
                appointmentDetails.setHospCode(BookAnAppointment.this.doctorDetails.getmHospital());
                appointmentDetails.setDoctorName(BookAnAppointment.this.doctorDetails.getmName());
                appointmentDetails.setPatientEmail(encodedPatientEmail);
                appointmentDetails.setPatientName(BookAnAppointment.this.patientDetails.getName());
                appointmentDetails.setPatientMobileNumber(BookAnAppointment.this.patientDetails.getmMobileNumber());
                try {
                    BookAnAppointment.this.awesomeProgressDialog = new AwesomeProgressDialog(BookAnAppointment.this).setMessage(R.string.book_appointment).show();
                    BookAnAppointment.this.myRef.child(CommonData.APPOINTMENTS).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.patient.BookAnAppointment.4.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                BookAnAppointment.this.myRef.child(CommonData.APPOINTMENTS).child(BookAnAppointment.this.myRef.push().getKey()).setValue(appointmentDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.patient.BookAnAppointment.4.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            BookAnAppointment.this.awesomeProgressDialog.dismiss();
                                            BookAnAppointment bookAnAppointment2 = BookAnAppointment.this;
                                            Utils.showSuccessDialogue(bookAnAppointment2, "Appointment with Doctor " + encodedDoctorEmail + " is added successfully", true);
                                        }
                                    }
                                });
                            } else {
                                BookAnAppointment.this.myRef.child(CommonData.APPOINTMENTS).child(BookAnAppointment.this.myRef.push().getKey()).setValue(appointmentDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.patient.BookAnAppointment.4.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            BookAnAppointment.this.awesomeProgressDialog.dismiss();
                                            BookAnAppointment bookAnAppointment2 = BookAnAppointment.this;
                                            Utils.showSuccessDialogue(bookAnAppointment2, "Appointment with Doctor " + encodedDoctorEmail + "is added successfully", true);
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            BookAnAppointment.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(BookAnAppointment.this, "Something went wrong. Please try again..!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    BookAnAppointment.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(BookAnAppointment.this, "Unable to process request. Please try again..!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        this.mDate.setText(sdf.format(this.myCalendar.getTime()));
    }


    public DatePickerDialog createDatePickerDialog() {
        this.datePickerDialog = new DatePickerDialog(this, this.date, this.myCalendar.get(1), this.myCalendar.get(2), this.myCalendar.get(5));
        this.datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        return this.datePickerDialog;
    }


    public DatabaseReference fireBaseInitialSetup() {
        this.database = FirebaseDatabase.getInstance(CommonData.DB_URL);
        this.myRef = this.database.getReference(CommonData.USERS);
        return this.myRef;
    }


    public boolean validateFields() {
        if (this.mDate.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please Select Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (this.mTime.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please Select Time", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
