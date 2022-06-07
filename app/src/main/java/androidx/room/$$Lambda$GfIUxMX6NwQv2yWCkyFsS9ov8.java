package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$GfIUxMX6NwQv2--yWCkyFsS9ov8  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$GfIUxMX6NwQv2yWCkyFsS9ov8 implements Function {
    public static final /* synthetic */ $$Lambda$GfIUxMX6NwQv2yWCkyFsS9ov8 INSTANCE = new $$Lambda$GfIUxMX6NwQv2yWCkyFsS9ov8();

    private /* synthetic */ $$Lambda$GfIUxMX6NwQv2yWCkyFsS9ov8() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return ((SupportSQLiteDatabase) obj).getAttachedDbs();
    }
}
