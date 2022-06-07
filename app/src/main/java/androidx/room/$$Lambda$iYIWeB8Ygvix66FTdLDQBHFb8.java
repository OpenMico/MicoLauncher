package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteStatement;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$iYIWeB8Yg-vix66FTd-LDQBHFb8  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$iYIWeB8Ygvix66FTdLDQBHFb8 implements Function {
    public static final /* synthetic */ $$Lambda$iYIWeB8Ygvix66FTdLDQBHFb8 INSTANCE = new $$Lambda$iYIWeB8Ygvix66FTdLDQBHFb8();

    private /* synthetic */ $$Lambda$iYIWeB8Ygvix66FTdLDQBHFb8() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Long.valueOf(((SupportSQLiteStatement) obj).simpleQueryForLong());
    }
}
