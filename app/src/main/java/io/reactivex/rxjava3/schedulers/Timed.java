package io.reactivex.rxjava3.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class Timed<T> {
    final T a;
    final long b;
    final TimeUnit c;

    public Timed(@NonNull T t, long j, @NonNull TimeUnit timeUnit) {
        this.a = (T) Objects.requireNonNull(t, "value is null");
        this.b = j;
        this.c = (TimeUnit) Objects.requireNonNull(timeUnit, "unit is null");
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
        return Objects.equals(this.a, timed.a) && this.b == timed.b && Objects.equals(this.c, timed.c);
    }

    public int hashCode() {
        long j = this.b;
        return (((this.a.hashCode() * 31) + ((int) (j ^ (j >>> 31)))) * 31) + this.c.hashCode();
    }

    public String toString() {
        return "Timed[time=" + this.b + ", unit=" + this.c + ", value=" + this.a + "]";
    }
}
