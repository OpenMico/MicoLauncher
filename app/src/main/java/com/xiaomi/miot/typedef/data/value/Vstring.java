package com.xiaomi.miot.typedef.data.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class Vstring extends DataValue {
    public static final Parcelable.Creator<Vstring> CREATOR = new Parcelable.Creator<Vstring>() { // from class: com.xiaomi.miot.typedef.data.value.Vstring.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vstring createFromParcel(Parcel parcel) {
            return new Vstring(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Vstring[] newArray(int i) {
            return new Vstring[i];
        }
    };
    private static final String TAG = "Vstring";
    private String value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Vstring() {
        this.value = "";
    }

    public Vstring(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }

    @Override // com.xiaomi.miot.typedef.data.DataValue
    public Object getObjectValue() {
        return this.value;
    }

    public static Vstring valueOf(Object obj) {
        Log.d(TAG, "valueOf: " + obj);
        if (obj instanceof String) {
            return new Vstring((String) obj);
        }
        return new Vstring(obj.toString());
    }

    public String toString() {
        return this.value;
    }

    public Vstring(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.value = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.value);
    }
}
