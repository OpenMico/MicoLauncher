package com.xiaomi.miot.typedef.error;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class MiotError implements Parcelable, Comparable<MiotError> {
    private int code;
    private String message;
    public static final MiotError OK = new MiotError(0, "OK");
    public static final MiotError IOT_CANNOT_WRITE = new MiotError(-70404, "Cannot write to read only characteristic.");
    public static final MiotError IOT_CANNOT_READ = new MiotError(-70405, "Cannot read from a write only characteristic.");
    public static final MiotError IOT_CANNOT_NOTIFY = new MiotError(-70406, "Notification is not supported for characteristic.");
    public static final MiotError IOT_OUT_OF_RESOURCES = new MiotError(-70407, "Out of resources to process request.");
    public static final MiotError IOT_TIMEOUT = new MiotError(-70408, "Operation timed out.");
    public static final MiotError IOT_RESOURCE_NOT_EXIST = new MiotError(-70409, "Resource does not exist.");
    public static final MiotError IOT_INVALID_VALUE = new MiotError(-70410, "Accessory received an invalid value in a write request.");
    public static final MiotError MIOT_SPEC_V2_OPERATE_FAILED = new MiotError(1, "operate failed.");
    public static final MiotError MIOT_SPEC_V2_PROPERTY_NOT_READABLE = new MiotError(-4001, "property not readable.");
    public static final MiotError MIOT_SPEC_V2_PROPERTY_NOT_WRITABLE = new MiotError(-4002, "property not writable.");
    public static final MiotError MIOT_SPEC_V2_PROPERTY_NOT_EXIST = new MiotError(-4003, "property not exist.");
    public static final MiotError MIOT_SPEC_V2_INTERNAL_ERROR = new MiotError(-4004, "internal error.");
    public static final MiotError MIOT_SPEC_V2_PROPERY_VALUE_INVALID = new MiotError(-4005, "property value invalid.");
    public static final MiotError MIOT_SPEC_V2_ACTION_IN_PARAMS_INVALID = new MiotError(-4006, "action in params invalid.");
    public static final MiotError MIOT_SPEC_V2_DID_INVALID = new MiotError(-4007, "did invalid.");
    public static final MiotError INTERNAL = new MiotError(100, "internal error");
    public static final MiotError INTERNAL_NOT_INITIALIZED = new MiotError(101, "not initialized");
    public static final MiotError INTERNAL_INVALID_OPERATION = new MiotError(102, "INVALID OPERATION");
    public static final MiotError INTERNAL_INVALID_ARGS = new MiotError(103, "INVALID ARGS");
    public static final MiotError INTERNAL_INTERRUPTED = new MiotError(104, "interrupted");
    public static final MiotError INVALID_SESSION_ID = new MiotError(105, "INVALID sessionId");
    public static final MiotError INTERNAL_MIOT_SERVICE_DISCONNECTED = new MiotError(106, "miot service disconnected");
    public static final MiotError INTERNAL_REQUEST_TIMEOUT = new MiotError(107, "request timeout");
    public static final MiotError INTERNAL_NO_CONNECTION_ESTABLISHED = new MiotError(108, "no connection established");
    public static final MiotError INTERNAL_RESPONSE_FORMAT_ERROR = new MiotError(109, "response format error");
    public static final MiotError INTERNAL_OT_SERVICE_NOT_START = new MiotError(110, "ot service not start");
    public static final Parcelable.Creator<MiotError> CREATOR = new Parcelable.Creator<MiotError>() { // from class: com.xiaomi.miot.typedef.error.MiotError.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotError createFromParcel(Parcel parcel) {
            return new MiotError(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotError[] newArray(int i) {
            return new MiotError[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MiotError(int i, String str) {
        this.code = i;
        this.message = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String toString() {
        return String.valueOf(this.code) + ' ' + this.message;
    }

    public int hashCode() {
        return this.code;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MiotError) && this.code == ((MiotError) obj).code;
    }

    public int compareTo(MiotError miotError) {
        return this.code - miotError.code;
    }

    public MiotError() {
    }

    public MiotError(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.code = parcel.readInt();
        this.message = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.code);
        String str = this.message;
        if (str == null) {
            str = "";
        }
        parcel.writeString(str);
    }
}
