package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class BizcardResultParser extends a {
    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String massagedText = getMassagedText(result);
        if (!massagedText.startsWith("BIZCARD:")) {
            return null;
        }
        String a = a(b("N:", massagedText, true), b("X:", massagedText, true));
        String b = b("T:", massagedText, true);
        String b2 = b("C:", massagedText, true);
        return new AddressBookParsedResult(maybeWrap(a), null, null, a(b("B:", massagedText, true), b("M:", massagedText, true), b("F:", massagedText, true)), null, maybeWrap(b("E:", massagedText, true)), null, null, null, a("A:", massagedText, true), null, b2, null, b, null, null);
    }

    private static String[] a(String str, String str2, String str3) {
        ArrayList arrayList = new ArrayList(3);
        if (str != null) {
            arrayList.add(str);
        }
        if (str2 != null) {
            arrayList.add(str2);
        }
        if (str3 != null) {
            arrayList.add(str3);
        }
        int size = arrayList.size();
        if (size == 0) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[size]);
    }

    private static String a(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        return str + ' ' + str2;
    }
}
