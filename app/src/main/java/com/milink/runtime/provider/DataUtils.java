package com.milink.runtime.provider;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class DataUtils {
    private DataUtils() {
    }

    @NonNull
    public static Cursor obtainEmptyCursor(int i, Bundle bundle) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[0], 0);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt("code", i);
        matrixCursor.setExtras(bundle);
        return matrixCursor;
    }
}
