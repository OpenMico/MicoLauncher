package java8.util.stream;

import java8.util.stream.Sink;

/* compiled from: SinkDefaults.java */
/* loaded from: classes5.dex */
final class gj {

    /* compiled from: SinkDefaults.java */
    /* loaded from: classes5.dex */
    static final class b {
        /* JADX INFO: Access modifiers changed from: package-private */
        public static void a(Sink.OfInt ofInt, Integer num) {
            ofInt.accept(num.intValue());
        }
    }

    /* compiled from: SinkDefaults.java */
    /* loaded from: classes5.dex */
    static final class c {
        /* JADX INFO: Access modifiers changed from: package-private */
        public static void a(Sink.OfLong ofLong, Long l) {
            ofLong.accept(l.longValue());
        }
    }

    /* compiled from: SinkDefaults.java */
    /* loaded from: classes5.dex */
    static final class a {
        /* JADX INFO: Access modifiers changed from: package-private */
        public static void a(Sink.OfDouble ofDouble, Double d) {
            ofDouble.accept(d.doubleValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> void a() {
        throw new IllegalStateException("called wrong accept method");
    }
}
