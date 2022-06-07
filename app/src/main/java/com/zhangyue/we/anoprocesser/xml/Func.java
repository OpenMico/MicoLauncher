package com.zhangyue.we.anoprocesser.xml;

import com.xiaomi.onetrack.OneTrack;

/* loaded from: classes4.dex */
public class Func {
    public boolean isView;
    public String name;
    public String paramsType;

    public Func(String str) {
        String[] split = str.split("\\.");
        if (split.length == 2) {
            this.isView = split[0].equalsIgnoreCase(OneTrack.Event.VIEW);
            int indexOf = split[1].indexOf("=");
            if (indexOf > 0) {
                int i = indexOf + 1;
                this.paramsType = split[1].substring(i);
                this.name = "%s." + split[1].substring(0, i) + "%s";
            } else {
                int indexOf2 = split[1].indexOf("(");
                int indexOf3 = split[1].indexOf(")");
                this.paramsType = split[1].substring(split[1].indexOf("(") + 1, split[1].indexOf(")"));
                this.name = "%s." + split[1].substring(0, indexOf2 + 1) + "%s" + split[1].substring(indexOf3);
            }
            if (!this.name.endsWith(";")) {
                this.name += ";";
            }
        }
    }

    public String toString() {
        return "Func{name='" + this.name + "', isView=" + this.isView + ", paramsType='" + this.paramsType + "'}";
    }
}
