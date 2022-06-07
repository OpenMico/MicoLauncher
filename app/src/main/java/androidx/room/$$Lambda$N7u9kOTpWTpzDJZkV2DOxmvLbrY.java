package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteStatement;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$N7u9kOTpWTpzDJZkV2DOxmvLbrY  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$N7u9kOTpWTpzDJZkV2DOxmvLbrY implements Function {
    public static final /* synthetic */ $$Lambda$N7u9kOTpWTpzDJZkV2DOxmvLbrY INSTANCE = new $$Lambda$N7u9kOTpWTpzDJZkV2DOxmvLbrY();

    private /* synthetic */ $$Lambda$N7u9kOTpWTpzDJZkV2DOxmvLbrY() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Long.valueOf(((SupportSQLiteStatement) obj).executeInsert());
    }
}
