package edu.communication.hemo.patient;

import android.os.Parcel;
import android.os.Parcelable;
public class BloodPlasmaRequestDetails implements Parcelable {

    private String hospAddress;
    private String hospCode;
    private String hospEmail;
    private String hospName;
    private String isRequestAccepted;
    private String noOfUnits;
    private String patientAge;
    private String patientBloodGroup;
    private String patientEmail;
    private String patientGender;
    private String patientMobile;
    private String patientName;
    private String purpose;
    private String requestFor;
    private String requestType;
    private String requiredDate;
    private String uId;

    public static final Creator<BloodPlasmaRequestDetails> CREATOR = new Creator<BloodPlasmaRequestDetails>() {
        @Override
        public BloodPlasmaRequestDetails createFromParcel(Parcel in) {
            return new BloodPlasmaRequestDetails(in);
        }

        @Override
        public BloodPlasmaRequestDetails[] newArray(int size) {
            return new BloodPlasmaRequestDetails[size];
        }
    };

    public String getuId() {
        return this.uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    protected BloodPlasmaRequestDetails(Parcel in) {
        this.hospCode = in.readString();
        this.hospName = in.readString();
        this.hospEmail = in.readString();
        this.hospAddress = in.readString();
        this.patientName = in.readString();
        this.patientEmail = in.readString();
        this.patientMobile = in.readString();
        this.patientAge = in.readString();
        this.patientGender = in.readString();
        this.patientBloodGroup = in.readString();
        this.requestType = in.readString();
        this.requestFor = in.readString();
        this.noOfUnits = in.readString();
        this.requiredDate = in.readString();
        this.purpose = in.readString();
        this.isRequestAccepted = in.readString();
    }

    public BloodPlasmaRequestDetails() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hospCode);
        dest.writeString(this.hospName);
        dest.writeString(this.hospEmail);
        dest.writeString(this.hospAddress);
        dest.writeString(this.patientName);
        dest.writeString(this.patientEmail);
        dest.writeString(this.patientMobile);
        dest.writeString(this.patientAge);
        dest.writeString(this.patientGender);
        dest.writeString(this.patientBloodGroup);
        dest.writeString(this.requestType);
        dest.writeString(this.requestFor);
        dest.writeString(this.noOfUnits);
        dest.writeString(this.requiredDate);
        dest.writeString(this.purpose);
        dest.writeString(this.isRequestAccepted);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getHospCode() {
        return this.hospCode;
    }

    public void setHospCode(String hospCode) {
        this.hospCode = hospCode;
    }

    public String getHospName() {
        return this.hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getHospEmail() {
        return this.hospEmail;
    }

    public void setHospEmail(String hospEmail) {
        this.hospEmail = hospEmail;
    }

    public String getHospAddress() {
        return this.hospAddress;
    }

    public void setHospAddress(String hospAddress) {
        this.hospAddress = hospAddress;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientEmail() {
        return this.patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientMobile() {
        return this.patientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile;
    }

    public String getPatientAge() {
        return this.patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return this.patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientBloodGroup() {
        return this.patientBloodGroup;
    }

    public void setPatientBloodGroup(String patientBloodGroup) {
        this.patientBloodGroup = patientBloodGroup;
    }

    public String getRequestType() {
        return this.requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestFor() {
        return this.requestFor;
    }

    public void setRequestFor(String requestFor) {
        this.requestFor = requestFor;
    }

    public String getNoOfUnits() {
        return this.noOfUnits;
    }

    public void setNoOfUnits(String noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public String getRequiredDate() {
        return this.requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getIsRequestAccepted() {
        return this.isRequestAccepted;
    }

    public void setIsRequestAccepted(String isRequestAccepted) {
        this.isRequestAccepted = isRequestAccepted;
    }
}
