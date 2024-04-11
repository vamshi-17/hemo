package edu.communication.hemo.patient.model;

import android.os.Parcel;
import android.os.Parcelable;
public class PatientDetails implements Parcelable {
    private String mAge;
    private String mBloodGroup;
    private String mEmail;
    private String mGender;
    private String mMobileNumber;
    private String mPassword;
    private String name;

    public PatientDetails(Parcel in) {
        this.name = in.readString();
        this.mAge = in.readString();
        this.mBloodGroup = in.readString();
        this.mEmail = in.readString();
        this.mMobileNumber = in.readString();
        this.mGender = in.readString();
        this.mPassword = in.readString();
    }

    public PatientDetails() {
    }

    public static final Creator<PatientDetails> CREATOR = new Creator<PatientDetails>() {
        @Override
        public PatientDetails createFromParcel(Parcel in) {
            return new PatientDetails(in);
        }

        @Override
        public PatientDetails[] newArray(int size) {
            return new PatientDetails[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmAge() {
        return this.mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }

    public String getmBloodGroup() {
        return this.mBloodGroup;
    }

    public void setmBloodGroup(String mBloodGroup) {
        this.mBloodGroup = mBloodGroup;
    }

    public String getmEmail() {
        return this.mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmMobileNumber() {
        return this.mMobileNumber;
    }

    public void setmMobileNumber(String mMobileNumber) {
        this.mMobileNumber = mMobileNumber;
    }

    public String getmGender() {
        return this.mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmPassword() {
        return this.mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.mAge);
        dest.writeString(this.mBloodGroup);
        dest.writeString(this.mEmail);
        dest.writeString(this.mMobileNumber);
        dest.writeString(this.mGender);
        dest.writeString(this.mPassword);
    }
}
