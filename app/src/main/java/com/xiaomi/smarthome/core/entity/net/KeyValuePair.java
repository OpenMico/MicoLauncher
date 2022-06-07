package com.xiaomi.smarthome.core.entity.net;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class KeyValuePair implements Parcelable {
    public static final Parcelable.Creator<KeyValuePair> CREATOR = new Parcelable.Creator<KeyValuePair>() { // from class: com.xiaomi.smarthome.core.entity.net.KeyValuePair.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyValuePair createFromParcel(Parcel parcel) {
            return new KeyValuePair(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyValuePair[] newArray(int i) {
            return new KeyValuePair[i];
        }
    };
    private final String key;
    private final String value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeyValuePair(String str, String str2) {
        if (str != null) {
            this.key = str;
            this.value = str2;
            return;
        }
        throw new IllegalArgumentException("Key may not be null");
    }

    protected KeyValuePair(Parcel parcel) {
        this.key = parcel.readString();
        this.value = parcel.readString();
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeString(this.value);
    }

    public String toString() {
        return this.key + Constants.COLON_SEPARATOR + this.value;
    }
}
