package org.apache.commons.text;

import java.util.Formattable;
import java.util.Formatter;

/* loaded from: classes5.dex */
public class FormattableUtils {
    public static String toString(Formattable formattable) {
        return String.format("%s", formattable);
    }

    public static Formatter append(CharSequence charSequence, Formatter formatter, int i, int i2, int i3) {
        return append(charSequence, formatter, i, i2, i3, ' ', null);
    }

    public static Formatter append(CharSequence charSequence, Formatter formatter, int i, int i2, int i3, char c) {
        return append(charSequence, formatter, i, i2, i3, c, null);
    }

    public static Formatter append(CharSequence charSequence, Formatter formatter, int i, int i2, int i3, CharSequence charSequence2) {
        return append(charSequence, formatter, i, i2, i3, ' ', charSequence2);
    }

    public static Formatter append(CharSequence charSequence, Formatter formatter, int i, int i2, int i3, char c, CharSequence charSequence2) {
        boolean z = true;
        if (charSequence2 == null || i3 < 0 || charSequence2.length() <= i3) {
            StringBuilder sb = new StringBuilder(charSequence);
            if (i3 >= 0 && i3 < charSequence.length()) {
                if (charSequence2 == null) {
                    charSequence2 = "";
                }
                sb.replace(i3 - charSequence2.length(), charSequence.length(), charSequence2.toString());
            }
            if ((i & 1) != 1) {
                z = false;
            }
            for (int length = sb.length(); length < i2; length++) {
                sb.insert(z ? length : 0, c);
            }
            formatter.format(sb.toString(), new Object[0]);
            return formatter;
        }
        throw new IllegalArgumentException(String.format("Specified ellipsis '%1$s' exceeds precision of %2$s", charSequence2, Integer.valueOf(i3)));
    }
}
