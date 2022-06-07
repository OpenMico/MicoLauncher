package com.xiaomi.miplay.mylibrary.session.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class AppMetaData implements Parcelable {
    public static final Parcelable.Creator<AppMetaData> CREATOR = new Parcelable.Creator<AppMetaData>() { // from class: com.xiaomi.miplay.mylibrary.session.data.AppMetaData.1
        /* renamed from: a */
        public AppMetaData createFromParcel(Parcel parcel) {
            return new AppMetaData(parcel);
        }

        /* renamed from: a */
        public AppMetaData[] newArray(int i) {
            return new AppMetaData[i];
        }
    };
    @NonNull
    private final String a;
    private final int b;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AppMetaData(@NonNull String str, int i) {
        this.a = str;
        this.b = i;
    }

    @NonNull
    public String getPackageName() {
        return this.a;
    }

    public int getUid() {
        return this.b;
    }

    protected AppMetaData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
    }
}
