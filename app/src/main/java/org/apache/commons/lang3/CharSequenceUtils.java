package org.apache.commons.lang3;

/* loaded from: classes5.dex */
public class CharSequenceUtils {
    public static CharSequence subSequence(CharSequence charSequence, int i) {
        if (charSequence == null) {
            return null;
        }
        return charSequence.subSequence(i, charSequence.length());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(CharSequence charSequence, int i, int i2) {
        if (charSequence instanceof String) {
            return ((String) charSequence).indexOf(i, i2);
        }
        int length = charSequence.length();
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < length) {
            if (charSequence.charAt(i2) == i) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(CharSequence charSequence, CharSequence charSequence2, int i) {
        return charSequence.toString().indexOf(charSequence2.toString(), i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b(CharSequence charSequence, int i, int i2) {
        if (charSequence instanceof String) {
            return ((String) charSequence).lastIndexOf(i, i2);
        }
        int length = charSequence.length();
        if (i2 < 0) {
            return -1;
        }
        if (i2 >= length) {
            i2 = length - 1;
        }
        while (i2 >= 0) {
            if (charSequence.charAt(i2) == i) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b(CharSequence charSequence, CharSequence charSequence2, int i) {
        return charSequence.toString().lastIndexOf(charSequence2.toString(), i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static char[] a(CharSequence charSequence) {
        if (charSequence instanceof String) {
            return ((String) charSequence).toCharArray();
        }
        int length = charSequence.length();
        char[] cArr = new char[charSequence.length()];
        for (int i = 0; i < length; i++) {
            cArr[i] = charSequence.charAt(i);
        }
        return cArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(CharSequence charSequence, boolean z, int i, CharSequence charSequence2, int i2, int i3) {
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return ((String) charSequence).regionMatches(z, i, (String) charSequence2, i2, i3);
        }
        int length = charSequence.length() - i;
        int length2 = charSequence2.length() - i2;
        if (i < 0 || i2 < 0 || i3 < 0 || length < i3 || length2 < i3) {
            return false;
        }
        while (true) {
            i3--;
            if (i3 <= 0) {
                return true;
            }
            i++;
            char charAt = charSequence.charAt(i);
            i2++;
            char charAt2 = charSequence2.charAt(i2);
            if (charAt != charAt2) {
                if (!z) {
                    return false;
                }
                if (!(Character.toUpperCase(charAt) == Character.toUpperCase(charAt2) || Character.toLowerCase(charAt) == Character.toLowerCase(charAt2))) {
                    return false;
                }
            }
        }
    }
}
