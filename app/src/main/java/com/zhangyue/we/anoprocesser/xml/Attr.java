package com.zhangyue.we.anoprocesser.xml;

import java.util.HashMap;

/* loaded from: classes4.dex */
public class Attr implements Comparable<Attr> {
    public HashMap<String, String> enums = new HashMap<>();
    public String format;
    public String name;
    public Func toFunc;

    public String toString() {
        return "Attr{format='" + this.format + "', name='" + this.name + "', enums=" + this.enums + ", toFunc='" + this.toFunc + "'}";
    }

    public int compareTo(Attr attr) {
        if (attr == null) {
            return 1;
        }
        if (attr.format != null || this.format == null) {
            return (attr.enums.size() != 0 || this.enums.size() <= 0) ? 0 : 1;
        }
        return 1;
    }
}
