package kotlin.collections;

import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: AbstractMap.kt */
@Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\u0002\u001a\u00020\u0003H\u0096\u0002J\u000e\u0010\u0004\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"kotlin/collections/AbstractMap$values$1$iterator$1", "", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class AbstractMap$values$1$iterator$1 implements Iterator<V>, KMappedMarker {
    final /* synthetic */ Iterator a;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AbstractMap$values$1$iterator$1(Iterator it) {
        this.a = it;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.a.hasNext();
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [V, java.lang.Object] */
    @Override // java.util.Iterator
    public V next() {
        return ((Map.Entry) this.a.next()).getValue();
    }
}
