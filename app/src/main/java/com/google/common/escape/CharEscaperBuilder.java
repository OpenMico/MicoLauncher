package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Map;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public final class CharEscaperBuilder {
    private int b = -1;
    private final Map<Character, String> a = new HashMap();

    /* loaded from: classes2.dex */
    private static class a extends CharEscaper {
        private final char[][] a;
        private final int b;

        a(char[][] cArr) {
            this.a = cArr;
            this.b = cArr.length;
        }

        @Override // com.google.common.escape.CharEscaper, com.google.common.escape.Escaper
        public String escape(String str) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                char[][] cArr = this.a;
                if (charAt < cArr.length && cArr[charAt] != null) {
                    return escapeSlow(str, i);
                }
            }
            return str;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.escape.CharEscaper
        public char[] escape(char c) {
            if (c < this.b) {
                return this.a[c];
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CanIgnoreReturnValue
    public CharEscaperBuilder addEscape(char c, String str) {
        this.a.put(Character.valueOf(c), Preconditions.checkNotNull(str));
        if (c > this.b) {
            this.b = c;
        }
        return this;
    }

    @CanIgnoreReturnValue
    public CharEscaperBuilder addEscapes(char[] cArr, String str) {
        Preconditions.checkNotNull(str);
        for (char c : cArr) {
            addEscape(c, str);
        }
        return this;
    }

    public char[][] toArray() {
        char[][] cArr = new char[this.b + 1];
        for (Map.Entry<Character, String> entry : this.a.entrySet()) {
            cArr[entry.getKey().charValue()] = entry.getValue().toCharArray();
        }
        return cArr;
    }

    public Escaper toEscaper() {
        return new a(toArray());
    }
}
