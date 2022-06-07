package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$V7flxCHPcSui801PEVK0MKrvfCU  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$V7flxCHPcSui801PEVK0MKrvfCU implements Function {
    public static final /* synthetic */ $$Lambda$V7flxCHPcSui801PEVK0MKrvfCU INSTANCE = new $$Lambda$V7flxCHPcSui801PEVK0MKrvfCU();

    private /* synthetic */ $$Lambda$V7flxCHPcSui801PEVK0MKrvfCU() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(((SupportSQLiteDatabase) obj).yieldIfContendedSafely());
    }
}
