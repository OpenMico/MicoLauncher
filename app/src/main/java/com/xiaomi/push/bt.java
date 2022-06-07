package com.xiaomi.push;

import com.xiaomi.push.bx;

/* loaded from: classes4.dex */
public class bt extends bx.d {
    protected String a;

    public bt(String str, String str2, String[] strArr, String str3) {
        super(str, str2, strArr);
        this.a = "MessageDeleteJob";
        this.a = str3;
    }

    public static bt a(String str) {
        return new bt(str, "status = ?", new String[]{String.valueOf(2)}, "a job build to delete uploaded job");
    }
}
