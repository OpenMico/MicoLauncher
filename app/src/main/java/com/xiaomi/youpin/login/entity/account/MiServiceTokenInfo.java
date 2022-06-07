package com.xiaomi.youpin.login.entity.account;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class MiServiceTokenInfo implements Parcelable {
    public static final Parcelable.Creator<MiServiceTokenInfo> CREATOR = new Parcelable.Creator<MiServiceTokenInfo>() { // from class: com.xiaomi.youpin.login.entity.account.MiServiceTokenInfo.1
        /* renamed from: a */
        public MiServiceTokenInfo createFromParcel(Parcel parcel) {
            return new MiServiceTokenInfo(parcel);
        }

        /* renamed from: a */
        public MiServiceTokenInfo[] newArray(int i) {
            return new MiServiceTokenInfo[i];
        }
    };
    public String cUserId;
    public String domain;
    public String serviceToken;
    public String sid;
    public String ssecurity;
    public long timeDiff;
    public String userId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MiServiceTokenInfo() {
    }

    public MiServiceTokenInfo(String str, String str2, String str3, String str4, String str5, String str6, long j) {
        this.userId = str;
        this.sid = str2;
        this.cUserId = str3;
        this.serviceToken = str4;
        this.ssecurity = str5;
        this.timeDiff = j;
        this.domain = str6;
    }

    protected MiServiceTokenInfo(Parcel parcel) {
        this.userId = parcel.readString();
        this.sid = parcel.readString();
        this.cUserId = parcel.readString();
        this.serviceToken = parcel.readString();
        this.ssecurity = parcel.readString();
        this.timeDiff = parcel.readLong();
        this.domain = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.sid);
        parcel.writeString(this.cUserId);
        parcel.writeString(this.serviceToken);
        parcel.writeString(this.ssecurity);
        parcel.writeLong(this.timeDiff);
        parcel.writeString(this.domain);
    }

    public String getUserId() {
        return this.userId;
    }

    public String toString() {
        return "MiServiceTokenInfo{sid='" + this.sid + "', cUserId='" + this.cUserId + "', serviceToken='" + this.serviceToken + "', ssecurity='" + this.ssecurity + "', timeDiff=" + this.timeDiff + ", domain='" + this.domain + "'}";
    }
}
