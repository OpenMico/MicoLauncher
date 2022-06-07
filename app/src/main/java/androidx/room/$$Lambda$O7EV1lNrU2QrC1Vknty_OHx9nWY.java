package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$O7EV1lNrU2QrC1Vknty_OHx9nWY  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$O7EV1lNrU2QrC1Vknty_OHx9nWY implements Function {
    public static final /* synthetic */ $$Lambda$O7EV1lNrU2QrC1Vknty_OHx9nWY INSTANCE = new $$Lambda$O7EV1lNrU2QrC1Vknty_OHx9nWY();

    private /* synthetic */ $$Lambda$O7EV1lNrU2QrC1Vknty_OHx9nWY() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Long.valueOf(((SupportSQLiteDatabase) obj).getPageSize());
    }
}
