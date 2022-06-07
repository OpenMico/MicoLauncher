package com.xiaomi.account.diagnosis.util;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes2.dex */
public class DiagnosisPreference {
    private static final String DIAGNOSIS_ENABLED_KEY = "diagnosis_enabled";
    private static final String PREFERENCE_NAME = "passport_diagnosis";

    private DiagnosisPreference() {
    }

    private static SharedPreferences getDiagnosisPreference(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 4);
    }

    public static boolean isDiagnosisEnabled(Context context) {
        return getDiagnosisPreference(context).getBoolean(DIAGNOSIS_ENABLED_KEY, false);
    }

    public static boolean setDiagnosisEnabled(Context context, boolean z) {
        return getDiagnosisPreference(context).edit().putBoolean(DIAGNOSIS_ENABLED_KEY, z).commit();
    }
}
