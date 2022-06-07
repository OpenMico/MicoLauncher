package androidx.activity;

import android.annotation.SuppressLint;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayDeque;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class OnBackPressedDispatcher {
    final ArrayDeque<OnBackPressedCallback> a;
    @Nullable
    private final Runnable b;

    public OnBackPressedDispatcher() {
        this(null);
    }

    public OnBackPressedDispatcher(@Nullable Runnable runnable) {
        this.a = new ArrayDeque<>();
        this.b = runnable;
    }

    @MainThread
    public void addCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
        a(onBackPressedCallback);
    }

    @NonNull
    @MainThread
    a a(@NonNull OnBackPressedCallback onBackPressedCallback) {
        this.a.add(onBackPressedCallback);
        a aVar = new a(onBackPressedCallback);
        onBackPressedCallback.a(aVar);
        return aVar;
    }

    @SuppressLint({"LambdaLast"})
    @MainThread
    public void addCallback(@NonNull LifecycleOwner lifecycleOwner, @NonNull OnBackPressedCallback onBackPressedCallback) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
            onBackPressedCallback.a(new LifecycleOnBackPressedCancellable(lifecycle, onBackPressedCallback));
        }
    }

    @MainThread
    public boolean hasEnabledCallbacks() {
        Iterator<OnBackPressedCallback> descendingIterator = this.a.descendingIterator();
        while (descendingIterator.hasNext()) {
            if (descendingIterator.next().isEnabled()) {
                return true;
            }
        }
        return false;
    }

    @MainThread
    public void onBackPressed() {
        Iterator<OnBackPressedCallback> descendingIterator = this.a.descendingIterator();
        while (descendingIterator.hasNext()) {
            OnBackPressedCallback next = descendingIterator.next();
            if (next.isEnabled()) {
                next.handleOnBackPressed();
                return;
            }
        }
        Runnable runnable = this.b;
        if (runnable != null) {
            runnable.run();
        }
    }

    /* loaded from: classes.dex */
    public class a implements a {
        private final OnBackPressedCallback b;

        a(OnBackPressedCallback onBackPressedCallback) {
            OnBackPressedDispatcher.this = r1;
            this.b = onBackPressedCallback;
        }

        @Override // androidx.activity.a
        public void a() {
            OnBackPressedDispatcher.this.a.remove(this.b);
            this.b.b(this);
        }
    }

    /* loaded from: classes.dex */
    public class LifecycleOnBackPressedCancellable implements a, LifecycleEventObserver {
        private final Lifecycle b;
        private final OnBackPressedCallback c;
        @Nullable
        private a d;

        LifecycleOnBackPressedCancellable(Lifecycle lifecycle, @NonNull OnBackPressedCallback onBackPressedCallback) {
            OnBackPressedDispatcher.this = r1;
            this.b = lifecycle;
            this.c = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                this.d = OnBackPressedDispatcher.this.a(this.c);
            } else if (event == Lifecycle.Event.ON_STOP) {
                a aVar = this.d;
                if (aVar != null) {
                    aVar.a();
                }
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                a();
            }
        }

        @Override // androidx.activity.a
        public void a() {
            this.b.removeObserver(this);
            this.c.b(this);
            a aVar = this.d;
            if (aVar != null) {
                aVar.a();
                this.d = null;
            }
        }
    }
}
