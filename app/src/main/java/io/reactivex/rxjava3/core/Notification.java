package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class Notification<T> {
    static final Notification<Object> b = new Notification<>(null);
    final Object a;

    private Notification(@Nullable Object obj) {
        this.a = obj;
    }

    public boolean isOnComplete() {
        return this.a == null;
    }

    public boolean isOnError() {
        return NotificationLite.isError(this.a);
    }

    public boolean isOnNext() {
        Object obj = this.a;
        return obj != null && !NotificationLite.isError(obj);
    }

    @Nullable
    public T getValue() {
        Object obj = this.a;
        if (obj == null || NotificationLite.isError(obj)) {
            return null;
        }
        return (T) this.a;
    }

    @Nullable
    public Throwable getError() {
        Object obj = this.a;
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Notification) {
            return Objects.equals(this.a, ((Notification) obj).a);
        }
        return false;
    }

    public int hashCode() {
        Object obj = this.a;
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public String toString() {
        Object obj = this.a;
        if (obj == null) {
            return "OnCompleteNotification";
        }
        if (NotificationLite.isError(obj)) {
            return "OnErrorNotification[" + NotificationLite.getError(obj) + "]";
        }
        return "OnNextNotification[" + this.a + "]";
    }

    @NonNull
    public static <T> Notification<T> createOnNext(T t) {
        Objects.requireNonNull(t, "value is null");
        return new Notification<>(t);
    }

    @NonNull
    public static <T> Notification<T> createOnError(@NonNull Throwable th) {
        Objects.requireNonNull(th, "error is null");
        return new Notification<>(NotificationLite.error(th));
    }

    @NonNull
    public static <T> Notification<T> createOnComplete() {
        return (Notification<T>) b;
    }
}
