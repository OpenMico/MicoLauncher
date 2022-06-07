package com.xiaomi.miplay.mylibrary.utils;

import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class ByteUtils {
    private static final String a = "ByteUtils";
    private static char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static int getCrc16(String str) {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        int i = 0;
        int i2 = 65535;
        while (i < length) {
            int i3 = ((i2 & 255) ^ (bytes[i] & 255)) | (65280 & i2);
            for (int i4 = 0; i4 < 8; i4++) {
                i3 = (i3 & 1) > 0 ? (i3 >> 1) ^ 40961 : i3 >> 1;
            }
            i++;
            i2 = i3;
        }
        return i2 & 65535;
    }

    public static String toPrintString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 0; i < bArr.length; i++) {
            sb.append(Integer.toHexString(bArr[i] & 255));
            if (i < bArr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    private ByteUtils() {
    }

    public static int byteToInt(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i += (bArr[i2] & 255) << (((bArr.length - 1) - i2) * 8);
        }
        return i;
    }

    public static int getPort(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return -1;
        }
        return byteToInt(new byte[]{bArr[0], bArr[1]});
    }

    public static int getDeviceKeyLen(byte[] bArr, int i) {
        try {
            byte[] bArr2 = new byte[1];
            for (int i2 = 0; i2 < bArr2.length; i2++) {
                bArr2[i2] = bArr[i2 + i];
            }
            return bArr2[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.w(a, "Exception锛�" + e, new Object[0]);
            return -1;
        }
    }

    public static int getDeviceValueInfoLen(byte[] bArr, int i, int i2) {
        try {
            byte[] bArr2 = new byte[i2];
            for (int i3 = 0; i3 < bArr2.length; i3++) {
                bArr2[i3] = bArr[i3 + i];
            }
            return byteToInt(bArr2);
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.w(a, "Exception锛�" + e, new Object[0]);
            return -1;
        }
    }

    public static int getDeviceTypeLen(byte[] bArr, int i) {
        try {
            byte[] bArr2 = new byte[1];
            for (int i2 = 0; i2 < bArr2.length; i2++) {
                bArr2[i2] = bArr[i2 + i];
            }
            return bArr2[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.w(a, "Exception锛�" + e, new Object[0]);
            return -1;
        }
    }

    public static String getDeviceValue(byte[] bArr, int i, int i2) {
        try {
            byte[] bArr2 = new byte[i];
            for (int i3 = 0; i3 < bArr2.length; i3++) {
                bArr2[i3] = bArr[i3 + i2];
            }
            try {
                return new String(bArr2, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        } catch (ArrayIndexOutOfBoundsException e2) {
            Logger.w(a, "Exception锛�" + e2, new Object[0]);
            return "";
        }
    }

    public static String bytes2HexString(byte[] bArr, int i) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(byte2HexString(bArr[i2]) + StringUtils.SPACE);
        }
        return stringBuffer.toString();
    }

    public static String byte2HexString(byte b2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(b[(b2 >>> 4) & 15]);
        stringBuffer.append(b[b2 & 15]);
        return stringBuffer.toString();
    }
}
