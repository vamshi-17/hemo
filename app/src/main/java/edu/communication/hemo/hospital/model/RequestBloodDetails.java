package edu.communication.hemo.hospital.model;

import android.os.Parcel;
import android.os.Parcelable;
public class RequestBloodDetails implements Parcelable {

    private String donorAge;
    private String donorBloodGroup;
    private String donorEmail;
    private String donorGender;
    private String donorMobileNumber;
    private String donorName;
    private String hospAddress;
    private String hospCode;
    private String hospEmail;
    private String hospitalName;
    private boolean isDonationAccepted;
    private String uId;

    public static final Creator<RequestBloodDetails> CREATOR = new Creator<RequestBloodDetails>() {
        @Override
        public RequestBloodDetails createFromParcel(Parcel in) {
            return new RequestBloodDetails(in);
        }

        @Override
        public RequestBloodDetails[] newArray(int size) {
            return new RequestBloodDetails[size];
        }
    };

    public String getHospAddress() {
        return this.hospAddress;
    }

    public void setHospAddress(String hospAddress) {
        this.hospAddress = hospAddress;
    }

    public boolean isDonationAccepted() {
        return this.isDonationAccepted;
    }

    public void setDonationAccepted(boolean donationAccepted) {
        this.isDonationAccepted = donationAccepted;
    }

    protected RequestBloodDetails(Parcel in) {
        this.donorName = in.readString();
        this.donorBloodGroup = in.readString();
        this.donorAge = in.readString();
        this.donorGender = in.readString();
        this.donorEmail = in.readString();
        this.donorMobileNumber = in.readString();
        this.hospitalName = in.readString();
        this.hospCode = in.readString();
        this.hospEmail = in.readString();
        this.hospEmail = in.readString();
        this.isDonationAccepted = in.readBoolean();
    }

    public RequestBloodDetails() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.donorName);
        dest.writeString(this.donorBloodGroup);
        dest.writeString(this.donorAge);
        dest.writeString(this.donorGender);
        dest.writeString(this.donorEmail);
        dest.writeString(this.donorMobileNumber);
        dest.writeString(this.hospitalName);
        dest.writeString(this.hospCode);
        dest.writeString(this.hospEmail);
        dest.writeBoolean(this.isDonationAccepted);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getDonorName() {
        return this.donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
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

    public String getDonorEmail() {
        return this.donorEmail;
    }

    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }

    public String getDonorMobileNumber() {
        return this.donorMobileNumber;
    }

    public void setDonorMobileNumber(String donorMobileNumber) {
        this.donorMobileNumber = donorMobileNumber;
    }

    public String getHospitalName() {
        return this.hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospCode() {
        return this.hospCode;
    }

    public void setHospCode(String hospCode) {
        this.hospCode = hospCode;
    }

    public String getHospEmail() {
        return this.hospEmail;
    }

    public void setHospEmail(String hospEmail) {
        this.hospEmail = hospEmail;
    }

    public boolean getIsDonationAccepted() {
        return this.isDonationAccepted;
    }

    public void setIsDonationAccepted(boolean isDonationAccepted) {
        this.isDonationAccepted = isDonationAccepted;
    }

    public String getuId() {
        return this.uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
