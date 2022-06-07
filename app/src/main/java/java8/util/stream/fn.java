package java8.util.stream;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java8.util.J8Arrays;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.concurrent.CountedCompleter;
import java8.util.function.BinaryOperator;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.IntFunction;
import java8.util.function.LongConsumer;
import java8.util.function.LongFunction;
import java8.util.stream.Node;
import java8.util.stream.Sink;
import java8.util.stream.gj;
import java8.util.stream.gn;

/* compiled from: Nodes.java */
/* loaded from: classes5.dex */
public final class fn {
    private static final Node a = new i.d();
    private static final Node.OfInt b = new i.b();
    private static final Node.OfLong c = new i.c();
    private static final Node.OfDouble d = new i.a();
    private static final int[] e = new int[0];
    private static final long[] f = new long[0];
    private static final double[] g = new double[0];

    public static /* synthetic */ void a(Object obj) {
    }

    static <T> int b() {
        return 0;
    }

    static <T> gq a() {
        return gq.REFERENCE;
    }

    static <T> Node<T> c() {
        throw new IndexOutOfBoundsException();
    }

    static <T> Node<T> a(Node<T> node, long j2, long j3, IntFunction<T[]> intFunction) {
        if (j2 == 0 && j3 == node.a()) {
            return node;
        }
        Spliterator<T> f_ = node.f_();
        long j4 = j3 - j2;
        Node.Builder a2 = a(j4, intFunction);
        a2.begin(j4);
        for (int i2 = 0; i2 < j2 && f_.tryAdvance(fo.a()); i2++) {
        }
        if (j3 == node.a()) {
            f_.forEachRemaining(a2);
        } else {
            for (int i3 = 0; i3 < j4 && f_.tryAdvance(a2); i3++) {
            }
        }
        a2.end();
        return a2.build();
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class u {
        static <T, T_CONS, T_ARR, T_NODE extends Node.OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>> T_NODE a() {
            throw new IndexOutOfBoundsException();
        }

        static <T, T_CONS, T_ARR, T_NODE extends Node.OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>> T[] a(Node.OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE> ofPrimitive, IntFunction<T[]> intFunction) {
            if (ofPrimitive.a() < 2147483639) {
                T[] apply = intFunction.apply((int) ofPrimitive.a());
                ofPrimitive.a(apply, 0);
                return apply;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class r {
        public static /* synthetic */ void a(double d) {
        }

        /* JADX WARN: Type inference failed for: r7v3, types: [java8.util.stream.Node$OfDouble] */
        static Node.OfDouble a(Node.OfDouble ofDouble, long j, long j2, IntFunction<Double[]> intFunction) {
            if (j == 0 && j2 == ofDouble.a()) {
                return ofDouble;
            }
            long j3 = j2 - j;
            Spliterator.OfDouble spliterator = ofDouble.spliterator();
            Node.Builder.OfDouble c = fn.c(j3);
            c.begin(j3);
            for (int i = 0; i < j && spliterator.tryAdvance(fy.a()); i++) {
            }
            if (j2 == ofDouble.a()) {
                spliterator.forEachRemaining((DoubleConsumer) c);
            } else {
                for (int i2 = 0; i2 < j3 && spliterator.tryAdvance((DoubleConsumer) c); i2++) {
                }
            }
            c.end();
            return c.build();
        }

        static void a(Node.OfDouble ofDouble, Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                ofDouble.forEach((Node.OfDouble) ((DoubleConsumer) consumer));
            } else {
                ofDouble.spliterator().forEachRemaining(consumer);
            }
        }

        static void a(Node.OfDouble ofDouble, Double[] dArr, int i) {
            double[] asPrimitiveArray = ofDouble.asPrimitiveArray();
            for (int i2 = 0; i2 < asPrimitiveArray.length; i2++) {
                dArr[i + i2] = Double.valueOf(asPrimitiveArray[i2]);
            }
        }

        static double[] a(Node.OfDouble ofDouble, int i) {
            return new double[i];
        }

        static gq a() {
            return gq.DOUBLE_VALUE;
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class t {
        public static /* synthetic */ void a(long j) {
        }

        /* JADX WARN: Type inference failed for: r7v3, types: [java8.util.stream.Node$OfLong] */
        static Node.OfLong a(Node.OfLong ofLong, long j, long j2, IntFunction<Long[]> intFunction) {
            if (j == 0 && j2 == ofLong.a()) {
                return ofLong;
            }
            long j3 = j2 - j;
            Spliterator.OfLong spliterator = ofLong.spliterator();
            Node.Builder.OfLong b = fn.b(j3);
            b.begin(j3);
            for (int i = 0; i < j && spliterator.tryAdvance(ga.a()); i++) {
            }
            if (j2 == ofLong.a()) {
                spliterator.forEachRemaining((LongConsumer) b);
            } else {
                for (int i2 = 0; i2 < j3 && spliterator.tryAdvance((LongConsumer) b); i2++) {
                }
            }
            b.end();
            return b.build();
        }

        static void a(Node.OfLong ofLong, Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                ofLong.forEach((Node.OfLong) ((LongConsumer) consumer));
            } else {
                ofLong.spliterator().forEachRemaining(consumer);
            }
        }

        static void a(Node.OfLong ofLong, Long[] lArr, int i) {
            long[] asPrimitiveArray = ofLong.asPrimitiveArray();
            for (int i2 = 0; i2 < asPrimitiveArray.length; i2++) {
                lArr[i + i2] = Long.valueOf(asPrimitiveArray[i2]);
            }
        }

        static long[] a(Node.OfLong ofLong, int i) {
            return new long[i];
        }

        static gq a() {
            return gq.LONG_VALUE;
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class s {
        public static /* synthetic */ void a(int i) {
        }

        /* JADX WARN: Type inference failed for: r7v3, types: [java8.util.stream.Node$OfInt] */
        static Node.OfInt a(Node.OfInt ofInt, long j, long j2, IntFunction<Integer[]> intFunction) {
            if (j == 0 && j2 == ofInt.a()) {
                return ofInt;
            }
            long j3 = j2 - j;
            Spliterator.OfInt spliterator = ofInt.spliterator();
            Node.Builder.OfInt a = fn.a(j3);
            a.begin(j3);
            for (int i = 0; i < j && spliterator.tryAdvance(fz.a()); i++) {
            }
            if (j2 == ofInt.a()) {
                spliterator.forEachRemaining((IntConsumer) a);
            } else {
                for (int i2 = 0; i2 < j3 && spliterator.tryAdvance((IntConsumer) a); i2++) {
                }
            }
            a.end();
            return a.build();
        }

        static void a(Node.OfInt ofInt, Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                ofInt.forEach((Node.OfInt) ((IntConsumer) consumer));
            } else {
                ofInt.spliterator().forEachRemaining(consumer);
            }
        }

        static void a(Node.OfInt ofInt, Integer[] numArr, int i) {
            int[] asPrimitiveArray = ofInt.asPrimitiveArray();
            for (int i2 = 0; i2 < asPrimitiveArray.length; i2++) {
                numArr[i + i2] = Integer.valueOf(asPrimitiveArray[i2]);
            }
        }

        static int[] a(Node.OfInt ofInt, int i) {
            return new int[i];
        }

        static gq a() {
            return gq.INT_VALUE;
        }
    }

    public static /* synthetic */ Object[] a(int i2) {
        return new Object[i2];
    }

    public static <T> IntFunction<T[]> d() {
        return fp.a();
    }

    public static <T> Node<T> a(gq gqVar) {
        switch (gqVar) {
            case REFERENCE:
                return a;
            case INT_VALUE:
                return b;
            case LONG_VALUE:
                return c;
            case DOUBLE_VALUE:
                return d;
            default:
                throw new IllegalStateException("Unknown shape " + gqVar);
        }
    }

    public static <T> Node<T> a(gq gqVar, Node<T> node, Node<T> node2) {
        switch (gqVar) {
            case REFERENCE:
                return new e(node, node2);
            case INT_VALUE:
                return new e.b((Node.OfInt) node, (Node.OfInt) node2);
            case LONG_VALUE:
                return new e.c((Node.OfLong) node, (Node.OfLong) node2);
            case DOUBLE_VALUE:
                return new e.a((Node.OfDouble) node, (Node.OfDouble) node2);
            default:
                throw new IllegalStateException("Unknown shape " + gqVar);
        }
    }

    public static <T> Node<T> a(T[] tArr) {
        return new b(tArr);
    }

    public static <T> Node<T> a(Collection<T> collection) {
        return new c(collection);
    }

    public static <T> Node.Builder<T> a(long j2, IntFunction<T[]> intFunction) {
        if (j2 < 0 || j2 >= 2147483639) {
            return e();
        }
        return new j(j2, intFunction);
    }

    static <T> Node.Builder<T> e() {
        return new w();
    }

    public static Node.OfInt a(int[] iArr) {
        return new k(iArr);
    }

    public static Node.Builder.OfInt a(long j2) {
        if (j2 < 0 || j2 >= 2147483639) {
            return f();
        }
        return new l(j2);
    }

    static Node.Builder.OfInt f() {
        return new m();
    }

    public static Node.OfLong a(long[] jArr) {
        return new o(jArr);
    }

    public static Node.Builder.OfLong b(long j2) {
        if (j2 < 0 || j2 >= 2147483639) {
            return g();
        }
        return new p(j2);
    }

    static Node.Builder.OfLong g() {
        return new q();
    }

    public static Node.OfDouble a(double[] dArr) {
        return new f(dArr);
    }

    public static Node.Builder.OfDouble c(long j2) {
        if (j2 < 0 || j2 >= 2147483639) {
            return h();
        }
        return new g(j2);
    }

    static Node.Builder.OfDouble h() {
        return new h();
    }

    public static <P_IN, P_OUT> Node<P_OUT> a(gb<P_OUT> gbVar, Spliterator<P_IN> spliterator, boolean z, IntFunction<P_OUT[]> intFunction) {
        long a2 = gbVar.a(spliterator);
        if (a2 < 0 || !spliterator.hasCharacteristics(16384)) {
            Node<P_OUT> node = (Node) new d.C0358d(gbVar, intFunction, spliterator).invoke();
            return z ? a(node, intFunction) : node;
        } else if (a2 < 2147483639) {
            P_OUT[] apply = intFunction.apply((int) a2);
            new v.d(spliterator, gbVar, apply).invoke();
            return a((Object[]) apply);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static <P_IN> Node.OfInt a(gb<Integer> gbVar, Spliterator<P_IN> spliterator, boolean z) {
        long a2 = gbVar.a(spliterator);
        if (a2 < 0 || !spliterator.hasCharacteristics(16384)) {
            Node.OfInt ofInt = (Node.OfInt) new d.b(gbVar, spliterator).invoke();
            return z ? a(ofInt) : ofInt;
        } else if (a2 < 2147483639) {
            int[] iArr = new int[(int) a2];
            new v.b(spliterator, gbVar, iArr).invoke();
            return a(iArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static <P_IN> Node.OfLong b(gb<Long> gbVar, Spliterator<P_IN> spliterator, boolean z) {
        long a2 = gbVar.a(spliterator);
        if (a2 < 0 || !spliterator.hasCharacteristics(16384)) {
            Node.OfLong ofLong = (Node.OfLong) new d.c(gbVar, spliterator).invoke();
            return z ? a(ofLong) : ofLong;
        } else if (a2 < 2147483639) {
            long[] jArr = new long[(int) a2];
            new v.c(spliterator, gbVar, jArr).invoke();
            return a(jArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static <P_IN> Node.OfDouble c(gb<Double> gbVar, Spliterator<P_IN> spliterator, boolean z) {
        long a2 = gbVar.a(spliterator);
        if (a2 < 0 || !spliterator.hasCharacteristics(16384)) {
            Node.OfDouble ofDouble = (Node.OfDouble) new d.a(gbVar, spliterator).invoke();
            return z ? a(ofDouble) : ofDouble;
        } else if (a2 < 2147483639) {
            double[] dArr = new double[(int) a2];
            new v.a(spliterator, gbVar, dArr).invoke();
            return a(dArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static <T> Node<T> a(Node<T> node, IntFunction<T[]> intFunction) {
        if (node.c() <= 0) {
            return node;
        }
        long a2 = node.a();
        if (a2 < 2147483639) {
            T[] apply = intFunction.apply((int) a2);
            new x.e(node, apply, 0).invoke();
            return a((Object[]) apply);
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public static Node.OfInt a(Node.OfInt ofInt) {
        if (ofInt.c() <= 0) {
            return ofInt;
        }
        long a2 = ofInt.a();
        if (a2 < 2147483639) {
            int[] iArr = new int[(int) a2];
            new x.b(ofInt, iArr, 0).invoke();
            return a(iArr);
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public static Node.OfLong a(Node.OfLong ofLong) {
        if (ofLong.c() <= 0) {
            return ofLong;
        }
        long a2 = ofLong.a();
        if (a2 < 2147483639) {
            long[] jArr = new long[(int) a2];
            new x.c(ofLong, jArr, 0).invoke();
            return a(jArr);
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public static Node.OfDouble a(Node.OfDouble ofDouble) {
        if (ofDouble.c() <= 0) {
            return ofDouble;
        }
        long a2 = ofDouble.a();
        if (a2 < 2147483639) {
            double[] dArr = new double[(int) a2];
            new x.a(ofDouble, dArr, 0).invoke();
            return a(dArr);
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static abstract class i<T, T_ARR, T_CONS> implements Node<T> {
        @Override // java8.util.stream.Node
        public long a() {
            return 0L;
        }

        public void copyInto(T_ARR t_arr, int i) {
        }

        public void forEach(T_CONS t_cons) {
        }

        i() {
        }

        @Override // java8.util.stream.Node
        public T[] asArray(IntFunction<T[]> intFunction) {
            return intFunction.apply(0);
        }

        public gq getShape() {
            return fn.a();
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        @Override // java8.util.stream.Node
        public Node<T> a_(int i) {
            return fn.c();
        }

        @Override // java8.util.stream.Node
        public Node<T> a(long j, long j2, IntFunction<T[]> intFunction) {
            return fn.a(this, j, j2, intFunction);
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        private static class d<T> extends i<T, T[], Consumer<? super T>> {
            @Override // java8.util.stream.Node
            public /* synthetic */ void a(Object[] objArr, int i) {
                super.copyInto(objArr, i);
            }

            @Override // java8.util.stream.Node
            public /* bridge */ /* synthetic */ void forEach(Consumer consumer) {
                super.forEach((d<T>) consumer);
            }

            private d() {
            }

            @Override // java8.util.stream.Node
            public Spliterator<T> f_() {
                return Spliterators.emptySpliterator();
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        private static final class b extends i<Integer, int[], IntConsumer> implements Node.OfInt {
            @Override // java8.util.stream.fn.i, java8.util.stream.Node
            public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
                return truncate(j, j2, (IntFunction<Integer[]>) intFunction);
            }

            b() {
            }

            @Override // java8.util.stream.Node
            public void forEach(Consumer<? super Integer> consumer) {
                s.a(this, consumer);
            }

            /* renamed from: d */
            public Spliterator.OfInt spliterator() {
                return Spliterators.emptyIntSpliterator();
            }

            /* renamed from: b */
            public Node.OfInt getChild(int i) {
                return (Node.OfInt) u.a();
            }

            @Override // java8.util.stream.Node.OfInt, java8.util.stream.Node.OfPrimitive
            public Node.OfInt truncate(long j, long j2, IntFunction<Integer[]> intFunction) {
                return s.a(this, j, j2, intFunction);
            }

            /* renamed from: e */
            public int[] asPrimitiveArray() {
                return fn.e;
            }

            @Override // java8.util.stream.Node.OfInt
            /* renamed from: copyInto */
            public void a(Integer[] numArr, int i) {
                s.a(this, numArr, i);
            }

            @Override // java8.util.stream.Node.OfInt, java8.util.stream.Node.OfPrimitive
            public int[] newArray(int i) {
                return s.a(this, i);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        private static final class c extends i<Long, long[], LongConsumer> implements Node.OfLong {
            @Override // java8.util.stream.fn.i, java8.util.stream.Node
            public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
                return truncate(j, j2, (IntFunction<Long[]>) intFunction);
            }

            c() {
            }

            @Override // java8.util.stream.Node
            public void forEach(Consumer<? super Long> consumer) {
                t.a(this, consumer);
            }

            /* renamed from: d */
            public Spliterator.OfLong spliterator() {
                return Spliterators.emptyLongSpliterator();
            }

            /* renamed from: b */
            public Node.OfLong getChild(int i) {
                return (Node.OfLong) u.a();
            }

            @Override // java8.util.stream.Node.OfLong, java8.util.stream.Node.OfPrimitive
            public Node.OfLong truncate(long j, long j2, IntFunction<Long[]> intFunction) {
                return t.a(this, j, j2, intFunction);
            }

            /* renamed from: e */
            public long[] asPrimitiveArray() {
                return fn.f;
            }

            @Override // java8.util.stream.Node.OfLong
            /* renamed from: copyInto */
            public void a(Long[] lArr, int i) {
                t.a(this, lArr, i);
            }

            @Override // java8.util.stream.Node.OfLong, java8.util.stream.Node.OfPrimitive
            public long[] newArray(int i) {
                return t.a(this, i);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        private static final class a extends i<Double, double[], DoubleConsumer> implements Node.OfDouble {
            @Override // java8.util.stream.fn.i, java8.util.stream.Node
            public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
                return truncate(j, j2, (IntFunction<Double[]>) intFunction);
            }

            a() {
            }

            @Override // java8.util.stream.Node
            public void forEach(Consumer<? super Double> consumer) {
                r.a(this, consumer);
            }

            /* renamed from: b */
            public Node.OfDouble getChild(int i) {
                return (Node.OfDouble) u.a();
            }

            @Override // java8.util.stream.Node.OfDouble, java8.util.stream.Node.OfPrimitive
            public Node.OfDouble truncate(long j, long j2, IntFunction<Double[]> intFunction) {
                return r.a(this, j, j2, intFunction);
            }

            /* renamed from: d */
            public Spliterator.OfDouble spliterator() {
                return Spliterators.emptyDoubleSpliterator();
            }

            @Override // java8.util.stream.Node.OfDouble
            /* renamed from: copyInto */
            public void a(Double[] dArr, int i) {
                r.a(this, dArr, i);
            }

            /* renamed from: e */
            public double[] asPrimitiveArray() {
                return fn.g;
            }

            @Override // java8.util.stream.Node.OfDouble, java8.util.stream.Node.OfPrimitive
            public double[] newArray(int i) {
                return r.a(this, i);
            }
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static class b<T> implements Node<T> {
        final T[] a;
        int b;

        b(long j, IntFunction<T[]> intFunction) {
            if (j < 2147483639) {
                this.a = intFunction.apply((int) j);
                this.b = 0;
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        b(T[] tArr) {
            this.a = tArr;
            this.b = tArr.length;
        }

        @Override // java8.util.stream.Node
        public Spliterator<T> f_() {
            return J8Arrays.spliterator(this.a, 0, this.b);
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        @Override // java8.util.stream.Node
        public Node<T> a_(int i) {
            return fn.c();
        }

        @Override // java8.util.stream.Node
        public Node<T> a(long j, long j2, IntFunction<T[]> intFunction) {
            return fn.a(this, j, j2, intFunction);
        }

        @Override // java8.util.stream.Node
        public void a(T[] tArr, int i) {
            System.arraycopy(this.a, 0, tArr, i, this.b);
        }

        @Override // java8.util.stream.Node
        public T[] asArray(IntFunction<T[]> intFunction) {
            T[] tArr = this.a;
            if (tArr.length == this.b) {
                return tArr;
            }
            throw new IllegalStateException();
        }

        @Override // java8.util.stream.Node
        public long a() {
            return this.b;
        }

        @Override // java8.util.stream.Node
        public void forEach(Consumer<? super T> consumer) {
            for (int i = 0; i < this.b; i++) {
                consumer.accept((Object) this.a[i]);
            }
        }

        public String toString() {
            return String.format("ArrayNode[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class c<T> implements Node<T> {
        private final Collection<T> a;

        c(Collection<T> collection) {
            this.a = collection;
        }

        @Override // java8.util.stream.Node
        public Spliterator<T> f_() {
            return Spliterators.spliterator(this.a);
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        @Override // java8.util.stream.Node
        public Node<T> a_(int i) {
            return fn.c();
        }

        @Override // java8.util.stream.Node
        public Node<T> a(long j, long j2, IntFunction<T[]> intFunction) {
            return fn.a(this, j, j2, intFunction);
        }

        @Override // java8.util.stream.Node
        public void a(T[] tArr, int i) {
            for (T t : this.a) {
                i++;
                tArr[i] = t;
            }
        }

        @Override // java8.util.stream.Node
        public T[] asArray(IntFunction<T[]> intFunction) {
            Collection<T> collection = this.a;
            return (T[]) collection.toArray(intFunction.apply(collection.size()));
        }

        @Override // java8.util.stream.Node
        public long a() {
            return this.a.size();
        }

        @Override // java8.util.stream.Node
        public void forEach(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            for (T t : this.a) {
                consumer.accept(t);
            }
        }

        public String toString() {
            return String.format("CollectionNode[%d][%s]", Integer.valueOf(this.a.size()), this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static abstract class a<T, T_NODE extends Node<T>> implements Node<T> {
        protected final T_NODE a;
        protected final T_NODE b;
        private final long c;

        @Override // java8.util.stream.Node
        public int c() {
            return 2;
        }

        a(T_NODE t_node, T_NODE t_node2) {
            this.a = t_node;
            this.b = t_node2;
            this.c = t_node.a() + t_node2.a();
        }

        public gq getShape() {
            return fn.a();
        }

        @Override // java8.util.stream.Node
        public T_NODE a_(int i) {
            if (i == 0) {
                return this.a;
            }
            if (i == 1) {
                return this.b;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override // java8.util.stream.Node
        public long a() {
            return this.c;
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class e<T> extends a<T, Node<T>> implements Node<T> {
        public e(Node<T> node, Node<T> node2) {
            super(node, node2);
        }

        @Override // java8.util.stream.Node
        public Spliterator<T> f_() {
            return new n.e(this);
        }

        @Override // java8.util.stream.Node
        public void a(T[] tArr, int i) {
            Objects.requireNonNull(tArr);
            this.a.a(tArr, i);
            this.b.a(tArr, i + ((int) this.a.a()));
        }

        @Override // java8.util.stream.Node
        public T[] asArray(IntFunction<T[]> intFunction) {
            long a2 = a();
            if (a2 < 2147483639) {
                T[] apply = intFunction.apply((int) a2);
                a(apply, 0);
                return apply;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // java8.util.stream.Node
        public void forEach(Consumer<? super T> consumer) {
            this.a.forEach(consumer);
            this.b.forEach(consumer);
        }

        @Override // java8.util.stream.Node
        public Node<T> a(long j, long j2, IntFunction<T[]> intFunction) {
            if (j == 0 && j2 == a()) {
                return this;
            }
            long a2 = this.a.a();
            if (j >= a2) {
                return this.b.a(j - a2, j2 - a2, intFunction);
            }
            if (j2 <= a2) {
                return this.a.a(j, j2, intFunction);
            }
            return fn.a(getShape(), this.a.a(j, a2, intFunction), this.b.a(0L, j2 - a2, intFunction));
        }

        public String toString() {
            return a() < 32 ? String.format("ConcNode[%s.%s]", this.a, this.b) : String.format("ConcNode[size=%d]", Long.valueOf(a()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static abstract class d<E, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<E, T_CONS, T_SPLITR>, T_NODE extends Node.OfPrimitive<E, T_CONS, T_ARR, T_SPLITR, T_NODE>> extends a<E, T_NODE> implements Node.OfPrimitive<E, T_CONS, T_ARR, T_SPLITR, T_NODE> {
            @Override // java8.util.stream.Node.OfPrimitive
            public /* synthetic */ Node.OfPrimitive getChild(int i) {
                return (Node.OfPrimitive) super.a_(i);
            }

            d(T_NODE t_node, T_NODE t_node2) {
                super(t_node, t_node2);
            }

            @Override // java8.util.stream.Node.OfPrimitive
            public void forEach(T_CONS t_cons) {
                ((Node.OfPrimitive) this.a).forEach((Node.OfPrimitive) t_cons);
                ((Node.OfPrimitive) this.b).forEach((Node.OfPrimitive) t_cons);
            }

            @Override // java8.util.stream.Node.OfPrimitive
            public void copyInto(T_ARR t_arr, int i) {
                ((Node.OfPrimitive) this.a).copyInto(t_arr, i);
                ((Node.OfPrimitive) this.b).copyInto(t_arr, i + ((int) ((Node.OfPrimitive) this.a).a()));
            }

            @Override // java8.util.stream.Node.OfPrimitive
            public T_ARR asPrimitiveArray() {
                long a = a();
                if (a < 2147483639) {
                    T_ARR newArray = newArray((int) a);
                    copyInto(newArray, 0);
                    return newArray;
                }
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }

            @Override // java8.util.stream.Node
            public E[] asArray(IntFunction<E[]> intFunction) {
                return (E[]) u.a(this, intFunction);
            }

            public String toString() {
                return a() < 32 ? String.format("%s[%s.%s]", getClass().getName(), this.a, this.b) : String.format("%s[size=%d]", getClass().getName(), Long.valueOf(a()));
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class b extends d<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt> implements Node.OfInt {
            @Override // java8.util.stream.Node
            public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
                return truncate(j, j2, (IntFunction<Integer[]>) intFunction);
            }

            public b(Node.OfInt ofInt, Node.OfInt ofInt2) {
                super(ofInt, ofInt2);
            }

            @Override // java8.util.stream.Node
            public void forEach(Consumer<? super Integer> consumer) {
                s.a(this, consumer);
            }

            /* renamed from: d */
            public Spliterator.OfInt spliterator() {
                return new n.b(this);
            }

            @Override // java8.util.stream.Node.OfPrimitive
            public Node.OfInt truncate(long j, long j2, IntFunction<Integer[]> intFunction) {
                return s.a(this, j, j2, intFunction);
            }

            @Override // java8.util.stream.Node.OfInt
            /* renamed from: copyInto */
            public void a(Integer[] numArr, int i) {
                s.a(this, numArr, i);
            }

            @Override // java8.util.stream.Node.OfPrimitive
            public int[] newArray(int i) {
                return s.a(this, i);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class c extends d<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong> implements Node.OfLong {
            @Override // java8.util.stream.Node
            public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
                return truncate(j, j2, (IntFunction<Long[]>) intFunction);
            }

            public c(Node.OfLong ofLong, Node.OfLong ofLong2) {
                super(ofLong, ofLong2);
            }

            @Override // java8.util.stream.Node
            public void forEach(Consumer<? super Long> consumer) {
                t.a(this, consumer);
            }

            @Override // java8.util.stream.Node.OfPrimitive
            public Node.OfLong truncate(long j, long j2, IntFunction<Long[]> intFunction) {
                return t.a(this, j, j2, intFunction);
            }

            /* renamed from: d */
            public Spliterator.OfLong spliterator() {
                return new n.c(this);
            }

            @Override // java8.util.stream.Node.OfLong
            /* renamed from: copyInto */
            public void a(Long[] lArr, int i) {
                t.a(this, lArr, i);
            }

            @Override // java8.util.stream.Node.OfPrimitive
            public long[] newArray(int i) {
                return t.a(this, i);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class a extends d<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble> implements Node.OfDouble {
            @Override // java8.util.stream.Node
            public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
                return truncate(j, j2, (IntFunction<Double[]>) intFunction);
            }

            public a(Node.OfDouble ofDouble, Node.OfDouble ofDouble2) {
                super(ofDouble, ofDouble2);
            }

            @Override // java8.util.stream.Node
            public void forEach(Consumer<? super Double> consumer) {
                r.a(this, consumer);
            }

            @Override // java8.util.stream.Node.OfPrimitive
            public Node.OfDouble truncate(long j, long j2, IntFunction<Double[]> intFunction) {
                return r.a(this, j, j2, intFunction);
            }

            /* renamed from: d */
            public Spliterator.OfDouble spliterator() {
                return new n.a(this);
            }

            @Override // java8.util.stream.Node.OfDouble
            /* renamed from: copyInto */
            public void a(Double[] dArr, int i) {
                r.a(this, dArr, i);
            }

            @Override // java8.util.stream.Node.OfPrimitive
            public double[] newArray(int i) {
                return r.a(this, i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static abstract class n<T, S extends Spliterator<T>, N extends Node<T>> implements Spliterator<T> {
        N a;
        int b;
        S c;
        S d;
        Deque<N> e;

        @Override // java8.util.Spliterator
        public final int characteristics() {
            return 64;
        }

        n(N n) {
            this.a = n;
        }

        protected final Deque<N> a() {
            ArrayDeque arrayDeque = new ArrayDeque(8);
            int c2 = this.a.c();
            while (true) {
                c2--;
                if (c2 < this.b) {
                    return arrayDeque;
                }
                arrayDeque.addFirst(this.a.a_(c2));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        protected final N a(Deque<N> deque) {
            while (true) {
                N n = (N) ((Node) deque.pollFirst());
                if (n == null) {
                    return null;
                }
                if (n.c() != 0) {
                    for (int c2 = n.c() - 1; c2 >= 0; c2--) {
                        deque.addFirst(n.a_(c2));
                    }
                } else if (n.a() > 0) {
                    return n;
                }
            }
        }

        protected final boolean b() {
            if (this.a == null) {
                return false;
            }
            if (this.d != null) {
                return true;
            }
            S s = this.c;
            if (s == null) {
                this.e = a();
                N a2 = a(this.e);
                if (a2 != null) {
                    this.d = (S) a2.f_();
                    return true;
                }
                this.a = null;
                return false;
            }
            this.d = s;
            return true;
        }

        /* JADX WARN: Type inference failed for: r0v12, types: [S extends java8.util.Spliterator<T>, java8.util.Spliterator] */
        /* JADX WARN: Type inference failed for: r0v19, types: [S extends java8.util.Spliterator<T>, java8.util.Spliterator] */
        @Override // java8.util.Spliterator
        public final S trySplit() {
            N n = this.a;
            if (n == null || this.d != null) {
                return null;
            }
            S s = this.c;
            if (s != null) {
                return (S) s.trySplit();
            }
            if (this.b < n.c() - 1) {
                N n2 = this.a;
                int i = this.b;
                this.b = i + 1;
                return n2.a_(i).f_();
            }
            this.a = (N) this.a.a_(this.b);
            if (this.a.c() == 0) {
                this.c = (S) this.a.f_();
                return (S) this.c.trySplit();
            }
            this.b = 0;
            N n3 = this.a;
            int i2 = this.b;
            this.b = i2 + 1;
            return n3.a_(i2).f_();
        }

        @Override // java8.util.Spliterator
        public final long estimateSize() {
            long j = 0;
            if (this.a == null) {
                return 0L;
            }
            S s = this.c;
            if (s != null) {
                return s.estimateSize();
            }
            for (int i = this.b; i < this.a.c(); i++) {
                j += this.a.a_(i).a();
            }
            return j;
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        private static final class e<T> extends n<T, Spliterator<T>, Node<T>> {
            e(Node<T> node) {
                super(node);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Node<T> a;
                if (!b()) {
                    return false;
                }
                boolean tryAdvance = this.d.tryAdvance(consumer);
                if (!tryAdvance) {
                    if (this.c != null || (a = a(this.e)) == null) {
                        this.a = null;
                    } else {
                        this.d = a.f_();
                        return this.d.tryAdvance(consumer);
                    }
                }
                return tryAdvance;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super T> consumer) {
                if (this.a != null) {
                    if (this.d != null) {
                        do {
                        } while (tryAdvance(consumer));
                    } else if (this.c == null) {
                        Deque a = a();
                        while (true) {
                            Node a2 = a(a);
                            if (a2 != null) {
                                a2.forEach(consumer);
                            } else {
                                this.a = null;
                                return;
                            }
                        }
                    } else {
                        this.c.forEachRemaining(consumer);
                    }
                }
            }

            @Override // java8.util.Spliterator
            public long getExactSizeIfKnown() {
                return Spliterators.getExactSizeIfKnown(this);
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }

            @Override // java8.util.Spliterator
            public Comparator<? super T> getComparator() {
                return Spliterators.getComparator(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static abstract class d<T, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, N extends Node.OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, N>> extends n<T, T_SPLITR, N> implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            @Override // java8.util.stream.fn.n, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            d(N n) {
                super(n);
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public boolean tryAdvance(T_CONS t_cons) {
                Node.OfPrimitive ofPrimitive;
                if (!b()) {
                    return false;
                }
                boolean tryAdvance = ((Spliterator.OfPrimitive) this.d).tryAdvance((Spliterator.OfPrimitive) t_cons);
                if (!tryAdvance) {
                    if (this.c != null || (ofPrimitive = (Node.OfPrimitive) a(this.e)) == null) {
                        this.a = null;
                    } else {
                        this.d = ofPrimitive.spliterator();
                        return ((Spliterator.OfPrimitive) this.d).tryAdvance((Spliterator.OfPrimitive) t_cons);
                    }
                }
                return tryAdvance;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java8.util.Spliterator.OfPrimitive
            public void forEachRemaining(T_CONS t_cons) {
                if (this.a != null) {
                    if (this.d != null) {
                        do {
                        } while (tryAdvance((d<T, T_CONS, T_ARR, T_SPLITR, N>) t_cons));
                    } else if (this.c == null) {
                        Deque a = a();
                        while (true) {
                            Node.OfPrimitive ofPrimitive = (Node.OfPrimitive) a(a);
                            if (ofPrimitive != null) {
                                ofPrimitive.forEach((Node.OfPrimitive) t_cons);
                            } else {
                                this.a = null;
                                return;
                            }
                        }
                    } else {
                        ((Spliterator.OfPrimitive) this.c).forEachRemaining((Spliterator.OfPrimitive) t_cons);
                    }
                }
            }

            @Override // java8.util.Spliterator
            public long getExactSizeIfKnown() {
                return Spliterators.getExactSizeIfKnown(this);
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }

            @Override // java8.util.Spliterator
            public Comparator<? super T> getComparator() {
                return Spliterators.getComparator(this);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class b extends d<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt> implements Spliterator.OfInt {
            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((b) intConsumer);
            }

            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((b) intConsumer);
            }

            @Override // java8.util.stream.fn.n.d, java8.util.stream.fn.n, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            b(Node.OfInt ofInt) {
                super(ofInt);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Integer> consumer) {
                return Spliterators.OfInt.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Integer> consumer) {
                Spliterators.OfInt.forEachRemaining(this, consumer);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class c extends d<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong> implements Spliterator.OfLong {
            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((c) longConsumer);
            }

            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((c) longConsumer);
            }

            @Override // java8.util.stream.fn.n.d, java8.util.stream.fn.n, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            c(Node.OfLong ofLong) {
                super(ofLong);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Long> consumer) {
                return Spliterators.OfLong.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Long> consumer) {
                Spliterators.OfLong.forEachRemaining(this, consumer);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class a extends d<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble> implements Spliterator.OfDouble {
            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((a) doubleConsumer);
            }

            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((a) doubleConsumer);
            }

            @Override // java8.util.stream.fn.n.d, java8.util.stream.fn.n, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            a(Node.OfDouble ofDouble) {
                super(ofDouble);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Double> consumer) {
                return Spliterators.OfDouble.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Double> consumer) {
                Spliterators.OfDouble.forEachRemaining(this, consumer);
            }
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class j<T> extends b<T> implements Node.Builder<T> {
        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        j(long j, IntFunction<T[]> intFunction) {
            super(j, intFunction);
        }

        @Override // java8.util.stream.Node.Builder
        public Node<T> build() {
            if (this.b >= this.a.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.b), Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            if (j == this.a.length) {
                this.b = 0;
                return;
            }
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.function.Consumer
        public void accept(T t) {
            if (this.b < this.a.length) {
                Object[] objArr = this.a;
                int i = this.b;
                this.b = i + 1;
                objArr[i] = t;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink
        public void end() {
            if (this.b < this.a.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.b), Integer.valueOf(this.a.length)));
            }
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        @Override // java8.util.stream.fn.b
        public String toString() {
            return String.format("FixedNodeBuilder[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class w<T> extends gn<T> implements Node<T>, Node.Builder<T> {
        @Override // java8.util.stream.Node.Builder
        public Node<T> build() {
            return this;
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        w() {
        }

        @Override // java8.util.stream.gn, java8.util.stream.Node
        public Spliterator<T> f_() {
            return super.f_();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java8.util.stream.gn, java8.util.stream.Node
        public void forEach(Consumer<? super T> consumer) {
            super.forEach(consumer);
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        @Override // java8.util.stream.Node
        public Node<T> a_(int i) {
            return fn.c();
        }

        @Override // java8.util.stream.Node
        public Node<T> a(long j, long j2, IntFunction<T[]> intFunction) {
            return fn.a(this, j, j2, intFunction);
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            b();
            a(j);
        }

        @Override // java8.util.stream.gn, java8.util.function.Consumer
        public void accept(T t) {
            super.accept((w<T>) t);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        @Override // java8.util.stream.gn, java8.util.stream.Node
        public void a(T[] tArr, int i) {
            super.a(tArr, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java8.util.stream.gn, java8.util.stream.Node
        public T[] asArray(IntFunction<T[]> intFunction) {
            return (T[]) super.asArray(intFunction);
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static class k implements Node.OfInt {
        final int[] a;
        int b;

        @Override // java8.util.stream.Node
        public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
            return truncate(j, j2, (IntFunction<Integer[]>) intFunction);
        }

        k(long j) {
            if (j < 2147483639) {
                this.a = new int[(int) j];
                this.b = 0;
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        k(int[] iArr) {
            this.a = iArr;
            this.b = iArr.length;
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        @Override // java8.util.stream.Node.OfInt, java8.util.stream.Node
        public void forEach(Consumer<? super Integer> consumer) {
            s.a(this, consumer);
        }

        /* renamed from: b */
        public Node.OfInt getChild(int i) {
            return (Node.OfInt) u.a();
        }

        /* renamed from: d */
        public Spliterator.OfInt spliterator() {
            return J8Arrays.spliterator(this.a, 0, this.b);
        }

        @Override // java8.util.stream.Node.OfInt, java8.util.stream.Node.OfPrimitive
        public Node.OfInt truncate(long j, long j2, IntFunction<Integer[]> intFunction) {
            return s.a(this, j, j2, intFunction);
        }

        @Override // java8.util.stream.Node.OfInt
        /* renamed from: copyInto */
        public void a(Integer[] numArr, int i) {
            s.a(this, numArr, i);
        }

        /* renamed from: e */
        public int[] asPrimitiveArray() {
            int[] iArr = this.a;
            int length = iArr.length;
            int i = this.b;
            return length == i ? iArr : Arrays.copyOf(iArr, i);
        }

        /* renamed from: a */
        public Integer[] asArray(IntFunction<Integer[]> intFunction) {
            return (Integer[]) u.a(this, intFunction);
        }

        @Override // java8.util.stream.Node.OfInt, java8.util.stream.Node.OfPrimitive
        public int[] newArray(int i) {
            return s.a(this, i);
        }

        @Override // java8.util.stream.Node.OfInt
        public gq getShape() {
            return s.a();
        }

        /* renamed from: a */
        public void copyInto(int[] iArr, int i) {
            System.arraycopy(this.a, 0, iArr, i, this.b);
        }

        @Override // java8.util.stream.Node
        public long a() {
            return this.b;
        }

        /* renamed from: a */
        public void forEach(IntConsumer intConsumer) {
            for (int i = 0; i < this.b; i++) {
                intConsumer.accept(this.a[i]);
            }
        }

        public String toString() {
            return String.format("IntArrayNode[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static class o implements Node.OfLong {
        final long[] a;
        int b;

        @Override // java8.util.stream.Node
        public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
            return truncate(j, j2, (IntFunction<Long[]>) intFunction);
        }

        o(long j) {
            if (j < 2147483639) {
                this.a = new long[(int) j];
                this.b = 0;
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        o(long[] jArr) {
            this.a = jArr;
            this.b = jArr.length;
        }

        @Override // java8.util.stream.Node.OfLong, java8.util.stream.Node
        public void forEach(Consumer<? super Long> consumer) {
            t.a(this, consumer);
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        /* renamed from: b */
        public Node.OfLong getChild(int i) {
            return (Node.OfLong) u.a();
        }

        @Override // java8.util.stream.Node.OfLong
        /* renamed from: copyInto */
        public void a(Long[] lArr, int i) {
            t.a(this, lArr, i);
        }

        @Override // java8.util.stream.Node.OfLong, java8.util.stream.Node.OfPrimitive
        public Node.OfLong truncate(long j, long j2, IntFunction<Long[]> intFunction) {
            return t.a(this, j, j2, intFunction);
        }

        /* renamed from: d */
        public Spliterator.OfLong spliterator() {
            return J8Arrays.spliterator(this.a, 0, this.b);
        }

        /* renamed from: e */
        public long[] asPrimitiveArray() {
            long[] jArr = this.a;
            int length = jArr.length;
            int i = this.b;
            return length == i ? jArr : Arrays.copyOf(jArr, i);
        }

        /* renamed from: a */
        public Long[] asArray(IntFunction<Long[]> intFunction) {
            return (Long[]) u.a(this, intFunction);
        }

        @Override // java8.util.stream.Node.OfLong, java8.util.stream.Node.OfPrimitive
        public long[] newArray(int i) {
            return t.a(this, i);
        }

        @Override // java8.util.stream.Node.OfLong
        public gq getShape() {
            return t.a();
        }

        /* renamed from: a */
        public void copyInto(long[] jArr, int i) {
            System.arraycopy(this.a, 0, jArr, i, this.b);
        }

        @Override // java8.util.stream.Node
        public long a() {
            return this.b;
        }

        /* renamed from: a */
        public void forEach(LongConsumer longConsumer) {
            for (int i = 0; i < this.b; i++) {
                longConsumer.accept(this.a[i]);
            }
        }

        public String toString() {
            return String.format("LongArrayNode[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static class f implements Node.OfDouble {
        final double[] a;
        int b;

        @Override // java8.util.stream.Node
        public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
            return truncate(j, j2, (IntFunction<Double[]>) intFunction);
        }

        f(long j) {
            if (j < 2147483639) {
                this.a = new double[(int) j];
                this.b = 0;
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        f(double[] dArr) {
            this.a = dArr;
            this.b = dArr.length;
        }

        @Override // java8.util.stream.Node.OfDouble, java8.util.stream.Node
        public void forEach(Consumer<? super Double> consumer) {
            r.a(this, consumer);
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        /* renamed from: b */
        public Node.OfDouble getChild(int i) {
            return (Node.OfDouble) u.a();
        }

        @Override // java8.util.stream.Node.OfDouble, java8.util.stream.Node.OfPrimitive
        public Node.OfDouble truncate(long j, long j2, IntFunction<Double[]> intFunction) {
            return r.a(this, j, j2, intFunction);
        }

        /* renamed from: d */
        public Spliterator.OfDouble spliterator() {
            return J8Arrays.spliterator(this.a, 0, this.b);
        }

        /* renamed from: e */
        public double[] asPrimitiveArray() {
            double[] dArr = this.a;
            int length = dArr.length;
            int i = this.b;
            return length == i ? dArr : Arrays.copyOf(dArr, i);
        }

        /* renamed from: a */
        public Double[] asArray(IntFunction<Double[]> intFunction) {
            return (Double[]) u.a(this, intFunction);
        }

        @Override // java8.util.stream.Node.OfDouble, java8.util.stream.Node.OfPrimitive
        public double[] newArray(int i) {
            return r.a(this, i);
        }

        @Override // java8.util.stream.Node.OfDouble
        /* renamed from: copyInto */
        public void a(Double[] dArr, int i) {
            r.a(this, dArr, i);
        }

        @Override // java8.util.stream.Node.OfDouble
        public gq getShape() {
            return r.a();
        }

        /* renamed from: a */
        public void copyInto(double[] dArr, int i) {
            System.arraycopy(this.a, 0, dArr, i, this.b);
        }

        @Override // java8.util.stream.Node
        public long a() {
            return this.b;
        }

        /* renamed from: a */
        public void forEach(DoubleConsumer doubleConsumer) {
            for (int i = 0; i < this.b; i++) {
                doubleConsumer.accept(this.a[i]);
            }
        }

        public String toString() {
            return String.format("DoubleArrayNode[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class l extends k implements Node.Builder.OfInt {
        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        l(long j) {
            super(j);
        }

        @Override // java8.util.stream.Node.Builder.OfInt, java8.util.stream.Node.Builder
        /* renamed from: build */
        public Node<Integer> build2() {
            if (this.b >= this.a.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.b), Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            if (j == this.a.length) {
                this.b = 0;
                return;
            }
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            if (this.b < this.a.length) {
                int[] iArr = this.a;
                int i2 = this.b;
                this.b = i2 + 1;
                iArr[i2] = i;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink.OfInt
        public void accept(Integer num) {
            gj.b.a(this, num);
        }

        @Override // java8.util.stream.Sink
        public void end() {
            if (this.b < this.a.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.b), Integer.valueOf(this.a.length)));
            }
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        @Override // java8.util.stream.fn.k
        public String toString() {
            return String.format("IntFixedNodeBuilder[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class p extends o implements Node.Builder.OfLong {
        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        p(long j) {
            super(j);
        }

        @Override // java8.util.stream.Node.Builder.OfLong, java8.util.stream.Node.Builder
        /* renamed from: build */
        public Node<Long> build2() {
            if (this.b >= this.a.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.b), Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            if (j == this.a.length) {
                this.b = 0;
                return;
            }
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            if (this.b < this.a.length) {
                long[] jArr = this.a;
                int i = this.b;
                this.b = i + 1;
                jArr[i] = j;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink.OfLong
        public void accept(Long l) {
            gj.c.a(this, l);
        }

        @Override // java8.util.stream.Sink
        public void end() {
            if (this.b < this.a.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.b), Integer.valueOf(this.a.length)));
            }
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        @Override // java8.util.stream.fn.o
        public String toString() {
            return String.format("LongFixedNodeBuilder[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class g extends f implements Node.Builder.OfDouble {
        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        g(long j) {
            super(j);
        }

        @Override // java8.util.stream.Node.Builder.OfDouble, java8.util.stream.Node.Builder
        /* renamed from: build */
        public Node<Double> build2() {
            if (this.b >= this.a.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.b), Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            if (j == this.a.length) {
                this.b = 0;
                return;
            }
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            if (this.b < this.a.length) {
                double[] dArr = this.a;
                int i = this.b;
                this.b = i + 1;
                dArr[i] = d;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.a.length)));
        }

        @Override // java8.util.stream.Sink.OfDouble
        public void accept(Double d) {
            gj.a.a(this, d);
        }

        @Override // java8.util.stream.Sink
        public void end() {
            if (this.b < this.a.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.b), Integer.valueOf(this.a.length)));
            }
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.fn.f
        public String toString() {
            return String.format("DoubleFixedNodeBuilder[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class m extends gn.c implements Node.Builder.OfInt, Node.OfInt {
        @Override // java8.util.stream.Node.Builder.OfInt, java8.util.stream.Node.Builder
        /* renamed from: build */
        public Node<Integer> build2() {
            return this;
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        @Override // java8.util.stream.Node
        public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
            return truncate(j, j2, (IntFunction<Integer[]>) intFunction);
        }

        @Override // java8.util.stream.Node.OfPrimitive, java8.util.stream.Node
        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return a((IntFunction<Integer[]>) intFunction);
        }

        m() {
        }

        @Override // java8.util.stream.gn.c
        /* renamed from: d */
        public Spliterator.OfInt spliterator() {
            return super.d();
        }

        /* renamed from: a */
        public void forEach(IntConsumer intConsumer) {
            super.forEach((m) intConsumer);
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            b();
            b(j);
        }

        @Override // java8.util.stream.gn.c, java8.util.function.IntConsumer
        public void accept(int i) {
            super.accept(i);
        }

        @Override // java8.util.stream.Sink.OfInt
        public void accept(Integer num) {
            gj.b.a(this, num);
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        /* renamed from: a */
        public void copyInto(int[] iArr, int i) throws IndexOutOfBoundsException {
            super.copyInto((m) iArr, i);
        }

        @Override // java8.util.stream.Node.OfInt
        /* renamed from: copyInto */
        public void a(Integer[] numArr, int i) {
            s.a(this, numArr, i);
        }

        @Override // java8.util.stream.Node.OfInt, java8.util.stream.Node.OfPrimitive
        public Node.OfInt truncate(long j, long j2, IntFunction<Integer[]> intFunction) {
            return s.a(this, j, j2, intFunction);
        }

        /* renamed from: e */
        public int[] asPrimitiveArray() {
            return (int[]) super.asPrimitiveArray();
        }

        public Integer[] a(IntFunction<Integer[]> intFunction) {
            return (Integer[]) u.a(this, intFunction);
        }

        @Override // java8.util.stream.Node.OfInt
        public gq getShape() {
            return s.a();
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        /* renamed from: b */
        public Node.OfInt getChild(int i) {
            return (Node.OfInt) u.a();
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class q extends gn.d implements Node.Builder.OfLong, Node.OfLong {
        @Override // java8.util.stream.Node.Builder.OfLong, java8.util.stream.Node.Builder
        /* renamed from: build */
        public Node<Long> build2() {
            return this;
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        @Override // java8.util.stream.Node
        public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
            return truncate(j, j2, (IntFunction<Long[]>) intFunction);
        }

        @Override // java8.util.stream.Node.OfPrimitive, java8.util.stream.Node
        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return a((IntFunction<Long[]>) intFunction);
        }

        q() {
        }

        @Override // java8.util.stream.Node.OfLong, java8.util.stream.Node.OfPrimitive
        public Node.OfLong truncate(long j, long j2, IntFunction<Long[]> intFunction) {
            return t.a(this, j, j2, intFunction);
        }

        @Override // java8.util.stream.gn.d
        /* renamed from: d */
        public Spliterator.OfLong spliterator() {
            return super.d();
        }

        /* renamed from: a */
        public void forEach(LongConsumer longConsumer) {
            super.forEach((q) longConsumer);
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            b();
            b(j);
        }

        @Override // java8.util.stream.gn.d, java8.util.function.LongConsumer
        public void accept(long j) {
            super.accept(j);
        }

        @Override // java8.util.stream.Sink.OfLong
        public void accept(Long l) {
            gj.c.a(this, l);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        /* renamed from: a */
        public void copyInto(long[] jArr, int i) {
            super.copyInto((q) jArr, i);
        }

        @Override // java8.util.stream.Node.OfLong
        /* renamed from: copyInto */
        public void a(Long[] lArr, int i) {
            t.a(this, lArr, i);
        }

        /* renamed from: e */
        public long[] asPrimitiveArray() {
            return (long[]) super.asPrimitiveArray();
        }

        public Long[] a(IntFunction<Long[]> intFunction) {
            return (Long[]) u.a(this, intFunction);
        }

        @Override // java8.util.stream.Node.OfLong
        public gq getShape() {
            return t.a();
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        /* renamed from: b */
        public Node.OfLong getChild(int i) {
            return (Node.OfLong) u.a();
        }
    }

    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static final class h extends gn.b implements Node.Builder.OfDouble, Node.OfDouble {
        @Override // java8.util.stream.Node.Builder.OfDouble, java8.util.stream.Node.Builder
        /* renamed from: build */
        public Node<Double> build2() {
            return this;
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        @Override // java8.util.stream.Node
        public /* synthetic */ Node a(long j, long j2, IntFunction intFunction) {
            return truncate(j, j2, (IntFunction<Double[]>) intFunction);
        }

        @Override // java8.util.stream.Node.OfPrimitive, java8.util.stream.Node
        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return a((IntFunction<Double[]>) intFunction);
        }

        h() {
        }

        @Override // java8.util.stream.Node.OfDouble, java8.util.stream.Node.OfPrimitive
        public Node.OfDouble truncate(long j, long j2, IntFunction<Double[]> intFunction) {
            return r.a(this, j, j2, intFunction);
        }

        @Override // java8.util.stream.gn.b
        /* renamed from: d */
        public Spliterator.OfDouble spliterator() {
            return super.d();
        }

        /* renamed from: a */
        public void forEach(DoubleConsumer doubleConsumer) {
            super.forEach((h) doubleConsumer);
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            b();
            b(j);
        }

        @Override // java8.util.stream.gn.b, java8.util.function.DoubleConsumer
        public void accept(double d) {
            super.accept(d);
        }

        @Override // java8.util.stream.Sink.OfDouble
        public void accept(Double d) {
            gj.a.a(this, d);
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        /* renamed from: a */
        public void copyInto(double[] dArr, int i) {
            super.copyInto((h) dArr, i);
        }

        @Override // java8.util.stream.Node.OfDouble
        /* renamed from: copyInto */
        public void a(Double[] dArr, int i) {
            r.a(this, dArr, i);
        }

        /* renamed from: e */
        public double[] asPrimitiveArray() {
            return (double[]) super.asPrimitiveArray();
        }

        public Double[] a(IntFunction<Double[]> intFunction) {
            return (Double[]) u.a(this, intFunction);
        }

        @Override // java8.util.stream.Node
        public int c() {
            return fn.b();
        }

        /* renamed from: b */
        public Node.OfDouble getChild(int i) {
            return (Node.OfDouble) u.a();
        }

        @Override // java8.util.stream.Node.OfDouble
        public gq getShape() {
            return r.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static abstract class v<P_IN, P_OUT, T_SINK extends Sink<P_OUT>, K extends v<P_IN, P_OUT, T_SINK, K>> extends CountedCompleter<Void> implements Sink<P_OUT> {
        protected int fence;
        protected final gb<P_OUT> helper;
        protected int index;
        protected long length;
        protected long offset;
        protected final Spliterator<P_IN> spliterator;
        protected final long targetSize;

        abstract K a(Spliterator<P_IN> spliterator, long j, long j2);

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return false;
        }

        @Override // java8.util.stream.Sink
        public void end() {
        }

        v(Spliterator<P_IN> spliterator, gb<P_OUT> gbVar, int i) {
            this.spliterator = spliterator;
            this.helper = gbVar;
            this.targetSize = g.b(spliterator.estimateSize());
            this.offset = 0L;
            this.length = i;
        }

        v(K k, Spliterator<P_IN> spliterator, long j, long j2, int i) {
            super(k);
            this.spliterator = spliterator;
            this.helper = k.helper;
            this.targetSize = k.targetSize;
            this.offset = j;
            this.length = j2;
            if (j < 0 || j2 < 0 || (j + j2) - 1 >= i) {
                throw new IllegalArgumentException(String.format("offset and length interval [%d, %d + %d) is not within array size interval [0, %d)", Long.valueOf(j), Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i)));
            }
        }

        @Override // java8.util.concurrent.CountedCompleter
        public void compute() {
            Spliterator<P_IN> trySplit;
            Spliterator<P_IN> spliterator = this.spliterator;
            v<P_IN, P_OUT, T_SINK, K> vVar = this;
            while (spliterator.estimateSize() > vVar.targetSize && (trySplit = spliterator.trySplit()) != null) {
                vVar.setPendingCount(1);
                long estimateSize = trySplit.estimateSize();
                vVar.a(trySplit, vVar.offset, estimateSize).fork();
                vVar = vVar.a(spliterator, vVar.offset + estimateSize, vVar.length - estimateSize);
            }
            vVar.helper.a((gb<P_OUT>) vVar, (Spliterator) spliterator);
            vVar.propagateCompletion();
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            long j2 = this.length;
            if (j <= j2) {
                this.index = (int) this.offset;
                this.fence = this.index + ((int) j2);
                return;
            }
            throw new IllegalStateException("size passed to Sink.begin exceeds array length");
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d2) {
            gj.a();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class d<P_IN, P_OUT> extends v<P_IN, P_OUT, Sink<P_OUT>, d<P_IN, P_OUT>> implements Sink<P_OUT> {
            private final P_OUT[] array;

            d(Spliterator<P_IN> spliterator, gb<P_OUT> gbVar, P_OUT[] p_outArr) {
                super(spliterator, gbVar, p_outArr.length);
                this.array = p_outArr;
            }

            d(d<P_IN, P_OUT> dVar, Spliterator<P_IN> spliterator, long j, long j2) {
                super(dVar, spliterator, j, j2, dVar.array.length);
                this.array = dVar.array;
            }

            /* renamed from: b */
            public d<P_IN, P_OUT> a(Spliterator<P_IN> spliterator, long j, long j2) {
                return new d<>(this, spliterator, j, j2);
            }

            @Override // java8.util.function.Consumer
            public void accept(P_OUT p_out) {
                if (this.index < this.fence) {
                    P_OUT[] p_outArr = this.array;
                    int i = this.index;
                    this.index = i + 1;
                    p_outArr[i] = p_out;
                    return;
                }
                throw new IndexOutOfBoundsException(Integer.toString(this.index));
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class b<P_IN> extends v<P_IN, Integer, Sink.OfInt, b<P_IN>> implements Sink.OfInt {
            private final int[] array;

            b(Spliterator<P_IN> spliterator, gb<Integer> gbVar, int[] iArr) {
                super(spliterator, gbVar, iArr.length);
                this.array = iArr;
            }

            b(b<P_IN> bVar, Spliterator<P_IN> spliterator, long j, long j2) {
                super(bVar, spliterator, j, j2, bVar.array.length);
                this.array = bVar.array;
            }

            /* renamed from: b */
            public b<P_IN> a(Spliterator<P_IN> spliterator, long j, long j2) {
                return new b<>(this, spliterator, j, j2);
            }

            @Override // java8.util.stream.fn.v, java8.util.stream.Sink
            public void accept(int i) {
                if (this.index < this.fence) {
                    int[] iArr = this.array;
                    int i2 = this.index;
                    this.index = i2 + 1;
                    iArr[i2] = i;
                    return;
                }
                throw new IndexOutOfBoundsException(Integer.toString(this.index));
            }

            @Override // java8.util.stream.Sink.OfInt
            public void accept(Integer num) {
                gj.b.a(this, num);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class c<P_IN> extends v<P_IN, Long, Sink.OfLong, c<P_IN>> implements Sink.OfLong {
            private final long[] array;

            c(Spliterator<P_IN> spliterator, gb<Long> gbVar, long[] jArr) {
                super(spliterator, gbVar, jArr.length);
                this.array = jArr;
            }

            c(c<P_IN> cVar, Spliterator<P_IN> spliterator, long j, long j2) {
                super(cVar, spliterator, j, j2, cVar.array.length);
                this.array = cVar.array;
            }

            /* renamed from: b */
            public c<P_IN> a(Spliterator<P_IN> spliterator, long j, long j2) {
                return new c<>(this, spliterator, j, j2);
            }

            @Override // java8.util.stream.fn.v, java8.util.stream.Sink
            public void accept(long j) {
                if (this.index < this.fence) {
                    long[] jArr = this.array;
                    int i = this.index;
                    this.index = i + 1;
                    jArr[i] = j;
                    return;
                }
                throw new IndexOutOfBoundsException(Integer.toString(this.index));
            }

            @Override // java8.util.stream.Sink.OfLong
            public void accept(Long l) {
                gj.c.a(this, l);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class a<P_IN> extends v<P_IN, Double, Sink.OfDouble, a<P_IN>> implements Sink.OfDouble {
            private final double[] array;

            a(Spliterator<P_IN> spliterator, gb<Double> gbVar, double[] dArr) {
                super(spliterator, gbVar, dArr.length);
                this.array = dArr;
            }

            a(a<P_IN> aVar, Spliterator<P_IN> spliterator, long j, long j2) {
                super(aVar, spliterator, j, j2, aVar.array.length);
                this.array = aVar.array;
            }

            /* renamed from: b */
            public a<P_IN> a(Spliterator<P_IN> spliterator, long j, long j2) {
                return new a<>(this, spliterator, j, j2);
            }

            @Override // java8.util.stream.fn.v, java8.util.stream.Sink
            public void accept(double d) {
                if (this.index < this.fence) {
                    double[] dArr = this.array;
                    int i = this.index;
                    this.index = i + 1;
                    dArr[i] = d;
                    return;
                }
                throw new IndexOutOfBoundsException(Integer.toString(this.index));
            }

            @Override // java8.util.stream.Sink.OfDouble
            public void accept(Double d) {
                gj.a.a(this, d);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static abstract class x<T, T_NODE extends Node<T>, K extends x<T, T_NODE, K>> extends CountedCompleter<Void> {
        protected final T_NODE node;
        protected final int offset;

        abstract K a(int i, int i2);

        abstract void a();

        x(T_NODE t_node, int i) {
            this.node = t_node;
            this.offset = i;
        }

        x(K k, T_NODE t_node, int i) {
            super(k);
            this.node = t_node;
            this.offset = i;
        }

        @Override // java8.util.concurrent.CountedCompleter
        public void compute() {
            x<T, T_NODE, K> xVar = this;
            while (xVar.node.c() != 0) {
                xVar.setPendingCount(xVar.node.c() - 1);
                int i = 0;
                int i2 = 0;
                while (i < xVar.node.c() - 1) {
                    K a2 = xVar.a(i, xVar.offset + i2);
                    i2 = (int) (i2 + a2.node.a());
                    a2.fork();
                    i++;
                }
                xVar = xVar.a(i, xVar.offset + i2);
            }
            xVar.a();
            xVar.propagateCompletion();
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class e<T> extends x<T, Node<T>, e<T>> {
            private final T[] array;

            private e(Node<T> node, T[] tArr, int i) {
                super(node, i);
                this.array = tArr;
            }

            private e(e<T> eVar, Node<T> node, int i) {
                super(eVar, node, i);
                this.array = eVar.array;
            }

            /* renamed from: b */
            public e<T> a(int i, int i2) {
                return new e<>(this, this.node.a_(i), i2);
            }

            @Override // java8.util.stream.fn.x
            void a() {
                this.node.a(this.array, this.offset);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static class d<T, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_NODE extends Node.OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>> extends x<T, T_NODE, d<T, T_CONS, T_ARR, T_SPLITR, T_NODE>> {
            private final T_ARR array;

            private d(T_NODE t_node, T_ARR t_arr, int i) {
                super(t_node, i);
                this.array = t_arr;
            }

            private d(d<T, T_CONS, T_ARR, T_SPLITR, T_NODE> dVar, T_NODE t_node, int i) {
                super(dVar, t_node, i);
                this.array = dVar.array;
            }

            /* renamed from: b */
            public d<T, T_CONS, T_ARR, T_SPLITR, T_NODE> a(int i, int i2) {
                return new d<>(this, ((Node.OfPrimitive) this.node).getChild(i), i2);
            }

            @Override // java8.util.stream.fn.x
            void a() {
                ((Node.OfPrimitive) this.node).copyInto(this.array, this.offset);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class b extends d<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt> {
            private b(Node.OfInt ofInt, int[] iArr, int i) {
                super(ofInt, iArr, i);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class c extends d<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong> {
            private c(Node.OfLong ofLong, long[] jArr, int i) {
                super(ofLong, jArr, i);
            }
        }

        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class a extends d<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble> {
            private a(Node.OfDouble ofDouble, double[] dArr, int i) {
                super(ofDouble, dArr, i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Nodes.java */
    /* loaded from: classes5.dex */
    public static class d<P_IN, P_OUT, T_NODE extends Node<P_OUT>, T_BUILDER extends Node.Builder<P_OUT>> extends g<P_IN, P_OUT, T_NODE, d<P_IN, P_OUT, T_NODE, T_BUILDER>> {
        protected final LongFunction<T_BUILDER> builderFactory;
        protected final BinaryOperator<T_NODE> concFactory;
        protected final gb<P_OUT> helper;

        d(gb<P_OUT> gbVar, Spliterator<P_IN> spliterator, LongFunction<T_BUILDER> longFunction, BinaryOperator<T_NODE> binaryOperator) {
            super(gbVar, spliterator);
            this.helper = gbVar;
            this.builderFactory = longFunction;
            this.concFactory = binaryOperator;
        }

        d(d<P_IN, P_OUT, T_NODE, T_BUILDER> dVar, Spliterator<P_IN> spliterator) {
            super(dVar, spliterator);
            this.helper = dVar.helper;
            this.builderFactory = dVar.builderFactory;
            this.concFactory = dVar.concFactory;
        }

        /* renamed from: b */
        public d<P_IN, P_OUT, T_NODE, T_BUILDER> a(Spliterator<P_IN> spliterator) {
            return new d<>(this, spliterator);
        }

        /* renamed from: a */
        public T_NODE i() {
            return (T_NODE) ((Node.Builder) this.helper.a((gb<P_OUT>) this.builderFactory.apply(this.helper.a(this.spliterator)), this.spliterator)).build();
        }

        @Override // java8.util.stream.g, java8.util.concurrent.CountedCompleter
        public void onCompletion(CountedCompleter<?> countedCompleter) {
            if (!j()) {
                b((d<P_IN, P_OUT, T_NODE, T_BUILDER>) ((BinaryOperator<T_NODE>) this.concFactory).apply(((d) this.leftChild).b(), ((d) this.rightChild).b()));
            }
            super.onCompletion(countedCompleter);
        }

        /* compiled from: Nodes.java */
        /* renamed from: java8.util.stream.fn$d$d */
        /* loaded from: classes5.dex */
        public static final class C0358d<P_IN, P_OUT> extends d<P_IN, P_OUT, Node<P_OUT>, Node.Builder<P_OUT>> {
            @Override // java8.util.stream.fn.d, java8.util.stream.g
            protected /* synthetic */ g a(Spliterator spliterator) {
                return super.a(spliterator);
            }

            @Override // java8.util.stream.fn.d, java8.util.stream.g
            protected /* synthetic */ Object i() {
                return super.i();
            }

            C0358d(gb<P_OUT> gbVar, IntFunction<P_OUT[]> intFunction, Spliterator<P_IN> spliterator) {
                super(gbVar, spliterator, fw.a(intFunction), fx.a());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class b<P_IN> extends d<P_IN, Integer, Node.OfInt, Node.Builder.OfInt> {
            @Override // java8.util.stream.fn.d, java8.util.stream.g
            protected /* synthetic */ g a(Spliterator spliterator) {
                return super.a(spliterator);
            }

            @Override // java8.util.stream.fn.d, java8.util.stream.g
            protected /* synthetic */ Object i() {
                return super.i();
            }

            b(gb<Integer> gbVar, Spliterator<P_IN> spliterator) {
                super(gbVar, spliterator, fs.a(), ft.a());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class c<P_IN> extends d<P_IN, Long, Node.OfLong, Node.Builder.OfLong> {
            @Override // java8.util.stream.fn.d, java8.util.stream.g
            protected /* synthetic */ g a(Spliterator spliterator) {
                return super.a(spliterator);
            }

            @Override // java8.util.stream.fn.d, java8.util.stream.g
            protected /* synthetic */ Object i() {
                return super.i();
            }

            c(gb<Long> gbVar, Spliterator<P_IN> spliterator) {
                super(gbVar, spliterator, fu.a(), fv.a());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: Nodes.java */
        /* loaded from: classes5.dex */
        public static final class a<P_IN> extends d<P_IN, Double, Node.OfDouble, Node.Builder.OfDouble> {
            @Override // java8.util.stream.fn.d, java8.util.stream.g
            protected /* synthetic */ g a(Spliterator spliterator) {
                return super.a(spliterator);
            }

            @Override // java8.util.stream.fn.d, java8.util.stream.g
            protected /* synthetic */ Object i() {
                return super.i();
            }

            a(gb<Double> gbVar, Spliterator<P_IN> spliterator) {
                super(gbVar, spliterator, fq.a(), fr.a());
            }
        }
    }
}
