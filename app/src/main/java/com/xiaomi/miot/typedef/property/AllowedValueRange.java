package com.xiaomi.miot.typedef.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class AllowedValueRange implements Parcelable {
    public static final Parcelable.Creator<AllowedValueRange> CREATOR = new Parcelable.Creator<AllowedValueRange>() { // from class: com.xiaomi.miot.typedef.property.AllowedValueRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AllowedValueRange createFromParcel(Parcel parcel) {
            return new AllowedValueRange(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AllowedValueRange[] newArray(int i) {
            return new AllowedValueRange[i];
        }
    };
    private static final String TAG = "AllowedValueRange";
    private DataType dataType;
    private boolean hasStep;
    private DataValue maxValue;
    private DataValue minValue;
    private DataValue step;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AllowedValueRange(DataType dataType, Object obj, Object obj2) {
        this.minValue = dataType.createValue(obj);
        this.maxValue = dataType.createValue(obj2);
        this.hasStep = false;
        if (!dataType.validate(this.minValue, this.maxValue)) {
            Log.e(TAG, "min >= max");
        }
    }

    public AllowedValueRange(DataType dataType, Object obj, Object obj2, Object obj3) {
        this.minValue = dataType.createValue(obj);
        this.maxValue = dataType.createValue(obj2);
        this.hasStep = true;
        this.step = dataType.createValue(obj3);
        if (!dataType.validate(this.minValue, this.maxValue)) {
            Log.e(TAG, "min >= max");
        }
    }

    public DataValue getMinValue() {
        return this.minValue;
    }

    public DataValue getMaxValue() {
        return this.maxValue;
    }

    public boolean isValid(DataValue dataValue) {
        return this.dataType.validate(this.minValue, dataValue, this.maxValue);
    }

    private AllowedValueRange() {
    }

    public AllowedValueRange(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.dataType = DataType.retrieveType(parcel.readString());
        this.minValue = (DataValue) parcel.readParcelable(this.dataType.getJavaClass().getClassLoader());
        this.maxValue = (DataValue) parcel.readParcelable(this.dataType.getJavaClass().getClassLoader());
        boolean z = true;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.hasStep = z;
        if (this.hasStep) {
            this.step = (DataValue) parcel.readParcelable(this.dataType.getJavaClass().getClassLoader());
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.dataType.toString());
        parcel.writeParcelable(this.minValue, i);
        parcel.writeParcelable(this.maxValue, i);
        if (this.hasStep) {
            parcel.writeInt(1);
            parcel.writeParcelable(this.step, i);
        }
    }
}
