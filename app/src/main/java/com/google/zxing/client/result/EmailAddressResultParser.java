package com.google.zxing.client.result;

import androidx.core.net.MailTo;
import com.google.zxing.Result;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class EmailAddressResultParser extends ResultParser {
    private static final Pattern a = Pattern.compile(Constants.ACCEPT_TIME_SEPARATOR_SP);

    @Override // com.google.zxing.client.result.ResultParser
    public EmailAddressParsedResult parse(Result result) {
        String str;
        String str2;
        String[] strArr;
        String str3;
        String massagedText = getMassagedText(result);
        String[] strArr2 = null;
        if (massagedText.startsWith(MailTo.MAILTO_SCHEME) || massagedText.startsWith("MAILTO:")) {
            String substring = massagedText.substring(7);
            int indexOf = substring.indexOf(63);
            if (indexOf >= 0) {
                substring = substring.substring(0, indexOf);
            }
            try {
                String c = c(substring);
                String[] split = !c.isEmpty() ? a.split(c) : null;
                Map<String, String> b = b(massagedText);
                if (b != null) {
                    if (split == null && (str3 = b.get("to")) != null) {
                        split = a.split(str3);
                    }
                    String str4 = b.get("cc");
                    strArr = str4 != null ? a.split(str4) : null;
                    String str5 = b.get("bcc");
                    if (str5 != null) {
                        strArr2 = a.split(str5);
                    }
                    str2 = b.get("subject");
                    str = b.get("body");
                } else {
                    split = split;
                    strArr = null;
                    strArr2 = null;
                    str2 = null;
                    str = null;
                }
                return new EmailAddressParsedResult(split, strArr, strArr2, str2, str);
            } catch (IllegalArgumentException unused) {
                return null;
            }
        } else if (!EmailDoCoMoResultParser.a(massagedText)) {
            return null;
        } else {
            return new EmailAddressParsedResult(massagedText);
        }
    }
}
