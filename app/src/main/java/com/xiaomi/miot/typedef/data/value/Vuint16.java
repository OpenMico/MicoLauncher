package com.xiaomi.miot.typedef.data.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class Vuint16 extends DataValue {
    public static final Parcelable.Creator<Vuint16> CREATOR = new Parcelable.Creator<Vuint16>() { // from class: com.xiaomi.miot.typedef.data.value.Vuint16.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vuint16 createFromParcel(Parcel parcel) {
            return new Vuint16(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vuint16[] newArray(int i) {
            return new Vuint16[i];
        }
    };
    private static final String TAG = "Vuint16";
    private int value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Vuint16() {
        this.value = 0;
    }

    public Vuint16(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean lessThan(DataValue dataValue) {
        return dataValue.getClass() == getClass() && this.value < ((Vuint16) dataValue).value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean validate(DataValue dataValue, DataValue dataValue2) {
        if (dataValue.getClass() != getClass() || dataValue2.getClass() != getClass()) {
            return false;
        }
        Vuint16 vuint16 = (Vuint16) dataValue2;
        int i = this.value;
        return (i < ((Vuint16) dataValue).value || i > vuint16.value) ? false : false;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public Object getObjectValue() {
        return Integer.valueOf(this.value);
    }

    public static Vuint16 valueOf(Object obj) {
        Log.d(TAG, "valueOf: " + obj);
        if (obj instanceof Integer) {
            return new Vuint16(((Integer) obj).intValue());
        }
        return null;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public Vuint16(Parcel parcel) {
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
