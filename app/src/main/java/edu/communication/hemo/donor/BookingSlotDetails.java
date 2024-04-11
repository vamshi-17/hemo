package edu.communication.hemo.donor;

import android.os.Parcel;
import android.os.Parcelable;
public class BookingSlotDetails implements Parcelable {

    private String date;
    private String donorAge;
    private String donorBloodGroup;
    private String donorGender;
    private String donorMail;
    private String donorMobile;
    private String donorName;
    private String hosCode;
    private String hospAdd;
    private String hospEmail;
    private String hospName;
    private String time;

    protected BookingSlotDetails(Parcel in) {
        this.hospName = in.readString();
        this.hospEmail = in.readString();
        this.hospAdd = in.readString();
        this.hosCode = in.readString();
        this.donorMail = in.readString();
        this.donorName = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.donorMobile = in.readString();
        this.donorBloodGroup = in.readString();
        this.donorAge = in.readString();
        this.donorGender = in.readString();
    }

    public BookingSlotDetails() {
    }

    public static final Creator<BookingSlotDetails> CREATOR = new Creator<BookingSlotDetails>() {
        @Override
        public BookingSlotDetails createFromParcel(Parcel in) {
            return new BookingSlotDetails(in);
        }

        @Override
        public BookingSlotDetails[] newArray(int size) {
            return new BookingSlotDetails[size];
        }
    };

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hospName);
        dest.writeString(this.hospEmail);
        dest.writeString(this.hospAdd);
        dest.writeString(this.hosCode);
        dest.writeString(this.donorMail);
        dest.writeString(this.donorName);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeString(this.donorMobile);
        dest.writeString(this.donorBloodGroup);
        dest.writeString(this.donorAge);
        dest.writeString(this.donorGender);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public String getHospAdd() {
        return this.hospAdd;
    }

    public void setHospAdd(String hospAdd) {
        this.hospAdd = hospAdd;
    }

    public String getHosCode() {
        return this.hosCode;
    }

    public void setHosCode(String hosCode) {
        this.hosCode = hosCode;
    }

    public String getDonorMail() {
        return this.donorMail;
    }

    public void setDonorMail(String donorMail) {
        this.donorMail = donorMail;
    }

    public String getDonorName() {
        return this.donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDonorMobile() {
        return this.donorMobile;
    }

    public void setDonorMobile(String donorMobile) {
        this.donorMobile = donorMobile;
    }

    public String getDonorBloodGroup() {
        return this.donorBloodGroup;
    }

    public void setDonorBloodGroup(String donorBloodGroup) {
        this.donorBloodGroup = donorBloodGroup;
    }

    public String getDonorAge() {
        return this.donorAge;
    }

    public void setDonorAge(String donorAge) {
        this.donorAge = donorAge;
    }

    public String getDonorGender() {
        return this.donorGender;
    }

    public void setDonorGender(String donorGender) {
        this.donorGender = donorGender;
    }
}
