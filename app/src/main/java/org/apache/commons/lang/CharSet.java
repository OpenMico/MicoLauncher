package org.apache.commons.lang;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class CharSet implements Serializable {
    private static final long serialVersionUID = 5947847346149275958L;
    private final Set set = Collections.synchronizedSet(new HashSet());
    public static final CharSet EMPTY = new CharSet((String) null);
    public static final CharSet ASCII_ALPHA = new CharSet("a-zA-Z");
    public static final CharSet ASCII_ALPHA_LOWER = new CharSet("a-z");
    public static final CharSet ASCII_ALPHA_UPPER = new CharSet("A-Z");
    public static final CharSet ASCII_NUMERIC = new CharSet("0-9");
    protected static final Map COMMON = Collections.synchronizedMap(new HashMap());

    static {
        COMMON.put(null, EMPTY);
        COMMON.put("", EMPTY);
        COMMON.put("a-zA-Z", ASCII_ALPHA);
        COMMON.put("A-Za-z", ASCII_ALPHA);
        COMMON.put("a-z", ASCII_ALPHA_LOWER);
        COMMON.put("A-Z", ASCII_ALPHA_UPPER);
        COMMON.put("0-9", ASCII_NUMERIC);
    }

    public static CharSet getInstance(String str) {
        Object obj = COMMON.get(str);
        if (obj != null) {
            return (CharSet) obj;
        }
        return new CharSet(str);
    }

    public static CharSet getInstance(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        return new CharSet(strArr);
    }

    protected CharSet(String str) {
        add(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CharSet(String[] strArr) {
        for (String str : strArr) {
            add(str);
        }
    }

    protected void add(String str) {
        if (str != null) {
            int length = str.length();
            int i = 0;
            while (i < length) {
                int i2 = length - i;
                if (i2 >= 4 && str.charAt(i) == '^' && str.charAt(i + 2) == '-') {
                    this.set.add(CharRange.isNotIn(str.charAt(i + 1), str.charAt(i + 3)));
                    i += 4;
                } else if (i2 >= 3 && str.charAt(i + 1) == '-') {
                    this.set.add(CharRange.isIn(str.charAt(i), str.charAt(i + 2)));
                    i += 3;
                } else if (i2 < 2 || str.charAt(i) != '^') {
                    this.set.add(CharRange.is(str.charAt(i)));
                    i++;
                } else {
                    this.set.add(CharRange.isNot(str.charAt(i + 1)));
                    i += 2;
                }
            }
        }
    }

    public CharRange[] getCharRanges() {
        Set set = this.set;
        return (CharRange[]) set.toArray(new CharRange[set.size()]);
    }

    public boolean contains(char c) {
        for (CharRange charRange : this.set) {
            if (charRange.contains(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CharSet)) {
            return false;
        }
        return this.set.equals(((CharSet) obj).set);
    }

    public int hashCode() {
        return this.set.hashCode() + 89;
    }

    public String toString() {
        return this.set.toString();
    }
}
