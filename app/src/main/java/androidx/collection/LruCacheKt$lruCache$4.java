package androidx.collection;

import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LruCache.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0001J\u0017\u0010\u0002\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0003\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0004J/\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00028\u00002\u0006\u0010\t\u001a\u00028\u00012\b\u0010\n\u001a\u0004\u0018\u00018\u0001H\u0014¢\u0006\u0002\u0010\u000bJ\u001d\u0010\f\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00028\u0001H\u0014¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"androidx/collection/LruCacheKt$lruCache$4", "Landroidx/collection/LruCache;", "create", "key", "(Ljava/lang/Object;)Ljava/lang/Object;", "entryRemoved", "", "evicted", "", "oldValue", "newValue", "(ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "sizeOf", "", b.p, "(Ljava/lang/Object;Ljava/lang/Object;)I", "collection-ktx"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class LruCacheKt$lruCache$4 extends LruCache<K, V> {
    final /* synthetic */ Function2 a;
    final /* synthetic */ Function1 b;
    final /* synthetic */ Function4 c;
    final /* synthetic */ int d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LruCacheKt$lruCache$4(Function2 function2, Function1 function1, Function4 function4, int i, int i2) {
        super(i2);
        this.a = function2;
        this.b = function1;
        this.c = function4;
        this.d = i;
    }

    @Override // androidx.collection.LruCache
    protected int sizeOf(@NotNull K key, @NotNull V value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        return ((Number) this.a.invoke(key, value)).intValue();
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [V, java.lang.Object] */
    @Override // androidx.collection.LruCache
    @Nullable
    protected V create(@NotNull K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.b.invoke(key);
    }

    @Override // androidx.collection.LruCache
    protected void entryRemoved(boolean z, @NotNull K key, @NotNull V oldValue, @Nullable V v) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(oldValue, "oldValue");
        this.c.invoke(Boolean.valueOf(z), key, oldValue, v);
    }
}
