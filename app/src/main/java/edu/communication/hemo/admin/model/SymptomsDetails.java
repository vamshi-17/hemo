package edu.communication.hemo.admin.model;

import android.os.Parcel;
import android.os.Parcelable;
public class SymptomsDetails implements Parcelable {

    private String mPreferredDoctorType;
    private String mSolution;
    private String mSymptom;

    public static final Creator<SymptomsDetails> CREATOR = new Creator<SymptomsDetails>() {
        @Override
        public SymptomsDetails createFromParcel(Parcel in) {
            return new SymptomsDetails(in);
        }

        @Override
        public SymptomsDetails[] newArray(int size) {
            return new SymptomsDetails[size];
        }
    };

    public String getmPreferredDoctorType() {
        return this.mPreferredDoctorType;
    }

    public void setmPreferredDoctorType(String mPreferredDoctorType) {
        this.mPreferredDoctorType = mPreferredDoctorType;
    }

    protected SymptomsDetails(Parcel in) {
        this.mSymptom = in.readString();
        this.mSolution = in.readString();
        this.mPreferredDoctorType = in.readString();
    }

    public SymptomsDetails() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSymptom);
        dest.writeString(this.mSolution);
        dest.writeString(this.mPreferredDoctorType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getmSymptom() {
        return this.mSymptom;
    }

    public void setmSymptom(String mSymptom) {
        this.mSymptom = mSymptom;
    }

    public String getmSolution() {
        return this.mSolution;
    }

    public void setmSolution(String mSolution) {
        this.mSolution = mSolution;
    }
}
