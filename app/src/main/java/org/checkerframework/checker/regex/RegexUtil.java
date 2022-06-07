package org.checkerframework.checker.regex;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.checkerframework.checker.regex.qual.Regex;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.framework.qual.EnsuresQualifierIf;

/* loaded from: classes5.dex */
public final class RegexUtil {
    private RegexUtil() {
        throw new Error("do not instantiate");
    }

    /* loaded from: classes5.dex */
    public static class CheckedPatternSyntaxException extends Exception {
        private static final long serialVersionUID = 6266881831979001480L;
        private final PatternSyntaxException pse;

        public CheckedPatternSyntaxException(PatternSyntaxException patternSyntaxException) {
            this.pse = patternSyntaxException;
        }

        public CheckedPatternSyntaxException(String str, String str2, int i) {
            this(new PatternSyntaxException(str, str2, i));
        }

        public String getDescription() {
            return this.pse.getDescription();
        }

        public int getIndex() {
            return this.pse.getIndex();
        }

        @Override // java.lang.Throwable
        @Pure
        public String getMessage() {
            return this.pse.getMessage();
        }

        public String getPattern() {
            return this.pse.getPattern();
        }
    }

    @EnsuresQualifierIf(expression = {"#1"}, qualifier = Regex.class, result = true)
    @Pure
    public static boolean isRegex(String str) {
        return isRegex(str, 0);
    }

    @EnsuresQualifierIf(expression = {"#1"}, qualifier = Regex.class, result = true)
    @Pure
    public static boolean isRegex(String str, int i) {
        try {
            return a(Pattern.compile(str)) >= i;
        } catch (PatternSyntaxException unused) {
            return false;
        }
    }

    @EnsuresQualifierIf(expression = {"#1"}, qualifier = Regex.class, result = true)
    @Pure
    public static boolean isRegex(char c) {
        return isRegex(Character.toString(c));
    }

    @SideEffectFree
    public static String regexError(String str) {
        return regexError(str, 0);
    }

    @SideEffectFree
    public static String regexError(String str, int i) {
        try {
            int a = a(Pattern.compile(str));
            if (a < i) {
                return a(str, i, a);
            }
            return null;
        } catch (PatternSyntaxException e) {
            return e.getMessage();
        }
    }

    @SideEffectFree
    public static PatternSyntaxException regexException(String str) {
        return regexException(str, 0);
    }

    @SideEffectFree
    public static PatternSyntaxException regexException(String str, int i) {
        try {
            int a = a(Pattern.compile(str));
            if (a < i) {
                return new PatternSyntaxException(a(str, i, a), str, -1);
            }
            return null;
        } catch (PatternSyntaxException e) {
            return e;
        }
    }

    @SideEffectFree
    public static String asRegex(String str) {
        return asRegex(str, 0);
    }

    @SideEffectFree
    public static String asRegex(String str, int i) {
        try {
            int a = a(Pattern.compile(str));
            if (a >= i) {
                return str;
            }
            throw new Error(a(str, i, a));
        } catch (PatternSyntaxException e) {
            throw new Error(e);
        }
    }

    @SideEffectFree
    private static String a(String str, int i, int i2) {
        return "regex \"" + str + "\" has " + i2 + " groups, but " + i + " groups are needed.";
    }

    @Pure
    private static int a(Pattern pattern) {
        return pattern.matcher("").groupCount();
    }
}
