package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;

/* loaded from: classes4.dex */
public class f {
    public String a(Context context) {
        Uri parse = Uri.parse("content://com.meizu.flyme.openidsdk/");
        String str = "";
        try {
            Cursor query = context.getContentResolver().query(parse, null, null, new String[]{OneTrack.Param.OAID}, null);
            str = a(query);
            query.close();
            return str;
        } catch (Throwable th) {
            th.printStackTrace();
            return str;
        }
    }

    private String a(Cursor cursor) {
        String str = null;
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(b.p);
        if (columnIndex > 0) {
            str = cursor.getString(columnIndex);
        }
        int columnIndex2 = cursor.getColumnIndex("code");
        if (columnIndex2 > 0) {
            cursor.getInt(columnIndex2);
        }
        int columnIndex3 = cursor.getColumnIndex(MIBrain.CPBindStatus.Exired);
        if (columnIndex3 > 0) {
            cursor.getLong(columnIndex3);
        }
        return str;
    }
}
