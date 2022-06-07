package kotlin.sequences;

import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
@Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001J\t\u0010\f\u001a\u00020\rH\u0096\u0002J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002H\u0096\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f"}, d2 = {"kotlin/sequences/IndexingSequence$iterator$1", "", "Lkotlin/collections/IndexedValue;", MiSoundBoxCommandExtras.INDEX, "", "getIndex", "()I", "setIndex", "(I)V", "iterator", "getIterator", "()Ljava/util/Iterator;", "hasNext", "", "next", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class IndexingSequence$iterator$1 implements Iterator<IndexedValue<? extends T>>, KMappedMarker {
    final /* synthetic */ IndexingSequence a;
    @NotNull
    private final Iterator<T> b;
    private int c;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public IndexingSequence$iterator$1(IndexingSequence indexingSequence) {
        Sequence sequence;
        this.a = indexingSequence;
        sequence = indexingSequence.a;
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

    @Override // java.util.Iterator
    @NotNull
    public IndexedValue<T> next() {
        int i = this.c;
        this.c = i + 1;
        if (i < 0) {
            CollectionsKt.throwIndexOverflow();
        }
        return new IndexedValue<>(i, this.b.next());
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.b.hasNext();
    }
}
