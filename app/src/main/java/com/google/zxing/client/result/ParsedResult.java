package com.google.zxing.client.result;

/* loaded from: classes2.dex */
public abstract class ParsedResult {
    private final ParsedResultType a;

    public abstract String getDisplayResult();

    /* JADX INFO: Access modifiers changed from: protected */
    public ParsedResult(ParsedResultType parsedResultType) {
        this.a = parsedResultType;
    }

    public final ParsedResultType getType() {
        return this.a;
    }

    public final String toString() {
        return getDisplayResult();
    }

    public static void maybeAppend(String str, StringBuilder sb) {
        if (str != null && !str.isEmpty()) {
            if (sb.length() > 0) {
                sb.append('\n');
            }
            sb.append(str);
        }
    }

    public static void maybeAppend(String[] strArr, StringBuilder sb) {
        if (strArr != null) {
            for (String str : strArr) {
                maybeAppend(str, sb);
            }
        }
    }
}
