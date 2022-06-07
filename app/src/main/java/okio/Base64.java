package okio;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: -Base64.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0001*\u00020\u0007H\u0000\u001a\u0016\u0010\b\u001a\u00020\u0007*\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\u0001H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006\n"}, d2 = {"BASE64", "", "getBASE64", "()[B", "BASE64_URL_SAFE", "getBASE64_URL_SAFE", "decodeBase64ToArray", "", "encodeBase64", "map", "okio"}, k = 2, mv = {1, 1, 16})
@JvmName(name = "-Base64")
/* renamed from: okio.-Base64 */
/* loaded from: classes5.dex */
public final class Base64 {
    @NotNull
    private static final byte[] BASE64 = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData$okio();
    @NotNull
    private static final byte[] BASE64_URL_SAFE = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData$okio();

    @NotNull
    public static final byte[] getBASE64() {
        return BASE64;
    }

    @NotNull
    public static final byte[] getBASE64_URL_SAFE() {
        return BASE64_URL_SAFE;
    }

    @Nullable
    public static final byte[] decodeBase64ToArray(@NotNull String decodeBase64ToArray) {
        int i;
        char charAt;
        Intrinsics.checkParameterIsNotNull(decodeBase64ToArray, "$this$decodeBase64ToArray");
        int length = decodeBase64ToArray.length();
        while (length > 0 && ((charAt = decodeBase64ToArray.charAt(length - 1)) == '=' || charAt == '\n' || charAt == '\r' || charAt == ' ' || charAt == '\t')) {
            length--;
        }
        byte[] bArr = new byte[(int) ((length * 6) / 8)];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            char charAt2 = decodeBase64ToArray.charAt(i5);
            if ('A' <= charAt2 && 'Z' >= charAt2) {
                i = charAt2 - 'A';
            } else if ('a' <= charAt2 && 'z' >= charAt2) {
                i = charAt2 - 'G';
            } else if ('0' <= charAt2 && '9' >= charAt2) {
                i = charAt2 + 4;
            } else if (charAt2 == '+' || charAt2 == '-') {
                i = 62;
            } else if (charAt2 == '/' || charAt2 == '_') {
                i = 63;
            } else {
                if (!(charAt2 == '\n' || charAt2 == '\r' || charAt2 == ' ' || charAt2 == '\t')) {
                    return null;
                }
            }
            i3 = (i3 << 6) | i;
            i2++;
            if (i2 % 4 == 0) {
                int i6 = i4 + 1;
                bArr[i4] = (byte) (i3 >> 16);
                int i7 = i6 + 1;
                bArr[i6] = (byte) (i3 >> 8);
                i4 = i7 + 1;
                bArr[i7] = (byte) i3;
            }
        }
        switch (i2 % 4) {
            case 1:
                return null;
            case 2:
                i4++;
                bArr[i4] = (byte) ((i3 << 12) >> 16);
                break;
            case 3:
                int i8 = i3 << 6;
                int i9 = i4 + 1;
                bArr[i4] = (byte) (i8 >> 16);
                i4 = i9 + 1;
                bArr[i9] = (byte) (i8 >> 8);
                break;
        }
        if (i4 == bArr.length) {
            return bArr;
        }
        byte[] copyOf = Arrays.copyOf(bArr, i4);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    public static /* synthetic */ String encodeBase64$default(byte[] bArr, byte[] bArr2, int i, Object obj) {
        if ((i & 1) != 0) {
            bArr2 = BASE64;
        }
        return encodeBase64(bArr, bArr2);
    }

    @NotNull
    public static final String encodeBase64(@NotNull byte[] encodeBase64, @NotNull byte[] map) {
        Intrinsics.checkParameterIsNotNull(encodeBase64, "$this$encodeBase64");
        Intrinsics.checkParameterIsNotNull(map, "map");
        byte[] bArr = new byte[((encodeBase64.length + 2) / 3) * 4];
        int length = encodeBase64.length - (encodeBase64.length % 3);
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 1;
            byte b = encodeBase64[i];
            int i4 = i3 + 1;
            byte b2 = encodeBase64[i3];
            i = i4 + 1;
            byte b3 = encodeBase64[i4];
            int i5 = i2 + 1;
            bArr[i2] = map[(b & 255) >> 2];
            int i6 = i5 + 1;
            bArr[i5] = map[((b & 3) << 4) | ((b2 & 255) >> 4)];
            int i7 = i6 + 1;
            bArr[i6] = map[((b2 & 15) << 2) | ((b3 & 255) >> 6)];
            i2 = i7 + 1;
            bArr[i7] = map[b3 & Utf8.REPLACEMENT_BYTE];
        }
        switch (encodeBase64.length - length) {
            case 1:
                byte b4 = encodeBase64[i];
                int i8 = i2 + 1;
                bArr[i2] = map[(b4 & 255) >> 2];
                int i9 = i8 + 1;
                bArr[i8] = map[(b4 & 3) << 4];
                byte b5 = (byte) 61;
                bArr[i9] = b5;
                bArr[i9 + 1] = b5;
                break;
            case 2:
                int i10 = i + 1;
                byte b6 = encodeBase64[i];
                byte b7 = encodeBase64[i10];
                int i11 = i2 + 1;
                bArr[i2] = map[(b6 & 255) >> 2];
                int i12 = i11 + 1;
                bArr[i11] = map[((b6 & 3) << 4) | ((b7 & 255) >> 4)];
                bArr[i12] = map[(b7 & 15) << 2];
                bArr[i12 + 1] = (byte) 61;
                break;
        }
        return Platform.toUtf8String(bArr);
    }
}
