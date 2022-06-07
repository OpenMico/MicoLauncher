package org.apache.commons.codec.digest;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.Charsets;

/* loaded from: classes5.dex */
public class Sha2Crypt {
    private static final Pattern a = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");

    public static String sha256Crypt(byte[] bArr) {
        return sha256Crypt(bArr, null);
    }

    public static String sha256Crypt(byte[] bArr, String str) {
        if (str == null) {
            str = "$5$" + a.a(8);
        }
        return a(bArr, str, "$5$", 32, MessageDigestAlgorithms.SHA_256);
    }

    private static String a(byte[] bArr, String str, String str2, int i, String str3) {
        boolean z;
        int i2;
        byte b;
        int length = bArr.length;
        if (str != null) {
            Matcher matcher = a.matcher(str);
            if (matcher == null || !matcher.find()) {
                throw new IllegalArgumentException("Invalid salt value: " + str);
            }
            if (matcher.group(3) != null) {
                i2 = Math.max(1000, Math.min(999999999, Integer.parseInt(matcher.group(3))));
                z = true;
            } else {
                i2 = 5000;
                z = false;
            }
            String group = matcher.group(4);
            byte[] bytes = group.getBytes(Charsets.UTF_8);
            int length2 = bytes.length;
            MessageDigest digest = DigestUtils.getDigest(str3);
            digest.update(bArr);
            digest.update(bytes);
            MessageDigest digest2 = DigestUtils.getDigest(str3);
            digest2.update(bArr);
            digest2.update(bytes);
            digest2.update(bArr);
            byte[] digest3 = digest2.digest();
            int length3 = bArr.length;
            while (length3 > i) {
                digest.update(digest3, 0, i);
                length3 -= i;
            }
            digest.update(digest3, 0, length3);
            for (int length4 = bArr.length; length4 > 0; length4 >>= 1) {
                if ((length4 & 1) != 0) {
                    digest.update(digest3, 0, i);
                } else {
                    digest.update(bArr);
                }
            }
            byte[] digest4 = digest.digest();
            MessageDigest digest5 = DigestUtils.getDigest(str3);
            for (int i3 = 1; i3 <= length; i3++) {
                digest5.update(bArr);
            }
            byte[] digest6 = digest5.digest();
            byte[] bArr2 = new byte[length];
            int i4 = 0;
            while (i4 < length - i) {
                System.arraycopy(digest6, 0, bArr2, i4, i);
                i4 += i;
            }
            System.arraycopy(digest6, 0, bArr2, i4, length - i4);
            MessageDigest digest7 = DigestUtils.getDigest(str3);
            for (int i5 = 1; i5 <= (digest4[0] & 255) + 16; i5++) {
                digest7.update(bytes);
            }
            byte[] digest8 = digest7.digest();
            byte[] bArr3 = new byte[length2];
            int i6 = 0;
            MessageDigest messageDigest = digest;
            while (i6 < length2 - i) {
                System.arraycopy(digest8, 0, bArr3, i6, i);
                i6 += i;
            }
            System.arraycopy(digest8, 0, bArr3, i6, length2 - i6);
            int i7 = 0;
            while (i7 <= i2 - 1) {
                MessageDigest digest9 = DigestUtils.getDigest(str3);
                int i8 = i7 & 1;
                if (i8 != 0) {
                    digest9.update(bArr2, 0, length);
                } else {
                    digest9.update(digest4, 0, i);
                }
                if (i7 % 3 != 0) {
                    digest9.update(bArr3, 0, length2);
                }
                if (i7 % 7 != 0) {
                    digest9.update(bArr2, 0, length);
                }
                if (i8 != 0) {
                    digest9.update(digest4, 0, i);
                } else {
                    digest9.update(bArr2, 0, length);
                }
                digest4 = digest9.digest();
                i7++;
                messageDigest = digest9;
            }
            StringBuilder sb = new StringBuilder(str2);
            if (z) {
                sb.append("rounds=");
                sb.append(i2);
                sb.append("$");
            }
            sb.append(group);
            sb.append("$");
            if (i == 32) {
                a.a(digest4[0], digest4[10], digest4[20], 4, sb);
                a.a(digest4[21], digest4[1], digest4[11], 4, sb);
                a.a(digest4[12], digest4[22], digest4[2], 4, sb);
                a.a(digest4[3], digest4[13], digest4[23], 4, sb);
                a.a(digest4[24], digest4[4], digest4[14], 4, sb);
                a.a(digest4[15], digest4[25], digest4[5], 4, sb);
                a.a(digest4[6], digest4[16], digest4[26], 4, sb);
                a.a(digest4[27], digest4[7], digest4[17], 4, sb);
                a.a(digest4[18], digest4[28], digest4[8], 4, sb);
                a.a(digest4[9], digest4[19], digest4[29], 4, sb);
                a.a((byte) 0, digest4[31], digest4[30], 3, sb);
                b = 0;
            } else {
                a.a(digest4[0], digest4[21], digest4[42], 4, sb);
                a.a(digest4[22], digest4[43], digest4[1], 4, sb);
                a.a(digest4[44], digest4[2], digest4[23], 4, sb);
                a.a(digest4[3], digest4[24], digest4[45], 4, sb);
                a.a(digest4[25], digest4[46], digest4[4], 4, sb);
                a.a(digest4[47], digest4[5], digest4[26], 4, sb);
                a.a(digest4[6], digest4[27], digest4[48], 4, sb);
                a.a(digest4[28], digest4[49], digest4[7], 4, sb);
                a.a(digest4[50], digest4[8], digest4[29], 4, sb);
                a.a(digest4[9], digest4[30], digest4[51], 4, sb);
                a.a(digest4[31], digest4[52], digest4[10], 4, sb);
                a.a(digest4[53], digest4[11], digest4[32], 4, sb);
                a.a(digest4[12], digest4[33], digest4[54], 4, sb);
                a.a(digest4[34], digest4[55], digest4[13], 4, sb);
                a.a(digest4[56], digest4[14], digest4[35], 4, sb);
                a.a(digest4[15], digest4[36], digest4[57], 4, sb);
                a.a(digest4[37], digest4[58], digest4[16], 4, sb);
                a.a(digest4[59], digest4[17], digest4[38], 4, sb);
                a.a(digest4[18], digest4[39], digest4[60], 4, sb);
                a.a(digest4[40], digest4[61], digest4[19], 4, sb);
                a.a(digest4[62], digest4[20], digest4[41], 4, sb);
                b = 0;
                a.a((byte) 0, (byte) 0, digest4[63], 2, sb);
            }
            Arrays.fill(digest8, b);
            Arrays.fill(bArr2, b);
            Arrays.fill(bArr3, b);
            messageDigest.reset();
            digest7.reset();
            Arrays.fill(bArr, b);
            Arrays.fill(bytes, b);
            return sb.toString();
        }
        throw new IllegalArgumentException("Salt must not be null");
    }

    public static String sha512Crypt(byte[] bArr) {
        return sha512Crypt(bArr, null);
    }

    public static String sha512Crypt(byte[] bArr, String str) {
        if (str == null) {
            str = "$6$" + a.a(8);
        }
        return a(bArr, str, "$6$", 64, MessageDigestAlgorithms.SHA_512);
    }
}
