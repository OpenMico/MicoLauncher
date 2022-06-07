package com.xiaomi.push;

import android.content.Context;
import android.database.Cursor;
import com.xiaomi.push.bx;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class bs extends bx.b<Long> {
    private long c = 0;
    private String d;

    public bs(String str, List<String> list, String str2, String[] strArr, String str3, String str4, String str5, int i, String str6) {
        super(str, list, str2, strArr, str3, str4, str5, i);
        this.d = str6;
    }

    public static bs a(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("count(*)");
        return new bs(str, arrayList, null, null, null, null, null, 0, "job to get count of all message");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaomi.push.bx.b
    public Long a(Context context, Cursor cursor) {
        return Long.valueOf(cursor.getLong(0));
    }

    @Override // com.xiaomi.push.bx.b, com.xiaomi.push.bx.a
    public Object a() {
        return Long.valueOf(this.c);
    }

    @Override // com.xiaomi.push.bx.b
    public void a(Context context, List<Long> list) {
        if (context != null && list != null && list.size() > 0) {
            this.c = list.get(0).longValue();
        }
    }
}
