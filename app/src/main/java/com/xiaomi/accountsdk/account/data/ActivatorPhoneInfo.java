package com.xiaomi.accountsdk.account.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ActivatorPhoneInfo implements Parcelable {
    public static final Parcelable.Creator<ActivatorPhoneInfo> CREATOR = new Parcelable.Creator<ActivatorPhoneInfo>() { // from class: com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivatorPhoneInfo createFromParcel(Parcel parcel) {
            Bundle readBundle = parcel.readBundle();
            if (readBundle == null) {
                return null;
            }
            return new Builder().phone(readBundle.getString("phone")).phoneHash(readBundle.getString(ActivatorPhoneInfo.KEY_PHONE_HASH)).activatorToken(readBundle.getString(ActivatorPhoneInfo.KEY_ACTIVATOR_TOKEN)).slotId(readBundle.getInt(ActivatorPhoneInfo.KEY_SLOT_ID)).copyWriter(readBundle.getString(ActivatorPhoneInfo.KEY_COPY_WRITER)).operatorLink(readBundle.getString(ActivatorPhoneInfo.KEY_OPERATOR_LINK)).needVerify(readBundle.getBoolean(ActivatorPhoneInfo.KEY_NEED_VERIFY)).isVerified(readBundle.getBoolean(ActivatorPhoneInfo.KEY_IS_VERIFIED)).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivatorPhoneInfo[] newArray(int i) {
            return new ActivatorPhoneInfo[i];
        }
    };
    public static final boolean IS_VERIFIED_DEFAULT_VALUE = false;
    private static final String KEY_ACTIVATOR_TOKEN = "activator_token";
    private static final String KEY_COPY_WRITER = "copy_writer";
    private static final String KEY_IS_VERIFIED = "is_verified";
    private static final String KEY_NEED_VERIFY = "need_verify";
    private static final String KEY_OPERATOR_LINK = "operator_link";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PHONE_HASH = "phone_hash";
    private static final String KEY_SLOT_ID = "slot_id";
    public static final boolean NEED_VERIFY_DEFAULT_VALUE = true;
    public final String activatorToken;
    public final String copyWriter;
    public final boolean isVerified;
    public final boolean needVerify;
    public final String operatorLink;
    public final String phone;
    public final String phoneHash;
    public final int slotId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ActivatorPhoneInfo(Builder builder) {
        this.phone = builder.phone;
        this.phoneHash = builder.phoneHash;
        this.activatorToken = builder.activatorToken;
        this.slotId = builder.slotId;
        this.copyWriter = builder.copyWriter;
        this.operatorLink = builder.operatorLink;
        this.needVerify = builder.needVerify;
        this.isVerified = builder.isVerified;
    }

    public boolean needVerifyPhone() {
        return !this.isVerified && this.needVerify;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private String activatorToken;
        private String copyWriter;
        private String operatorLink;
        private String phone;
        private String phoneHash;
        private int slotId;
        private boolean needVerify = true;
        private boolean isVerified = false;

        public Builder phone(String str) {
            this.phone = str;
            return this;
        }

        public Builder phoneHash(String str) {
            this.phoneHash = str;
            return this;
        }

        public Builder activatorToken(String str) {
            this.activatorToken = str;
            return this;
        }

        public Builder slotId(int i) {
            this.slotId = i;
            return this;
        }

        public Builder copyWriter(String str) {
            this.copyWriter = str;
            return this;
        }

        public Builder operatorLink(String str) {
            this.operatorLink = str;
            return this;
        }

        public Builder needVerify(boolean z) {
            this.needVerify = z;
            return this;
        }

        public Builder isVerified(boolean z) {
            this.isVerified = z;
            return this;
        }

        public ActivatorPhoneInfo build() {
            return new ActivatorPhoneInfo(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("phone", this.phone);
        bundle.putString(KEY_PHONE_HASH, this.phoneHash);
        bundle.putString(KEY_ACTIVATOR_TOKEN, this.activatorToken);
        bundle.putInt(KEY_SLOT_ID, this.slotId);
        bundle.putString(KEY_COPY_WRITER, this.copyWriter);
        bundle.putString(KEY_OPERATOR_LINK, this.operatorLink);
        bundle.putBoolean(KEY_NEED_VERIFY, this.needVerify);
        bundle.putBoolean(KEY_IS_VERIFIED, this.isVerified);
        parcel.writeBundle(bundle);
    }
}
