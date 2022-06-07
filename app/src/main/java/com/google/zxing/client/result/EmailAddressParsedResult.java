package com.google.zxing.client.result;

import androidx.core.net.MailTo;

/* loaded from: classes2.dex */
public final class EmailAddressParsedResult extends ParsedResult {
    private final String[] a;
    private final String[] b;
    private final String[] c;
    private final String d;
    private final String e;

    @Deprecated
    public String getMailtoURI() {
        return MailTo.MAILTO_SCHEME;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EmailAddressParsedResult(String str) {
        this(new String[]{str}, null, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EmailAddressParsedResult(String[] strArr, String[] strArr2, String[] strArr3, String str, String str2) {
        super(ParsedResultType.EMAIL_ADDRESS);
        this.a = strArr;
        this.b = strArr2;
        this.c = strArr3;
        this.d = str;
        this.e = str2;
    }

    @Deprecated
    public String getEmailAddress() {
        String[] strArr = this.a;
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        return strArr[0];
    }

    public String[] getTos() {
        return this.a;
    }

    public String[] getCCs() {
        return this.b;
    }

    public String[] getBCCs() {
        return this.c;
    }

    public String getSubject() {
        return this.d;
    }

    public String getBody() {
        return this.e;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(30);
        maybeAppend(this.a, sb);
        maybeAppend(this.b, sb);
        maybeAppend(this.c, sb);
        maybeAppend(this.d, sb);
        maybeAppend(this.e, sb);
        return sb.toString();
    }
}
