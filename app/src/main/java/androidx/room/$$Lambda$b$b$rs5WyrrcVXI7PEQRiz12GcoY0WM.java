package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteStatement;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$b$b$rs5WyrrcVXI7PEQRiz12GcoY0WM  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$b$b$rs5WyrrcVXI7PEQRiz12GcoY0WM implements Function {
    public static final /* synthetic */ $$Lambda$b$b$rs5WyrrcVXI7PEQRiz12GcoY0WM INSTANCE = new $$Lambda$b$b$rs5WyrrcVXI7PEQRiz12GcoY0WM();

    private /* synthetic */ $$Lambda$b$b$rs5WyrrcVXI7PEQRiz12GcoY0WM() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        Object execute;
        execute = ((SupportSQLiteStatement) obj).execute();
        return execute;
    }
}
