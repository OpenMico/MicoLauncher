package io.reactivex.schedulers;

import io.reactivex.annotations.NonNull;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class Timed<T> {
    final T a;
    final long b;
    final TimeUnit c;

    public Timed(@NonNull T t, long j, @NonNull TimeUnit timeUnit) {
        this.a = t;
        this.b = j;
        this.c = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
    }

    @NonNull
    public T value() {
        return this.a;
    }

    @NonNull
    public TimeUnit unit() {
        return this.c;
    }

    public long time() {
        return this.b;
    }

    public long time(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.b, this.c);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Timed)) {
            return false;
        }
        Timed timed = (Timed) obj;
        return ObjectHelper.equals(this.a, timed.a) && this.b == timed.b && ObjectHelper.equals(this.c, timed.c);
    }

    public int hashCode() {
        T t = this.a;
        int hashCode = t != null ? t.hashCode() : 0;
        long j = this.b;
        return (((hashCode * 31) + ((int) (j ^ (j >>> 31)))) * 31) + this.c.hashCode();
    }

    public String toString() {
        return "Timed[time=" + this.b + ", unit=" + this.c + ", value=" + this.a + "]";
    }
}
