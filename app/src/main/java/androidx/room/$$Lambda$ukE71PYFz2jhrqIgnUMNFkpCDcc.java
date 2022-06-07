package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$ukE71PYFz2jhrqIgnUMNFkpCDcc  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$ukE71PYFz2jhrqIgnUMNFkpCDcc implements Function {
    public static final /* synthetic */ $$Lambda$ukE71PYFz2jhrqIgnUMNFkpCDcc INSTANCE = new $$Lambda$ukE71PYFz2jhrqIgnUMNFkpCDcc();

    private /* synthetic */ $$Lambda$ukE71PYFz2jhrqIgnUMNFkpCDcc() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(((SupportSQLiteDatabase) obj).isDbLockedByCurrentThread());
    }
}
