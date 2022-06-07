package java8.util.stream;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java8.util.Maps;
import java8.util.Spliterator;

/* compiled from: StreamOpFlag.java */
/* loaded from: classes5.dex */
public enum gp {
    DISTINCT(0, a(b.SPLITERATOR).a(b.STREAM).c(b.OP)),
    SORTED(1, a(b.SPLITERATOR).a(b.STREAM).c(b.OP)),
    ORDERED(2, a(b.SPLITERATOR).a(b.STREAM).c(b.OP).b(b.TERMINAL_OP).b(b.UPSTREAM_TERMINAL_OP)),
    SIZED(3, a(b.SPLITERATOR).a(b.STREAM).b(b.OP)),
    SHORT_CIRCUIT(12, a(b.OP).a(b.TERMINAL_OP));
    
    static final int k;
    static final int l;
    static final int m;
    static final int n;
    static final int o;
    static final int p;
    static final int q;
    static final int r;
    static final int s;
    private static final int v;
    private static final int w;
    private final int bitPosition;
    private final int clear;
    private final Map<b, Integer> maskTable;
    private final int preserve;
    private final int set;
    static final int f = b(b.SPLITERATOR);
    static final int g = b(b.STREAM);
    static final int h = b(b.OP);
    static final int i = b(b.TERMINAL_OP);
    static final int j = b(b.UPSTREAM_TERMINAL_OP);
    private static final int u = a();
    static final int t = SHORT_CIRCUIT.set;

    /* compiled from: StreamOpFlag.java */
    /* loaded from: classes5.dex */
    public enum b {
        SPLITERATOR,
        STREAM,
        OP,
        TERMINAL_OP,
        UPSTREAM_TERMINAL_OP
    }

    static {
        int i2 = g;
        v = i2;
        w = i2 << 1;
        k = v | w;
        gp gpVar = DISTINCT;
        l = gpVar.set;
        m = gpVar.clear;
        gp gpVar2 = SORTED;
        n = gpVar2.set;
        o = gpVar2.clear;
        gp gpVar3 = ORDERED;
        p = gpVar3.set;
        q = gpVar3.clear;
        gp gpVar4 = SIZED;
        r = gpVar4.set;
        s = gpVar4.clear;
    }

    private static a a(b bVar) {
        return new a(new EnumMap(b.class)).a(bVar);
    }

    /* compiled from: StreamOpFlag.java */
    /* loaded from: classes5.dex */
    public static class a {
        final Map<b, Integer> a;

        a(Map<b, Integer> map) {
            this.a = map;
        }

        a a(b bVar, Integer num) {
            this.a.put(bVar, num);
            return this;
        }

        a a(b bVar) {
            return a(bVar, 1);
        }

        a b(b bVar) {
            return a(bVar, 2);
        }

        a c(b bVar) {
            return a(bVar, 3);
        }

        Map<b, Integer> a() {
            Map<b, Integer> map = this.a;
            if (map instanceof ConcurrentMap) {
                ConcurrentMap concurrentMap = (ConcurrentMap) map;
                for (b bVar : b.values()) {
                    concurrentMap.putIfAbsent(bVar, 0);
                }
                return concurrentMap;
            }
            for (b bVar2 : b.values()) {
                Maps.putIfAbsent(this.a, bVar2, 0);
            }
            return this.a;
        }
    }

    gp(int i2, a aVar) {
        this.maskTable = aVar.a();
        int i3 = i2 * 2;
        this.bitPosition = i3;
        this.set = 1 << i3;
        this.clear = 2 << i3;
        this.preserve = 3 << i3;
    }

    public boolean a(int i2) {
        return (i2 & this.preserve) == this.set;
    }

    public boolean b(int i2) {
        int i3 = this.preserve;
        return (i2 & i3) == i3;
    }

    private static int b(b bVar) {
        gp[] values = values();
        int i2 = 0;
        for (gp gpVar : values) {
            i2 |= gpVar.maskTable.get(bVar).intValue() << gpVar.bitPosition;
        }
        return i2;
    }

    private static int a() {
        int i2 = 0;
        for (gp gpVar : values()) {
            i2 |= gpVar.preserve;
        }
        return i2;
    }

    private static int f(int i2) {
        if (i2 == 0) {
            return u;
        }
        return ~(((i2 & w) >> 1) | ((v & i2) << 1) | i2);
    }

    public static int a(int i2, int i3) {
        return i2 | (i3 & f(i2));
    }

    public static int c(int i2) {
        return i2 & ((~i2) >> 1) & v;
    }

    public static int d(int i2) {
        return i2 & f;
    }

    public static int a(Spliterator<?> spliterator) {
        int characteristics = spliterator.characteristics();
        if ((characteristics & 4) == 0 || spliterator.getComparator() == null) {
            return f & characteristics;
        }
        return f & characteristics & (-5);
    }

    public static int e(int i2) {
        return i2 & f;
    }
}
