package org.seamless.util.io;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/* loaded from: classes4.dex */
public class MD5Crypt {
    private static final String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final String itoa64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final int bytes2u(byte b) {
        return b & 255;
    }

    private static final String to64(long j, int i) {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            i--;
            if (i < 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(itoa64.charAt((int) (63 & j)));
            j >>>= 6;
        }
    }

    private static final void clearbits(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = 0;
        }
    }

    public static final String crypt(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        while (stringBuffer.length() < 8) {
            int nextFloat = (int) (random.nextFloat() * 62);
            stringBuffer.append(SALTCHARS.substring(nextFloat, nextFloat + 1));
        }
        return crypt(str, stringBuffer.toString(), "$1$");
    }

    public static final String crypt(String str, String str2) {
        return crypt(str, str2, "$1$");
    }

    public static final String crypt(String str, String str2, String str3) {
        try {
            MessageDigest instance = MessageDigest.getInstance("md5");
            MessageDigest instance2 = MessageDigest.getInstance("md5");
            if (str2.startsWith(str3)) {
                str2 = str2.substring(str3.length());
            }
            if (str2.indexOf(36) != -1) {
                str2 = str2.substring(0, str2.indexOf(36));
            }
            if (str2.length() > 8) {
                str2 = str2.substring(0, 8);
            }
            instance.update(str.getBytes());
            instance.update(str3.getBytes());
            instance.update(str2.getBytes());
            instance2.update(str.getBytes());
            instance2.update(str2.getBytes());
            instance2.update(str.getBytes());
            byte[] digest = instance2.digest();
            int length = str.length();
            while (true) {
                int i = 16;
                if (length <= 0) {
                    break;
                }
                if (length <= 16) {
                    i = length;
                }
                instance.update(digest, 0, i);
                length -= 16;
            }
            clearbits(digest);
            for (int length2 = str.length(); length2 != 0; length2 >>>= 1) {
                if ((length2 & 1) != 0) {
                    instance.update(digest, 0, 1);
                } else {
                    instance.update(str.getBytes(), 0, 1);
                }
            }
            byte[] digest2 = instance.digest();
            for (int i2 = 0; i2 < 1000; i2++) {
                try {
                    MessageDigest instance3 = MessageDigest.getInstance("md5");
                    int i3 = i2 & 1;
                    if (i3 != 0) {
                        instance3.update(str.getBytes());
                    } else {
                        instance3.update(digest2, 0, 16);
                    }
                    if (i2 % 3 != 0) {
                        instance3.update(str2.getBytes());
                    }
                    if (i2 % 7 != 0) {
                        instance3.update(str.getBytes());
                    }
                    if (i3 != 0) {
                        instance3.update(digest2, 0, 16);
                    } else {
                        instance3.update(str.getBytes());
                    }
                    digest2 = instance3.digest();
                } catch (NoSuchAlgorithmException unused) {
                    return null;
                }
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str3);
            stringBuffer.append(str2);
            stringBuffer.append("$");
            stringBuffer.append(to64((bytes2u(digest2[0]) << 16) | (bytes2u(digest2[6]) << 8) | bytes2u(digest2[12]), 4));
            stringBuffer.append(to64((bytes2u(digest2[1]) << 16) | (bytes2u(digest2[7]) << 8) | bytes2u(digest2[13]), 4));
            stringBuffer.append(to64((bytes2u(digest2[2]) << 16) | (bytes2u(digest2[8]) << 8) | bytes2u(digest2[14]), 4));
            stringBuffer.append(to64((bytes2u(digest2[3]) << 16) | (bytes2u(digest2[9]) << 8) | bytes2u(digest2[15]), 4));
            stringBuffer.append(to64((bytes2u(digest2[4]) << 16) | (bytes2u(digest2[10]) << 8) | bytes2u(digest2[5]), 4));
            stringBuffer.append(to64(bytes2u(digest2[11]), 2));
            clearbits(digest2);
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e);
            return null;
        }
    }

    public static boolean isEqual(String str, String str2) {
        return isEqual(str.toCharArray(), str2);
    }

    public static boolean isEqual(char[] cArr, String str) {
        String[] split = str.split("\\$");
        if (split.length != 4) {
            return false;
        }
        char[] charArray = str.toCharArray();
        char[] charArray2 = crypt(new String(cArr), split[2], "$" + split[1] + "$").toCharArray();
        if (charArray == null || charArray2 == null) {
            return charArray == charArray2;
        }
        if (charArray.length != charArray2.length) {
            return false;
        }
        boolean z = true;
        for (int i = 0; i < charArray.length && z; i++) {
            z = charArray[i] == charArray2[i];
        }
        return z;
    }
}
