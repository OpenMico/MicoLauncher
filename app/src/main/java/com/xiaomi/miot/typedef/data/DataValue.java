package com.xiaomi.miot.typedef.data;

import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes3.dex */
public abstract class DataValue implements Parcelable {
    private static final String TAG = "DataValue";

    public abstract Object getObjectValue();

    public boolean lessThan(DataValue dataValue) {
        Log.d(TAG, "lessThan: NOT IMPLEMENTED");
        return false;
    }

    public boolean validate(DataValue dataValue, DataValue dataValue2) {
        Log.d(TAG, "validate: NOT IMPLEMENTED");
        return false;
    }
}
