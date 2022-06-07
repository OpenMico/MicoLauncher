package com.xiaomi.miot.typedef.data.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public final class Vbool extends DataValue {
    public static final Parcelable.Creator<Vbool> CREATOR = new Parcelable.Creator<Vbool>() { // from class: com.xiaomi.miot.typedef.data.value.Vbool.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vbool createFromParcel(Parcel parcel) {
            return new Vbool(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vbool[] newArray(int i) {
            return new Vbool[i];
        }
    };
    private static final String TAG = "Vbool";
    private boolean value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Vbool() {
        this.value = false;
    }

    public Vbool(boolean z) {
        this.value = z;
    }

    public Vbool(int i) {
        this.value = i != 1 ? false : true;
    }

    public Vbool(String str) {
        this.value = Boolean.valueOf(str).booleanValue();
    }

    public boolean getValue() {
        return this.value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public Object getObjectValue() {
        return Boolean.valueOf(this.value);
    }

    public static Vbool valueOf(Object obj) {
        Log.d(TAG, "valueOf: " + obj);
        if (obj instanceof Boolean) {
            return new Vbool(((Boolean) obj).booleanValue());
        }
        if (obj instanceof Integer) {
            return new Vbool(((Integer) obj).intValue());
        }
        if (obj instanceof String) {
            return new Vbool((String) obj);
        }
        return null;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public Vbool(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        boolean z = true;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.value = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.value ? 1 : 0);
    }
}
