package com.xiaomi.phonenum.innetdate;

import com.xiaomi.phonenum.bean.Error;

/* loaded from: classes4.dex */
public class InNetDateResult {
    private Error a;
    private String b;
    private long c;
    private String d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public InNetDateResult(long j, String str) {
        this.c = j;
        this.d = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public InNetDateResult(Error error) {
        this(error, error.toString());
    }

    InNetDateResult(Error error, String str) {
        this.a = error;
        this.b = str;
    }

    public boolean isSuccess() {
        return this.a == null;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    public long getInNetDate() {
        return this.c;
    }

    public String getPhoneNum() {
        return this.d;
    }

    public Error getError() {
        if (isSuccess()) {
            return Error.NONE;
        }
        return this.a;
    }

    public String getMassage() {
        return this.b;
    }

    public String toString() {
        return "IabResult: " + getMassage() + " date:" + this.c;
    }
}
