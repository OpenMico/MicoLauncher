package com.allenliu.versionchecklib.core;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.allenliu.versionchecklib.core.http.HttpHeaders;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.utils.FileHelper;

@Deprecated
/* loaded from: classes.dex */
public class VersionParams implements Parcelable {
    public static final Parcelable.Creator<VersionParams> CREATOR = new Parcelable.Creator<VersionParams>() { // from class: com.allenliu.versionchecklib.core.VersionParams.1
        /* renamed from: a */
        public VersionParams createFromParcel(Parcel parcel) {
            return new VersionParams(parcel);
        }

        /* renamed from: a */
        public VersionParams[] newArray(int i) {
            return new VersionParams[i];
        }
    };
    private String a;
    private String b;
    private HttpHeaders c;
    private long d;
    private HttpRequestMethod e;
    private HttpParams f;
    private Class<? extends VersionDialogActivity> g;
    private Class<? extends AVersionService> h;
    private boolean i;
    public boolean isForceRedownload;
    public boolean isSilentDownload;
    private String j;
    private String k;
    private String l;
    private Bundle m;
    private boolean n;
    private boolean o;
    private boolean p;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isShowDownloadingDialog() {
        return this.n;
    }

    public boolean isShowDownloadFailDialog() {
        return this.p;
    }

    public boolean isShowNotification() {
        return this.o;
    }

    public String getTitle() {
        return this.j;
    }

    public String getDownloadUrl() {
        return this.k;
    }

    public String getUpdateMsg() {
        return this.l;
    }

    public Bundle getParamBundle() {
        return this.m;
    }

    private VersionParams() {
    }

    public VersionParams(String str, String str2, HttpHeaders httpHeaders, long j, HttpRequestMethod httpRequestMethod, HttpParams httpParams, Class<? extends VersionDialogActivity> cls, boolean z, boolean z2, Class<? extends AVersionService> cls2, boolean z3, String str3, String str4, String str5, Bundle bundle) {
        this.a = str;
        this.b = str2;
        this.c = httpHeaders;
        this.d = j;
        this.e = httpRequestMethod;
        this.f = httpParams;
        this.g = cls;
        this.isForceRedownload = z;
        this.isSilentDownload = z2;
        this.h = cls2;
        this.i = z3;
        this.j = str3;
        this.k = str4;
        this.l = str5;
        this.m = bundle;
        if (this.h == null) {
            throw new RuntimeException("you must define your service which extends AVService.");
        } else if (str == null) {
            throw new RuntimeException("requestUrl is needed.");
        }
    }

    public Class<? extends AVersionService> getService() {
        return this.h;
    }

    public String getRequestUrl() {
        return this.a;
    }

    public String getDownloadAPKPath() {
        return this.b;
    }

    public HttpHeaders getHttpHeaders() {
        return this.c;
    }

    public long getPauseRequestTime() {
        return this.d;
    }

    public HttpRequestMethod getRequestMethod() {
        return this.e;
    }

    public HttpParams getRequestParams() {
        return this.f;
    }

    public Class getCustomDownloadActivityClass() {
        return this.g;
    }

    public boolean isForceRedownload() {
        return this.isForceRedownload;
    }

    public boolean isSilentDownload() {
        return this.isSilentDownload;
    }

    public boolean isOnlyDownload() {
        return this.i;
    }

    public void setParamBundle(Bundle bundle) {
        this.m = bundle;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        VersionParams a = new VersionParams();

        public Builder() {
            this.a.b = FileHelper.getDownloadApkCachePath();
            this.a.d = 30000L;
            this.a.e = HttpRequestMethod.GET;
            this.a.g = VersionDialogActivity.class;
            VersionParams versionParams = this.a;
            versionParams.isForceRedownload = false;
            versionParams.isSilentDownload = false;
            versionParams.i = false;
            this.a.p = true;
            this.a.h = MyService.class;
            this.a.o = true;
            this.a.n = true;
        }

        public Builder setParamBundle(Bundle bundle) {
            this.a.m = bundle;
            return this;
        }

        public Builder setDownloadUrl(String str) {
            this.a.k = str;
            return this;
        }

        public Builder setTitle(String str) {
            this.a.j = str;
            return this;
        }

        public Builder setUpdateMsg(String str) {
            this.a.l = str;
            return this;
        }

        public Builder setOnlyDownload(boolean z) {
            this.a.i = z;
            return this;
        }

        public Builder setRequestUrl(String str) {
            this.a.a = str;
            return this;
        }

        public Builder setDownloadAPKPath(String str) {
            this.a.b = str;
            return this;
        }

        public Builder setHttpHeaders(HttpHeaders httpHeaders) {
            this.a.c = httpHeaders;
            return this;
        }

        public Builder setPauseRequestTime(long j) {
            this.a.d = j;
            return this;
        }

        public Builder setRequestMethod(HttpRequestMethod httpRequestMethod) {
            this.a.e = httpRequestMethod;
            return this;
        }

        public Builder setRequestParams(HttpParams httpParams) {
            this.a.f = httpParams;
            return this;
        }

        public Builder setCustomDownloadActivityClass(Class cls) {
            this.a.g = cls;
            return this;
        }

        public Builder setForceRedownload(boolean z) {
            this.a.isForceRedownload = z;
            return this;
        }

        public Builder setSilentDownload(boolean z) {
            this.a.isSilentDownload = z;
            return this;
        }

        public Builder setService(Class<? extends AVersionService> cls) {
            this.a.h = cls;
            return this;
        }

        public Builder setShowDownloadingDialog(boolean z) {
            this.a.n = z;
            return this;
        }

        public Builder setShowNotification(boolean z) {
            this.a.o = z;
            return this;
        }

        public Builder setShowDownLoadFailDialog(boolean z) {
            this.a.p = z;
            return this;
        }

        public VersionParams build() {
            return this.a;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeSerializable(this.c);
        parcel.writeLong(this.d);
        HttpRequestMethod httpRequestMethod = this.e;
        parcel.writeInt(httpRequestMethod == null ? -1 : httpRequestMethod.ordinal());
        parcel.writeSerializable(this.f);
        parcel.writeSerializable(this.g);
        parcel.writeByte(this.isForceRedownload ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isSilentDownload ? (byte) 1 : (byte) 0);
        parcel.writeSerializable(this.h);
        parcel.writeByte(this.i ? (byte) 1 : (byte) 0);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeBundle(this.m);
        parcel.writeByte(this.n ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.o ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.p ? (byte) 1 : (byte) 0);
    }

    protected VersionParams(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = (HttpHeaders) parcel.readSerializable();
        this.d = parcel.readLong();
        int readInt = parcel.readInt();
        this.e = readInt == -1 ? null : HttpRequestMethod.values()[readInt];
        this.f = (HttpParams) parcel.readSerializable();
        this.g = (Class) parcel.readSerializable();
        boolean z = true;
        this.isForceRedownload = parcel.readByte() != 0;
        this.isSilentDownload = parcel.readByte() != 0;
        this.h = (Class) parcel.readSerializable();
        this.i = parcel.readByte() != 0;
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.l = parcel.readString();
        this.m = parcel.readBundle();
        this.n = parcel.readByte() != 0;
        this.o = parcel.readByte() != 0;
        this.p = parcel.readByte() == 0 ? false : z;
    }
}
