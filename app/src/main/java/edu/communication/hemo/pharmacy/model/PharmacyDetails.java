package edu.communication.hemo.pharmacy.model;

import android.os.Parcel;
import android.os.Parcelable;
public class PharmacyDetails implements Parcelable {

    private String mAddress;
    private String mEmail;
    private String mMobileNumber;
    private String mPassword;
    private String name;

    public static final Creator<PharmacyDetails> CREATOR = new Creator<PharmacyDetails>() {
        @Override
        public PharmacyDetails createFromParcel(Parcel in) {
            return new PharmacyDetails(in);
        }

        @Override
        public PharmacyDetails[] newArray(int size) {
            return new PharmacyDetails[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getmAddress() {
        return this.mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPassword() {
        return this.mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public PharmacyDetails() {
    }

    protected PharmacyDetails(Parcel in) {
        this.name = in.readString();
        this.mEmail = in.readString();
        this.mMobileNumber = in.readString();
        this.mAddress = in.readString();
        this.mPassword = in.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.mEmail);
        dest.writeString(this.mMobileNumber);
        dest.writeString(this.mAddress);
        dest.writeString(this.mPassword);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
