package java8.util.stream;

import java8.util.Spliterator;
import java8.util.function.Consumer;
import java8.util.function.IntFunction;
import java8.util.stream.Node;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PipelineHelper.java */
/* loaded from: classes5.dex */
public abstract class gb<P_OUT> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <P_IN> long a(Spliterator<P_IN> spliterator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <P_IN, S extends Consumer<P_OUT>> S a(S s, Spliterator<P_IN> spliterator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Node.Builder<P_OUT> a(long j, IntFunction<P_OUT[]> intFunction);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <P_IN> Node<P_OUT> a(Spliterator<P_IN> spliterator, boolean z, IntFunction<P_OUT[]> intFunction);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <P_IN> Sink<P_IN> a(Consumer<P_OUT> consumer);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <P_IN> Sink<P_IN> a(Sink<P_OUT> sink);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <P_IN, S extends Sink<P_OUT>> S a(S s, Spliterator<P_IN> spliterator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <P_IN> Spliterator<P_OUT> b(Spliterator<P_IN> spliterator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract gq b();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <P_IN> void b(Sink<P_IN> sink, Spliterator<P_IN> spliterator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int c();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <P_IN> boolean c(Sink<P_IN> sink, Spliterator<P_IN> spliterator);
}
