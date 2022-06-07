package io.reactivex;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;

/* loaded from: classes4.dex */
public final class Notification<T> {
    static final Notification<Object> b = new Notification<>(null);
    final Object a;

    private Notification(Object obj) {
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
            return ObjectHelper.equals(this.a, ((Notification) obj).a);
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
    public static <T> Notification<T> createOnNext(@NonNull T t) {
        ObjectHelper.requireNonNull(t, "value is null");
        return new Notification<>(t);
    }

    @NonNull
    public static <T> Notification<T> createOnError(@NonNull Throwable th) {
        ObjectHelper.requireNonNull(th, "error is null");
        return new Notification<>(NotificationLite.error(th));
    }

    @NonNull
    public static <T> Notification<T> createOnComplete() {
        return (Notification<T>) b;
    }
}
