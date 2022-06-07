package com.xiaomi.smarthome.core.server.internal.account;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class RefreshServiceTokenResult implements Parcelable {
    public static final Parcelable.Creator<RefreshServiceTokenResult> CREATOR = new Parcelable.Creator<RefreshServiceTokenResult>() { // from class: com.xiaomi.smarthome.core.server.internal.account.RefreshServiceTokenResult.1
        /* renamed from: a */
        public RefreshServiceTokenResult createFromParcel(Parcel parcel) {
            return new RefreshServiceTokenResult(parcel);
        }

        /* renamed from: a */
        public RefreshServiceTokenResult[] newArray(int i) {
            return new RefreshServiceTokenResult[i];
        }
    };
    public String passToken;
    public String serviceToken;
    public String ssecurity;
    public long timeDiff;
    public String userId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RefreshServiceTokenResult() {
    }

    protected RefreshServiceTokenResult(Parcel parcel) {
        this.userId = parcel.readString();
        this.passToken = parcel.readString();
        this.serviceToken = parcel.readString();
        this.ssecurity = parcel.readString();
        this.timeDiff = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.passToken);
        parcel.writeString(this.serviceToken);
        parcel.writeString(this.ssecurity);
        parcel.writeLong(this.timeDiff);
    }
}
