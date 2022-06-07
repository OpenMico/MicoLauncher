package autodispose2;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;

/* compiled from: lambda */
/* renamed from: autodispose2.-$$Lambda$xDY4R77p6mGM_7tgoLEaL8J7eIU  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$xDY4R77p6mGM_7tgoLEaL8J7eIU implements ScopeProvider {
    public static final /* synthetic */ $$Lambda$xDY4R77p6mGM_7tgoLEaL8J7eIU INSTANCE = new $$Lambda$xDY4R77p6mGM_7tgoLEaL8J7eIU();

    private /* synthetic */ $$Lambda$xDY4R77p6mGM_7tgoLEaL8J7eIU() {
    }

    @Override // autodispose2.ScopeProvider
    public final CompletableSource requestScope() {
        return Completable.never();
    }
}
