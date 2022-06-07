package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$yKxzlL2CA_3zs224T-C80CaPwg4  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$yKxzlL2CA_3zs224TC80CaPwg4 implements Function {
    public static final /* synthetic */ $$Lambda$yKxzlL2CA_3zs224TC80CaPwg4 INSTANCE = new $$Lambda$yKxzlL2CA_3zs224TC80CaPwg4();

    private /* synthetic */ $$Lambda$yKxzlL2CA_3zs224TC80CaPwg4() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return ((SupportSQLiteDatabase) obj).getPath();
    }
}
