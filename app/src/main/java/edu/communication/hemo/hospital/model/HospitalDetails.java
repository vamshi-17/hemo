package edu.communication.hemo.hospital.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HospitalDetails implements Parcelable {

    private String mAddress;
    private String mEmail;
    private String mHosCode;
    private String mHosName;
    private String mPassword;

    protected HospitalDetails(Parcel in) {
        this.mHosName = in.readString();
        this.mAddress = in.readString();
        this.mEmail = in.readString();
        this.mPassword = in.readString();
        this.mHosCode = in.readString();
    }

    public HospitalDetails() {
    }

    public static final Creator<HospitalDetails> CREATOR = new Creator<HospitalDetails>() {
        @Override
        public HospitalDetails createFromParcel(Parcel in) {
            return new HospitalDetails(in);
        }

        @Override
        public HospitalDetails[] newArray(int size) {
            return new HospitalDetails[size];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mHosName);
        dest.writeString(this.mAddress);
        dest.writeString(this.mEmail);
        dest.writeString(this.mPassword);
        dest.writeString(this.mHosCode);
    }

    public String getmHosName() {
        return this.mHosName;
    }

    public void setmHosName(String mHosName) {
        this.mHosName = mHosName;
    }

    public String getmAddress() {
        return this.mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
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

    public String getmHosCode() {
        return this.mHosCode;
    }

    public void setmHosCode(String mHosCode) {
        this.mHosCode = mHosCode;
    }
}
