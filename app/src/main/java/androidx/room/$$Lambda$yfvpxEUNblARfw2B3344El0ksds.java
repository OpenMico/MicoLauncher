package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$yfvpxEUNblARfw2B3344El0ksds  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$yfvpxEUNblARfw2B3344El0ksds implements Function {
    public static final /* synthetic */ $$Lambda$yfvpxEUNblARfw2B3344El0ksds INSTANCE = new $$Lambda$yfvpxEUNblARfw2B3344El0ksds();

    private /* synthetic */ $$Lambda$yfvpxEUNblARfw2B3344El0ksds() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Long.valueOf(((SupportSQLiteDatabase) obj).getMaximumSize());
    }
}
