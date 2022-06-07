package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$FnfrLN73Kb5eEMd_EThzd87ecn4  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$FnfrLN73Kb5eEMd_EThzd87ecn4 implements Function {
    public static final /* synthetic */ $$Lambda$FnfrLN73Kb5eEMd_EThzd87ecn4 INSTANCE = new $$Lambda$FnfrLN73Kb5eEMd_EThzd87ecn4();

    private /* synthetic */ $$Lambda$FnfrLN73Kb5eEMd_EThzd87ecn4() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(((SupportSQLiteDatabase) obj).isReadOnly());
    }
}
