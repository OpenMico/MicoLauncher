package com.google.zxing.client.android;

import android.content.Intent;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.Intents;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class DecodeFormatManager {
    private static final Pattern g = Pattern.compile(Constants.ACCEPT_TIME_SEPARATOR_SP);
    static final Set<BarcodeFormat> c = EnumSet.of(BarcodeFormat.QR_CODE);
    static final Set<BarcodeFormat> d = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    static final Set<BarcodeFormat> e = EnumSet.of(BarcodeFormat.AZTEC);
    static final Set<BarcodeFormat> f = EnumSet.of(BarcodeFormat.PDF_417);
    static final Set<BarcodeFormat> a = EnumSet.of(BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED);
    static final Set<BarcodeFormat> b = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);
    private static final Set<BarcodeFormat> h = EnumSet.copyOf(a);
    private static final Map<String, Set<BarcodeFormat>> i = new HashMap();

    static {
        h.addAll(b);
        i.put(Intents.Scan.ONE_D_MODE, h);
        i.put(Intents.Scan.PRODUCT_MODE, a);
        i.put(Intents.Scan.QR_CODE_MODE, c);
        i.put(Intents.Scan.DATA_MATRIX_MODE, d);
        i.put(Intents.Scan.AZTEC_MODE, e);
        i.put(Intents.Scan.PDF417_MODE, f);
    }

    private DecodeFormatManager() {
    }

    public static Set<BarcodeFormat> parseDecodeFormats(Intent intent) {
        String stringExtra = intent.getStringExtra(Intents.Scan.FORMATS);
        return a(stringExtra != null ? Arrays.asList(g.split(stringExtra)) : null, intent.getStringExtra(Intents.Scan.MODE));
    }

    private static Set<BarcodeFormat> a(Iterable<String> iterable, String str) {
        if (iterable != null) {
            EnumSet noneOf = EnumSet.noneOf(BarcodeFormat.class);
            try {
                for (String str2 : iterable) {
                    noneOf.add(BarcodeFormat.valueOf(str2));
                }
                return noneOf;
            } catch (IllegalArgumentException unused) {
            }
        }
        if (str != null) {
            return i.get(str);
        }
        return null;
    }
}
