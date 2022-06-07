package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.CharCompanionObject;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public final class Escapers {
    private static final Escaper a = new CharEscaper() { // from class: com.google.common.escape.Escapers.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.escape.CharEscaper
        public char[] escape(char c) {
            return null;
        }

        @Override // com.google.common.escape.CharEscaper, com.google.common.escape.Escaper
        public String escape(String str) {
            return (String) Preconditions.checkNotNull(str);
        }
    };

    private Escapers() {
    }

    public static Escaper nullEscaper() {
        return a;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Beta
    /* loaded from: classes2.dex */
    public static final class Builder {
        private final Map<Character, String> a;
        private char b;
        private char c;
        private String d;

        private Builder() {
            this.a = new HashMap();
            this.b = (char) 0;
            this.c = CharCompanionObject.MAX_VALUE;
            this.d = null;
        }

        @CanIgnoreReturnValue
        public Builder setSafeRange(char c, char c2) {
            this.b = c;
            this.c = c2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUnsafeReplacement(@NullableDecl String str) {
            this.d = str;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addEscape(char c, String str) {
            Preconditions.checkNotNull(str);
            this.a.put(Character.valueOf(c), str);
            return this;
        }

        public Escaper build() {
            return new ArrayBasedCharEscaper(this.a, this.b, this.c) { // from class: com.google.common.escape.Escapers.Builder.1
                private final char[] b;

                {
                    this.b = Builder.this.d != null ? Builder.this.d.toCharArray() : null;
                }

                @Override // com.google.common.escape.ArrayBasedCharEscaper
                protected char[] escapeUnsafe(char c) {
                    return this.b;
                }
            };
        }
    }

    public static String computeReplacement(CharEscaper charEscaper, char c) {
        return a(charEscaper.escape(c));
    }

    public static String computeReplacement(UnicodeEscaper unicodeEscaper, int i) {
        return a(unicodeEscaper.escape(i));
    }

    private static String a(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        return new String(cArr);
    }
}
