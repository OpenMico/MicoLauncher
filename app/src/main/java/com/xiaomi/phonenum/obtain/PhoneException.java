package com.xiaomi.phonenum.obtain;

import com.xiaomi.phonenum.bean.Error;

/* loaded from: classes4.dex */
public class PhoneException extends Exception {
    public final Error error;

    public PhoneException(Error error) {
        this(error, error.name());
    }

    public PhoneException(Error error, String str) {
        this(error, str, null);
    }

    public PhoneException(Error error, String str, Throwable th) {
        super(str, th);
        this.error = error;
    }
}
