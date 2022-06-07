package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class EmailDoCoMoResultParser extends a {
    private static final Pattern a = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");

    @Override // com.google.zxing.client.result.ResultParser
    public EmailAddressParsedResult parse(Result result) {
        String[] a2;
        String massagedText = getMassagedText(result);
        if (!massagedText.startsWith("MATMSG:") || (a2 = a("TO:", massagedText, true)) == null) {
            return null;
        }
        for (String str : a2) {
            if (!a(str)) {
                return null;
            }
        }
        return new EmailAddressParsedResult(a2, null, null, b("SUB:", massagedText, false), b("BODY:", massagedText, false));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(String str) {
        return str != null && a.matcher(str).matches() && str.indexOf(64) >= 0;
    }
}
