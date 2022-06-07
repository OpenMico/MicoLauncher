package com.google.zxing.aztec.decoder;

import androidx.exifinterface.media.ExifInterface;
import com.google.zxing.FormatException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.api.c;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.fourthline.cling.support.messagebox.parser.MessageElement;
import org.seamless.xhtml.XHTMLElement;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public final class Decoder {
    private static final String[] a = {"CTRL_PS", StringUtils.SPACE, "A", "B", HomePageRecordHelper.AREA_C, HomePageRecordHelper.AREA_D, "E", HomePageRecordHelper.AREA_F, "G", c.b, "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
    private static final String[] b = {"CTRL_PS", StringUtils.SPACE, ai.at, "b", ai.aD, "d", "e", "f", "g", XHTMLElement.XPATH_PREFIX, ai.aA, "j", "k", com.xiaomi.onetrack.a.c.a, MessageElement.XPATH_PREFIX, "n", "o", ai.av, "q", "r", ai.az, ai.aF, ai.aE, ai.aC, "w", "x", "y", ai.aB, "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
    private static final String[] c = {"CTRL_PS", StringUtils.SPACE, "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b", "\t", "\n", "\u000b", "\f", StringUtils.CR, "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@", "\\", "^", "_", "`", "|", Constants.WAVE_SEPARATOR, "\u007f", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"};
    private static final String[] d = {"", StringUtils.CR, "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", MusicGroupListActivity.SPECIAL_SYMBOL, LrcRow.SINGLE_QUOTE, "(", ")", "*", Marker.ANY_NON_NULL_MARKER, Constants.ACCEPT_TIME_SEPARATOR_SP, Constants.ACCEPT_TIME_SEPARATOR_SERVER, ".", "/", Constants.COLON_SEPARATOR, ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL"};
    private static final String[] e = {"CTRL_PS", StringUtils.SPACE, "0", "1", "2", "3", Commands.ResolutionValues.BITSTREAM_BLUE_HIGH, Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND, "6", "7", "8", Commands.ResolutionValues.BITSTREAM_4K, Constants.ACCEPT_TIME_SEPARATOR_SP, ".", "CTRL_UL", "CTRL_US"};
    private AztecDetectorResult f;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public enum a {
        UPPER,
        LOWER,
        MIXED,
        DIGIT,
        PUNCT,
        BINARY
    }

    private static int a(int i, boolean z) {
        return ((z ? 88 : 112) + (i << 4)) * i;
    }

    public DecoderResult decode(AztecDetectorResult aztecDetectorResult) throws FormatException {
        this.f = aztecDetectorResult;
        boolean[] c2 = c(a(aztecDetectorResult.getBits()));
        DecoderResult decoderResult = new DecoderResult(a(c2), b(c2), null, null);
        decoderResult.setNumBits(c2.length);
        return decoderResult;
    }

    public static String highLevelDecode(boolean[] zArr) {
        return b(zArr);
    }

    private static String b(boolean[] zArr) {
        int length = zArr.length;
        a aVar = a.UPPER;
        a aVar2 = a.UPPER;
        StringBuilder sb = new StringBuilder(20);
        a aVar3 = aVar;
        int i = 0;
        while (i < length) {
            if (aVar2 == a.BINARY) {
                if (length - i < 5) {
                    break;
                }
                int a2 = a(zArr, i, 5);
                int i2 = i + 5;
                if (a2 == 0) {
                    if (length - i2 < 11) {
                        break;
                    }
                    a2 = a(zArr, i2, 11) + 31;
                    i2 += 11;
                }
                int i3 = 0;
                while (true) {
                    if (i3 >= a2) {
                        i = i2;
                        break;
                    } else if (length - i2 < 8) {
                        i = length;
                        break;
                    } else {
                        sb.append((char) a(zArr, i2, 8));
                        i2 += 8;
                        i3++;
                    }
                }
                aVar2 = aVar3;
            } else {
                int i4 = aVar2 == a.DIGIT ? 4 : 5;
                if (length - i < i4) {
                    break;
                }
                int a3 = a(zArr, i, i4);
                i += i4;
                String a4 = a(aVar2, a3);
                if (a4.startsWith("CTRL_")) {
                    aVar3 = a(a4.charAt(5));
                    if (a4.charAt(6) == 'L') {
                        aVar2 = aVar3;
                    } else {
                        aVar3 = aVar2;
                        aVar2 = aVar3;
                    }
                } else {
                    sb.append(a4);
                    aVar2 = aVar3;
                }
            }
        }
        return sb.toString();
    }

    private static a a(char c2) {
        if (c2 == 'B') {
            return a.BINARY;
        }
        if (c2 == 'D') {
            return a.DIGIT;
        }
        if (c2 == 'P') {
            return a.PUNCT;
        }
        switch (c2) {
            case 'L':
                return a.LOWER;
            case 'M':
                return a.MIXED;
            default:
                return a.UPPER;
        }
    }

    private static String a(a aVar, int i) {
        switch (aVar) {
            case UPPER:
                return a[i];
            case LOWER:
                return b[i];
            case MIXED:
                return c[i];
            case PUNCT:
                return d[i];
            case DIGIT:
                return e[i];
            default:
                throw new IllegalStateException("Bad table");
        }
    }

    private boolean[] c(boolean[] zArr) throws FormatException {
        GenericGF genericGF;
        int i = 8;
        if (this.f.getNbLayers() <= 2) {
            i = 6;
            genericGF = GenericGF.AZTEC_DATA_6;
        } else if (this.f.getNbLayers() <= 8) {
            genericGF = GenericGF.AZTEC_DATA_8;
        } else if (this.f.getNbLayers() <= 22) {
            i = 10;
            genericGF = GenericGF.AZTEC_DATA_10;
        } else {
            i = 12;
            genericGF = GenericGF.AZTEC_DATA_12;
        }
        int nbDatablocks = this.f.getNbDatablocks();
        int length = zArr.length / i;
        if (length >= nbDatablocks) {
            int length2 = zArr.length % i;
            int[] iArr = new int[length];
            int i2 = 0;
            while (i2 < length) {
                iArr[i2] = a(zArr, length2, i);
                i2++;
                length2 += i;
            }
            try {
                new ReedSolomonDecoder(genericGF).decode(iArr, length - nbDatablocks);
                int i3 = (1 << i) - 1;
                int i4 = 0;
                for (int i5 = 0; i5 < nbDatablocks; i5++) {
                    int i6 = iArr[i5];
                    if (i6 == 0 || i6 == i3) {
                        throw FormatException.getFormatInstance();
                    }
                    if (i6 == 1 || i6 == i3 - 1) {
                        i4++;
                    }
                }
                boolean[] zArr2 = new boolean[(nbDatablocks * i) - i4];
                int i7 = 0;
                for (int i8 = 0; i8 < nbDatablocks; i8++) {
                    int i9 = iArr[i8];
                    if (i9 == 1 || i9 == i3 - 1) {
                        Arrays.fill(zArr2, i7, (i7 + i) - 1, i9 > 1);
                        i7 += i - 1;
                    } else {
                        for (int i10 = i - 1; i10 >= 0; i10--) {
                            i7++;
                            zArr2[i7] = ((1 << i10) & i9) != 0;
                        }
                    }
                }
                return zArr2;
            } catch (ReedSolomonException e2) {
                throw FormatException.getFormatInstance(e2);
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private boolean[] a(BitMatrix bitMatrix) {
        boolean isCompact = this.f.isCompact();
        int nbLayers = this.f.getNbLayers();
        int i = (isCompact ? 11 : 14) + (nbLayers << 2);
        int[] iArr = new int[i];
        boolean[] zArr = new boolean[a(nbLayers, isCompact)];
        int i2 = 2;
        if (isCompact) {
            for (int i3 = 0; i3 < iArr.length; i3++) {
                iArr[i3] = i3;
            }
        } else {
            int i4 = i / 2;
            int i5 = ((i + 1) + (((i4 - 1) / 15) * 2)) / 2;
            for (int i6 = 0; i6 < i4; i6++) {
                int i7 = (i6 / 15) + i6;
                iArr[(i4 - i6) - 1] = (i5 - i7) - 1;
                iArr[i4 + i6] = i7 + i5 + 1;
            }
        }
        int i8 = 0;
        int i9 = 0;
        while (i8 < nbLayers) {
            int i10 = ((nbLayers - i8) << i2) + (isCompact ? 9 : 12);
            int i11 = i8 << 1;
            int i12 = (i - 1) - i11;
            int i13 = 0;
            while (i13 < i10) {
                int i14 = i13 << 1;
                int i15 = 0;
                while (i15 < i2) {
                    int i16 = i11 + i15;
                    int i17 = i11 + i13;
                    zArr[i9 + i14 + i15] = bitMatrix.get(iArr[i16], iArr[i17]);
                    int i18 = i12 - i15;
                    zArr[(i10 * 2) + i9 + i14 + i15] = bitMatrix.get(iArr[i17], iArr[i18]);
                    int i19 = i12 - i13;
                    zArr[(i10 * 4) + i9 + i14 + i15] = bitMatrix.get(iArr[i18], iArr[i19]);
                    zArr[(i10 * 6) + i9 + i14 + i15] = bitMatrix.get(iArr[i19], iArr[i16]);
                    i15++;
                    nbLayers = nbLayers;
                    isCompact = isCompact;
                    i2 = 2;
                }
                i13++;
                i2 = 2;
            }
            i9 += i10 << 3;
            i8++;
            i2 = 2;
        }
        return zArr;
    }

    private static int a(boolean[] zArr, int i, int i2) {
        int i3 = 0;
        for (int i4 = i; i4 < i + i2; i4++) {
            i3 <<= 1;
            if (zArr[i4]) {
                i3 |= 1;
            }
        }
        return i3;
    }

    private static byte a(boolean[] zArr, int i) {
        int length = zArr.length - i;
        if (length >= 8) {
            return (byte) a(zArr, i, 8);
        }
        return (byte) (a(zArr, i, length) << (8 - length));
    }

    static byte[] a(boolean[] zArr) {
        byte[] bArr = new byte[(zArr.length + 7) / 8];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = a(zArr, i << 3);
        }
        return bArr;
    }
}
