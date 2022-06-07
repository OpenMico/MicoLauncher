package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;

/* loaded from: classes4.dex */
public class br extends bt {
    public br(String str, String str2, String[] strArr, String str3) {
        super(str, str2, strArr, str3);
    }

    public static br a(Context context, String str, int i) {
        b.b("delete  messages when db size is too bigger");
        String a = bx.a(context).a(str);
        if (TextUtils.isEmpty(a)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("rowDataId in (select ");
        sb.append("rowDataId from " + a);
        sb.append(" order by createTimeStamp asc");
        sb.append(" limit ?)");
        return new br(str, sb.toString(), new String[]{String.valueOf(i)}, "a job build to delete history message");
    }

    private void a(long j) {
        if (this.a != null && this.a.length > 0) {
            this.a[0] = String.valueOf(j);
        }
    }

    @Override // com.xiaomi.push.bx.a
    public void a(Context context, Object obj) {
        if (obj instanceof Long) {
            long longValue = ((Long) obj).longValue();
            long a = cd.a(a());
            long j = bp.f14a;
            if (a > j) {
                long j2 = (long) ((((a - j) * 1.2d) / j) * longValue);
                a(j2);
                bl a2 = bl.a(context);
                a2.a("begin delete " + j2 + "noUpload messages , because db size is " + a + "B");
                super.a(context, obj);
                return;
            }
            b.b("db size is suitable");
        }
    }
}
