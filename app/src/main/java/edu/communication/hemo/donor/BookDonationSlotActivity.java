package edu.communication.hemo.donor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
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
import edu.communication.hemo.custom.AwesomeProgressDialog;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.donor.model.DonorDetails;
import edu.communication.hemo.hospital.model.HospitalDetails;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookDonationSlotActivity extends AppCompatActivity {
    private Dialog awesomeProgressDialog;
    Button bookSlot;
    FirebaseDatabase database;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog datePickerDialog;
    DonorDetails donorDetails;
    TextView hospAddress;
    TextView hospCode;
    TextView hospMail;
    TextView hospName;
    HospitalDetails hospitalDetails;
    TextInputEditText mDate;
    TextInputEditText mTime;
    Calendar myCalendar;
    DatabaseReference myRef;


    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_donation_slot);
        this.hospName = (TextView) findViewById(R.id.hosp_name_et);
        this.hospAddress = (TextView) findViewById(R.id.hosp_add_et);
        this.hospCode = (TextView) findViewById(R.id.hosp_code_et);
        this.hospMail = (TextView) findViewById(R.id.hosp_mail_et);
        this.mDate = (TextInputEditText) findViewById(R.id.date_et);
        this.mTime = (TextInputEditText) findViewById(R.id.time_et);
        this.bookSlot = (Button) findViewById(R.id.book_slot_btn);
        try {
            this.hospitalDetails = (HospitalDetails) getIntent().getExtras().getParcelable("hospitalDetails");
            this.donorDetails = (DonorDetails) getIntent().getExtras().getParcelable("donorDetails");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.hospName.setText(this.hospitalDetails.getmHosName());
        this.hospCode.setText(this.hospitalDetails.getmHosCode());
        this.hospMail.setText(this.hospitalDetails.getmEmail());
        this.hospAddress.setText(this.hospitalDetails.getmAddress());
        this.myCalendar = Calendar.getInstance();
        this.mDate.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.BookDonationSlotActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BookDonationSlotActivity bookDonationSlotActivity = BookDonationSlotActivity.this;
                bookDonationSlotActivity.datePickerDialog = bookDonationSlotActivity.createDatePickerDialog();
                BookDonationSlotActivity.this.datePickerDialog.show();
            }
        });
        this.mTime.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.BookDonationSlotActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(11);
                int minute = mcurrentTime.get(12);
                TimePickerDialog mTimePicker = new TimePickerDialog(BookDonationSlotActivity.this, new TimePickerDialog.OnTimeSetListener() { // from class: edu.communication.hemo.donor.BookDonationSlotActivity.2.1
                    @Override // android.app.TimePickerDialog.OnTimeSetListener
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        TextInputEditText textInputEditText = BookDonationSlotActivity.this.mTime;
                        textInputEditText.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        this.date = new DatePickerDialog.OnDateSetListener() { // from class: edu.communication.hemo.donor.BookDonationSlotActivity.3
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("onDateSet()", "arg0 = [" + view + "], year = [" + year + "], monthOfYear = [" + monthOfYear + "], dayOfMonth = [" + dayOfMonth + "]");
                BookDonationSlotActivity.this.myCalendar.set(1, year);
                BookDonationSlotActivity.this.myCalendar.set(2, monthOfYear);
                BookDonationSlotActivity.this.myCalendar.set(5, dayOfMonth);
                BookDonationSlotActivity.this.updateLabel();
            }
        };
        this.bookSlot.setOnClickListener(new AnonymousClass4());
    }


    class AnonymousClass4 implements View.OnClickListener {
        AnonymousClass4() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (BookDonationSlotActivity.this.validateFields()) {
                BookDonationSlotActivity bookDonationSlotActivity = BookDonationSlotActivity.this;
                bookDonationSlotActivity.myRef = bookDonationSlotActivity.fireBaseInitialSetup();
                String encodedHospEmail = Utils.EncodeString(BookDonationSlotActivity.this.hospitalDetails.getmEmail());
                String encodedDonorEmail = Utils.EncodeString(BookDonationSlotActivity.this.donorDetails.getmEmail());
                final BookingSlotDetails bookingSlotDetails = new BookingSlotDetails();
                bookingSlotDetails.setDate(BookDonationSlotActivity.this.mDate.getText().toString());
                bookingSlotDetails.setTime(BookDonationSlotActivity.this.mTime.getText().toString());
                bookingSlotDetails.setHospEmail(encodedHospEmail);
                bookingSlotDetails.setHospName(BookDonationSlotActivity.this.hospitalDetails.getmHosName());
                bookingSlotDetails.setHosCode(BookDonationSlotActivity.this.hospitalDetails.getmHosCode());
                bookingSlotDetails.setHospAdd(BookDonationSlotActivity.this.hospitalDetails.getmAddress());
                bookingSlotDetails.setDonorMail(encodedDonorEmail);
                bookingSlotDetails.setDonorName(BookDonationSlotActivity.this.donorDetails.getName());
                bookingSlotDetails.setDonorAge(BookDonationSlotActivity.this.donorDetails.getmAge());
                bookingSlotDetails.setDonorBloodGroup(BookDonationSlotActivity.this.donorDetails.getmBloodGroup());
                bookingSlotDetails.setDonorMobile(BookDonationSlotActivity.this.donorDetails.getmMobileNumber());
                bookingSlotDetails.setDonorGender(BookDonationSlotActivity.this.donorDetails.getmGender());
                try {
                    BookDonationSlotActivity.this.awesomeProgressDialog = new AwesomeProgressDialog(BookDonationSlotActivity.this).setMessage(R.string.book_appointment).show();
                    BookDonationSlotActivity.this.myRef.child(CommonData.DONATION_SLOT).addListenerForSingleValueEvent(new ValueEventListener() { // from class: edu.communication.hemo.donor.BookDonationSlotActivity.4.1
                        @Override // com.google.firebase.database.ValueEventListener
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                BookDonationSlotActivity.this.myRef.child(CommonData.DONATION_SLOT).child(BookDonationSlotActivity.this.myRef.push().getKey()).setValue(bookingSlotDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.donor.BookDonationSlotActivity.4.1.1
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            BookDonationSlotActivity.this.awesomeProgressDialog.dismiss();
                                            BookDonationSlotActivity bookDonationSlotActivity2 = BookDonationSlotActivity.this;
                                            Utils.showSuccessDialogue(bookDonationSlotActivity2, "Donation Slot registered with " + BookDonationSlotActivity.this.hospitalDetails.getmHosName() + " Hospital successfully", true);
                                        }
                                    }
                                });
                            } else {
                                BookDonationSlotActivity.this.myRef.child(CommonData.DONATION_SLOT).child(BookDonationSlotActivity.this.myRef.push().getKey()).setValue(bookingSlotDetails).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: edu.communication.hemo.donor.BookDonationSlotActivity.4.1.2
                                    @Override // com.google.android.gms.tasks.OnCompleteListener
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            BookDonationSlotActivity.this.awesomeProgressDialog.dismiss();
                                            BookDonationSlotActivity bookDonationSlotActivity2 = BookDonationSlotActivity.this;
                                            Utils.showSuccessDialogue(bookDonationSlotActivity2, "Donation Slot registered with " + BookDonationSlotActivity.this.hospitalDetails.getmHosName() + " Hospital successfully", true);
                                        }
                                    }
                                });
                            }
                        }

                        @Override // com.google.firebase.database.ValueEventListener
                        public void onCancelled(DatabaseError databaseError) {
                            BookDonationSlotActivity.this.awesomeProgressDialog.dismiss();
                            Toast.makeText(BookDonationSlotActivity.this, "Something went wrong. Please try again..!!", 0).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    BookDonationSlotActivity.this.awesomeProgressDialog.dismiss();
                    Toast.makeText(BookDonationSlotActivity.this, "Unable to process request. Please try again..!!", 0).show();
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
            Toast.makeText(this, "Please Select Date", 0).show();
            return false;
        } else if (this.mTime.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please Select Time", 0).show();
            return false;
        } else {
            return true;
        }
    }
}
