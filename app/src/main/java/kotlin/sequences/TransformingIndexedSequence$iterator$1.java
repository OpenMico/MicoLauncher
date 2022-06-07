package kotlin.sequences;

import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\u000b\u001a\u00020\fH\u0096\u0002J\u000e\u0010\r\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"kotlin/sequences/TransformingIndexedSequence$iterator$1", "", MiSoundBoxCommandExtras.INDEX, "", "getIndex", "()I", "setIndex", "(I)V", "iterator", "getIterator", "()Ljava/util/Iterator;", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class TransformingIndexedSequence$iterator$1 implements Iterator<R>, KMappedMarker {
    final /* synthetic */ TransformingIndexedSequence a;
    @NotNull
    private final Iterator<T> b;
    private int c;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TransformingIndexedSequence$iterator$1(TransformingIndexedSequence transformingIndexedSequence) {
        Sequence sequence;
        this.a = transformingIndexedSequence;
        sequence = transformingIndexedSequence.a;
        this.b = sequence.iterator();
    }

    @NotNull
    public final Iterator<T> getIterator() {
        return this.b;
    }

    public final int getIndex() {
        return this.c;
    }

    public final void setIndex(int i) {
        this.c = i;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [R, java.lang.Object] */
    @Override // java.util.Iterator
    public R next() {
        Function2 function2;
        function2 = this.a.b;
        int i = this.c;
        this.c = i + 1;
        if (i < 0) {
            CollectionsKt.throwIndexOverflow();
        }
        return function2.invoke(Integer.valueOf(i), this.b.next());
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.b.hasNext();
    }
}
