package com.milink.base.utils;

import androidx.annotation.NonNull;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public class ByteUtil {
    public static final int NOT_FOUND = -1;

    private ByteUtil() {
    }

    public static int toInt(byte[] bArr) {
        int min = Math.min(bArr.length, 8);
        int i = 0;
        for (int i2 = 0; i2 < min; i2++) {
            i += (bArr[i2] & 255) << ((1 - i2) * 8);
        }
        return i;
    }

    public static int find(@NonNull byte[] bArr, @NonNull byte[] bArr2) {
        Objects.requireNonNull(bArr);
        Objects.requireNonNull(bArr2);
        if (bArr2.length == bArr.length) {
            return Arrays.equals(bArr2, bArr) ? 0 : -1;
        }
        if (bArr2.length > bArr.length) {
            return -1;
        }
        int length = (bArr.length - bArr2.length) + 1;
        for (int i = 0; i < length; i++) {
            if (bArr[i] == bArr2[0]) {
                int i2 = i;
                int i3 = 0;
                while (i3 < bArr2.length) {
                    if (bArr[i2] != bArr2[i3]) {
                        break;
                    }
                    i3++;
                    i2++;
                }
                return i;
            }
        }
        return -1;
    }
}
