package edu.communication.hemo.patient.model;

import android.os.Parcel;
import android.os.Parcelable;
public class SharePrescriptionToPharmacyDetails implements Parcelable {

    private String consultDate;
    private String consultType;
    private String doctorEmail;
    private String doctorName;
    private String patientEmail;
    private String patientMobileNumber;
    private String patientName;
    private String pharmacyEmail;
    private String prescriptionImageURL;
    private String suggestions;

    public static final Creator<SharePrescriptionToPharmacyDetails> CREATOR = new Creator<SharePrescriptionToPharmacyDetails>() {
        @Override
        public SharePrescriptionToPharmacyDetails createFromParcel(Parcel in) {
            return new SharePrescriptionToPharmacyDetails(in);
        }

        @Override
        public SharePrescriptionToPharmacyDetails[] newArray(int size) {
            return new SharePrescriptionToPharmacyDetails[size];
        }
    };

    public String getPharmacyEmail() {
        return this.pharmacyEmail;
    }

    public void setPharmacyEmail(String pharmacyEmail) {
        this.pharmacyEmail = pharmacyEmail;
    }

    public SharePrescriptionToPharmacyDetails() {
    }

    public String getConsultDate() {
        return this.consultDate;
    }

    public void setConsultDate(String consultDate) {
        this.consultDate = consultDate;
    }

    public String getConsultType() {
        return this.consultType;
    }

    public void setConsultType(String consultType) {
        this.consultType = consultType;
    }

    public String getSuggestions() {
        return this.suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getDoctorEmail() {
        return this.doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorName() {
        return this.doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientMobileNumber() {
        return this.patientMobileNumber;
    }

    public void setPatientMobileNumber(String patientMobileNumber) {
        this.patientMobileNumber = patientMobileNumber;
    }

    public String getPatientEmail() {
        return this.patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPrescriptionImageURL() {
        return this.prescriptionImageURL;
    }

    public void setPrescriptionImageURL(String prescriptionImageURL) {
        this.prescriptionImageURL = prescriptionImageURL;
    }

    protected SharePrescriptionToPharmacyDetails(Parcel in) {
        this.consultDate = in.readString();
        this.consultType = in.readString();
        this.suggestions = in.readString();
        this.doctorEmail = in.readString();
        this.doctorName = in.readString();
        this.patientName = in.readString();
        this.patientMobileNumber = in.readString();
        this.patientEmail = in.readString();
        this.prescriptionImageURL = in.readString();
        this.pharmacyEmail = in.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.consultDate);
        dest.writeString(this.consultType);
        dest.writeString(this.suggestions);
        dest.writeString(this.doctorEmail);
        dest.writeString(this.doctorName);
        dest.writeString(this.patientName);
        dest.writeString(this.patientMobileNumber);
        dest.writeString(this.patientEmail);
        dest.writeString(this.prescriptionImageURL);
        dest.writeString(this.pharmacyEmail);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
