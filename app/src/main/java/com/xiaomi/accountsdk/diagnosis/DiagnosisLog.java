package com.xiaomi.accountsdk.diagnosis;

/* loaded from: classes2.dex */
public class DiagnosisLog {
    private static DiagnosisLogInterface a = new d();

    private DiagnosisLog() {
    }

    public static DiagnosisLogInterface get() {
        return a;
    }

    public static void set(DiagnosisLogInterface diagnosisLogInterface) {
        a = diagnosisLogInterface;
    }
}
