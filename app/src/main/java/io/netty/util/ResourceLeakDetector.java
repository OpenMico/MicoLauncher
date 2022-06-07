package io.netty.util;

import io.netty.util.internal.MathUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class ResourceLeakDetector<T> {
    private static final int b;
    private static Level c;
    private static final String[] p;
    private final ResourceLeakDetector<T>.a e;
    private final ResourceLeakDetector<T>.a f;
    private final ReferenceQueue<Object> g;
    private final ConcurrentMap<String, Boolean> h;
    private final String i;
    private final int j;
    private final int k;
    private final long l;
    private long m;
    private final AtomicBoolean n;
    private long o;
    private static final Level a = Level.SIMPLE;
    private static final InternalLogger d = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);

    /* loaded from: classes4.dex */
    public enum Level {
        DISABLED,
        SIMPLE,
        ADVANCED,
        PARANOID
    }

    static /* synthetic */ long c(ResourceLeakDetector resourceLeakDetector) {
        long j = resourceLeakDetector.m;
        resourceLeakDetector.m = 1 + j;
        return j;
    }

    static /* synthetic */ long d(ResourceLeakDetector resourceLeakDetector) {
        long j = resourceLeakDetector.m;
        resourceLeakDetector.m = j - 1;
        return j;
    }

    static {
        boolean z = false;
        if (SystemPropertyUtil.get("io.netty.noResourceLeakDetection") != null) {
            z = SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
            d.debug("-Dio.netty.noResourceLeakDetection: {}", Boolean.valueOf(z));
            d.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", "io.netty.leakDetection.level", a.name().toLowerCase());
        }
        String upperCase = SystemPropertyUtil.get("io.netty.leakDetection.level", SystemPropertyUtil.get("io.netty.leakDetectionLevel", (z ? Level.DISABLED : a).name()).trim().toUpperCase()).trim().toUpperCase();
        Level level = a;
        Iterator it = EnumSet.allOf(Level.class).iterator();
        while (it.hasNext()) {
            Level level2 = (Level) it.next();
            if (upperCase.equals(level2.name()) || upperCase.equals(String.valueOf(level2.ordinal()))) {
                level = level2;
            }
        }
        b = SystemPropertyUtil.getInt("io.netty.leakDetection.maxRecords", 4);
        c = level;
        if (d.isDebugEnabled()) {
            d.debug("-D{}: {}", "io.netty.leakDetection.level", level.name().toLowerCase());
            d.debug("-D{}: {}", "io.netty.leakDetection.maxRecords", Integer.valueOf(b));
        }
        p = new String[]{"io.netty.util.ReferenceCountUtil.touch(", "io.netty.buffer.AdvancedLeakAwareByteBuf.touch(", "io.netty.buffer.AbstractByteBufAllocator.toLeakAwareBuffer(", "io.netty.buffer.AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation("};
    }

    @Deprecated
    public static void setEnabled(boolean z) {
        setLevel(z ? Level.SIMPLE : Level.DISABLED);
    }

    public static boolean isEnabled() {
        return getLevel().ordinal() > Level.DISABLED.ordinal();
    }

    public static void setLevel(Level level) {
        if (level != null) {
            c = level;
            return;
        }
        throw new NullPointerException(com.xiaomi.onetrack.a.a.d);
    }

    public static Level getLevel() {
        return c;
    }

    public ResourceLeakDetector(Class<?> cls) {
        this(StringUtil.simpleClassName(cls));
    }

    public ResourceLeakDetector(String str) {
        this(str, 128, Long.MAX_VALUE);
    }

    public ResourceLeakDetector(Class<?> cls, int i, long j) {
        this(StringUtil.simpleClassName(cls), i, j);
    }

    public ResourceLeakDetector(String str, int i, long j) {
        this.e = new a(null);
        this.f = new a(null);
        this.g = new ReferenceQueue<>();
        this.h = PlatformDependent.newConcurrentHashMap();
        this.n = new AtomicBoolean();
        if (str == null) {
            throw new NullPointerException("resourceType");
        } else if (i <= 0) {
            throw new IllegalArgumentException("samplingInterval: " + i + " (expected: 1+)");
        } else if (j > 0) {
            this.i = str;
            this.j = MathUtil.findNextPositivePowerOfTwo(i);
            this.k = this.j - 1;
            this.l = j;
            ((a) this.e).f = this.f;
            ((a) this.f).e = this.e;
        } else {
            throw new IllegalArgumentException("maxActive: " + j + " (expected: 1+)");
        }
    }

    public ResourceLeak open(T t) {
        Level level = c;
        if (level == Level.DISABLED) {
            return null;
        }
        if (level.ordinal() < Level.PARANOID.ordinal()) {
            long j = this.o;
            this.o = 1 + j;
            if ((j & this.k) != 0) {
                return null;
            }
            a(level);
            return new a(t);
        }
        a(level);
        return new a(t);
    }

    private void a(Level level) {
        if (!d.isErrorEnabled()) {
            while (true) {
                a aVar = (a) this.g.poll();
                if (aVar != null) {
                    aVar.close();
                } else {
                    return;
                }
            }
        } else {
            if (this.m * (level == Level.PARANOID ? 1 : this.j) > this.l && this.n.compareAndSet(false, true)) {
                InternalLogger internalLogger = d;
                internalLogger.error("LEAK: You are creating too many " + this.i + " instances.  " + this.i + " is a shared resource that must be reused across the JVM,so that only a few instances are created.");
            }
            while (true) {
                a aVar2 = (a) this.g.poll();
                if (aVar2 != null) {
                    aVar2.clear();
                    if (aVar2.close()) {
                        String aVar3 = aVar2.toString();
                        if (this.h.putIfAbsent(aVar3, Boolean.TRUE) == null) {
                            if (aVar3.isEmpty()) {
                                d.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel() See http://netty.io/wiki/reference-counted-objects.html for more information.", this.i, "io.netty.leakDetection.level", Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName(this));
                            } else {
                                d.error("LEAK: {}.release() was not called before it's garbage-collected. See http://netty.io/wiki/reference-counted-objects.html for more information.{}", this.i, aVar3);
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a extends PhantomReference<Object> implements ResourceLeak {
        private final String b;
        private final Deque<String> c;
        private final AtomicBoolean d;
        private ResourceLeakDetector<T>.a e;
        private ResourceLeakDetector<T>.a f;
        private int g;

        a(Object obj) {
            super(obj, obj != null ? ResourceLeakDetector.this.g : null);
            this.c = new ArrayDeque();
            if (obj != null) {
                if (ResourceLeakDetector.getLevel().ordinal() >= Level.ADVANCED.ordinal()) {
                    this.b = ResourceLeakDetector.a(null, 3);
                } else {
                    this.b = null;
                }
                synchronized (ResourceLeakDetector.this.e) {
                    this.e = ResourceLeakDetector.this.e;
                    this.f = ResourceLeakDetector.this.e.f;
                    ResourceLeakDetector.this.e.f.e = this;
                    ResourceLeakDetector.this.e.f = this;
                    ResourceLeakDetector.c(ResourceLeakDetector.this);
                }
                this.d = new AtomicBoolean();
                return;
            }
            this.b = null;
            this.d = new AtomicBoolean(true);
        }

        @Override // io.netty.util.ResourceLeak
        public void record() {
            a((Object) null, 3);
        }

        @Override // io.netty.util.ResourceLeak
        public void record(Object obj) {
            a(obj, 3);
        }

        private void a(Object obj, int i) {
            if (this.b != null) {
                String a = ResourceLeakDetector.a(obj, i);
                synchronized (this.c) {
                    int size = this.c.size();
                    if (size == 0 || !this.c.getLast().equals(a)) {
                        this.c.add(a);
                    }
                    if (size > ResourceLeakDetector.b) {
                        this.c.removeFirst();
                        this.g++;
                    }
                }
            }
        }

        @Override // io.netty.util.ResourceLeak
        public boolean close() {
            if (!this.d.compareAndSet(false, true)) {
                return false;
            }
            synchronized (ResourceLeakDetector.this.e) {
                ResourceLeakDetector.d(ResourceLeakDetector.this);
                this.e.f = this.f;
                this.f.e = this.e;
                this.e = null;
                this.f = null;
            }
            return true;
        }

        public String toString() {
            Object[] array;
            int i;
            if (this.b == null) {
                return "";
            }
            synchronized (this.c) {
                array = this.c.toArray();
                i = this.g;
            }
            StringBuilder sb = new StringBuilder(16384);
            sb.append(StringUtil.NEWLINE);
            if (i > 0) {
                sb.append("WARNING: ");
                sb.append(i);
                sb.append(" leak records were discarded because the leak record count is limited to ");
                sb.append(ResourceLeakDetector.b);
                sb.append(". Use system property ");
                sb.append("io.netty.leakDetection.maxRecords");
                sb.append(" to increase the limit.");
                sb.append(StringUtil.NEWLINE);
            }
            sb.append("Recent access records: ");
            sb.append(array.length);
            sb.append(StringUtil.NEWLINE);
            if (array.length > 0) {
                for (int length = array.length - 1; length >= 0; length--) {
                    sb.append('#');
                    sb.append(length + 1);
                    sb.append(':');
                    sb.append(StringUtil.NEWLINE);
                    sb.append(array[length]);
                }
            }
            sb.append("Created at:");
            sb.append(StringUtil.NEWLINE);
            sb.append(this.b);
            sb.setLength(sb.length() - StringUtil.NEWLINE.length());
            return sb.toString();
        }
    }

    static String a(Object obj, int i) {
        boolean z;
        StringBuilder sb = new StringBuilder(4096);
        if (obj != null) {
            sb.append("\tHint: ");
            if (obj instanceof ResourceLeakHint) {
                sb.append(((ResourceLeakHint) obj).toHintString());
            } else {
                sb.append(obj);
            }
            sb.append(StringUtil.NEWLINE);
        }
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int i2 = i;
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (i2 > 0) {
                i2--;
            } else {
                String stackTraceElement2 = stackTraceElement.toString();
                String[] strArr = p;
                int length = strArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        z = false;
                        break;
                    } else if (stackTraceElement2.startsWith(strArr[i3])) {
                        z = true;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (!z) {
                    sb.append('\t');
                    sb.append(stackTraceElement2);
                    sb.append(StringUtil.NEWLINE);
                }
            }
        }
        return sb.toString();
    }
}
