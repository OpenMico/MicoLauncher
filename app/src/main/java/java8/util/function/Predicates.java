package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class Predicates {
    public static <T> Predicate<T> and(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(predicate2);
        return ag.a(predicate, predicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean b(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) && predicate2.test(obj);
    }

    public static <T> Predicate<T> negate(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return ah.a(predicate);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(Predicate predicate, Object obj) {
        return !predicate.test(obj);
    }

    public static <T> Predicate<T> or(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(predicate2);
        return ai.a(predicate, predicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) || predicate2.test(obj);
    }

    public static <T> Predicate<T> isEqual(Object obj) {
        return obj == null ? aj.a() : ak.a(obj);
    }

    public static <T> Predicate<T> not(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return negate(predicate);
    }

    private Predicates() {
    }
}
