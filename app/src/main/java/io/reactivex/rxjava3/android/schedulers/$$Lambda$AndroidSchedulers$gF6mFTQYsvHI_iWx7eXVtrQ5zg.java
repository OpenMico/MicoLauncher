package io.reactivex.rxjava3.android.schedulers;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import java.util.concurrent.Callable;

/* compiled from: lambda */
/* renamed from: io.reactivex.rxjava3.android.schedulers.-$$Lambda$AndroidSchedulers$gF6mFTQYsvHI_iWx7eXVtrQ5z-g  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$AndroidSchedulers$gF6mFTQYsvHI_iWx7eXVtrQ5zg implements Callable {
    public static final /* synthetic */ $$Lambda$AndroidSchedulers$gF6mFTQYsvHI_iWx7eXVtrQ5zg INSTANCE = new $$Lambda$AndroidSchedulers$gF6mFTQYsvHI_iWx7eXVtrQ5zg();

    private /* synthetic */ $$Lambda$AndroidSchedulers$gF6mFTQYsvHI_iWx7eXVtrQ5zg() {
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        Scheduler scheduler;
        scheduler = AndroidSchedulers.a.a;
        return scheduler;
    }
}
