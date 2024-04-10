package edu.communication.hemo.patient.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AppointmentDetails implements Parcelable {


    private String date;
    private String doctorEmail;
    private String doctorName;
    private String hospCode;
    private boolean isAccepted;
    private String patientEmail;
    private String patientMobileNumber;
    private String patientName;
    private String time;
    private String uId;

    protected AppointmentDetails(Parcel in) {
        date = in.readString();
        doctorEmail = in.readString();
        doctorName = in.readString();
        hospCode = in.readString();
        isAccepted = in.readByte() != 0;
        patientEmail = in.readString();
        patientMobileNumber = in.readString();
        patientName = in.readString();
        time = in.readString();
        uId = in.readString();
    }

    public AppointmentDetails() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(doctorEmail);
        dest.writeString(doctorName);
        dest.writeString(hospCode);
        dest.writeByte((byte) (isAccepted ? 1 : 0));
        dest.writeString(patientEmail);
        dest.writeString(patientMobileNumber);
        dest.writeString(patientName);
        dest.writeString(time);
        dest.writeString(uId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppointmentDetails> CREATOR = new Creator<AppointmentDetails>() {
        @Override
        public AppointmentDetails createFromParcel(Parcel in) {
            return new AppointmentDetails(in);
        }

        @Override
        public AppointmentDetails[] newArray(int size) {
            return new AppointmentDetails[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospCode() {
        return hospCode;
    }

    public void setHospCode(String hospCode) {
        this.hospCode = hospCode;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientMobileNumber() {
        return patientMobileNumber;
    }

    public void setPatientMobileNumber(String patientMobileNumber) {
        this.patientMobileNumber = patientMobileNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
