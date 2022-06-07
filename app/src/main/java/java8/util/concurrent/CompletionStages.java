package java8.util.concurrent;

import java.util.concurrent.Executor;
import java8.util.function.BiFunction;
import java8.util.function.Function;
import java8.util.function.Functions;

/* loaded from: classes5.dex */
public final class CompletionStages {
    public static <T> CompletionStage<T> exceptionallyAsync(final CompletionStage<T> completionStage, final Function<Throwable, ? extends T> function) {
        return completionStage.handle((BiFunction<T, Throwable, CompletionStage<T>>) new BiFunction<T, Throwable, CompletionStage<T>>() { // from class: java8.util.concurrent.CompletionStages.1
            /* renamed from: a */
            public CompletionStage<T> apply(T t, Throwable th) {
                return th == null ? CompletionStage.this : CompletionStage.this.handleAsync(new BiFunction<T, Throwable, T>() { // from class: java8.util.concurrent.CompletionStages.1.1
                    /* JADX WARN: Type inference failed for: r1v3, types: [T, java.lang.Object] */
                    /* renamed from: a */
                    public T apply(T t2, Throwable th2) {
                        return function.apply(th2);
                    }
                });
            }
        }).thenCompose(Functions.identity());
    }

    public static <T> CompletionStage<T> exceptionallyAsync(final CompletionStage<T> completionStage, final Function<Throwable, ? extends T> function, final Executor executor) {
        return completionStage.handle((BiFunction<T, Throwable, CompletionStage<T>>) new BiFunction<T, Throwable, CompletionStage<T>>() { // from class: java8.util.concurrent.CompletionStages.2
            /* renamed from: a */
            public CompletionStage<T> apply(T t, Throwable th) {
                return th == null ? CompletionStage.this : CompletionStage.this.handleAsync(new BiFunction<T, Throwable, T>() { // from class: java8.util.concurrent.CompletionStages.2.1
                    /* JADX WARN: Type inference failed for: r1v3, types: [T, java.lang.Object] */
                    /* renamed from: a */
                    public T apply(T t2, Throwable th2) {
                        return function.apply(th2);
                    }
                }, executor);
            }
        }).thenCompose(Functions.identity());
    }

    public static <T> CompletionStage<T> exceptionallyCompose(final CompletionStage<T> completionStage, final Function<Throwable, ? extends CompletionStage<T>> function) {
        return completionStage.handle((BiFunction<T, Throwable, CompletionStage<T>>) new BiFunction<T, Throwable, CompletionStage<T>>() { // from class: java8.util.concurrent.CompletionStages.3
            /* renamed from: a */
            public CompletionStage<T> apply(T t, Throwable th) {
                return th == null ? CompletionStage.this : (CompletionStage) function.apply(th);
            }
        }).thenCompose(Functions.identity());
    }

    public static <T> CompletionStage<T> exceptionallyComposeAsync(final CompletionStage<T> completionStage, final Function<Throwable, ? extends CompletionStage<T>> function) {
        return completionStage.handle((BiFunction<T, Throwable, CompletionStage<T>>) new BiFunction<T, Throwable, CompletionStage<T>>() { // from class: java8.util.concurrent.CompletionStages.4
            /* renamed from: a */
            public CompletionStage<T> apply(T t, Throwable th) {
                if (th == null) {
                    return CompletionStage.this;
                }
                return CompletionStage.this.handleAsync(new BiFunction<T, Throwable, CompletionStage<T>>() { // from class: java8.util.concurrent.CompletionStages.4.1
                    /* renamed from: a */
                    public CompletionStage<T> apply(T t2, Throwable th2) {
                        return (CompletionStage) function.apply(th2);
                    }
                }).thenCompose(Functions.identity());
            }
        }).thenCompose(Functions.identity());
    }

    public static <T> CompletionStage<T> exceptionallyComposeAsync(final CompletionStage<T> completionStage, final Function<Throwable, ? extends CompletionStage<T>> function, final Executor executor) {
        return completionStage.handle((BiFunction<T, Throwable, CompletionStage<T>>) new BiFunction<T, Throwable, CompletionStage<T>>() { // from class: java8.util.concurrent.CompletionStages.5
            /* renamed from: a */
            public CompletionStage<T> apply(T t, Throwable th) {
                if (th == null) {
                    return CompletionStage.this;
                }
                return CompletionStage.this.handleAsync(new BiFunction<T, Throwable, CompletionStage<T>>() { // from class: java8.util.concurrent.CompletionStages.5.1
                    /* renamed from: a */
                    public CompletionStage<T> apply(T t2, Throwable th2) {
                        return (CompletionStage) function.apply(th2);
                    }
                }, executor).thenCompose(Functions.identity());
            }
        }).thenCompose(Functions.identity());
    }

    private CompletionStages() {
    }
}
