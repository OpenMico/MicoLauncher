package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes2.dex */
public final class AddressBookDoCoMoResultParser extends a {
    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String[] a;
        String massagedText = getMassagedText(result);
        if (!massagedText.startsWith("MECARD:") || (a = a("N:", massagedText, true)) == null) {
            return null;
        }
        String a2 = a(a[0]);
        String b = b("SOUND:", massagedText, true);
        String[] a3 = a("TEL:", massagedText, true);
        String[] a4 = a("EMAIL:", massagedText, true);
        String b2 = b("NOTE:", massagedText, false);
        String[] a5 = a("ADR:", massagedText, true);
        String b3 = b("BDAY:", massagedText, true);
        return new AddressBookParsedResult(maybeWrap(a2), null, b, a3, null, a4, null, null, b2, a5, null, b("ORG:", massagedText, true), !isStringOfDigits(b3, 8) ? null : b3, null, a("URL:", massagedText, true), null);
    }

    private static String a(String str) {
        int indexOf = str.indexOf(44);
        if (indexOf < 0) {
            return str;
        }
        return str.substring(indexOf + 1) + ' ' + str.substring(0, indexOf);
    }
}
