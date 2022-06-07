package com.xiaomi.miot.typedef.data.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class Vuint8 extends DataValue {
    public static final Parcelable.Creator<Vuint8> CREATOR = new Parcelable.Creator<Vuint8>() { // from class: com.xiaomi.miot.typedef.data.value.Vuint8.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vuint8 createFromParcel(Parcel parcel) {
            return new Vuint8(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vuint8[] newArray(int i) {
            return new Vuint8[i];
        }
    };
    private static final String TAG = "Vuint8";
    private int value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Vuint8() {
        this.value = 0;
    }

    public Vuint8(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean lessThan(DataValue dataValue) {
        return dataValue.getClass() == getClass() && this.value < ((Vuint8) dataValue).value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean validate(DataValue dataValue, DataValue dataValue2) {
        if (dataValue.getClass() != getClass() || dataValue2.getClass() != getClass()) {
            return false;
        }
        Vuint8 vuint8 = (Vuint8) dataValue2;
        int i = this.value;
        return (i < ((Vuint8) dataValue).value || i > vuint8.value) ? false : false;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public Object getObjectValue() {
        return Integer.valueOf(this.value);
    }

    public static Vuint8 valueOf(Object obj) {
        Log.d(TAG, "valueOf: " + obj);
        if (obj instanceof Integer) {
            return new Vuint8(((Integer) obj).intValue());
        }
        return null;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public Vuint8(Parcel parcel) {
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
