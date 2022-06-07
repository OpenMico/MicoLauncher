package com.xiaomi.push;

import android.content.ContentValues;
import android.content.Context;
import com.xiaomi.push.bx;

/* loaded from: classes4.dex */
public class bu extends bx.e {
    private String c;

    public bu(String str, ContentValues contentValues, String str2) {
        super(str, contentValues);
        this.c = "MessageInsertJob";
        this.c = str2;
    }

    public static bu a(Context context, String str, hl hlVar) {
        byte[] a = ir.a(hlVar);
        if (a == null || a.length <= 0) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", (Integer) 0);
        contentValues.put("messageId", "");
        contentValues.put("messageItemId", hlVar.d());
        contentValues.put("messageItem", a);
        contentValues.put("appId", bl.a(context).b());
        contentValues.put("packageName", bl.a(context).a());
        contentValues.put("createTimeStamp", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("uploadTimestamp", (Integer) 0);
        return new bu(str, contentValues, "a job build to insert message to db");
    }
}
