package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.analytics.pro.ai;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;

/* compiled from: NewUMIDTracker.java */
/* loaded from: classes2.dex */
public class h extends a {
    private static final String a = "newumid";
    private Context b;

    public h(Context context) {
        super(a);
        this.b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return UMEnvelopeBuild.imprintProperty(this.b, ai.g, null);
    }
}
