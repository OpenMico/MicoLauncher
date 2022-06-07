package com.xiaomi.miot.typedef.data.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class Vuint64 extends DataValue {
    public static final Parcelable.Creator<Vuint64> CREATOR = new Parcelable.Creator<Vuint64>() { // from class: com.xiaomi.miot.typedef.data.value.Vuint64.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vuint64 createFromParcel(Parcel parcel) {
            return new Vuint64(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vuint64[] newArray(int i) {
            return new Vuint64[i];
        }
    };
    private static final String TAG = "Vuint64";
    private long value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Vuint64() {
        this.value = 0L;
    }

    public Vuint64(int i) {
        this.value = i;
    }

    public Vuint64(long j) {
        this.value = j;
    }

    public long getValue() {
        return this.value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean lessThan(DataValue dataValue) {
        return dataValue.getClass() == getClass() && this.value < ((Vuint64) dataValue).value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean validate(DataValue dataValue, DataValue dataValue2) {
        if (dataValue.getClass() != getClass() || dataValue2.getClass() != getClass()) {
            return false;
        }
        Vuint64 vuint64 = (Vuint64) dataValue2;
        long j = this.value;
        return (j < ((Vuint64) dataValue).value || j > vuint64.value) ? false : false;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public Object getObjectValue() {
        return Long.valueOf(this.value);
    }

    public static Vuint64 valueOf(Object obj) {
        Log.d(TAG, "valueOf: " + obj);
        if (obj instanceof Integer) {
            return new Vuint64(((Integer) obj).intValue());
        }
        if (obj instanceof Long) {
            return new Vuint64(((Long) obj).longValue());
        }
        return null;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public Vuint64(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.value = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.value);
    }
}
