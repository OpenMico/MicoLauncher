package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.xiaomi.mipush.sdk.Constants;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public enum CaseFormat {
    LOWER_HYPHEN(CharMatcher.is('-'), Constants.ACCEPT_TIME_SEPARATOR_SERVER) {
        @Override // com.google.common.base.CaseFormat
        String a(String str) {
            return Ascii.toLowerCase(str);
        }

        @Override // com.google.common.base.CaseFormat
        String a(CaseFormat caseFormat, String str) {
            if (caseFormat == LOWER_UNDERSCORE) {
                return str.replace('-', '_');
            }
            if (caseFormat == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(str.replace('-', '_'));
            }
            return CaseFormat.super.a(caseFormat, str);
        }
    },
    LOWER_UNDERSCORE(CharMatcher.is('_'), "_") {
        @Override // com.google.common.base.CaseFormat
        String a(String str) {
            return Ascii.toLowerCase(str);
        }

        @Override // com.google.common.base.CaseFormat
        String a(CaseFormat caseFormat, String str) {
            if (caseFormat == LOWER_HYPHEN) {
                return str.replace('_', '-');
            }
            if (caseFormat == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(str);
            }
            return CaseFormat.super.a(caseFormat, str);
        }
    },
    LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
        @Override // com.google.common.base.CaseFormat
        String a(String str) {
            return CaseFormat.d(str);
        }
    },
    UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
        @Override // com.google.common.base.CaseFormat
        String a(String str) {
            return CaseFormat.d(str);
        }
    },
    UPPER_UNDERSCORE(CharMatcher.is('_'), "_") {
        @Override // com.google.common.base.CaseFormat
        String a(String str) {
            return Ascii.toUpperCase(str);
        }

        @Override // com.google.common.base.CaseFormat
        String a(CaseFormat caseFormat, String str) {
            if (caseFormat == LOWER_HYPHEN) {
                return Ascii.toLowerCase(str.replace('_', '-'));
            }
            if (caseFormat == LOWER_UNDERSCORE) {
                return Ascii.toLowerCase(str);
            }
            return CaseFormat.super.a(caseFormat, str);
        }
    };
    
    private final CharMatcher wordBoundary;
    private final String wordSeparator;

    abstract String a(String str);

    CaseFormat(CharMatcher charMatcher, String str) {
        this.wordBoundary = charMatcher;
        this.wordSeparator = str;
    }

    public final String to(CaseFormat caseFormat, String str) {
        Preconditions.checkNotNull(caseFormat);
        Preconditions.checkNotNull(str);
        return caseFormat == this ? str : a(caseFormat, str);
    }

    String a(CaseFormat caseFormat, String str) {
        StringBuilder sb = null;
        int i = 0;
        int i2 = -1;
        while (true) {
            i2 = this.wordBoundary.indexIn(str, i2 + 1);
            if (i2 == -1) {
                break;
            }
            if (i == 0) {
                sb = new StringBuilder(str.length() + (this.wordSeparator.length() * 4));
                sb.append(caseFormat.c(str.substring(i, i2)));
            } else {
                sb.append(caseFormat.a(str.substring(i, i2)));
            }
            sb.append(caseFormat.wordSeparator);
            i = this.wordSeparator.length() + i2;
        }
        if (i == 0) {
            return caseFormat.c(str);
        }
        sb.append(caseFormat.a(str.substring(i)));
        return sb.toString();
    }

    public Converter<String, String> converterTo(CaseFormat caseFormat) {
        return new a(this, caseFormat);
    }

    /* loaded from: classes2.dex */
    private static final class a extends Converter<String, String> implements Serializable {
        private static final long serialVersionUID = 0;
        private final CaseFormat sourceFormat;
        private final CaseFormat targetFormat;

        a(CaseFormat caseFormat, CaseFormat caseFormat2) {
            this.sourceFormat = (CaseFormat) Preconditions.checkNotNull(caseFormat);
            this.targetFormat = (CaseFormat) Preconditions.checkNotNull(caseFormat2);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public String doForward(String str) {
            return this.sourceFormat.to(this.targetFormat, str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public String doBackward(String str) {
            return this.targetFormat.to(this.sourceFormat, str);
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.sourceFormat.equals(aVar.sourceFormat) && this.targetFormat.equals(aVar.targetFormat);
        }

        public int hashCode() {
            return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
        }

        public String toString() {
            return this.sourceFormat + ".converterTo(" + this.targetFormat + ")";
        }
    }

    private String c(String str) {
        return this == LOWER_CAMEL ? Ascii.toLowerCase(str) : a(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return Ascii.toUpperCase(str.charAt(0)) + Ascii.toLowerCase(str.substring(1));
    }
}
