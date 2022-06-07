package com.xiaomi.smarthome.base;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import autodispose2.OutsideScopeException;
import autodispose2.lifecycle.CorrespondingEventsFunction;
import autodispose2.lifecycle.LifecycleEndedException;
import autodispose2.lifecycle.LifecycleScopeProvider;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes4.dex */
public class BaseViewModel extends AndroidViewModel implements LifecycleScopeProvider<a> {
    private static final CorrespondingEventsFunction<a> b = $$Lambda$BaseViewModel$zlgsc0rUupLTcucXuRCjvP1QKAw.INSTANCE;
    private final BehaviorSubject<a> a = BehaviorSubject.createDefault(a.CREATE);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        CREATE,
        CLEARED
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ a a(a aVar) throws OutsideScopeException {
        if (aVar == a.CREATE) {
            return a.CLEARED;
        }
        throw new LifecycleEndedException("Cannot bind to ViewModel lifecycle after onCleared.");
    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override // autodispose2.lifecycle.LifecycleScopeProvider
    public Observable<a> lifecycle() {
        return this.a.hide();
    }

    @Override // autodispose2.lifecycle.LifecycleScopeProvider
    public CorrespondingEventsFunction<a> correspondingEvents() {
        return b;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        this.a.onNext(a.CLEARED);
        super.onCleared();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // autodispose2.lifecycle.LifecycleScopeProvider
    @Nullable
    public a peekLifecycle() {
        return this.a.getValue();
    }
}
