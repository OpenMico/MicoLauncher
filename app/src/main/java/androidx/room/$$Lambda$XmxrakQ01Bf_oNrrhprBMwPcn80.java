package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$XmxrakQ01Bf_oNrrhprBMwPcn80  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$XmxrakQ01Bf_oNrrhprBMwPcn80 implements Function {
    public static final /* synthetic */ $$Lambda$XmxrakQ01Bf_oNrrhprBMwPcn80 INSTANCE = new $$Lambda$XmxrakQ01Bf_oNrrhprBMwPcn80();

    private /* synthetic */ $$Lambda$XmxrakQ01Bf_oNrrhprBMwPcn80() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((SupportSQLiteDatabase) obj).getVersion());
    }
}
