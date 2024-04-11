package edu.communication.hemo.donor.model;

import android.os.Parcel;
import android.os.Parcelable;
public class DonorDetails implements Parcelable {

    private String mAge;
    private String mBloodGroup;
    private String mEmail;
    private String mGender;
    private String mLastDonated;
    private String mMobileNumber;
    private String mPassword;
    private String name;

    public static final Creator<DonorDetails> CREATOR = new Creator<DonorDetails>() {
        @Override
        public DonorDetails createFromParcel(Parcel in) {
            return new DonorDetails(in);
        }

        @Override
        public DonorDetails[] newArray(int size) {
            return new DonorDetails[size];
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

    public String getmLastDonated() {
        return this.mLastDonated;
    }

    public void setmLastDonated(String mLastDonated) {
        this.mLastDonated = mLastDonated;
    }

    public DonorDetails() {
    }

    protected DonorDetails(Parcel in) {
        this.name = in.readString();
        this.mAge = in.readString();
        this.mBloodGroup = in.readString();
        this.mEmail = in.readString();
        this.mMobileNumber = in.readString();
        this.mGender = in.readString();
        this.mPassword = in.readString();
        this.mLastDonated = in.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.mAge);
        parcel.writeString(this.mBloodGroup);
        parcel.writeString(this.mEmail);
        parcel.writeString(this.mMobileNumber);
        parcel.writeString(this.mGender);
        parcel.writeString(this.mPassword);
        parcel.writeString(this.mLastDonated);
    }
}
