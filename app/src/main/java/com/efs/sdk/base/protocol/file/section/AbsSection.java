package com.efs.sdk.base.protocol.file.section;

import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes.dex */
public abstract class AbsSection {
    public static final String SEP_LINE_BREAK = "linebreak";
    public static final String SEP_ORIGIN_LINE_BREAK = "\n";
    String type;
    String name = "";
    String version = "1.0";
    String sep = "";

    public abstract String changeToStr();

    public void setSep(String str) {
        if (str.equals("\n")) {
            this.sep = SEP_LINE_BREAK;
        } else {
            this.sep = str;
        }
    }

    public AbsSection(String str) {
        this.type = str;
    }

    String getDeclarationLine() {
        return "section:" + this.name + Constants.ACCEPT_TIME_SEPARATOR_SP + this.type + Constants.ACCEPT_TIME_SEPARATOR_SP + this.version + Constants.ACCEPT_TIME_SEPARATOR_SP + this.sep;
    }
}
