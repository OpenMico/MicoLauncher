package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class AddressBookAUResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String massagedText = getMassagedText(result);
        String[] strArr = null;
        if (!massagedText.contains("MEMORY") || !massagedText.contains("\r\n")) {
            return null;
        }
        String b = b("NAME1:", massagedText, '\r', true);
        String b2 = b("NAME2:", massagedText, '\r', true);
        String[] a = a("TEL", 3, massagedText, true);
        String[] a2 = a("MAIL", 3, massagedText, true);
        String b3 = b("MEMORY:", massagedText, '\r', false);
        String b4 = b("ADD:", massagedText, '\r', true);
        if (b4 != null) {
            strArr = new String[]{b4};
        }
        return new AddressBookParsedResult(maybeWrap(b), null, b2, a, null, a2, null, null, b3, strArr, null, null, null, null, null, null);
    }

    private static String[] a(String str, int i, String str2, boolean z) {
        ArrayList arrayList = null;
        for (int i2 = 1; i2 <= i; i2++) {
            String b = b(str + i2 + ':', str2, '\r', z);
            if (b == null) {
                break;
            }
            if (arrayList == null) {
                arrayList = new ArrayList(i);
            }
            arrayList.add(b);
        }
        if (arrayList == null) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
