package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.utils.b;

/* compiled from: UTDIdTracker.java */
/* loaded from: classes2.dex */
public class l extends a {
    private static final String a = "utdid";
    private Context b;

    public l(Context context) {
        super(a);
        this.b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        try {
            if (FieldManager.allow(b.u)) {
                return (String) Class.forName("com.ut.device.UTDevice").getMethod("getUtdid", Context.class).invoke(null, this.b);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
