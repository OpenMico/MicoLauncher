package org.fourthline.cling.support.model;

/* loaded from: classes5.dex */
public enum RecordMediumWriteStatus {
    WRITABLE,
    PROTECTED,
    NOT_WRITABLE,
    UNKNOWN,
    NOT_IMPLEMENTED;

    public static RecordMediumWriteStatus valueOrUnknownOf(String str) {
        if (str == null) {
            return UNKNOWN;
        }
        try {
            return valueOf(str);
        } catch (IllegalArgumentException unused) {
            return UNKNOWN;
        }
    }
}
