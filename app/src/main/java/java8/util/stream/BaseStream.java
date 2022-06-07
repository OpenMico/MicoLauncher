package java8.util.stream;

import java.util.Iterator;
import java8.util.Spliterator;
import java8.util.stream.BaseStream;

/* loaded from: classes5.dex */
public interface BaseStream<T, S extends BaseStream<T, S>> {
    void close();

    boolean isParallel();

    Iterator<T> iterator();

    S onClose(Runnable runnable);

    S parallel();

    S sequential();

    Spliterator<T> spliterator();

    S unordered();
}
