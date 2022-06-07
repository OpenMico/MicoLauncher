package kotlin.io;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReadWrite.kt */
@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0010(\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0006\u001a\u00020\u0004H\u0096\u0002J\t\u0010\u0007\u001a\u00020\u0002H\u0096\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"kotlin/io/LinesSequence$iterator$1", "", "", "done", "", "nextValue", "hasNext", "next", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class LinesSequence$iterator$1 implements Iterator<String>, KMappedMarker {
    final /* synthetic */ f a;
    private String b;
    private boolean c;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LinesSequence$iterator$1(f fVar) {
        this.a = fVar;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        BufferedReader bufferedReader;
        if (this.b == null && !this.c) {
            bufferedReader = this.a.a;
            this.b = bufferedReader.readLine();
            if (this.b == null) {
                this.c = true;
            }
        }
        return this.b != null;
    }

    @Override // java.util.Iterator
    @NotNull
    public String next() {
        if (hasNext()) {
            String str = this.b;
            this.b = null;
            Intrinsics.checkNotNull(str);
            return str;
        }
        throw new NoSuchElementException();
    }
}
