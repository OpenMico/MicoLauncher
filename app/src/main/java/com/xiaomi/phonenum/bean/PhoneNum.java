package com.xiaomi.phonenum.bean;

import android.os.Bundle;
import com.umeng.analytics.pro.ai;
import com.xiaomi.account.openauth.AuthorizeActivityBase;

/* loaded from: classes4.dex */
public class PhoneNum {
    public final String copywriter;
    public final int errorCode;
    public final String errorMsg;
    public final String iccid;
    public final boolean isVerified;
    public final String number;
    public final String numberHash;
    public final String operatorLink;
    public final int phoneLevel;
    public final int subId;
    public final String token;
    public final String traceId;
    public final String updateTime;

    private PhoneNum(Builder builder) {
        this.errorCode = builder.a;
        this.number = builder.c;
        this.iccid = builder.d;
        this.token = builder.e;
        this.errorMsg = builder.b;
        this.isVerified = builder.f;
        this.updateTime = builder.g;
        this.numberHash = builder.h;
        this.copywriter = builder.i;
        this.operatorLink = builder.j;
        this.subId = builder.k;
        this.traceId = builder.l;
        this.phoneLevel = builder.m;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("errorCode", this.errorCode);
        bundle.putString("errorMsg", this.errorMsg);
        bundle.putString("number", this.number);
        bundle.putString("numberHash", this.numberHash);
        bundle.putString(ai.aa, this.iccid);
        bundle.putString("token", this.token);
        bundle.putBoolean("isVerified", this.isVerified);
        bundle.putString("updateTime", this.updateTime);
        bundle.putString("copywriter", this.copywriter);
        bundle.putString(AuthorizeActivityBase.KEY_OPERATORLINK, this.operatorLink);
        bundle.putString("traceId", this.traceId);
        bundle.putInt("subId", this.subId);
        bundle.putInt("phoneLevel", this.phoneLevel);
        return bundle;
    }

    public String toString() {
        Bundle bundle = new Bundle();
        bundle.putInt("errorCode", this.errorCode);
        bundle.putString("errorMsg", this.errorMsg);
        bundle.putString("number", this.number);
        bundle.putString("traceId", this.traceId);
        bundle.putInt("subId", this.subId);
        return bundle.toString();
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        private String b;
        private String c;
        private String d;
        private String e;
        private String h;
        private String i;
        private String j;
        private String l;
        private int m;
        private int a = Error.NONE.code;
        private boolean f = false;
        private String g = String.valueOf(System.currentTimeMillis());
        private int k = -1;

        public Builder bundle(Bundle bundle) {
            this.a = bundle.getInt("errorCode");
            this.b = bundle.getString("errorMsg");
            this.c = bundle.getString("number");
            this.h = bundle.getString("numberHash");
            this.d = bundle.getString(ai.aa);
            this.e = bundle.getString("token");
            this.f = bundle.getBoolean("isVerified");
            this.g = bundle.getString("updateTime");
            this.i = bundle.getString("copywriter");
            this.j = bundle.getString(AuthorizeActivityBase.KEY_OPERATORLINK);
            this.l = bundle.getString("traceId");
            this.k = bundle.getInt("subId");
            this.m = bundle.getInt("phoneLevel");
            return this;
        }

        public Builder number(String str) {
            this.c = str;
            return this;
        }

        public Builder numberHash(String str) {
            this.h = str;
            return this;
        }

        public Builder errorMsg(String str) {
            this.b = str;
            return this;
        }

        public Builder token(String str) {
            this.e = str;
            return this;
        }

        public Builder iccid(String str) {
            this.d = str;
            return this;
        }

        public Builder errorCode(int i) {
            this.a = i;
            return this;
        }

        public Builder isVerified(boolean z) {
            this.f = z;
            return this;
        }

        public Builder updateTime(String str) {
            this.g = str;
            return this;
        }

        public Builder copywriter(String str) {
            this.i = str;
            return this;
        }

        public Builder operatorLink(String str) {
            this.j = str;
            return this;
        }

        public Builder traceId(String str) {
            this.l = str;
            return this;
        }

        public Builder subId(int i) {
            this.k = i;
            return this;
        }

        public Builder phoneLevel(int i) {
            this.m = i;
            return this;
        }

        public PhoneNum build() {
            if (this.b == null) {
                this.b = "" + Error.codeToError(this.a);
            } else {
                this.b = "" + Error.codeToError(this.a) + " : " + this.b;
            }
            return new PhoneNum(this);
        }
    }
}
