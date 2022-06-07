package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$z_yKiINBCC3WVGnJOjud-lDWWfQ  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$z_yKiINBCC3WVGnJOjudlDWWfQ implements Function {
    public static final /* synthetic */ $$Lambda$z_yKiINBCC3WVGnJOjudlDWWfQ INSTANCE = new $$Lambda$z_yKiINBCC3WVGnJOjudlDWWfQ();

    private /* synthetic */ $$Lambda$z_yKiINBCC3WVGnJOjudlDWWfQ() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(((SupportSQLiteDatabase) obj).isDatabaseIntegrityOk());
    }
}
