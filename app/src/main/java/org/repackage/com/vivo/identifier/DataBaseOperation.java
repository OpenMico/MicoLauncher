package org.repackage.com.vivo.identifier;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.xiaomi.onetrack.api.b;

/* loaded from: classes3.dex */
public class DataBaseOperation {
    private Context a;

    public DataBaseOperation(Context context) {
        this.a = context;
    }

    public String a(int i, String str) {
        Uri uri;
        String str2 = null;
        if (i != 4) {
            switch (i) {
                case 0:
                    uri = Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID");
                    break;
                case 1:
                    uri = Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/VAID_" + str);
                    break;
                case 2:
                    uri = Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/AAID_" + str);
                    break;
                default:
                    uri = null;
                    break;
            }
        } else {
            uri = Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAIDSTATUS");
        }
        Cursor query = this.a.getContentResolver().query(uri, null, null, null, null);
        if (query != null) {
            if (query.moveToNext()) {
                str2 = query.getString(query.getColumnIndex(b.p));
            }
            query.close();
        } else {
            Log.d("VMS_IDLG_SDK_DB", "return cursor is null,return");
        }
        return str2;
    }
}
