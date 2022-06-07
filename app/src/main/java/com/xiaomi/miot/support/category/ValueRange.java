package com.xiaomi.miot.support.category;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ValueRange implements Parcelable {
    public static final Parcelable.Creator<ValueRange> CREATOR = new Parcelable.Creator<ValueRange>() { // from class: com.xiaomi.miot.support.category.ValueRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ValueRange createFromParcel(Parcel parcel) {
            return new ValueRange(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ValueRange[] newArray(int i) {
            return new ValueRange[i];
        }
    };
    private String maxValue;
    private String minValue;
    private String step;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ValueRange() {
    }

    protected ValueRange(Parcel parcel) {
        this.minValue = parcel.readString();
        this.maxValue = parcel.readString();
        this.step = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.minValue);
        parcel.writeString(this.maxValue);
        parcel.writeString(this.step);
    }

    public String getMinValue() {
        return this.minValue;
    }

    public void setMinValue(String str) {
        this.minValue = str;
    }

    public String getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(String str) {
        this.maxValue = str;
    }

    public String getStep() {
        return this.step;
    }

    public void setStep(String str) {
        this.step = str;
    }
}
