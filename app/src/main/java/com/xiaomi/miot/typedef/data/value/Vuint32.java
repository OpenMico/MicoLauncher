package com.xiaomi.miot.typedef.data.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class Vuint32 extends DataValue {
    public static final Parcelable.Creator<Vuint32> CREATOR = new Parcelable.Creator<Vuint32>() { // from class: com.xiaomi.miot.typedef.data.value.Vuint32.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vuint32 createFromParcel(Parcel parcel) {
            return new Vuint32(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vuint32[] newArray(int i) {
            return new Vuint32[i];
        }
    };
    private static final String TAG = "Vuint32";
    private int value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Vuint32() {
        this.value = 0;
    }

    public Vuint32(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean lessThan(DataValue dataValue) {
        return dataValue.getClass() == getClass() && this.value < ((Vuint32) dataValue).value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean validate(DataValue dataValue, DataValue dataValue2) {
        if (dataValue.getClass() != getClass() || dataValue2.getClass() != getClass()) {
            return false;
        }
        Vuint32 vuint32 = (Vuint32) dataValue2;
        int i = this.value;
        return (i < ((Vuint32) dataValue).value || i > vuint32.value) ? false : false;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public Object getObjectValue() {
        return Integer.valueOf(this.value);
    }

    public static Vuint32 valueOf(Object obj) {
        Log.d(TAG, "valueOf: " + obj);
        if (obj instanceof Integer) {
            return new Vuint32(((Integer) obj).intValue());
        }
        return null;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public Vuint32(Parcel parcel) {
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
