package autodispose2;

import autodispose2.internal.DoNotMock;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.core.CompletableSource;

@DoNotMock("Use TestScopeProvider instead")
/* loaded from: classes.dex */
public interface ScopeProvider {
    public static final ScopeProvider UNBOUND = $$Lambda$xDY4R77p6mGM_7tgoLEaL8J7eIU.INSTANCE;

    @CheckReturnValue
    CompletableSource requestScope() throws Exception;
}
