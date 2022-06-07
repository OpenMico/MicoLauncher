package java8.util.stream;

import java8.util.Spliterator;

/* compiled from: TerminalOp.java */
/* loaded from: classes5.dex */
interface hg<E_IN, R> {
    int a();

    <P_IN> R a(gb<E_IN> gbVar, Spliterator<P_IN> spliterator);

    <P_IN> R b(gb<E_IN> gbVar, Spliterator<P_IN> spliterator);
}
