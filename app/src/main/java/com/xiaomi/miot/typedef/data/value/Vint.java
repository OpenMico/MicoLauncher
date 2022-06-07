package com.xiaomi.miot.typedef.data.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class Vint extends DataValue {
    public static final Parcelable.Creator<Vint> CREATOR = new Parcelable.Creator<Vint>() { // from class: com.xiaomi.miot.typedef.data.value.Vint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vint createFromParcel(Parcel parcel) {
            return new Vint(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vint[] newArray(int i) {
            return new Vint[i];
        }
    };
    private static final String TAG = "Vint";
    private int value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Vint() {
        this.value = 0;
    }

    public Vint(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean lessThan(DataValue dataValue) {
        return dataValue.getClass() == getClass() && this.value < ((Vint) dataValue).value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean validate(DataValue dataValue, DataValue dataValue2) {
        if (dataValue.getClass() != getClass() || dataValue2.getClass() != getClass()) {
            return false;
        }
        Vint vint = (Vint) dataValue2;
        int i = this.value;
        return (i < ((Vint) dataValue).value || i > vint.value) ? false : false;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public Object getObjectValue() {
        return Integer.valueOf(this.value);
    }

    public static Vint valueOf(Object obj) {
        Log.d(TAG, "valueOf: " + obj);
        if (obj instanceof Integer) {
            return new Vint(((Integer) obj).intValue());
        }
        return null;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public Vint(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.value = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.value);
    }
}
