package com.google.zxing.client.result;

import androidx.core.net.MailTo;
import com.blankj.utilcode.constant.PermissionConstants;
import com.google.zxing.Result;
import java.util.List;

/* loaded from: classes2.dex */
public final class VEventResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public CalendarParsedResult parse(Result result) {
        double d;
        String massagedText = getMassagedText(result);
        if (massagedText.indexOf("BEGIN:VEVENT") < 0) {
            return null;
        }
        String a = a("SUMMARY", massagedText, true);
        String a2 = a("DTSTART", massagedText, true);
        if (a2 == null) {
            return null;
        }
        String a3 = a("DTEND", massagedText, true);
        String a4 = a("DURATION", massagedText, true);
        String a5 = a(PermissionConstants.LOCATION, massagedText, true);
        String a6 = a(a("ORGANIZER", massagedText, true));
        String[] b = b("ATTENDEE", massagedText, true);
        if (b != null) {
            for (int i = 0; i < b.length; i++) {
                b[i] = a(b[i]);
            }
        }
        String a7 = a("DESCRIPTION", massagedText, true);
        String a8 = a("GEO", massagedText, true);
        double d2 = Double.NaN;
        if (a8 == null) {
            d = Double.NaN;
        } else {
            int indexOf = a8.indexOf(59);
            if (indexOf < 0) {
                return null;
            }
            try {
                d2 = Double.parseDouble(a8.substring(0, indexOf));
                d = Double.parseDouble(a8.substring(indexOf + 1));
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        try {
            return new CalendarParsedResult(a, a2, a3, a4, a5, a6, b, a7, d2, d);
        } catch (IllegalArgumentException unused2) {
            return null;
        }
    }

    private static String a(CharSequence charSequence, String str, boolean z) {
        List<String> b = VCardResultParser.b(charSequence, str, z, false);
        if (b == null || b.isEmpty()) {
            return null;
        }
        return b.get(0);
    }

    private static String[] b(CharSequence charSequence, String str, boolean z) {
        List<List<String>> a = VCardResultParser.a(charSequence, str, z, false);
        if (a == null || a.isEmpty()) {
            return null;
        }
        int size = a.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = a.get(i).get(0);
        }
        return strArr;
    }

    private static String a(String str) {
        return str != null ? (str.startsWith(MailTo.MAILTO_SCHEME) || str.startsWith("MAILTO:")) ? str.substring(7) : str : str;
    }
}
