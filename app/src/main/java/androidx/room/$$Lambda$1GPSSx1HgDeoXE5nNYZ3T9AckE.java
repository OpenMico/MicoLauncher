package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteStatement;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$1GPS-Sx1HgDeoXE5nNYZ3T9AckE  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$1GPSSx1HgDeoXE5nNYZ3T9AckE implements Function {
    public static final /* synthetic */ $$Lambda$1GPSSx1HgDeoXE5nNYZ3T9AckE INSTANCE = new $$Lambda$1GPSSx1HgDeoXE5nNYZ3T9AckE();

    private /* synthetic */ $$Lambda$1GPSSx1HgDeoXE5nNYZ3T9AckE() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((SupportSQLiteStatement) obj).executeUpdateDelete());
    }
}
