package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class URIResultParser extends ResultParser {
    private static final Pattern a = Pattern.compile("[a-zA-Z][a-zA-Z0-9+-.]+:");
    private static final Pattern b = Pattern.compile("([a-zA-Z0-9\\-]+\\.){1,6}[a-zA-Z]{2,}(:\\d{1,5})?(/|\\?|$)");

    @Override // com.google.zxing.client.result.ResultParser
    public URIParsedResult parse(Result result) {
        String massagedText = getMassagedText(result);
        if (massagedText.startsWith("URL:") || massagedText.startsWith("URI:")) {
            return new URIParsedResult(massagedText.substring(4).trim(), null);
        }
        String trim = massagedText.trim();
        if (a(trim)) {
            return new URIParsedResult(trim, null);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(String str) {
        if (str.contains(StringUtils.SPACE)) {
            return false;
        }
        Matcher matcher = a.matcher(str);
        if (matcher.find() && matcher.start() == 0) {
            return true;
        }
        Matcher matcher2 = b.matcher(str);
        return matcher2.find() && matcher2.start() == 0;
    }
}
