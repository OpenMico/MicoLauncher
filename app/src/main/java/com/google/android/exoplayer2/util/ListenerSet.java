package com.google.android.exoplayer2.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.FlagSet;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nonnull;

/* loaded from: classes2.dex */
public final class ListenerSet<T> {
    private final Clock a;
    private final HandlerWrapper b;
    private final IterationFinishedEvent<T> c;
    private final CopyOnWriteArraySet<a<T>> d;
    private final ArrayDeque<Runnable> e;
    private final ArrayDeque<Runnable> f;
    private boolean g;

    /* loaded from: classes2.dex */
    public interface Event<T> {
        void invoke(T t);
    }

    /* loaded from: classes2.dex */
    public interface IterationFinishedEvent<T> {
        void invoke(T t, FlagSet flagSet);
    }

    public ListenerSet(Looper looper, Clock clock, IterationFinishedEvent<T> iterationFinishedEvent) {
        this(new CopyOnWriteArraySet(), looper, clock, iterationFinishedEvent);
    }

    private ListenerSet(CopyOnWriteArraySet<a<T>> copyOnWriteArraySet, Looper looper, Clock clock, IterationFinishedEvent<T> iterationFinishedEvent) {
        this.a = clock;
        this.d = copyOnWriteArraySet;
        this.c = iterationFinishedEvent;
        this.e = new ArrayDeque<>();
        this.f = new ArrayDeque<>();
        this.b = clock.createHandler(looper, new Handler.Callback() { // from class: com.google.android.exoplayer2.util.-$$Lambda$ListenerSet$F3b-jEcf7ctQRTDmMUKAqhEZTII
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a2;
                a2 = ListenerSet.this.a(message);
                return a2;
            }
        });
    }

    @CheckResult
    public ListenerSet<T> copy(Looper looper, IterationFinishedEvent<T> iterationFinishedEvent) {
        return new ListenerSet<>(this.d, looper, this.a, iterationFinishedEvent);
    }

    public void add(T t) {
        if (!this.g) {
            Assertions.checkNotNull(t);
            this.d.add(new a<>(t));
        }
    }

    public void remove(T t) {
        Iterator<a<T>> it = this.d.iterator();
        while (it.hasNext()) {
            a<T> next = it.next();
            if (next.a.equals(t)) {
                next.a(this.c);
                this.d.remove(next);
            }
        }
    }

    public int size() {
        return this.d.size();
    }

    public void queueEvent(final int i, final Event<T> event) {
        final CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet(this.d);
        this.f.add(new Runnable() { // from class: com.google.android.exoplayer2.util.-$$Lambda$ListenerSet$go5sqsIDxps2OqUYdJ6_l8UixeA
            @Override // java.lang.Runnable
            public final void run() {
                ListenerSet.a(copyOnWriteArraySet, i, event);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(CopyOnWriteArraySet copyOnWriteArraySet, int i, Event event) {
        Iterator it = copyOnWriteArraySet.iterator();
        while (it.hasNext()) {
            ((a) it.next()).a(i, event);
        }
    }

    public void flushEvents() {
        if (!this.f.isEmpty()) {
            if (!this.b.hasMessages(0)) {
                HandlerWrapper handlerWrapper = this.b;
                handlerWrapper.sendMessageAtFrontOfQueue(handlerWrapper.obtainMessage(0));
            }
            boolean z = !this.e.isEmpty();
            this.e.addAll(this.f);
            this.f.clear();
            if (!z) {
                while (!this.e.isEmpty()) {
                    this.e.peekFirst().run();
                    this.e.removeFirst();
                }
            }
        }
    }

    public void sendEvent(int i, Event<T> event) {
        queueEvent(i, event);
        flushEvents();
    }

    public void release() {
        Iterator<a<T>> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().a(this.c);
        }
        this.d.clear();
        this.g = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Message message) {
        Iterator<a<T>> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().b(this.c);
            if (this.b.hasMessages(0)) {
                return true;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a<T> {
        @Nonnull
        public final T a;
        private FlagSet.Builder b = new FlagSet.Builder();
        private boolean c;
        private boolean d;

        public a(@Nonnull T t) {
            this.a = t;
        }

        public void a(IterationFinishedEvent<T> iterationFinishedEvent) {
            this.d = true;
            if (this.c) {
                iterationFinishedEvent.invoke(this.a, this.b.build());
            }
        }

        public void a(int i, Event<T> event) {
            if (!this.d) {
                if (i != -1) {
                    this.b.add(i);
                }
                this.c = true;
                event.invoke(this.a);
            }
        }

        public void b(IterationFinishedEvent<T> iterationFinishedEvent) {
            if (!this.d && this.c) {
                FlagSet build = this.b.build();
                this.b = new FlagSet.Builder();
                this.c = false;
                iterationFinishedEvent.invoke(this.a, build);
            }
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.a.equals(((a) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }
}
