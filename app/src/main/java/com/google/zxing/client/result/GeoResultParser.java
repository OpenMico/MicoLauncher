package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class GeoResultParser extends ResultParser {
    private static final Pattern a = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

    @Override // com.google.zxing.client.result.ResultParser
    public GeoParsedResult parse(Result result) {
        Matcher matcher = a.matcher(getMassagedText(result));
        if (!matcher.matches()) {
            return null;
        }
        String group = matcher.group(4);
        try {
            double parseDouble = Double.parseDouble(matcher.group(1));
            if (parseDouble <= 90.0d && parseDouble >= -90.0d) {
                double parseDouble2 = Double.parseDouble(matcher.group(2));
                if (parseDouble2 <= 180.0d && parseDouble2 >= -180.0d) {
                    double d = 0.0d;
                    if (matcher.group(3) != null) {
                        double parseDouble3 = Double.parseDouble(matcher.group(3));
                        if (parseDouble3 < 0.0d) {
                            return null;
                        }
                        d = parseDouble3;
                    }
                    return new GeoParsedResult(parseDouble, parseDouble2, d, group);
                }
                return null;
            }
            return null;
        } catch (NumberFormatException unused) {
            return null;
        }
    }
}
