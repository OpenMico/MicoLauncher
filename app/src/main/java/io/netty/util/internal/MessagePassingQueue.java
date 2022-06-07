package io.netty.util.internal;

/* loaded from: classes4.dex */
public interface MessagePassingQueue<T> {
    public static final int UNBOUNDED_CAPACITY = -1;

    /* loaded from: classes4.dex */
    public interface Consumer<T> {
        void accept(T t);
    }

    /* loaded from: classes4.dex */
    public interface ExitCondition {
        boolean keepRunning();
    }

    /* loaded from: classes4.dex */
    public interface Supplier<T> {
        T get();
    }

    /* loaded from: classes4.dex */
    public interface WaitStrategy {
        int idle(int i);
    }

    int capacity();

    void clear();

    int drain(Consumer<T> consumer);

    int drain(Consumer<T> consumer, int i);

    void drain(Consumer<T> consumer, WaitStrategy waitStrategy, ExitCondition exitCondition);

    int fill(Supplier<T> supplier);

    int fill(Supplier<T> supplier, int i);

    void fill(Supplier<T> supplier, WaitStrategy waitStrategy, ExitCondition exitCondition);

    boolean isEmpty();

    boolean offer(T t);

    T peek();

    T poll();

    boolean relaxedOffer(T t);

    T relaxedPeek();

    T relaxedPoll();

    int size();
}
