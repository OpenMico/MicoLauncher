package androidx.room;

import androidx.arch.core.util.Function;
import androidx.sqlite.db.SupportSQLiteStatement;

/* compiled from: lambda */
/* renamed from: androidx.room.-$$Lambda$86MUbrc6uipmvj_wBEBf4Ja9I-Y  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$86MUbrc6uipmvj_wBEBf4Ja9IY implements Function {
    public static final /* synthetic */ $$Lambda$86MUbrc6uipmvj_wBEBf4Ja9IY INSTANCE = new $$Lambda$86MUbrc6uipmvj_wBEBf4Ja9IY();

    private /* synthetic */ $$Lambda$86MUbrc6uipmvj_wBEBf4Ja9IY() {
    }

    @Override // androidx.arch.core.util.Function
    public final Object apply(Object obj) {
        return ((SupportSQLiteStatement) obj).simpleQueryForString();
    }
}
