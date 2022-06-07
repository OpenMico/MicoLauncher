package okio.internal;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Utf8;
import org.jetbrains.annotations.NotNull;

/* compiled from: -Utf8.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001e\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class _Utf8Kt {
    public static /* synthetic */ String commonToUtf8String$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        return commonToUtf8String(bArr, i, i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x011a, code lost:
        if (((r17[r5] & 192) == 128) == false) goto L_0x011c;
     */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.String commonToUtf8String(@org.jetbrains.annotations.NotNull byte[] r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 483
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal._Utf8Kt.commonToUtf8String(byte[], int, int):java.lang.String");
    }

    @NotNull
    public static final byte[] commonAsUtf8ToByteArray(@NotNull String commonAsUtf8ToByteArray) {
        int i;
        char charAt;
        Intrinsics.checkParameterIsNotNull(commonAsUtf8ToByteArray, "$this$commonAsUtf8ToByteArray");
        byte[] bArr = new byte[commonAsUtf8ToByteArray.length() * 4];
        int length = commonAsUtf8ToByteArray.length();
        int i2 = 0;
        while (i2 < length) {
            char charAt2 = commonAsUtf8ToByteArray.charAt(i2);
            if (charAt2 >= 128) {
                int length2 = commonAsUtf8ToByteArray.length();
                int i3 = i2;
                while (i2 < length2) {
                    char charAt3 = commonAsUtf8ToByteArray.charAt(i2);
                    if (charAt3 < 128) {
                        int i4 = i3 + 1;
                        bArr[i3] = (byte) charAt3;
                        i2++;
                        while (i2 < length2 && commonAsUtf8ToByteArray.charAt(i2) < 128) {
                            i2++;
                            i4++;
                            bArr[i4] = (byte) commonAsUtf8ToByteArray.charAt(i2);
                        }
                        i3 = i4;
                    } else if (charAt3 < 2048) {
                        int i5 = i3 + 1;
                        bArr[i3] = (byte) ((charAt3 >> 6) | 192);
                        i3 = i5 + 1;
                        bArr[i5] = (byte) ((charAt3 & '?') | 128);
                        i2++;
                    } else if (55296 > charAt3 || 57343 < charAt3) {
                        int i6 = i3 + 1;
                        bArr[i3] = (byte) ((charAt3 >> '\f') | 224);
                        int i7 = i6 + 1;
                        bArr[i6] = (byte) (((charAt3 >> 6) & 63) | 128);
                        i3 = i7 + 1;
                        bArr[i7] = (byte) ((charAt3 & '?') | 128);
                        i2++;
                    } else if (charAt3 > 56319 || length2 <= (i = i2 + 1) || 56320 > (charAt = commonAsUtf8ToByteArray.charAt(i)) || 57343 < charAt) {
                        i3++;
                        bArr[i3] = Utf8.REPLACEMENT_BYTE;
                        i2++;
                    } else {
                        int charAt4 = ((charAt3 << '\n') + commonAsUtf8ToByteArray.charAt(i)) - 56613888;
                        int i8 = i3 + 1;
                        bArr[i3] = (byte) ((charAt4 >> 18) | PsExtractor.VIDEO_STREAM_MASK);
                        int i9 = i8 + 1;
                        bArr[i8] = (byte) (((charAt4 >> 12) & 63) | 128);
                        int i10 = i9 + 1;
                        bArr[i9] = (byte) (((charAt4 >> 6) & 63) | 128);
                        i3 = i10 + 1;
                        bArr[i10] = (byte) ((charAt4 & 63) | 128);
                        i2 += 2;
                    }
                }
                byte[] copyOf = Arrays.copyOf(bArr, i3);
                Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
                return copyOf;
            }
            bArr[i2] = (byte) charAt2;
            i2++;
        }
        byte[] copyOf2 = Arrays.copyOf(bArr, commonAsUtf8ToByteArray.length());
        Intrinsics.checkExpressionValueIsNotNull(copyOf2, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf2;
    }
}
