package com.google.common.xml;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import kotlin.text.Typography;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public class XmlEscapers {
    private static final Escaper a;
    private static final Escaper b;
    private static final Escaper c;

    private XmlEscapers() {
    }

    public static Escaper xmlContentEscaper() {
        return b;
    }

    public static Escaper xmlAttributeEscaper() {
        return c;
    }

    static {
        Escapers.Builder builder = Escapers.builder();
        builder.setSafeRange((char) 0, (char) 65533);
        builder.setUnsafeReplacement("�");
        for (char c2 = 0; c2 <= 31; c2 = (char) (c2 + 1)) {
            if (!(c2 == '\t' || c2 == '\n' || c2 == '\r')) {
                builder.addEscape(c2, "�");
            }
        }
        builder.addEscape(Typography.amp, "&amp;");
        builder.addEscape(Typography.less, "&lt;");
        builder.addEscape(Typography.greater, "&gt;");
        b = builder.build();
        builder.addEscape('\'', LrcRow.APOS);
        builder.addEscape('\"', "&quot;");
        a = builder.build();
        builder.addEscape('\t', "&#x9;");
        builder.addEscape('\n', "&#xA;");
        builder.addEscape('\r', "&#xD;");
        c = builder.build();
    }
}
