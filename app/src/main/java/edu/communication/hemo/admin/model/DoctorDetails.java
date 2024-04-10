package edu.communication.hemo.admin.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DoctorDetails implements Parcelable {

    private String mAge;
    private String mCertification;
    private String mEmail;
    private String mGender;
    private String mHospital;
    private String mMobileNumber;
    private String mName;
    private String mPassword;
    private String mSpecialization;

    public DoctorDetails(Parcel in) {
        this.mName = in.readString();
        this.mAge = in.readString();
        this.mSpecialization = in.readString();
        this.mCertification = in.readString();
        this.mHospital = in.readString();
        this.mGender = in.readString();
        this.mEmail = in.readString();
        this.mPassword = in.readString();
        this.mMobileNumber = in.readString();
    }

    public DoctorDetails() {
    }

    public static final Creator<DoctorDetails> CREATOR = new Creator<DoctorDetails>() {
        @Override
        public DoctorDetails createFromParcel(Parcel in) {
            return new DoctorDetails(in);
        }

        @Override
        public DoctorDetails[] newArray(int size) {
            return new DoctorDetails[size];
        }
    };

    public String getmName() {
        return this.mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAge() {
        return this.mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }

    public String getmSpecialization() {
        return this.mSpecialization;
    }

    public void setmSpecialization(String mSpecialization) {
        this.mSpecialization = mSpecialization;
    }

    public String getmCertification() {
        return this.mCertification;
    }

    public void setmCertification(String mCertification) {
        this.mCertification = mCertification;
    }

    public String getmHospital() {
        return this.mHospital;
    }

    public void setmHospital(String mHospital) {
        this.mHospital = mHospital;
    }

    public String getmGender() {
        return this.mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmEmail() {
        return this.mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return this.mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmMobileNumber() {
        return this.mMobileNumber;
    }

    public void setmMobileNumber(String mMobileNumber) {
        this.mMobileNumber = mMobileNumber;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mAge);
        dest.writeString(this.mSpecialization);
        dest.writeString(this.mCertification);
        dest.writeString(this.mHospital);
        dest.writeString(this.mGender);
        dest.writeString(this.mEmail);
        dest.writeString(this.mPassword);
        dest.writeString(this.mMobileNumber);
    }
}
