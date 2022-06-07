package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$TMdjwAyA2HEhVVdu7fycE2v9GyM  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$TMdjwAyA2HEhVVdu7fycE2v9GyM implements Function {
    public static final /* synthetic */ $$Lambda$TMdjwAyA2HEhVVdu7fycE2v9GyM INSTANCE = new $$Lambda$TMdjwAyA2HEhVVdu7fycE2v9GyM();

    private /* synthetic */ $$Lambda$TMdjwAyA2HEhVVdu7fycE2v9GyM() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(((SupportSQLiteDatabase) obj).inTransaction());
    }
}
