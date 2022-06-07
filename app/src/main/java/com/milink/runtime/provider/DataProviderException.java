package com.milink.runtime.provider;

import android.os.Bundle;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class DataProviderException extends Exception {
    private Bundle mBundle;
    private int mCode;

    public DataProviderException(int i) {
        this(i, null);
    }

    public DataProviderException(int i, Bundle bundle) {
        if (i >= 0) {
            this.mCode = i;
            this.mBundle = bundle;
            return;
        }
        throw new RuntimeException("code must be > 0");
    }

    public int getCode() {
        return this.mCode;
    }

    @NonNull
    public Bundle getExtra() {
        if (this.mBundle == null) {
            this.mBundle = new Bundle();
        }
        return this.mBundle;
    }
}
