package kotlinx.coroutines.debug.internal;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin._Assertions;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConcurrentWeakMap.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0003*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010*:\u0003&'(B\u0011\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\u000b\u001a\u00020\n2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\nH\u0016¢\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\nH\u0002¢\u0006\u0004\b\u000f\u0010\u000eJ\u001a\u0010\u0011\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0004\b\u0011\u0010\u0012J!\u0010\u0014\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00028\u0001H\u0016¢\u0006\u0004\b\u0014\u0010\u0015J#\u0010\u0016\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u00002\b\u0010\u0013\u001a\u0004\u0018\u00018\u0001H\u0002¢\u0006\u0004\b\u0016\u0010\u0015J\u0019\u0010\u0017\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\u0017\u0010\u0012J\r\u0010\u0018\u001a\u00020\n¢\u0006\u0004\b\u0018\u0010\u000eR(\u0010\u001d\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001a0\u00198V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00198V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001cR\u0016\u0010#\u001a\u00020 8V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u001e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$8\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010%¨\u0006)"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "weakRefQueue", "<init>", "(Z)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "w", "", "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", "clear", "()V", "decrementSize", "key", BluetoothConstants.GET, "(Ljava/lang/Object;)Ljava/lang/Object;", com.xiaomi.onetrack.api.b.p, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putSynchronized", "remove", "runWeakRefQueueCleaningLoopUntilInterrupted", "", "", "getEntries", "()Ljava/util/Set;", "entries", "getKeys", "keys", "", "getSize", "()I", "size", "Ljava/lang/ref/ReferenceQueue;", "Ljava/lang/ref/ReferenceQueue;", "Core", "Entry", "KeyValueSet", "kotlinx-coroutines-core", "Lkotlin/collections/AbstractMutableMap;"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ConcurrentWeakMap<K, V> extends AbstractMutableMap<K, V> {
    private static final /* synthetic */ AtomicIntegerFieldUpdater a = AtomicIntegerFieldUpdater.newUpdater(ConcurrentWeakMap.class, "_size");
    @NotNull
    private volatile /* synthetic */ int _size;
    @Nullable
    private final ReferenceQueue<K> b;
    @NotNull
    volatile /* synthetic */ Object core;

    /* compiled from: ConcurrentWeakMap.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002\"\b\b\u0001\u0010\u0003*\u00020\u00022\u0006\u0010\u0004\u001a\u0002H\u00012\u0006\u0010\u0005\u001a\u0002H\u0003H\n"}, d2 = {"<anonymous>", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "k", "<anonymous parameter 1>"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    static final class e extends Lambda implements Function2<K, V, K> {
        public static final e a = new e();

        e() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        @NotNull
        public final K invoke(@NotNull K k, @NotNull V v) {
            return k;
        }
    }

    public ConcurrentWeakMap() {
        this(false, 1, null);
    }

    public /* synthetic */ ConcurrentWeakMap(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    public ConcurrentWeakMap(boolean z) {
        this._size = 0;
        this.core = new a(16);
        this.b = z ? new ReferenceQueue<>() : null;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public int getSize() {
        return this._size;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a() {
        a.decrementAndGet(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    @Nullable
    public V get(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        return (V) ((a) this.core).a((a) obj);
    }

    @Override // kotlin.collections.AbstractMutableMap, java.util.AbstractMap, java.util.Map
    @Nullable
    public V put(@NotNull K k, @NotNull V v) {
        V v2 = (V) a.a((a) this.core, k, v, null, 4, null);
        if (v2 == ConcurrentWeakMapKt.access$getREHASH$p()) {
            v2 = a(k, v);
        }
        if (v2 == null) {
            a.incrementAndGet(this);
        }
        return v2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    @Nullable
    public V remove(@Nullable Object obj) {
        if (obj == 0) {
            return null;
        }
        V v = (V) a.a((a) this.core, obj, null, null, 4, null);
        if (v == ConcurrentWeakMapKt.access$getREHASH$p()) {
            v = a(obj, null);
        }
        if (v != null) {
            a.decrementAndGet(this);
        }
        return v;
    }

    private final synchronized V a(K k, V v) {
        V v2;
        a aVar = (a) this.core;
        while (true) {
            v2 = (V) a.a(aVar, k, v, null, 4, null);
            if (v2 == ConcurrentWeakMapKt.access$getREHASH$p()) {
                aVar = aVar.a();
                this.core = aVar;
            }
        }
        return v2;
    }

    @Override // kotlin.collections.AbstractMutableMap
    @NotNull
    public Set<K> getKeys() {
        return new c(e.a);
    }

    /* compiled from: ConcurrentWeakMap.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010'\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0004\"\b\b\u0001\u0010\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u0002H\u00022\u0006\u0010\u0006\u001a\u0002H\u0003H\n"}, d2 = {"<anonymous>", "", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "k", ai.aC}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    static final class d extends Lambda implements Function2<K, V, Map.Entry<K, V>> {
        public static final d a = new d();

        d() {
            super(2);
        }

        @NotNull
        /* renamed from: a */
        public final Map.Entry<K, V> invoke(@NotNull K k, @NotNull V v) {
            return new b(k, v);
        }
    }

    @Override // kotlin.collections.AbstractMutableMap
    @NotNull
    public Set<Map.Entry<K, V>> getEntries() {
        return new c(d.a);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        for (K k : keySet()) {
            remove(k);
        }
    }

    public final void runWeakRefQueueCleaningLoopUntilInterrupted() {
        if (this.b != null) {
            while (true) {
                try {
                    Reference<? extends K> remove = this.b.remove();
                    if (remove == null) {
                        break;
                    }
                    a((HashedWeakRef) remove);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.debug.internal.HashedWeakRef<*>");
        } else {
            throw new IllegalStateException("Must be created with weakRefQueue = true".toString());
        }
    }

    private final void a(HashedWeakRef<?> hashedWeakRef) {
        ((a) this.core).a(hashedWeakRef);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ConcurrentWeakMap.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0082\u0004\u0018\u00002\u00020\u0018:\u0001#B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0019\u0010\b\u001a\u00020\u00072\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u0004\u0018\u00018\u00012\u0006\u0010\n\u001a\u00028\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ3\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00020\u0013\"\u0004\b\u0002\u0010\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0011¢\u0006\u0004\b\u0014\u0010\u0015J3\u0010\u0019\u001a\u0004\u0018\u00010\u00182\u0006\u0010\n\u001a\u00028\u00002\b\u0010\u0016\u001a\u0004\u0018\u00018\u00012\u0010\b\u0002\u0010\u0017\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005¢\u0006\u0004\b\u0019\u0010\u001aJ\u001d\u0010\u001c\u001a\u00120\u0000R\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001b¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u001e\u0010\u001fR\u0016\u0010\u0002\u001a\u00020\u00018\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010 R\u0016\u0010!\u001a\u00020\u00018\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b!\u0010 R\u0016\u0010\"\u001a\u00020\u00018\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\"\u0010 ¨\u0006$"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", "", "allocated", "<init>", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;I)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "weakRef", "", "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", "key", "getImpl", "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", MiSoundBoxCommandExtras.INDEX, "(I)I", "E", "Lkotlin/Function2;", "factory", "", "keyValueIterator", "(Lkotlin/jvm/functions/Function2;)Ljava/util/Iterator;", com.xiaomi.onetrack.api.b.p, "weakKey0", "", "putImpl", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlinx/coroutines/debug/internal/HashedWeakRef;)Ljava/lang/Object;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "rehash", "()Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", "removeCleanedAt", "(I)V", "I", "shift", "threshold", "KeyValueIterator", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public final class a {
        private static final /* synthetic */ AtomicIntegerFieldUpdater g = AtomicIntegerFieldUpdater.newUpdater(a.class, "load");
        @NotNull
        /* synthetic */ AtomicReferenceArray a;
        @NotNull
        /* synthetic */ AtomicReferenceArray b;
        private final int d;
        private final int e;
        private final int f;
        @NotNull
        private volatile /* synthetic */ int load = 0;

        public a(int i) {
            this.d = i;
            this.e = Integer.numberOfLeadingZeros(this.d) + 1;
            int i2 = this.d;
            this.f = (i2 * 2) / 3;
            this.a = new AtomicReferenceArray(i2);
            this.b = new AtomicReferenceArray(this.d);
        }

        private final int a(int i) {
            return (i * (-1640531527)) >>> this.e;
        }

        @Nullable
        public final V a(@NotNull K k) {
            int a = a(k.hashCode());
            while (true) {
                HashedWeakRef hashedWeakRef = (HashedWeakRef) this.a.get(a);
                if (hashedWeakRef == null) {
                    return null;
                }
                Object obj = hashedWeakRef.get();
                if (Intrinsics.areEqual(k, obj)) {
                    V v = (V) this.b.get(a);
                    return v instanceof b ? (V) ((b) v).a : v;
                }
                if (obj == null) {
                    b(a);
                }
                if (a == 0) {
                    a = this.d;
                }
                a--;
            }
        }

        private final void b(int i) {
            Object obj;
            do {
                obj = this.b.get(i);
                if (obj == null || (obj instanceof b)) {
                    return;
                }
            } while (!this.b.compareAndSet(i, obj, null));
            ConcurrentWeakMap.this.a();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Object a(a aVar, Object obj, Object obj2, HashedWeakRef hashedWeakRef, int i, Object obj3) {
            if ((i & 4) != 0) {
                hashedWeakRef = null;
            }
            return aVar.a(obj, obj2, hashedWeakRef);
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x0056, code lost:
            r6 = r5.b.get(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x005e, code lost:
            if ((r6 instanceof kotlinx.coroutines.debug.internal.b) == false) goto L_0x0065;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0064, code lost:
            return kotlinx.coroutines.debug.internal.ConcurrentWeakMapKt.access$getREHASH$p();
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x006b, code lost:
            if (r5.b.compareAndSet(r0, r6, r7) == false) goto L_0x0056;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x006d, code lost:
            return r6;
         */
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object a(@org.jetbrains.annotations.NotNull K r6, @org.jetbrains.annotations.Nullable V r7, @org.jetbrains.annotations.Nullable kotlinx.coroutines.debug.internal.HashedWeakRef<K> r8) {
            /*
                r5 = this;
                int r0 = r6.hashCode()
                int r0 = r5.a(r0)
                r1 = 0
            L_0x0009:
                java.util.concurrent.atomic.AtomicReferenceArray r2 = r5.a
                java.lang.Object r2 = r2.get(r0)
                kotlinx.coroutines.debug.internal.HashedWeakRef r2 = (kotlinx.coroutines.debug.internal.HashedWeakRef) r2
                if (r2 != 0) goto L_0x0045
                r2 = 0
                if (r7 != 0) goto L_0x0017
                return r2
            L_0x0017:
                if (r1 != 0) goto L_0x002f
            L_0x0019:
                int r1 = r5.load
                int r3 = r5.f
                if (r1 < r3) goto L_0x0024
                kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.debug.internal.ConcurrentWeakMapKt.access$getREHASH$p()
                return r6
            L_0x0024:
                int r3 = r1 + 1
                java.util.concurrent.atomic.AtomicIntegerFieldUpdater r4 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap.a.g
                boolean r1 = r4.compareAndSet(r5, r1, r3)
                if (r1 == 0) goto L_0x0019
                r1 = 1
            L_0x002f:
                if (r8 != 0) goto L_0x003c
                kotlinx.coroutines.debug.internal.HashedWeakRef r8 = new kotlinx.coroutines.debug.internal.HashedWeakRef
                kotlinx.coroutines.debug.internal.ConcurrentWeakMap<K, V> r3 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap.this
                java.lang.ref.ReferenceQueue r3 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap.access$getWeakRefQueue$p(r3)
                r8.<init>(r6, r3)
            L_0x003c:
                java.util.concurrent.atomic.AtomicReferenceArray r3 = r5.a
                boolean r2 = r3.compareAndSet(r0, r2, r8)
                if (r2 == 0) goto L_0x0009
                goto L_0x0056
            L_0x0045:
                java.lang.Object r2 = r2.get()
                boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                if (r3 == 0) goto L_0x006e
                if (r1 == 0) goto L_0x0056
                java.util.concurrent.atomic.AtomicIntegerFieldUpdater r6 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap.a.g
                r6.decrementAndGet(r5)
            L_0x0056:
                java.util.concurrent.atomic.AtomicReferenceArray r6 = r5.b
                java.lang.Object r6 = r6.get(r0)
                boolean r8 = r6 instanceof kotlinx.coroutines.debug.internal.b
                if (r8 == 0) goto L_0x0065
                kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.debug.internal.ConcurrentWeakMapKt.access$getREHASH$p()
                return r6
            L_0x0065:
                java.util.concurrent.atomic.AtomicReferenceArray r8 = r5.b
                boolean r8 = r8.compareAndSet(r0, r6, r7)
                if (r8 == 0) goto L_0x0056
                return r6
            L_0x006e:
                if (r2 != 0) goto L_0x0073
                r5.b(r0)
            L_0x0073:
                if (r0 != 0) goto L_0x0077
                int r0 = r5.d
            L_0x0077:
                int r0 = r0 + (-1)
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.ConcurrentWeakMap.a.a(java.lang.Object, java.lang.Object, kotlinx.coroutines.debug.internal.HashedWeakRef):java.lang.Object");
        }

        /* JADX WARN: Multi-variable type inference failed */
        @NotNull
        public final ConcurrentWeakMap<K, V>.a a() {
            ConcurrentWeakMap<K, V>.a aVar;
            Object obj;
            loop0: while (true) {
                aVar = (ConcurrentWeakMap<K, V>.a) new a(Integer.highestOneBit(RangesKt.coerceAtLeast(ConcurrentWeakMap.this.size(), 4)) * 4);
                int i = this.d;
                if (i <= 0) {
                    break;
                }
                int i2 = 0;
                while (true) {
                    int i3 = i2 + 1;
                    HashedWeakRef hashedWeakRef = (HashedWeakRef) this.a.get(i2);
                    Object obj2 = hashedWeakRef == null ? null : hashedWeakRef.get();
                    if (hashedWeakRef != null && obj2 == null) {
                        b(i2);
                    }
                    while (true) {
                        obj = this.b.get(i2);
                        if (!(obj instanceof b)) {
                            if (this.b.compareAndSet(i2, obj, ConcurrentWeakMapKt.access$mark(obj))) {
                                break;
                            }
                        } else {
                            obj = ((b) obj).a;
                            break;
                        }
                    }
                    if (!(obj2 == null || obj == null)) {
                        Object a = aVar.a(obj2, obj, hashedWeakRef);
                        if (a == ConcurrentWeakMapKt.access$getREHASH$p()) {
                            break;
                        }
                        boolean z = a == null;
                        if (_Assertions.ENABLED && !z) {
                            throw new AssertionError("Assertion failed");
                        }
                    }
                    if (i3 >= i) {
                        break loop0;
                    }
                    i2 = i3;
                }
            }
            return aVar;
        }

        public final void a(@NotNull HashedWeakRef<?> hashedWeakRef) {
            int a = a(hashedWeakRef.hash);
            while (true) {
                HashedWeakRef<?> hashedWeakRef2 = (HashedWeakRef) this.a.get(a);
                if (hashedWeakRef2 != null) {
                    if (hashedWeakRef2 == hashedWeakRef) {
                        b(a);
                        return;
                    }
                    if (a == 0) {
                        a = this.d;
                    }
                    a--;
                } else {
                    return;
                }
            }
        }

        @NotNull
        public final <E> Iterator<E> a(@NotNull Function2<? super K, ? super V, ? extends E> function2) {
            return new KeyValueIterator(function2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: ConcurrentWeakMap.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\b\u0082\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0002J\t\u0010\r\u001a\u00020\u000eH\u0096\u0002J\u000e\u0010\u000f\u001a\u00028\u0002H\u0096\u0002¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0012H\u0016R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00028\u0000X\u0082.¢\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\n\u001a\u00028\u0001X\u0082.¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core$KeyValueIterator;", "E", "", "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;Lkotlin/jvm/functions/Function2;)V", MiSoundBoxCommandExtras.INDEX, "", "key", "Ljava/lang/Object;", com.xiaomi.onetrack.api.b.p, "findNext", "", "hasNext", "", "next", "()Ljava/lang/Object;", "remove", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
        /* renamed from: kotlinx.coroutines.debug.internal.ConcurrentWeakMap$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public final class KeyValueIterator<E> implements Iterator<E>, KMutableIterator {
            @NotNull
            private final Function2<K, V, E> b;
            private int c = -1;
            private K d;
            private V e;

            /* JADX WARN: Multi-variable type inference failed */
            public KeyValueIterator(Function2<? super K, ? super V, ? extends E> function2) {
                this.b = function2;
                b();
            }

            private final void b() {
                while (true) {
                    this.c++;
                    if (this.c < ((a) a.this).d) {
                        HashedWeakRef hashedWeakRef = (HashedWeakRef) a.this.a.get(this.c);
                        K k = hashedWeakRef == null ? null : (K) hashedWeakRef.get();
                        if (k != null) {
                            this.d = k;
                            V v = (V) a.this.b.get(this.c);
                            if (v instanceof b) {
                                v = (V) ((b) v).a;
                            }
                            if (v != null) {
                                this.e = v;
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.c < ((a) a.this).d;
            }

            @Override // java.util.Iterator
            public E next() {
                if (this.c < ((a) a.this).d) {
                    Function2<K, V, E> function2 = this.b;
                    K k = this.d;
                    if (k != null) {
                        V v = this.e;
                        if (v != null) {
                            E invoke = function2.invoke(k, v);
                            b();
                            return invoke;
                        }
                        Intrinsics.throwUninitializedPropertyAccessException(com.xiaomi.onetrack.api.b.p);
                        throw null;
                    }
                    Intrinsics.throwUninitializedPropertyAccessException("key");
                    throw null;
                }
                throw new NoSuchElementException();
            }

            @NotNull
            /* renamed from: a */
            public Void remove() {
                ConcurrentWeakMapKt.access$noImpl();
                throw new KotlinNothingValueException();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ConcurrentWeakMap.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010'\n\u0002\b\u000b\b\u0002\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0002\u0012\u0006\u0010\u0005\u001a\u00028\u0003¢\u0006\u0002\u0010\u0006J\u0015\u0010\u000b\u001a\u00028\u00032\u0006\u0010\f\u001a\u00028\u0003H\u0016¢\u0006\u0002\u0010\rR\u0016\u0010\u0004\u001a\u00028\u0002X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0005\u001a\u00028\u0003X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Entry;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "key", com.xiaomi.onetrack.api.b.p, "(Ljava/lang/Object;Ljava/lang/Object;)V", "getKey", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getValue", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class b<K, V> implements Map.Entry<K, V>, KMutableMap.Entry {
        private final K a;
        private final V b;

        public b(K k, V v) {
            this.a = k;
            this.b = v;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.a;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.b;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            ConcurrentWeakMapKt.access$noImpl();
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: ConcurrentWeakMap.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010)\n\u0000\b\u0082\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0015\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\rJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00020\u000fH\u0096\u0002R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$KeyValueSet;", "E", "Lkotlin/collections/AbstractMutableSet;", "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;Lkotlin/jvm/functions/Function2;)V", "size", "", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "iterator", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    private final class c<E> extends AbstractMutableSet<E> {
        @NotNull
        private final Function2<K, V, E> b;

        /* JADX WARN: Multi-variable type inference failed */
        public c(Function2<? super K, ? super V, ? extends E> function2) {
            this.b = function2;
        }

        @Override // kotlin.collections.AbstractMutableSet
        public int getSize() {
            return ConcurrentWeakMap.this.size();
        }

        @Override // kotlin.collections.AbstractMutableSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean add(E e) {
            ConcurrentWeakMapKt.access$noImpl();
            throw new KotlinNothingValueException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        @NotNull
        public Iterator<E> iterator() {
            return ((a) ConcurrentWeakMap.this.core).a((Function2) this.b);
        }
    }
}
