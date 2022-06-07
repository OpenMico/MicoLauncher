package com.xiaomi.passport.snscorelib.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SNSTokenLoginResult implements Parcelable {
    public static final Parcelable.Creator<SNSTokenLoginResult> CREATOR = new Parcelable.Creator<SNSTokenLoginResult>() { // from class: com.xiaomi.passport.snscorelib.internal.entity.SNSTokenLoginResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSTokenLoginResult createFromParcel(Parcel parcel) {
            return new SNSTokenLoginResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSTokenLoginResult[] newArray(int i) {
            return new SNSTokenLoginResult[i];
        }
    };
    public boolean bindLimit;
    public final String callback;
    public final String notificationUrl;
    public final String openId;
    public final String passToken;
    public final String sid;
    public final String snsBindTryUrl;
    public String snsLoginUrl;
    public final String snsTokenPh;
    public final int status;
    public final String userId;
    public final String webViewCallback;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SNSTokenLoginResult(Builder builder) {
        this.status = builder.status;
        this.sid = builder.sid;
        this.webViewCallback = builder.webViewCallback;
        this.callback = builder.callback;
        this.notificationUrl = builder.notificationUrl;
        this.userId = builder.userId;
        this.passToken = builder.passToken;
        this.snsBindTryUrl = builder.snsBindTryUrl;
        this.snsTokenPh = builder.snsTokenPh;
        this.openId = builder.openId;
        this.snsLoginUrl = builder.snsLoginUrl;
        this.bindLimit = builder.bindLimit;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.status);
        parcel.writeString(this.sid);
        parcel.writeString(this.webViewCallback);
        parcel.writeString(this.callback);
        parcel.writeString(this.notificationUrl);
        parcel.writeString(this.userId);
        parcel.writeString(this.passToken);
        parcel.writeString(this.snsBindTryUrl);
        parcel.writeString(this.snsTokenPh);
        parcel.writeString(this.openId);
        parcel.writeString(this.snsLoginUrl);
        parcel.writeByte(this.bindLimit ? (byte) 1 : (byte) 0);
    }

    private SNSTokenLoginResult(Parcel parcel) {
        this.status = parcel.readInt();
        this.sid = parcel.readString();
        this.webViewCallback = parcel.readString();
        this.callback = parcel.readString();
        this.notificationUrl = parcel.readString();
        this.userId = parcel.readString();
        this.passToken = parcel.readString();
        this.snsBindTryUrl = parcel.readString();
        this.snsTokenPh = parcel.readString();
        this.openId = parcel.readString();
        this.snsLoginUrl = parcel.readString();
        this.bindLimit = parcel.readByte() != 0;
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        private boolean bindLimit;
        private String callback;
        private String notificationUrl;
        private String openId;
        private String passToken;
        private String sid;
        private String snsBindTryUrl;
        private String snsLoginUrl;
        private String snsTokenPh;
        private int status;
        private String userId;
        private String webViewCallback;

        public Builder status(int i) {
            this.status = i;
            return this;
        }

        public Builder sid(String str) {
            this.sid = str;
            return this;
        }

        public Builder userId(String str) {
            this.userId = str;
            return this;
        }

        public Builder passToken(String str) {
            this.passToken = str;
            return this;
        }

        public Builder webViewCallback(String str) {
            this.webViewCallback = str;
            return this;
        }

        public Builder callback(String str) {
            this.callback = str;
            return this;
        }

        public Builder notificationUrl(String str) {
            this.notificationUrl = str;
            return this;
        }

        public Builder snsBindTryUrl(String str) {
            this.snsBindTryUrl = str;
            return this;
        }

        public Builder snsTokenPh(String str) {
            this.snsTokenPh = str;
            return this;
        }

        public Builder openId(String str) {
            this.openId = str;
            return this;
        }

        public Builder snsLoginUrl(String str) {
            this.snsLoginUrl = str;
            return this;
        }

        public Builder bindLimit(boolean z) {
            this.bindLimit = z;
            return this;
        }

        public SNSTokenLoginResult build() {
            return new SNSTokenLoginResult(this);
        }
    }
}
