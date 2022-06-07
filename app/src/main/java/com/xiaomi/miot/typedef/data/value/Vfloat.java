package com.xiaomi.miot.typedef.data.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class Vfloat extends DataValue {
    public static final Parcelable.Creator<Vfloat> CREATOR = new Parcelable.Creator<Vfloat>() { // from class: com.xiaomi.miot.typedef.data.value.Vfloat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vfloat createFromParcel(Parcel parcel) {
            return new Vfloat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vfloat[] newArray(int i) {
            return new Vfloat[i];
        }
    };
    private static final String TAG = "Vfloat";
    private float value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Vfloat() {
        this.value = 0.0f;
    }

    public Vfloat(float f) {
        this.value = f;
    }

    public Vfloat(int i) {
        this.value = i;
    }

    public float getValue() {
        return this.value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean lessThan(DataValue dataValue) {
        return dataValue.getClass() == getClass() && this.value < ((Vfloat) dataValue).value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public boolean validate(DataValue dataValue, DataValue dataValue2) {
        if (dataValue.getClass() != getClass() || dataValue2.getClass() != getClass()) {
            return false;
        }
        Vfloat vfloat = (Vfloat) dataValue2;
        float f = this.value;
        return (f < ((Vfloat) dataValue).value || f > vfloat.value) ? false : false;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public Object getObjectValue() {
        return Float.valueOf(this.value);
    }

    public static Vfloat valueOf(Object obj) {
        Log.d(TAG, "valueOf: " + obj);
        if (obj instanceof Float) {
            return new Vfloat(((Float) obj).floatValue());
        }
        if (obj instanceof Integer) {
            return new Vfloat(((Integer) obj).intValue());
        }
        return null;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public Vfloat(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.value = parcel.readFloat();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.value);
    }
}
