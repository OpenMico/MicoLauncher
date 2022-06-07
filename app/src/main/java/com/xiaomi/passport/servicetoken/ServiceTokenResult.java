package com.xiaomi.passport.servicetoken;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class ServiceTokenResult implements Parcelable {
    public static final Parcelable.Creator<ServiceTokenResult> CREATOR = new Parcelable.Creator<ServiceTokenResult>() { // from class: com.xiaomi.passport.servicetoken.ServiceTokenResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceTokenResult createFromParcel(Parcel parcel) {
            return new ServiceTokenResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceTokenResult[] newArray(int i) {
            return new ServiceTokenResult[i];
        }
    };
    private static final String PARCEL_BUNDLE_KEY_C_USER_ID = "cUserId";
    private static final String PARCEL_BUNDLE_KEY_ERROR_CODE = "errorCode";
    private static final String PARCEL_BUNDLE_KEY_ERROR_MESSAGE = "errorMessage";
    private static final String PARCEL_BUNDLE_KEY_ERROR_STACK_TRACE = "stackTrace";
    private static final String PARCEL_BUNDLE_KEY_INTENT = "intent";
    private static final String PARCEL_BUNDLE_KEY_PEEKED = "peeked";
    private static final String PARCEL_BUNDLE_KEY_PH = "ph";
    private static final String PARCEL_BUNDLE_KEY_SECURITY = "security";
    private static final String PARCEL_BUNDLE_KEY_SERVICE_TOKEN = "serviceToken";
    private static final String PARCEL_BUNDLE_KEY_SID = "sid";
    private static final String PARCEL_BUNDLE_KEY_SLH = "slh";
    private static final String PARCEL_BUNDLE_KEY_USER_ID = "userId";
    private static final String PARCEL_V2_FLAG = "V2#";
    public static final int TO_STRING_MASK_SHOW_SECURITY = 2;
    public static final int TO_STRING_MASK_SHOW_SERVICETOKEN = 1;
    public final String cUserId;
    public final ErrorCode errorCode;
    public final String errorMessage;
    public final String errorStackTrace;
    public final Intent intent;
    public final boolean peeked;
    public final String ph;
    public final String security;
    public final String serviceToken;
    public final String sid;
    public final String slh;
    private final boolean useV1Parcel;
    public final String userId;

    /* loaded from: classes4.dex */
    public enum ErrorCode {
        ERROR_UNKNOWN,
        ERROR_NONE,
        ERROR_NO_ACCOUNT,
        ERROR_APP_PERMISSION_FORBIDDEN,
        ERROR_IOERROR,
        ERROR_OLD_MIUI_ACCOUNT_MANAGER_PERMISSION_ISSUE,
        ERROR_CANCELLED,
        ERROR_AUTHENTICATOR_ERROR,
        ERROR_TIME_OUT,
        ERROR_REMOTE_EXCEPTION,
        ERROR_USER_INTERACTION_NEEDED
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ServiceTokenResult(Builder builder) {
        this.sid = builder.sid;
        this.serviceToken = builder.serviceToken;
        this.security = builder.security;
        this.errorMessage = builder.errorMessage;
        this.errorCode = builder.errorCode;
        this.intent = builder.intent;
        this.errorStackTrace = builder.errorStackTrace;
        this.slh = builder.slh;
        this.ph = builder.ph;
        this.cUserId = builder.cUserId;
        this.peeked = builder.peeked;
        this.useV1Parcel = builder.useV1Parcel;
        this.userId = builder.userId;
    }

    public String toString() {
        return toString(0);
    }

    public String toString(int i) {
        String str;
        boolean z = true;
        boolean z2 = (i & 1) == 1;
        if ((i & 2) != 2) {
            z = false;
        }
        String str2 = z2 ? this.serviceToken : "serviceTokenMasked";
        String str3 = z ? this.security : "securityMasked";
        if (TextUtils.isEmpty(this.userId) || this.userId.length() <= 3) {
            str = this.cUserId;
        } else {
            str = TextUtils.substring(this.userId, 0, 2) + "****";
        }
        StringBuffer stringBuffer = new StringBuffer("ServiceTokenResult{");
        stringBuffer.append("userId=");
        stringBuffer.append(str);
        stringBuffer.append('\'');
        stringBuffer.append(", sid='");
        stringBuffer.append(this.sid);
        stringBuffer.append('\'');
        stringBuffer.append(", serviceToken='");
        stringBuffer.append(str2);
        stringBuffer.append('\'');
        stringBuffer.append(", security='");
        stringBuffer.append(str3);
        stringBuffer.append('\'');
        stringBuffer.append(", errorCode=");
        stringBuffer.append(this.errorCode);
        stringBuffer.append(", errorMessage='");
        stringBuffer.append(this.errorMessage);
        stringBuffer.append('\'');
        stringBuffer.append(", errorStackTrace='");
        stringBuffer.append(this.errorStackTrace);
        stringBuffer.append('\'');
        stringBuffer.append(", intent=");
        stringBuffer.append(this.intent);
        stringBuffer.append(", slh='");
        stringBuffer.append(this.slh);
        stringBuffer.append('\'');
        stringBuffer.append(", ph='");
        stringBuffer.append(this.ph);
        stringBuffer.append('\'');
        stringBuffer.append(", cUserId='");
        stringBuffer.append(this.cUserId);
        stringBuffer.append('\'');
        stringBuffer.append(", peeked=");
        stringBuffer.append(this.peeked);
        stringBuffer.append('\'');
        stringBuffer.append(", useV1Parcel=");
        stringBuffer.append(this.useV1Parcel);
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServiceTokenResult)) {
            return false;
        }
        ServiceTokenResult serviceTokenResult = (ServiceTokenResult) obj;
        if (this.userId != serviceTokenResult.userId || this.peeked != serviceTokenResult.peeked || this.useV1Parcel != serviceTokenResult.useV1Parcel) {
            return false;
        }
        String str = this.sid;
        if (str == null ? serviceTokenResult.sid != null : !str.equals(serviceTokenResult.sid)) {
            return false;
        }
        String str2 = this.serviceToken;
        if (str2 == null ? serviceTokenResult.serviceToken != null : !str2.equals(serviceTokenResult.serviceToken)) {
            return false;
        }
        String str3 = this.security;
        if (str3 == null ? serviceTokenResult.security != null : !str3.equals(serviceTokenResult.security)) {
            return false;
        }
        if (this.errorCode != serviceTokenResult.errorCode) {
            return false;
        }
        String str4 = this.errorMessage;
        if (str4 == null ? serviceTokenResult.errorMessage != null : !str4.equals(serviceTokenResult.errorMessage)) {
            return false;
        }
        String str5 = this.errorStackTrace;
        if (str5 == null ? serviceTokenResult.errorStackTrace != null : !str5.equals(serviceTokenResult.errorStackTrace)) {
            return false;
        }
        Intent intent = this.intent;
        if (intent == null ? serviceTokenResult.intent != null : !intent.equals(serviceTokenResult.intent)) {
            return false;
        }
        String str6 = this.slh;
        if (str6 == null ? serviceTokenResult.slh != null : !str6.equals(serviceTokenResult.slh)) {
            return false;
        }
        String str7 = this.ph;
        if (str7 == null ? serviceTokenResult.ph != null : !str7.equals(serviceTokenResult.ph)) {
            return false;
        }
        String str8 = this.cUserId;
        return str8 != null ? str8.equals(serviceTokenResult.cUserId) : serviceTokenResult.cUserId == null;
    }

    public int hashCode() {
        String str = this.sid;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.serviceToken;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.security;
        int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        ErrorCode errorCode = this.errorCode;
        int hashCode4 = (hashCode3 + (errorCode != null ? errorCode.hashCode() : 0)) * 31;
        String str4 = this.errorMessage;
        int hashCode5 = (hashCode4 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.errorStackTrace;
        int hashCode6 = (hashCode5 + (str5 != null ? str5.hashCode() : 0)) * 31;
        Intent intent = this.intent;
        int hashCode7 = (hashCode6 + (intent != null ? intent.hashCode() : 0)) * 31;
        String str6 = this.slh;
        int hashCode8 = (hashCode7 + (str6 != null ? str6.hashCode() : 0)) * 31;
        String str7 = this.ph;
        int hashCode9 = (hashCode8 + (str7 != null ? str7.hashCode() : 0)) * 31;
        String str8 = this.cUserId;
        int hashCode10 = (((((hashCode9 + (str8 != null ? str8.hashCode() : 0)) * 31) + (this.peeked ? 1 : 0)) * 31) + (this.useV1Parcel ? 1 : 0)) * 31;
        String str9 = this.userId;
        if (str9 != null) {
            i = str9.hashCode();
        }
        return hashCode10 + i;
    }

    /* loaded from: classes4.dex */
    public static final class Builder {
        private String cUserId;
        private ErrorCode errorCode = ErrorCode.ERROR_NONE;
        private String errorMessage;
        private String errorStackTrace;
        private Intent intent;
        private boolean peeked;
        private String ph;
        private String security;
        private String serviceToken;
        private final String sid;
        private String slh;
        private boolean useV1Parcel;
        private String userId;

        public Builder(String str) {
            this.sid = str;
        }

        public Builder serviceToken(String str) {
            this.serviceToken = str;
            return this;
        }

        public Builder security(String str) {
            this.security = str;
            return this;
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

        public Builder intent(Intent intent) {
            this.intent = intent;
            return this;
        }

        public Builder slh(String str) {
            this.slh = str;
            return this;
        }

        public Builder ph(String str) {
            this.ph = str;
            return this;
        }

        public Builder cUserId(String str) {
            this.cUserId = str;
            return this;
        }

        public Builder peeked(boolean z) {
            this.peeked = z;
            return this;
        }

        public Builder useV1Parcel(boolean z) {
            this.useV1Parcel = z;
            return this;
        }

        public Builder userId(String str) {
            this.userId = str;
            return this;
        }

        public static Builder copyFrom(ServiceTokenResult serviceTokenResult) {
            return new Builder(serviceTokenResult.sid).serviceToken(serviceTokenResult.serviceToken).security(serviceTokenResult.security).errorCode(serviceTokenResult.errorCode).errorMessage(serviceTokenResult.errorMessage).errorStackTrace(serviceTokenResult.errorStackTrace).intent(serviceTokenResult.intent).slh(serviceTokenResult.slh).ph(serviceTokenResult.ph).cUserId(serviceTokenResult.cUserId).peeked(serviceTokenResult.peeked).useV1Parcel(serviceTokenResult.useV1Parcel).userId(serviceTokenResult.userId);
        }

        public ServiceTokenResult build() {
            return new ServiceTokenResult(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.useV1Parcel) {
            writeToParcelV1(parcel, i);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(PARCEL_BUNDLE_KEY_SID, this.sid);
        bundle.putString("serviceToken", this.serviceToken);
        bundle.putString(PARCEL_BUNDLE_KEY_SECURITY, this.security);
        ErrorCode errorCode = this.errorCode;
        bundle.putInt("errorCode", errorCode == null ? -1 : errorCode.ordinal());
        bundle.putString("errorMessage", this.errorMessage);
        bundle.putString(PARCEL_BUNDLE_KEY_ERROR_STACK_TRACE, this.errorStackTrace);
        bundle.putParcelable("intent", this.intent);
        bundle.putString(PARCEL_BUNDLE_KEY_SLH, this.slh);
        bundle.putString(PARCEL_BUNDLE_KEY_PH, this.ph);
        bundle.putString(PARCEL_BUNDLE_KEY_C_USER_ID, this.cUserId);
        bundle.putBoolean(PARCEL_BUNDLE_KEY_PEEKED, this.peeked);
        bundle.putString("userId", this.userId);
        parcel.writeString(PARCEL_V2_FLAG);
        parcel.writeBundle(bundle);
    }

    protected ServiceTokenResult(Parcel parcel) {
        String readString = parcel.readString();
        ErrorCode errorCode = null;
        if (!TextUtils.equals(PARCEL_V2_FLAG, readString)) {
            this.sid = readString;
            this.serviceToken = parcel.readString();
            this.security = parcel.readString();
            int readInt = parcel.readInt();
            this.errorCode = readInt == -1 ? null : ErrorCode.values()[readInt];
            this.errorMessage = parcel.readString();
            this.errorStackTrace = parcel.readString();
            this.intent = (Intent) parcel.readParcelable(Intent.class.getClassLoader());
            this.slh = null;
            this.ph = null;
            this.cUserId = null;
            this.peeked = false;
            this.useV1Parcel = false;
            this.userId = null;
            return;
        }
        Bundle readBundle = parcel.readBundle(Intent.class.getClassLoader());
        this.sid = readBundle.getString(PARCEL_BUNDLE_KEY_SID);
        this.serviceToken = readBundle.getString("serviceToken");
        this.security = readBundle.getString(PARCEL_BUNDLE_KEY_SECURITY);
        int i = readBundle.getInt("errorCode");
        this.errorCode = i != -1 ? ErrorCode.values()[i] : errorCode;
        this.errorMessage = readBundle.getString("errorMessage");
        this.errorStackTrace = readBundle.getString(PARCEL_BUNDLE_KEY_ERROR_STACK_TRACE);
        this.intent = (Intent) readBundle.getParcelable("intent");
        this.slh = readBundle.getString(PARCEL_BUNDLE_KEY_SLH);
        this.ph = readBundle.getString(PARCEL_BUNDLE_KEY_PH);
        this.cUserId = readBundle.getString(PARCEL_BUNDLE_KEY_C_USER_ID);
        this.peeked = readBundle.getBoolean(PARCEL_BUNDLE_KEY_PEEKED);
        this.useV1Parcel = true;
        this.userId = readBundle.getString("userId");
    }

    private void writeToParcelV1(Parcel parcel, int i) {
        parcel.writeString(this.sid);
        parcel.writeString(this.serviceToken);
        parcel.writeString(this.security);
        ErrorCode errorCode = this.errorCode;
        parcel.writeInt(errorCode == null ? -1 : errorCode.ordinal());
        parcel.writeString(this.errorMessage);
        parcel.writeString(this.errorStackTrace);
        parcel.writeParcelable(this.intent, i);
    }
}
