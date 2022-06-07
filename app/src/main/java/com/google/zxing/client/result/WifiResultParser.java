package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes2.dex */
public final class WifiResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public WifiParsedResult parse(Result result) {
        String substring;
        String b;
        String massagedText = getMassagedText(result);
        if (!massagedText.startsWith("WIFI:") || (b = b("S:", (substring = massagedText.substring(5)), ';', false)) == null || b.isEmpty()) {
            return null;
        }
        String b2 = b("P:", substring, ';', false);
        String b3 = b("T:", substring, ';', false);
        return new WifiParsedResult(b3 == null ? "nopass" : b3, b, b2, Boolean.parseBoolean(b("H:", substring, ';', false)), b("I:", substring, ';', false), b("A:", substring, ';', false), b("E:", substring, ';', false), b("H:", substring, ';', false));
    }
}
