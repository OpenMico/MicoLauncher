package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Strings.kt */
@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\t\u0010\u0019\u001a\u00020\u001aH\u0096\u0002J\t\u0010\u001b\u001a\u00020\u0002H\u0096\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0002X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u001a\u0010\u0014\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\b¨\u0006\u001c"}, d2 = {"kotlin/text/DelimitedRangesSequence$iterator$1", "", "Lkotlin/ranges/IntRange;", "counter", "", "getCounter", "()I", "setCounter", "(I)V", "currentStartIndex", "getCurrentStartIndex", "setCurrentStartIndex", "nextItem", "getNextItem", "()Lkotlin/ranges/IntRange;", "setNextItem", "(Lkotlin/ranges/IntRange;)V", "nextSearchIndex", "getNextSearchIndex", "setNextSearchIndex", "nextState", "getNextState", "setNextState", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class DelimitedRangesSequence$iterator$1 implements Iterator<IntRange>, KMappedMarker {
    final /* synthetic */ c a;
    private int b = -1;
    private int c;
    private int d;
    @Nullable
    private IntRange e;
    private int f;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DelimitedRangesSequence$iterator$1(c cVar) {
        int i;
        CharSequence charSequence;
        this.a = cVar;
        i = cVar.b;
        charSequence = cVar.a;
        this.c = RangesKt.coerceIn(i, 0, charSequence.length());
        this.d = this.c;
    }

    public final int getNextState() {
        return this.b;
    }

    public final void setNextState(int i) {
        this.b = i;
    }

    public final int getCurrentStartIndex() {
        return this.c;
    }

    public final void setCurrentStartIndex(int i) {
        this.c = i;
    }

    public final int getNextSearchIndex() {
        return this.d;
    }

    public final void setNextSearchIndex(int i) {
        this.d = i;
    }

    @Nullable
    public final IntRange getNextItem() {
        return this.e;
    }

    public final void setNextItem(@Nullable IntRange intRange) {
        this.e = intRange;
    }

    public final int getCounter() {
        return this.f;
    }

    public final void setCounter(int i) {
        this.f = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0025, code lost:
        if (r0 < r4) goto L_0x0027;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void a() {
        /*
            r6 = this;
            int r0 = r6.d
            r1 = 0
            if (r0 >= 0) goto L_0x000e
            r6.b = r1
            r0 = 0
            kotlin.ranges.IntRange r0 = (kotlin.ranges.IntRange) r0
            r6.e = r0
            goto L_0x00a4
        L_0x000e:
            kotlin.text.c r0 = r6.a
            int r0 = kotlin.text.c.a(r0)
            r2 = -1
            r3 = 1
            if (r0 <= 0) goto L_0x0027
            int r0 = r6.f
            int r0 = r0 + r3
            r6.f = r0
            int r0 = r6.f
            kotlin.text.c r4 = r6.a
            int r4 = kotlin.text.c.a(r4)
            if (r0 >= r4) goto L_0x0035
        L_0x0027:
            int r0 = r6.d
            kotlin.text.c r4 = r6.a
            java.lang.CharSequence r4 = kotlin.text.c.b(r4)
            int r4 = r4.length()
            if (r0 <= r4) goto L_0x004b
        L_0x0035:
            int r0 = r6.c
            kotlin.ranges.IntRange r1 = new kotlin.ranges.IntRange
            kotlin.text.c r4 = r6.a
            java.lang.CharSequence r4 = kotlin.text.c.b(r4)
            int r4 = kotlin.text.StringsKt.getLastIndex(r4)
            r1.<init>(r0, r4)
            r6.e = r1
            r6.d = r2
            goto L_0x00a2
        L_0x004b:
            kotlin.text.c r0 = r6.a
            kotlin.jvm.functions.Function2 r0 = kotlin.text.c.c(r0)
            kotlin.text.c r4 = r6.a
            java.lang.CharSequence r4 = kotlin.text.c.b(r4)
            int r5 = r6.d
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Object r0 = r0.invoke(r4, r5)
            kotlin.Pair r0 = (kotlin.Pair) r0
            if (r0 != 0) goto L_0x007b
            int r0 = r6.c
            kotlin.ranges.IntRange r1 = new kotlin.ranges.IntRange
            kotlin.text.c r4 = r6.a
            java.lang.CharSequence r4 = kotlin.text.c.b(r4)
            int r4 = kotlin.text.StringsKt.getLastIndex(r4)
            r1.<init>(r0, r4)
            r6.e = r1
            r6.d = r2
            goto L_0x00a2
        L_0x007b:
            java.lang.Object r2 = r0.component1()
            java.lang.Number r2 = (java.lang.Number) r2
            int r2 = r2.intValue()
            java.lang.Object r0 = r0.component2()
            java.lang.Number r0 = (java.lang.Number) r0
            int r0 = r0.intValue()
            int r4 = r6.c
            kotlin.ranges.IntRange r4 = kotlin.ranges.RangesKt.until(r4, r2)
            r6.e = r4
            int r2 = r2 + r0
            r6.c = r2
            int r2 = r6.c
            if (r0 != 0) goto L_0x009f
            r1 = r3
        L_0x009f:
            int r2 = r2 + r1
            r6.d = r2
        L_0x00a2:
            r6.b = r3
        L_0x00a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.DelimitedRangesSequence$iterator$1.a():void");
    }

    @Override // java.util.Iterator
    @NotNull
    public IntRange next() {
        if (this.b == -1) {
            a();
        }
        if (this.b != 0) {
            IntRange intRange = this.e;
            if (intRange != null) {
                this.e = null;
                this.b = -1;
                return intRange;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.ranges.IntRange");
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.b == -1) {
            a();
        }
        return this.b == 1;
    }
}
