package com.xiaomi.accountsdk.service;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DeviceInfoResult implements Parcelable {
    public static final String BUNDLE_KEY_ANDROID_ID = "android_id";
    public static final String BUNDLE_KEY_HASHED_DEVICE_ID = "hashed_device_id";
    public static final Parcelable.Creator<DeviceInfoResult> CREATOR = new Parcelable.Creator<DeviceInfoResult>() { // from class: com.xiaomi.accountsdk.service.DeviceInfoResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceInfoResult createFromParcel(Parcel parcel) {
            return new DeviceInfoResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceInfoResult[] newArray(int i) {
            return new DeviceInfoResult[i];
        }
    };
    public static final int FLAG_ANDROID_ID = 2;
    public static final int FLAG_HASHED_DEVICE_ID = 1;
    private static final String PARCEL_BUNDLE_KEY_DEVICE_INFO = "device_info";
    private static final String PARCEL_BUNDLE_KEY_ERROR_CODE = "error_code";
    private static final String PARCEL_BUNDLE_KEY_ERROR_MESSAGE = "error_message";
    private static final String PARCEL_BUNDLE_KEY_ERROR_STACK_TRACE = "stacktrace";
    public final Bundle deviceInfo;
    public final ErrorCode errorCode;
    public final String errorMessage;
    public final String errorStackTrace;

    /* loaded from: classes2.dex */
    public enum ErrorCode {
        ERROR_UNKNOWN,
        ERROR_NONE,
        ERROR_APP_PERMISSION_FORBIDDEN,
        ERROR_TIME_OUT,
        ERROR_NOT_SUPPORTED,
        ERROR_EXECUTION_EXCEPTION,
        ERROR_QUERY_TOO_FREQUENTLY
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DeviceInfoResult(Builder builder) {
        this.deviceInfo = builder.deviceInfo;
        this.errorMessage = builder.errorMessage;
        this.errorCode = builder.errorCode;
        this.errorStackTrace = builder.errorStackTrace;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceInfoResult)) {
            return false;
        }
        DeviceInfoResult deviceInfoResult = (DeviceInfoResult) obj;
        Bundle bundle = this.deviceInfo;
        if (bundle == null ? deviceInfoResult.deviceInfo != null : !bundle.equals(deviceInfoResult.deviceInfo)) {
            return false;
        }
        if (this.errorCode != deviceInfoResult.errorCode) {
            return false;
        }
        String str = this.errorMessage;
        if (str == null ? deviceInfoResult.errorMessage != null : !str.equals(deviceInfoResult.errorMessage)) {
            return false;
        }
        String str2 = this.errorStackTrace;
        return str2 == null ? deviceInfoResult.errorStackTrace == null : str2.equals(deviceInfoResult.errorStackTrace);
    }

    public int hashCode() {
        Bundle bundle = this.deviceInfo;
        int i = 0;
        int hashCode = (bundle != null ? bundle.hashCode() : 0) * 31;
        ErrorCode errorCode = this.errorCode;
        int hashCode2 = (hashCode + (errorCode != null ? errorCode.hashCode() : 0)) * 31;
        String str = this.errorMessage;
        int hashCode3 = (hashCode2 + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.errorStackTrace;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode3 + i;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final Bundle deviceInfo;
        private ErrorCode errorCode = ErrorCode.ERROR_NONE;
        private String errorMessage;
        private String errorStackTrace;

        public Builder(Bundle bundle) {
            this.deviceInfo = bundle;
        }

        public Builder errorMessage(String str) {
            this.errorMessage = str;
            return this;
        }

        public Builder errorStackTrace(String str) {
            this.errorStackTrace = str;
            return this;
        }

        public Builder errorCode(ErrorCode errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public static Builder copyFrom(DeviceInfoResult deviceInfoResult) {
            return new Builder(deviceInfoResult.deviceInfo).errorCode(deviceInfoResult.errorCode).errorMessage(deviceInfoResult.errorMessage).errorStackTrace(deviceInfoResult.errorStackTrace);
        }

        public DeviceInfoResult build() {
            return new DeviceInfoResult(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putBundle(PARCEL_BUNDLE_KEY_DEVICE_INFO, this.deviceInfo);
        ErrorCode errorCode = this.errorCode;
        bundle.putInt(PARCEL_BUNDLE_KEY_ERROR_CODE, errorCode == null ? -1 : errorCode.ordinal());
        bundle.putString("error_message", this.errorMessage);
        bundle.putString(PARCEL_BUNDLE_KEY_ERROR_STACK_TRACE, this.errorStackTrace);
        parcel.writeBundle(bundle);
    }

    protected DeviceInfoResult(Parcel parcel) {
        Bundle readBundle = parcel.readBundle(DeviceInfoResult.class.getClassLoader());
        this.deviceInfo = readBundle.getBundle(PARCEL_BUNDLE_KEY_DEVICE_INFO);
        int i = readBundle.getInt(PARCEL_BUNDLE_KEY_ERROR_CODE);
        this.errorCode = i == -1 ? null : ErrorCode.values()[i];
        this.errorMessage = readBundle.getString("error_message");
        this.errorStackTrace = readBundle.getString(PARCEL_BUNDLE_KEY_ERROR_STACK_TRACE);
    }
}
